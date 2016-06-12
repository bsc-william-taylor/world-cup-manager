package drawables;

import objects.Globals;
import objects.Tournament;
import java.util.ArrayList;
import framework.core.*;

public class GroupObject implements IRenderable {
	private static final Integer HEIGHT = 250;
	private static final Integer WIDTH = 600;
	private static TeamObject[] teams;

	static {
		teams = new TeamObject[4];
	}

	private TeamObject teamThree;
	private TeamObject teamFour;
	private TeamObject teamOne;
	private TeamObject teamTwo;

	private Vector2[] positions;
	private Label points;
	private Label title;
	private Image background;

	private Tournament tournament;

	private Integer totalY;
	private Integer tx;
	private Integer ty;

	public GroupObject(String str, int x, int y, int groupNumber) {
		tournament = Globals.get().getGroup(groupNumber);

		Font font = Font.get("tiny");
		points = new Label(font);
		title = new Label(font);

		teamThree = new TeamObject(tournament, x+30, y + 60,  x + 175, 2);
		teamFour = new TeamObject(tournament, x+30, y + 30,  x + 175, 3);
		teamTwo = new TeamObject(tournament, x+30, y + 90,  x + 175, 1);
        teamOne = new TeamObject(tournament, x+30, y + 120, x + 175, 0);

		positions = new Vector2[4];

		positions[0] = new Vector2(x+30, y + 120);
		positions[1] = new Vector2(x+30, y + 90);
		positions[2] = new Vector2(x+30, y + 60);
		positions[3] = new Vector2(x+30, y + 30);
		
		background = new Image("sprites/back.png");
		background.setPosition(x, y, 500, 230);

		title.text(str);
		title.load(x+30, y + 175);
		title.setColour(1, 0, 0, 1);

		points.text(" M W D L  +  -  /  P");
		points.load(x + 196, y + 175);
		points.setColour(1, 0, 0, 1);

		totalY = 0;
		tx = x;
		ty = y;
	}

	public void restart() {
		teamThree.reset();
		teamFour.reset();
		teamOne.reset();
		teamTwo.reset();
	}

	public void reset() {
		background.reset();	
		points.reset();
		title.reset();
		totalY = 0;
	}

	public String[] getTeamNames() {
		String[] names = new String[4];
	
		names[0] = tournament.getTeam(0).getName();
		names[1] = tournament.getTeam(1).getName();
		names[2] = tournament.getTeam(2).getName();
		names[3] = tournament.getTeam(3).getName();
		
		return names;
	}

	public TeamObject getTeam(int i) {
		TeamObject team = null;
		switch(i) {
			case 0: team = teamOne; 	break;
			case 1: team = teamTwo; 	break;
			case 2: team = teamThree; 	break;
			case 3: team = teamFour; 	break;
			
			default: break;
		}

		return(team);
	}

	public void update(Integer y, Boolean sort) {
		if(!sort) {
			this.sort();
		}

		Vector2 position = points.getPosition();
		points.translate(position.getX(), position.getY() + y);
		points.update();

		position = title.getPosition();
		title.translate(position.getX(), position.getY() + y);
		title.update();

		background.translate(0, y);
		background.update();
		teamThree.update(y);
		teamFour.update(y);
		teamOne.update(y);
		teamTwo.update(y);
		totalY -= y;
	}

	private void sort() {
		Integer swaps = 0;
		teams[0] = teamOne;
        teams[1] = teamTwo;
		teams[2] = teamThree;
        teams[3] = teamFour;

        do {
			swaps = 0;
			for(int i = 0; i < teams.length - 1; i++) {
				if(teams[i].team.Points < teams[i+1].team.Points) {
					TeamObject tmp = teams[i];
					teams[i] = teams[i+1];
					teams[i+1] = tmp;
					swaps++;
				} else if(teams[i].team.Points == teams[i+1].team.Points && teams[i].team.GoalDifference < teams[i+1].team.GoalDifference) {
					TeamObject tmp = teams[i];
					teams[i] = teams[i+1];
					teams[i+1] = tmp;
					
					swaps++;
				} else if(teams[i].team.Points == teams[i+1].team.Points && teams[i].team.GoalDifference == teams[i+1].team.GoalDifference) {
					if(teams[i].team.GoalsScored < teams[i+1].team.GoalsScored) {
						TeamObject tmp = teams[i];
						teams[i] = teams[i+1];
						teams[i+1] = tmp;	
						swaps++;
					}
				}
			}
		} while(swaps != 0);

		for(int i = 0; i < teams.length; ++i) {
			teams[i].translateLabelScores(positions[i].getY() - totalY);
		}
	}

	public ArrayList<Object> getLabels() {
		ArrayList<Object> objects = new ArrayList<Object>();
		addStrings(objects, teamOne);
		addStrings(objects, teamTwo);
		addStrings(objects, teamThree);
		addStrings(objects, teamFour);

		return objects;
	}

	private void addStrings(ArrayList<Object> array, TeamObject team) {
		array.add(team.teamScore);
		array.add(team.name);
	}

	@Override
	public void render() {
		background.render();
		points.render();
		title.render();

		teamThree.render();
		teamFour.render();
		teamOne.render();
		teamTwo.render();
	}

	public Label getTitle() {
		return title;
	}

	public Label getScore() {
		return points;
	}

	public Image getBackground() {
		return background;
	}

	public int getWidth() {
		return(WIDTH);
	}

	public int getHeight() {
		return(HEIGHT);
	}

	public int getX() {
		return tx;
	}

	public int getY() {
		return ty;
	}
}
