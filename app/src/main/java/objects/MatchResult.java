package objects;

public class MatchResult {
	public enum Stage {
		ROUND_OF_SIXTEEN,
		QUATER_FINALS,
		SEMI_FINALS,
		FINAL
	}

	public Boolean reversed = false;
	public Boolean changed = false;
	public String matchResultSecondaryID;
	public String matchResultPrimaryID;
	public String teamOne;
	public String teamTwo;
	public Integer teamOneScore;
	public Integer teamTwoScore;
	public Integer type = 0;
	public Stage matchStage;

	public MatchResult() {
		;
	}

	public String getWinnersName() {
		if(teamOneScore < teamTwoScore) {
			return teamOne;
		} else {
			return teamTwo;
		}
	}
}