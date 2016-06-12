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
 * This is just a simple class which stores the
 * names of 2 teams that must play. This is
 * a class that i currently being 
 * developed to take all the logic out
 * of the global class and to put it here
 */
public class Match {
	/** Team one's name as a string */
	private String teamOne;
	/** Team two's name as a string */
	private String teamTwo;
	
	/**
	 * Basic Constructor
	 */
	public Match() {
		teamOne = "N/A";
		teamTwo = "N/A";
	}
	
	/** Set team function that sets the 2 variables */
	public void setTeams(String one, String two) {
		teamOne = one;
		teamTwo = two;
	}
	
	/**
	 * returns the name of team 1
	 */
	public String getTeamOne() {
		return teamOne;
	}
	
	/**
	 * returns the name of team 2
	 */
	public String getTeamTwo() {
		return teamTwo;
	}
}