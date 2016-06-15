
package objects;

import framework.core.ResourceFile;
import framework.core.MessageBox;

import java.util.ArrayList;

import drawables.TeamObject;

public class Globals {
	private ArrayList<MatchResult> knockoutResult = new ArrayList<MatchResult>();
    private ArrayList<MatchResult> matchResult = new ArrayList<MatchResult>();
    private Tournament[] tournaments = new Tournament[Tournament.NUMBER_OF_GROUPS];

    private Integer dayThreeMatchCount = 0;
    private Integer dayOneMatchCount = 0;
    private Integer dayTwoMatchCount = 0;
    private Integer currentDay = 1;
    private Boolean help = false;
	private ResourceFile saveFile;

    private static Globals instance;

	private Globals() {		
		try {
			for(int i = 0; i < Tournament.NUMBER_OF_GROUPS; i++) {
				tournaments[i] = new Tournament(i);
			}
			
			readSaveFile();
		} catch (Exception e) {
			System.err.print(e.toString());
		}
	}

	public Boolean ShowHelp() {
		return(help);
	}

	public static Globals get() {
		if(instance == null) {
			instance = new Globals();
		} return instance;
	}
	
	public void reload() {
		instance = new Globals();
	}

	public Boolean Reset() {
		if(!knockoutResult.isEmpty() || !matchResult.isEmpty()) {
			knockoutResult.clear();
			matchResult.clear();
			
			dayThreeMatchCount = 0;
		    dayOneMatchCount = 0;
		    dayTwoMatchCount = 0;
			currentDay = 1;
		    
		    for(int i = 0; i < tournaments.length; i++) {
		    	tournaments[i].Reset();
		    }
		    
		    return true;
		}

		return false;
	}

	public Boolean ResetKoStage() {
		if(!knockoutResult.isEmpty()) {
			knockoutResult.clear();		    
		    return true;
		}

		return false;
	}

	public Boolean helpEnabled() {
		return help;
	}

	public void showHelp(boolean b) {
		help = b;
	}

	public int getCurrentDay() {
		return(currentDay);
	}

	public Tournament getGroup(int i) {
		return tournaments[i];
	}

	public int getTotalMatchesPlayed() {
		Integer count = 0;
		for(int i = 0; i < 4; i++) {
			for(int z = 0; z < tournaments.length; z++) {
				count += tournaments[z].getTeam(i).MatchesPlayed;
			}
		}
		return count;
	}

	public void changeMatchResult(Integer s1, TeamObject t1, Integer s2, TeamObject t2) {
		// get the vector size
		Integer vecSize = matchResult.size();
		// iterate through the vector looking for the match result
		for(int i = 0; i < vecSize; i++) {
			MatchResult r = matchResult.get(i);
			// set the new scores up
			if(r.teamTwo.compareToIgnoreCase(t2.getName()) == 0 && r.teamOne.compareToIgnoreCase(t1.getName()) == 0) {
				r.teamOneScore = s1;
				r.teamTwoScore = s2;
				r.Changed = true;
			}
		}
	}

	public void addMatchResult(Integer s1, TeamObject t1, Integer s2, TeamObject t2, int day) {
		switch(day) {
			case 1: if(++dayOneMatchCount >= 16) this.currentDay = 2; break;
			case 2: if(++dayTwoMatchCount >= 16) this.currentDay = 3; break;
			case 3: dayThreeMatchCount++; break;
		}

		MatchResult r = new MatchResult();
		r.MatchResultSecondaryID = t1.getName() + " vs " + t2.getName();
		r.MatchResultPrimaryID = t2.getName() + " vs " + t1.getName();
		r.teamOneScore = s1;
		r.teamTwoScore = s2;
		r.teamTwo = t2.getName();
		r.teamOne = t1.getName();
		matchResult.add(r);
	}

