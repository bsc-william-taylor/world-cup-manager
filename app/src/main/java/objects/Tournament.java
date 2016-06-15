/**
 * Copyright (c) 2014 - William Taylor <wi11berto@yahoo.co.uk>
 *
 *	This software is provided 'as-is', without any express or implied warranty. 
 *  In no event will the authors be held liable for any damages arising from 
 *  the use of this software. Permission is granted to anyone to use this 
 *  software for any purpose, including commercial applications, and to 
 *  alter it and redistribute it freely, subject to the following 
 *  restrictions:
 *
 *	1. The origin of this software must not be misrepresented; 
 *     you must not claim that you wrote the original software. 
 *	   If you use this software in a product, an acknowledgment 
 *     in the product documentation would be appreciated 
 *     but is not required.
 *
 *  2. Altered source versions must be plainly marked as such, 
 *     and must not be misrepresented as being the original 
 *     software.
 *  
 *  3. This notice may not be removed or altered 
 *     from any source distribution.
 *     
 */
package objects;

/**
 * This is just a simple object that takes
 * all the interaction logic from the globals
 * class and places it here
 */
public class Tournament {
	/** The number of groups in the tournment */
	public static final Integer NUMBER_OF_GROUPS = 8;
	
	/** The range of letters for each group */
	public enum Letter { 
		A, B, C, D, E, F, G, H
	}
	
	/** The ID of this particular group */
	private Letter groupID;
	/** An array of teams in that group */
	private Team[] teams;
	
	/**
	 * Constructor sets up the teams group by a
	 * unique integer ID which represents a letter
	 * in the Letter enum
	 * @param gc a ID to the group character
	 * @throws Exception throws exception if GC doesnt match a
	 * expected ID number
	 */
	public Tournament(int gc) throws Exception {
		// create team array
		teams = new Team[4];
		
		// Aquire the group ID
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
	
		// Create the teams that make up the group
		create();
	}
	
	/**
	 * Reset function resets all the teams
	 */
	public void Reset() {
		for(Team team : teams) {
			team.Reset();
		}
	}
	
	/** 
	 * Returns the winner from the group
	 */
	public Team getWinner() {
		Team winner = teams[0];
			
		// get team with most points
		for(int i = 0; i < 4; i++) {
			if(teams[i].Points > winner.Points) {
				winner = teams[i];
			}
		}
		
		// Check to see if there is a draw
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
		 
		// Return winner
		return winner;
	}
	
	/**
	 * Returns the runner up in the group
	 */
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
		
		// Check to see if there is a draw
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
	
	/**
	 * returns a reference to the team in the array at point i
	 */
	public Team getTeam(int i) {
		return teams[i];
	}
	
	/**
	 * returns the name of the team stored at point i
	 */
	public String getTeamName(int i) {
		return teams[i].getName();
	}

	/**
	 * returns the team array
	 */
	public Team[] getTeams() {
		return teams;
	}
	
	/**
	 * Creates the teams that are in each group
	 * @throws Exception
	 */
	private void create() throws Exception {		
		// Each group has a set of teams
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
			
			// throw an exception if we dont have a ID that matches
			default:
				throw new Exception("Error creating group");
		}
	}
}