package events;

import java.util.ArrayList;
import java.util.Random;

import drawables.TeamObject;

import framework.core.*;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import objects.*;

public class MatchEvent implements IEvent, OnClickListener {
	private TeamObject mainteam;
	private TeamObject opponent;
	private Button buttonTwo;
	private Button buttonOne;
	private Integer newScoreOne;
	private Integer newScoreTwo;
	private Integer scoreOne;
	private Integer scoreTwo;
	private Integer count;
	private Integer day;
	private Boolean waitingForInput;

	private Listbox listOne;
	private Listbox listTwo;
	private String titleOne;
	private String titleTwo;

	public MatchEvent(Button buttonOne, Button buttonTwo, TeamObject team, TeamObject opponent, Integer day) {
		this.buttonTwo = buttonTwo;
		this.buttonOne = buttonOne;
		this.opponent = opponent;
		this.mainteam = team;
		this.day = day;
		
		titleTwo = opponent.getName();
		titleOne = team.getName();
		
		setDefaults();
		
		ArrayList<MatchResult> results = Globals.get().getMatchResults();
		for(int i = 0; i < results.size(); i++) {
			MatchResult result = results.get(i);
			if(titleOne.compareTo(result.teamOne) == 0 || titleOne.compareTo(result.teamTwo) == 0) {
				if(titleTwo.compareTo(result.teamOne) == 0 || titleTwo.compareTo(result.teamTwo) == 0) {						
					waitingForInput = false;	
					buttonTwo.addText(String.valueOf(result.teamOneScore)); 
					buttonOne.addText(String.valueOf(result.teamTwoScore));
					mainteam.updateString(result.teamTwoScore, result.teamOneScore);
					opponent.updateString(result.teamOneScore, result.teamTwoScore);
					
					scoreOne = result.teamOneScore;
					scoreTwo = result.teamTwoScore;
					count = 2;
					break;
				}
			}
		}
	}

	public void Reset() {
		scoreOne = -1;
		scoreTwo = -1;
		count = 0;
	}

	@Override
	public void onActivate(Object data) {
		if(Globals.get().getCurrentDay() != day && Globals.get().getCurrentDay() < day) {
			
			MessageBox message = new MessageBox();
			
			message.setMessage("You must complete all day " + (day-1) + " Matches to continue");
			message.setTitle("Progress");
			message.show(true);
			
		} else if((Boolean)data == false && (scoreOne == -1 || scoreTwo == -1) && !waitingForInput) {
			waitingForInput = true;
			getScore(0);
		} else if((Boolean)data == true && (scoreOne != -1 || scoreTwo != -1) && !waitingForInput){
			waitingForInput = true;
			getScore(0);
		}
	}
	
	private void getScore(int teamNumber) {
		if(teamNumber == 1) {
			listOne = new Listbox(this);
			listOne.setTitle("How many goals did " + titleOne + " score");
			listOne.Show();
		} else {
			listTwo = new Listbox(this);
			listTwo.setTitle("How many goals did " + titleTwo + " score");
			listTwo.Show();
		}
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		Globals gloabls = Globals.get();

		if(count == 0) {
			if(arg1 != 0) {
				scoreOne = arg1 - 1;
				count++;
				
				getScore(1);
			} 
				
			waitingForInput = false;
		} else if(count == 1) {
			if(arg1 != 0) {
				buttonTwo.addText(String.valueOf(scoreOne)); 
				buttonOne.addText(String.valueOf(arg1-1));
				waitingForInput = false;	
				scoreTwo = arg1 - 1;
				count++;

				gloabls.addMatchResult(scoreOne, opponent, scoreTwo, mainteam, day);
				mainteam.updateString(scoreTwo, scoreOne);
				opponent.updateString(scoreOne, scoreTwo);
			} else {
				setDefaults();
			}
		} else if(count == 2) {
			if(arg1 != 0) {
				newScoreOne = arg1 - 1;
				count++;
				
				getScore(1);
			} else {
				newScoreOne = 0;
			}
			
			waitingForInput = false;
		} else if(count == 3) {
			if(arg1 != 0) {
				buttonTwo.addText(String.valueOf(newScoreOne));
				buttonOne.addText(String.valueOf(arg1-1));
				newScoreTwo = arg1 - 1;
				
				gloabls.changeMatchResult(newScoreOne, opponent, newScoreTwo, mainteam);
				mainteam.updateString(newScoreTwo, newScoreOne, scoreTwo, scoreOne);
				opponent.updateString(newScoreOne, newScoreTwo, scoreOne, scoreTwo);
				
				scoreOne = newScoreOne;
				scoreTwo = newScoreTwo;
			} else {
				newScoreOne = 0;
				newScoreTwo = 0;
			}
			
			waitingForInput = false;
			count = 2;
		} 
	}

	@Override
	public void update() {
		;
	}

	private void setDefaults() {
		waitingForInput = false;
		newScoreOne = 0;
		newScoreTwo = 0;
		scoreOne = -1;
		scoreTwo = -1;
		count = 0;
		
		buttonOne.hideText();
		buttonTwo.hideText();
	}

	public void randomise() {
		if(count == 0) {
			scoreOne = randomScore();
			scoreTwo = randomScore();
			count = 2;
			
			// Add the result to the button & store the score
			buttonTwo.addText(String.valueOf(scoreOne)); 
			buttonOne.addText(String.valueOf(scoreTwo));
			
			// This time add the result to the stack of match results
			Globals.get().addMatchResult(scoreOne, opponent, scoreTwo, mainteam, day);
			mainteam.updateString(scoreTwo, scoreOne);
			opponent.updateString(scoreOne, scoreTwo);
			
			mainteam.team.matchesPlayed = 2;
			opponent.team.matchesPlayed = 2;
		} else {
			
			newScoreOne = randomScore();
			newScoreTwo = randomScore();
			
			// Add the result to the button & alter the previous store
			buttonTwo.addText(String.valueOf(newScoreOne));
			buttonOne.addText(String.valueOf(newScoreTwo));
			
			Globals.get().changeMatchResult(newScoreOne, opponent, newScoreTwo, mainteam);
			mainteam.updateString(newScoreTwo, newScoreOne, scoreTwo, scoreOne);
			opponent.updateString(newScoreOne, newScoreTwo, scoreOne, scoreTwo);
			
			mainteam.team.matchesPlayed = 2;
			opponent.team.matchesPlayed = 2;
			
			waitingForInput = false;
			scoreOne = newScoreOne;
			scoreTwo = newScoreTwo;
			
		}
	}

	public void grabRealResult() {
		// TODO Auto-generated method stub
	}
	
	private int randomScore() {
		return (new Random().nextInt(4))+1;
	}
}