	public void addKnockOutResult(Integer s1, Integer s2, Team t1, Team t2, Integer t, Boolean r) {
		MatchResult result = new MatchResult();
		result.MatchResultSecondaryID = t1.getName() + " vs " + t2.getName();
		result.MatchResultPrimaryID = t2.getName() + " vs " + t1.getName();
		result.teamOneScore = s1;
		result.teamTwoScore = s2;
		result.teamOne = t1.getName();
		result.teamTwo = t2.getName();
		result.type = t;
		result.Changed = false;
		result.Reversed = r;
		knockoutResult.add(result);

		if(s1 < s2) {
			if(t1.qualifyForQuater && t1.qualifyForSemi && t1.qualifyForFinal) {
				t1.wonTournement = true;
			}
			
			if(t1.qualifyForQuater && t1.qualifyForSemi ) {
				t1.qualifyForFinal = true;
			} else if(t1.qualifyForQuater) {
				t1.qualifyForSemi = true;
			} else {
				t1.qualifyForQuater = true;
			} 
		}

		if(s2 < s1) {
			if(t2.qualifyForQuater && t2.qualifyForSemi && t2.qualifyForFinal) {
				t2.wonTournement = true;
			} else if(t2.qualifyForQuater && t2.qualifyForSemi) {
				t2.qualifyForFinal = true;
			} else if(t2.qualifyForQuater) {
				t2.qualifyForSemi = true;
			} else {
				t2.qualifyForQuater = true;
			}
		}
	}

	public ArrayList<MatchResult> getKnockOutResults() {
		return knockoutResult;
	}

	public ArrayList<MatchResult> getMatchResults() {
		return matchResult;
	}

	public ArrayList<MatchResult> getRoundOfSixteenResults() {
		// create a new vector 
		ArrayList<MatchResult> results = new ArrayList<MatchResult>();
		// populate the new vector with all the round of sixteen results
		for(MatchResult result : knockoutResult) {
			if(result.type == 0) {
				results.add(result);
			}
		}
		// return the new vector
		return results;
	}

	public ArrayList<MatchResult> getQuaterFinalResults() {
		ArrayList<MatchResult> results = new ArrayList<MatchResult>();
		for(MatchResult result : knockoutResult) {
			if(result.type == 1) {
				results.add(result);
			}
		}

		return results;
	}

	public ArrayList<MatchResult> getSemiFinalResults() {
		ArrayList<MatchResult> results = new ArrayList<MatchResult>();
		for(MatchResult result : knockoutResult) {
			if(result.type == 2) {
				results.add(result);
			}
		}
		return results;
	}

	public ArrayList<MatchResult> getFinalResults() {
		ArrayList<MatchResult> results = new ArrayList<MatchResult>();
		for(MatchResult result : knockoutResult) {
			if(result.type == 3) {
				results.add(result);
			}
		}

		return results;
	}

