package objects;

public class Team {
	public Integer matchesPlayed = -1;
	public Integer matchesLost = 0;
	public Integer matchesDrawn = 0;
	public Integer matchesWon = 0;
	public Integer goalsConceded = 0;
	public Integer goalDifference = 0;
	public Integer goalsScoted = 0;
	public Integer points = 0;
	public Boolean qualifyForQuater = false;
	public Boolean qualifyForFinal = false;
	public Boolean qualifyForSemi = false;
	public Boolean wonTournement = false;
	public String teamName;

	public Team(String name) {
		teamName = name;
	}

	public String getScore(int ID) {
		String value = "E";
		switch(ID) {
			case 0: value = String.valueOf(matchesPlayed); break;
			case 1: value = String.valueOf(matchesWon); break;
			case 2: value = String.valueOf(matchesDrawn); break;
			case 3: value = String.valueOf(matchesLost); break;
			case 4: value = String.valueOf(goalsScoted); break;
			case 5: value = String.valueOf(goalsConceded); break;
			case 6: value = String.valueOf(goalDifference); break;
			case 7: value = String.valueOf(points); break;
		
				default: break;
		}
		
		return value;
	}

	public void reset() {
		// set booleans to false
		qualifyForQuater = false;
		qualifyForFinal = false;
		qualifyForSemi = false;
		wonTournement = false;
		
		// set scores to zero
		goalsConceded = 0;
		goalDifference = 0;
		matchesPlayed = 0;
		matchesLost = 0;
		matchesDrawn = 0;
		goalsScoted = 0;
		matchesWon = 0;
		points = 0;
	}

	public String getScoreAsString(boolean matchPlayed) {
		// should we increment the match played counter
		if(matchPlayed) {
			matchesPlayed++;
		}
		
		// create the string
		String string = "";
		
		// append all the scores seperated by a comma
		string += matchesPlayed + ",";
		string += matchesWon + ",";
		string += matchesDrawn + ",";
		string += matchesLost + ",";
		string += goalsScoted + ",";
		string += goalsConceded + ",";
		string += goalDifference + ",";
		string += points;
		
		// return the string to the user
		return(string);
	}

	public void updateGameScore(int score, int opponentScore, int ps, int pos) {
		if(ps == pos) {
			points -= 1;
			matchesDrawn--;
		} else if(ps < pos) {
			matchesLost--;
		} else {
			points -= 3;
			matchesWon--;
		}

		matchesPlayed -= 1;
		goalsConceded -= pos;
		goalsScoted -= ps;
		goalDifference = goalsScoted - goalsConceded;

		setGameScore(score, opponentScore);
	}

	public void setGameScore(int score, int opponentScore) {
		// if its a draw
		if(score == opponentScore) {
			points += 1;
			matchesDrawn++;
		} else if(score < opponentScore) {
			matchesLost++;
		} else {
			points += 3;
			matchesWon++;
		}

		goalsConceded += opponentScore;
		goalsScoted += score;
		goalDifference = goalsScoted - goalsConceded;
	}

	public String getName() {
		return teamName;
	}
}