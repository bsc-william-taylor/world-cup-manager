package objects;

public class Tournament {
	public static final Integer NUMBER_OF_GROUPS = 8;
	public enum Letter { 
		A, B, C, D, E, F, G, H
	}

	private Letter groupID;
	private Team[] teams;

	public Tournament(int gc) throws Exception {
		teams = new Team[4];

		switch(gc) {
			case 0: groupID = Letter.A; break;
			case 1: groupID = Letter.B; break;
			case 2: groupID = Letter.C; break;
			case 3: groupID = Letter.D; break;
			case 4: groupID = Letter.E; break;
			case 5: groupID = Letter.F; break;
			case 6: groupID = Letter.G; break;
			case 7: groupID = Letter.H; break;
		}

		create();
	}

	public void reset() {
		for(Team team : teams) {
			team.Reset();
		}
	}

	public Team getWinner() {
		Team winner = teams[0];
			
		// get team with most points
		for(int i = 0; i < 4; i++) {
			if(teams[i].Points > winner.Points) {
				winner = teams[i];
			}
		}

		for(int i = 0; i < 4; i++) {
			if(teams[i].Points == winner.Points) {
				if(teams[i].GoalDifference > winner.GoalDifference) {
					winner = teams[i];
				} else if(teams[i].GoalDifference == winner.GoalDifference) {
					if(teams[i].GoalsScored > winner.GoalsScored) {
						winner = teams[i];
					}
				}
			}
		}

		return winner;
	}

	public Team getRunnerUp() {
		String winner = getWinner().getName();
		
		Team[] tempArray = new Team[3];
		
		int count = 0;
		for(int i = 0; i < 4; i++) {
			if(teams[i].getName().compareToIgnoreCase(winner) == 0) {
				continue;
			} else {
				tempArray[count] = teams[i];
				count++;
				if(count >= 3) {
					break;
				}
			}
		}
		
		Team runnerup = tempArray[0];
		for(int i = 0; i < 3; i++) {
			if(tempArray[i].Points > runnerup.Points ) {
				runnerup = tempArray[i];
			}
		}

		for(int i = 0; i < 3; i++) {
			if(tempArray[i].Points == runnerup.Points) {
				if(tempArray[i].GoalDifference > runnerup.GoalDifference) {
					runnerup = tempArray[i];
				} else if(tempArray[i].GoalDifference == runnerup.GoalDifference) {
					if(tempArray[i].GoalsScored > runnerup.GoalsScored) {
						runnerup = tempArray[i];
					}
				}
			}
		}
		
		return runnerup;
	}

	public Team getTeam(int i) {
		return teams[i];
	}

	public String getTeamName(int i) {
		return teams[i].getName();
	}

	public Team[] getTeams() {
		return teams;
	}

	private void create() throws Exception {
		switch(groupID) {
			// Tournament A
			case A: {
				teams[0] = new Team("Brazil"); 		
				teams[1] = new Team("Croatia");
				teams[2] = new Team("Mexico"); 		
				teams[3] = new Team("Cameroon");
				
				break;
			}
			
			// Tournament B
			case B: {
				teams[0] = new Team("Spain"); 		
				teams[1] = new Team("Netherlands"); 
				teams[2] = new Team("Chile"); 		
				teams[3] = new Team("Austalia"); 
				
				break;
			}
			
			// Tournament C
			case C: {
				teams[0] = new Team("Columbia"); 	
				teams[1] = new Team("Greece"); 	
				teams[2] = new Team("Cote d Ivoire"); 
				teams[3] = new Team("Japan"); 
				
				break;
			}
			
			// Tournament D
			case D: {
				teams[0] = new Team("Uruguay"); 	
				teams[1] = new Team("Costa Rica"); 
				teams[2] = new Team("England"); 	
				teams[3] = new Team("Italy"); 
				
				break;
			}
			
			// Tournament E
			case E: {
				teams[0] = new Team("Switzerland");	
				teams[1] = new Team("Ecuador"); 
				teams[2] = new Team("France"); 		
				teams[3] = new Team("Honduras");
				
				break;
			}
			
			// Tournament F
			case F: {
				teams[0] = new Team("Argentina"); 	
				teams[1] = new Team("Bosnia & Herz"); 
				teams[2] = new Team("Iran"); 		
				teams[3] = new Team("Nigeria"); 
				
				break;
			}
			
			// Tournament G
			case G: {
				teams[0] = new Team("Germany"); 	
				teams[1] = new Team("Portugal");
				teams[2] = new Team("Ghana"); 		
				teams[3] = new Team("USA"); 
				
				break;
			}
			
			// Tournament H
			case H:  {
				teams[0] = new Team("Belgium"); 	
				teams[1] = new Team("Algeria"); 
				teams[2] = new Team("Russia"); 		
				teams[3] = new Team("Korea Republic"); 
				
				break;
			}
			
			default:
				throw new Exception("Error creating group");
		}
	}
}