	private void readSaveFile() {
		try {
			saveFile = new ResourceFile("WorldCupSave.sv");

			if(!saveFile.Exists())	{
				help = true;
			} else {
				saveFile.PrepareToRead();

				if(saveFile.ReadLine().compareToIgnoreCase("0") == 0) {
					help = false;
				} else {
					help = true;
				}

				currentDay = Integer.parseInt(saveFile.ReadLine());

				dayOneMatchCount = Integer.parseInt(saveFile.ReadLine());
				dayTwoMatchCount = Integer.parseInt(saveFile.ReadLine());
				dayThreeMatchCount = Integer.parseInt(saveFile.ReadLine());

				Integer matchResultCount =  Integer.parseInt(saveFile.ReadLine());
				for(int i = 0; i < matchResultCount; i++) {
					MatchResult result = new MatchResult();

					result.Changed = Boolean.parseBoolean(saveFile.ReadLine());
					result.MatchResultPrimaryID = saveFile.ReadLine();
					result.MatchResultSecondaryID = saveFile.ReadLine();
					result.teamOne = saveFile.ReadLine();
					result.teamOneScore = Integer.parseInt(saveFile.ReadLine());
					result.teamTwo = saveFile.ReadLine();
					result.teamTwoScore = Integer.parseInt(saveFile.ReadLine());
					result.type = Integer.parseInt(saveFile.ReadLine());

					matchResult.add(result);

					for(int z = 0; z < tournaments.length; z++) {
						Team[] team = tournaments[z].getTeams();
						Integer second = -1;
						Integer first = -1;

						for(int b = 0; b < 4; b++) {
							if(result.teamTwo.compareToIgnoreCase(team[b].teamName) == 0) {	second = b; }
							if(result.teamOne.compareToIgnoreCase(team[b].teamName) == 0) {	first = b; }
						}

						if(first != -1 && second != -1) {
							team[second].MatchesPlayed++;
							team[first].MatchesPlayed++;
						}
					}
				}

				Integer koResultCount =  Integer.parseInt(saveFile.ReadLine());
				for(int i = 0; i < koResultCount; i++) {
					MatchResult result = new MatchResult();
					result.Changed = Boolean.parseBoolean(saveFile.ReadLine());
					result.MatchResultPrimaryID = saveFile.ReadLine();
					result.MatchResultSecondaryID = saveFile.ReadLine();
					result.teamOne = saveFile.ReadLine();
					result.teamOneScore = Integer.parseInt(saveFile.ReadLine());
					result.teamTwo = saveFile.ReadLine();
					result.teamTwoScore = Integer.parseInt(saveFile.ReadLine());
					result.type = Integer.parseInt(saveFile.ReadLine());
					result.Reversed = Boolean.parseBoolean(saveFile.ReadLine());
					knockoutResult.add(result);
				}
			}
		} catch(Exception e) {
			MessageBox message = new MessageBox();
			message.setTitle("Error");
			message.setMessage(e.getMessage());
			message.show(true);
		}
	}

	public void ReleaseGlobals() {
		ArrayList<String> fileData = new ArrayList<String>();

		if(help) {
			fileData.add(String.valueOf(1));
		} else {
			fileData.add(String.valueOf(0));
		}

		fileData.add(String.valueOf(currentDay));
		fileData.add(String.valueOf(dayOneMatchCount));
		fileData.add(String.valueOf(dayTwoMatchCount));
		fileData.add(String.valueOf(dayThreeMatchCount));
		fileData.add(String.valueOf(matchResult.size()));

		for(int i = 0; i < matchResult.size(); i++) {
			MatchResult result = matchResult.get(i);
			fileData.add(String.valueOf(result.Changed));
			fileData.add(String.valueOf(result.MatchResultPrimaryID));
			fileData.add(String.valueOf(result.MatchResultSecondaryID));
			fileData.add(String.valueOf(result.teamOne));
			fileData.add(String.valueOf(result.teamOneScore));
			fileData.add(String.valueOf(result.teamTwo));
			fileData.add(String.valueOf(result.teamTwoScore));
			fileData.add(String.valueOf(result.type));	
		}
		
		fileData.add(String.valueOf(knockoutResult.size()));

		for(int i = 0; i < knockoutResult.size(); i++) {
			MatchResult result = knockoutResult.get(i);
			// copy the values into the file
			fileData.add(String.valueOf(result.Changed));
			fileData.add(String.valueOf(result.MatchResultPrimaryID));
			fileData.add(String.valueOf(result.MatchResultSecondaryID));
			fileData.add(String.valueOf(result.teamOne));
			fileData.add(String.valueOf(result.teamOneScore));
			fileData.add(String.valueOf(result.teamTwo));
			fileData.add(String.valueOf(result.teamTwoScore));
			fileData.add(String.valueOf(result.type));
			fileData.add(String.valueOf(result.Reversed));
		}

		saveFile.Write(fileData);
		saveFile.Close();
	}

