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
 * A class representing a result that is created in the application
 * it is meant to store the teams involved in the result and the score
 * of the match. Each result is then saved to file
 */
public class MatchResult {
	/** The stage the match took place */
	public enum Stage {
		ROUND_OF_SIXTEEN,
		QUATER_FINALS,
		SEMI_FINALS,
		FINAL
	}
	
	/** Has the score been entered in reverse order */
	public Boolean Reversed = false;
	/** Have the scores changed */
	public Boolean Changed = false;
	/** A unique secondary ID  */
	public String MatchResultSecondaryID;
	/** A unique primary ID  */
	public String MatchResultPrimaryID;
	/** The name of team 1 */
	public String teamOne;
	/** The name of team 2 */
	public String teamTwo;
	/** The score team 1 got */
	public Integer teamOneScore;
	/** The score team 2 got */
	public Integer teamTwoScore;
	/** The stage as an integer */
	public Integer type = 0;
	/** The stage the match took place */
	public Stage matchStage;
	
	/**
	 * Basic Contructor
	 */
	public MatchResult() {
		;
	}
	
	/**
	 * returns the name of the team that won the match
	 */
	public String getWinnersName() {
		if(teamOneScore < teamTwoScore) {
			return teamOne;
		} else {
			return teamTwo;
		}
	}
}