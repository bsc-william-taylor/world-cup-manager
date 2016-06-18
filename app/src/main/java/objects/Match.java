package objects;

public class Match {
	private String teamOne;
	private String teamTwo;

	public Match() {
		teamOne = "N/A";
		teamTwo = "N/A";
	}

	public void setTeams(String one, String two) {
		teamOne = one;
		teamTwo = two;
	}

	public String getTeamOne() {
		return teamOne;
	}

	public String getTeamTwo() {
		return teamTwo;
	}
}