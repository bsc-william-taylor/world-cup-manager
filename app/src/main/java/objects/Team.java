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
 * The team object represents a football
 * team in the application over time more
 * and more of the code written in the 
 * global class will be put here
 * 
 */
public class Team {
	/** The number of matches the team has played */
	public Integer MatchesPlayed = -1;
	/** The number of matches the team has lossed */
	public Integer MatchesLossed = 0;
	/** The number of matches the team has drawn */
	public Integer MatchesDrawn = 0;
	/** The number of matches the team has won */
	public Integer MatchesWon = 0;
	/** The number of goals the team has conceeded */
	public Integer GoalsConceeded = 0;
	/** The difference between goals scored and goals conceeded */
	public Integer GoalDifference = 0;
	/** The number of goals scored */
	public Integer GoalsScored = 0;
	/* The amount of points the team has* */
	public Integer Points = 0;
	/** Has the team qualified for the quater finals */
	public Boolean qualifyForQuater = false;
	/** Has the team qualified for the finals */
	public Boolean qualifyForFinal = false;
	/** Has the team qualified for the semi finals */
	public Boolean qualifyForSemi = false;
	/** Have they won the tournement*/
	public Boolean wonTournement = false;
	/** The teams name/country */
	public String teamName;
	
	/**
	 * Constructor
	 * @param name the name of the team
	 */
	public Team(String name) {
		teamName = name;
	}
	
	/**
	 *  Gets the value of a specific score by ID
	 *  
	 * @param ID a integer that corresponds to a specific score 
	 * @return the score as a string
	 */
	public String getScore(int ID) {
		String value = "E";
		switch(ID) {
			case 0: value = String.valueOf(MatchesPlayed); break;
			case 1: value = String.valueOf(MatchesWon); break;
			case 2: value = String.valueOf(MatchesDrawn); break;
			case 3: value = String.valueOf(MatchesLossed); break;
			case 4: value = String.valueOf(GoalsScored); break;
			case 5: value = String.valueOf(GoalsConceeded); break;
			case 6: value = String.valueOf(GoalDifference); break;
			case 7: value = String.valueOf(Points); break;
		
				default: break;
		}
		
		return value;
	}
	
	/**
	 * Resets the team back to the default values
	 */
	public void Reset() {
		// set booleans to false
		qualifyForQuater = false;
		qualifyForFinal = false;
		qualifyForSemi = false;
		wonTournement = false;
		
		// set scores to zero
		GoalsConceeded = 0;
		GoalDifference = 0;
		MatchesPlayed = 0;
		MatchesLossed = 0;
		MatchesDrawn = 0;
		GoalsScored = 0;
		MatchesWon = 0;
		Points = 0;
	}
	
	/**
	 * Returns all the scores put together in a string
	 * @param matchPlayed should the class increment the match played counter
	 */
	public String getScoreAsString(boolean matchPlayed) {
		// should we increment the match played counter
		if(matchPlayed) {
			MatchesPlayed++;
		}
		
		// create the string
		String string = "";
		
		// append all the scores seperated by a comma
		string += MatchesPlayed + ",";
		string += MatchesWon + ",";
		string += MatchesDrawn + ",";
		string += MatchesLossed + ",";
		string += GoalsScored + ",";
		string += GoalsConceeded + ",";
		string += GoalDifference + ",";
		string += Points;
		
		// return the string to the user
		return(string);
	}
	
	/**
	 * The function will update the scores if the result has been editied
	 * @param score the new score
	 * @param opponentScore the new opponents score
	 * @param ps the previous score
	 * @param pos the previous opponents score
	 */
	public void updateGameScore(int score, int opponentScore, int ps, int pos) {
		// if we drew last time
		if(ps == pos) {
			// deduct the original points
			Points -= 1;
			MatchesDrawn--;
		// do the same if we lossed
		} else if(ps < pos) {
			MatchesLossed--;
		} else {
			// or won
			Points -= 3;
			MatchesWon--;
		}
		
		// deduct all scores
		MatchesPlayed -= 1;
		GoalsConceeded -= pos;
		GoalsScored -= ps;
		GoalDifference = GoalsScored - GoalsConceeded;
		
		// then call the normal set score function with the new scores
		setGameScore(score, opponentScore);
	}
	
	/**
	 * Should be called when the team has played a match we then 
	 * update the scores based on the result of the match
	 * @param score the teams score
	 * @param opponentScore their oppenents score
	 */
	public void setGameScore(int score, int opponentScore) {
		// if its a draw
		if(score == opponentScore) {
			Points += 1;
			MatchesDrawn++;
		// if we lossed
		} else if(score < opponentScore) {
			MatchesLossed++;
		} else {
			// if we won
			Points += 3;
			MatchesWon++;
		}
		
		// update score fields
		GoalsConceeded += opponentScore;
		GoalsScored += score;
		GoalDifference = GoalsScored - GoalsConceeded;
	}
	
	/**
	 * Just returns the teams name
	 */
	public String getName() {
		return teamName;
	}
}