	public Match getDayOneMatch(Integer number) {
		Match match = new Match();
		switch(number) {
			case 0:  match.setTeams(tournaments[0].getTeam(0).getName(), tournaments[0].getTeam(1).getName()); break;
			case 1:  match.setTeams(tournaments[0].getTeam(2).getName(), tournaments[0].getTeam(3).getName()); break;
			case 2:  match.setTeams(tournaments[1].getTeam(0).getName(), tournaments[1].getTeam(1).getName()); break;
			case 3:  match.setTeams(tournaments[1].getTeam(2).getName(), tournaments[1].getTeam(3).getName()); break;
			case 4:  match.setTeams(tournaments[2].getTeam(0).getName(), tournaments[2].getTeam(1).getName()); break;
			case 5:  match.setTeams(tournaments[2].getTeam(2).getName(), tournaments[2].getTeam(3).getName()); break;
			case 6:  match.setTeams(tournaments[3].getTeam(0).getName(), tournaments[3].getTeam(1).getName()); break;
			case 7:  match.setTeams(tournaments[3].getTeam(2).getName(), tournaments[3].getTeam(3).getName()); break;
			case 8:  match.setTeams(tournaments[4].getTeam(0).getName(), tournaments[4].getTeam(1).getName()); break;
			case 9:  match.setTeams(tournaments[4].getTeam(2).getName(), tournaments[4].getTeam(3).getName()); break;
			case 10: match.setTeams(tournaments[5].getTeam(0).getName(), tournaments[5].getTeam(1).getName()); break;
			case 11: match.setTeams(tournaments[5].getTeam(2).getName(), tournaments[5].getTeam(3).getName()); break;
			case 12: match.setTeams(tournaments[6].getTeam(0).getName(), tournaments[6].getTeam(1).getName()); break;
			case 13: match.setTeams(tournaments[6].getTeam(2).getName(), tournaments[6].getTeam(3).getName()); break;
			case 14: match.setTeams(tournaments[7].getTeam(0).getName(), tournaments[7].getTeam(1).getName()); break;
			case 15: match.setTeams(tournaments[7].getTeam(2).getName(), tournaments[7].getTeam(3).getName()); break;
		};
		return(match);
	}

	public Match getDayTwoMatch(Integer number) {
		Match match = new Match();
		switch(number) {
			case 0:  match.setTeams(tournaments[0].getTeam(0).getName(), tournaments[0].getTeam(2).getName()); break;
			case 1:  match.setTeams(tournaments[0].getTeam(3).getName(), tournaments[0].getTeam(1).getName()); break;
			case 2:  match.setTeams(tournaments[1].getTeam(0).getName(), tournaments[1].getTeam(2).getName()); break;
			case 3:  match.setTeams(tournaments[1].getTeam(3).getName(), tournaments[1].getTeam(1).getName()); break;
			case 4:  match.setTeams(tournaments[2].getTeam(0).getName(), tournaments[2].getTeam(2).getName()); break;
			case 5:  match.setTeams(tournaments[2].getTeam(3).getName(), tournaments[2].getTeam(1).getName()); break;
			case 6:  match.setTeams(tournaments[3].getTeam(0).getName(), tournaments[3].getTeam(2).getName()); break;
			case 7:  match.setTeams(tournaments[3].getTeam(3).getName(), tournaments[3].getTeam(1).getName()); break;
			case 8:  match.setTeams(tournaments[4].getTeam(0).getName(), tournaments[4].getTeam(2).getName()); break;
			case 9:  match.setTeams(tournaments[4].getTeam(3).getName(), tournaments[4].getTeam(1).getName()); break;
			case 10: match.setTeams(tournaments[5].getTeam(0).getName(), tournaments[5].getTeam(2).getName()); break;
			case 11: match.setTeams(tournaments[5].getTeam(3).getName(), tournaments[5].getTeam(1).getName()); break;
			case 12: match.setTeams(tournaments[6].getTeam(0).getName(), tournaments[6].getTeam(2).getName()); break;
			case 13: match.setTeams(tournaments[6].getTeam(3).getName(), tournaments[6].getTeam(1).getName()); break;
			case 14: match.setTeams(tournaments[7].getTeam(0).getName(), tournaments[7].getTeam(2).getName()); break;
			case 15: match.setTeams(tournaments[7].getTeam(3).getName(), tournaments[7].getTeam(1).getName()); break;
		};
		return(match);
	}

