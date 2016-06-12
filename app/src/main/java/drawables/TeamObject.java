package drawables;

import framework.core.*;
import objects.Tournament;
import objects.Team;

import java.util.*;

public class TeamObject implements IRenderable {
	public Label teamScore;
	public Label name;
	public Team team;

	private ArrayList<Vector2> positions;
	private ArrayList<String> strings;
	private Boolean updateStr;
	private Integer start_y;
	private Integer start_x;

	public TeamObject(Tournament tournament, int x, int y, int tx, int number) {
		Font font = Font.get("tiny");

		start_x = tx + 39;
		start_y = y;
	
		positions = new ArrayList<Vector2>();
		strings = new ArrayList<String>();
		for(int i = 0; i < 8; i++) {
			positions.add(new Vector2(i * 30, 0));
			strings.add(String.valueOf(0));
		}

		teamScore = new Label(font);
		teamScore.Load(start_x, start_y, strings, positions);
		team = tournament.getTeam(number);

		name = new Label(font);
		name.text(tournament.getTeamName(number));
		name.load(x, y);
		name.setColour(1, 1, 0, 1);
		
		updateStr = true;
	}

	public void updateString(int score, int opponentScore) {
		if(score != -1 && opponentScore != -1) {
			team.setGameScore(score, opponentScore);
		}
		
		updateStr = true;
	}

	public void updateString(int score, int opponentScore, int previousScore, int previousOpponentScore) {
		if(score != -1 && opponentScore != -1) {
			team.updateGameScore(score, opponentScore, previousScore, previousOpponentScore);
		}
		
		updateStr = true;
	}

	public void reset() {
		teamScore.reset();
		team.MatchesPlayed--;
		updateStr = true;
	}
	
	public void setMatchesPlayed(int num) {
		team.MatchesPlayed = num;
	}

	public void update(Integer y) {
		if(updateStr) {
			team.MatchesPlayed++;

			strings.clear();
			strings.add(String.valueOf(team.MatchesPlayed));
			strings.add(String.valueOf(team.MatchesWon));
			strings.add(String.valueOf(team.MatchesDrawn));
			strings.add(String.valueOf(team.MatchesLossed));
			strings.add(String.valueOf(team.GoalsScored));
			strings.add(String.valueOf(team.GoalsConceeded));
			strings.add(String.valueOf(team.GoalDifference));
			strings.add(String.valueOf(team.Points));
			
			teamScore.Load(start_x, start_y, strings, positions);
			updateStr = false;
		} 

		teamScore.translate(teamScore.getPosition().getX(), teamScore.getPosition().getY()+ y);
		name.translate(name.getPosition().getX(), name.getPosition().getY()+ y);

		teamScore.update();
		name.update();
	}

	public void translateLabelScores(float f) {
		teamScore.translate(teamScore.getPosition().getX(), f);
		name.translate(name.getPosition().getX(), f);
	
	}

	public String getName() {
		return team.getName();
	}

	@Override
	public void render() {
		teamScore.render();
		name.render();
	}
}
