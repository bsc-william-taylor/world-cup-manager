package events;

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

import java.util.ArrayList;

import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import framework.core.*;
import objects.MatchResult;
import objects.Team;
import objects.Globals;

public class KnockOutEvent implements IEvent, OnClickListener {
	private Boolean loadedFromFile;
	private Integer scoreOne;
	private Integer scoreTwo;
	private Button buttonOne;
	private Button buttonTwo;

	private Integer round;
	private Integer count;
	private Boolean reverse;
	private Team teamOne;
	private Team teamTwo;

	private Listbox listOne;
	private Listbox listTwo;

	public KnockOutEvent(Button button, Button button2, Team team1, Team team2, int rnd) {
		loadedFromFile = false;
		buttonTwo = button2;
		buttonOne = button;
		teamTwo = team2;
		teamOne = team1;
		round = rnd;
		
		setDefaults();
		
		if(team1 != null && team2 != null) {
			ArrayList<MatchResult> results = Globals.get().getKnockOutResults();
			for(int i = 0; i < results.size(); i++) {
				MatchResult result = results.get(i);		
				if(teamOne.getName().compareTo(result.teamOne) == 0 && teamTwo.getName().compareTo(result.teamTwo) == 0 ||
						teamOne.getName().compareTo(result.teamTwo) == 0 && teamTwo.getName().compareTo(result.teamOne) == 0) {				
					scoreOne = result.teamOneScore;
					scoreTwo = result.teamTwoScore;
					count = 1;
					
					if(!result.Reversed) {
						buttonTwo.addText(String.valueOf(scoreOne));
						buttonOne.addText(String.valueOf(scoreTwo));
					} else {
						buttonTwo.addText(String.valueOf(scoreTwo));
						buttonOne.addText(String.valueOf(scoreOne));
					}
					
					loadedFromFile = true;
					break;
				}
			}
		}
	}

	public void SetTeam(Team team, Integer num) {
		if(num % 2 == 0) {
			teamOne = team;
		} else {
			teamTwo = team;
		}
	}

	public void ReverseOrder() {
		reverse = true;
	}

	public void Reset() {
		teamOne = null;
		teamTwo = null;
		scoreOne = -1;
		scoreTwo = -1;
		count = 0;
	}
	
	private void getScore(int teamNumber) {
		if(!reverse) {
			if(teamNumber == 0) {
				// set up lists and show them.
				listOne.setTitle("Enter " + teamOne.getName() + " score");
				listOne.Show();
			} else {
				listTwo.setTitle("Enter " + teamTwo.getName() + " score");
				listTwo.Show();	
			}
		} else {
			if(teamNumber == 0) {
				listTwo.setTitle("Enter " + teamTwo.getName() + " score");
				listTwo.Show();
			} else {
				listOne.setTitle("Enter " + teamOne.getName() + " score");
				listOne.Show();
			}
		}
	}

	@Override
	public void onActivate(Object data) {
		if(teamOne != null && teamTwo != null) {
			if(scoreOne == -1 && scoreTwo == -1) {
				listOne = new Listbox(this);
				listTwo = new Listbox(this);
				
				getScore(1);			
			} else {
				MessageBox message = new MessageBox();
				
				message.setMessage("This match has already been played");
				message.setTitle("Match Played");
				message.show(true);
			}
		}
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		if(count == 0) {
			if(arg1 != 0) {
				scoreOne = arg1-1;
				count++;
				getScore(0);
			}
		} else {
			if(arg1 != 0) {
				scoreTwo = arg1-1;

				if(scoreOne == scoreTwo) {
					scoreOne = -1;
					scoreTwo = -1;
					count = 0;
						
					MessageBox message = new MessageBox();
					message.setMessage("Add the final result after Extra Time or Penalty Shootout");
					message.setTitle("Draw ?");
					message.show(true);
					
				} else {
					Globals globals = Globals.get();

					if(!reverse) {
						globals.addKnockOutResult(scoreOne, scoreTwo, teamOne, teamTwo, round, false);
						
						buttonTwo.addText(String.valueOf(scoreOne));
						buttonOne.addText(String.valueOf(scoreTwo));
					} else {
						globals.addKnockOutResult(scoreTwo, scoreOne, teamOne, teamTwo, round, true);
						
						buttonTwo.addText(String.valueOf(scoreTwo));
						buttonOne.addText(String.valueOf(scoreOne));
					}
				}
			} else {
				scoreOne = -1;
				scoreTwo = -1;
				count = 0;
			}
		}
	}

	@Override
	public void update() {
		;
	}

	private void setDefaults() {
		reverse = false;
		scoreOne = -1;
		scoreTwo = -1;
		count = 0;
	}

	public boolean hasLoadedFromFile() {
		return(loadedFromFile);
	}

	public void triggerEvent(MatchResult newResult) {
		if(scoreOne == -1 && scoreTwo == -1) {
			scoreOne = newResult.teamOneScore;
			count = 1;
		} 
		
		if(scoreOne != -1 && scoreTwo == -1) {
			scoreTwo = newResult.teamTwoScore;
			
			buttonTwo.addText(String.valueOf(scoreOne));
			buttonOne.addText(String.valueOf(scoreTwo));
		}
	}
}