	public Match getDayThreeMatch(Integer number) {
		Match match = new Match();
		switch(number) {
			case 0:  match.setTeams(tournaments[0].getTeam(3).getName(), tournaments[0].getTeam(0).getName()); break;
			case 1:  match.setTeams(tournaments[0].getTeam(1).getName(), tournaments[0].getTeam(2).getName()); break;
			case 2:  match.setTeams(tournaments[1].getTeam(3).getName(), tournaments[1].getTeam(0).getName()); break;
			case 3:  match.setTeams(tournaments[1].getTeam(1).getName(), tournaments[1].getTeam(2).getName()); break;
			case 4:  match.setTeams(tournaments[2].getTeam(3).getName(), tournaments[2].getTeam(0).getName()); break;
			case 5:  match.setTeams(tournaments[2].getTeam(1).getName(), tournaments[2].getTeam(2).getName()); break;
			case 6:  match.setTeams(tournaments[3].getTeam(3).getName(), tournaments[3].getTeam(0).getName()); break;
			case 7:  match.setTeams(tournaments[3].getTeam(1).getName(), tournaments[3].getTeam(2).getName()); break;
			case 8:  match.setTeams(tournaments[4].getTeam(3).getName(), tournaments[4].getTeam(0).getName()); break;
			case 9:  match.setTeams(tournaments[4].getTeam(1).getName(), tournaments[4].getTeam(2).getName()); break;
			case 10: match.setTeams(tournaments[5].getTeam(3).getName(), tournaments[5].getTeam(0).getName()); break;
			case 11: match.setTeams(tournaments[5].getTeam(1).getName(), tournaments[5].getTeam(2).getName()); break;
			case 12: match.setTeams(tournaments[6].getTeam(3).getName(), tournaments[6].getTeam(0).getName()); break;
			case 13: match.setTeams(tournaments[6].getTeam(1).getName(), tournaments[6].getTeam(2).getName()); break;
			case 14: match.setTeams(tournaments[7].getTeam(3).getName(), tournaments[7].getTeam(0).getName()); break;
			case 15: match.setTeams(tournaments[7].getTeam(1).getName(), tournaments[7].getTeam(2).getName()); break;
		};
		return(match);
	}

	public Team getWinner(String str) {
		Team winner = null;

		if(str.compareToIgnoreCase("A") == 0) {
			winner = tournaments[0].getWinner();
		} else if(str.compareToIgnoreCase("B") == 0) {
			winner = tournaments[1].getWinner();
		} else if(str.compareToIgnoreCase("C") == 0) {
			winner = tournaments[2].getWinner();
		} else if(str.compareToIgnoreCase("D") == 0) {
			winner = tournaments[3].getWinner();
		} else if(str.compareToIgnoreCase("E") == 0) {
			winner = tournaments[4].getWinner();
		} else if(str.compareToIgnoreCase("F") == 0) {
			winner = tournaments[5].getWinner();
		} else if(str.compareToIgnoreCase("G") == 0) {
			winner = tournaments[6].getWinner();
		} else {
			winner = tournaments[7].getWinner();
		}
		
		return winner;
	}
	public Team getRunnerUp(String str) {
		Team winner = null;

		if(str.compareToIgnoreCase("A") == 0) {
			winner = tournaments[0].getRunnerUp();
		} else if(str.compareToIgnoreCase("B") == 0) {
			winner = tournaments[1].getRunnerUp();
		} else if(str.compareToIgnoreCase("C") == 0) {
			winner = tournaments[2].getRunnerUp();
		} else if(str.compareToIgnoreCase("D") == 0) {
			winner = tournaments[3].getRunnerUp();
		} else if(str.compareToIgnoreCase("E") == 0) {
			winner = tournaments[4].getRunnerUp();
		} else if(str.compareToIgnoreCase("F") == 0) {
			winner = tournaments[5].getRunnerUp();
		} else if(str.compareToIgnoreCase("G") == 0) {
			winner = tournaments[6].getRunnerUp();
		} else {
			winner = tournaments[7].getRunnerUp();
		}
		
		return winner;
	}	
}
