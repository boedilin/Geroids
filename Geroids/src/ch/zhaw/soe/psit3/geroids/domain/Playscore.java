package ch.zhaw.soe.psit3.geroids.domain;

public class Playscore {
	
	private int score;
	private static int killBonus = 1;
	private static int passingGeroidDecrease = 1;
	private static int SCORE_MIN_VALUE = 0;
	private static int SCORE_MAX_VALUE = Integer.MAX_VALUE;
	
	public Playscore(){
		score = 0;
	}

	/**
	 * Sets the score to a certain value. Value needs to be between SCORE_MIN_VALUE and SCORE_MAX_VALUE.
	 * @param score Value to score should be set to .
	 */
	public void setScore(int score) {
		if(checkMaxValue(score))
			this.score = SCORE_MAX_VALUE;
		if(checkMinValue(score))
			this.score = SCORE_MIN_VALUE;
		else{
		this.score = score;
		}
	}
	
	private boolean checkMaxValue(int score){
		if(score > SCORE_MAX_VALUE){
			return true;
		}
		return false;
	}

	private boolean checkMinValue(int score){
		if(score < SCORE_MIN_VALUE){
			return true;
		}
		return false;
	}
	
	/**
	 * Adds points to the current score, equivalent to killbonus * geroidSpeed
	 * @param geroidSpeed The speed of the hit geroid.
	 */
	public void addingScoreIfGeroidKilled(int geroidSpeed){
		setScore(score + killBonus*geroidSpeed);
	}
	/**
	 * Adds points to score for a certain timebonus
	 * @param timebonus Bonuspoints to be aded for time.
	 */
	public void addingScoreForTimeBonus(int timebonus){
		setScore(score + timebonus);
	}
	
	/**
	 * Deducts points from the score.
	 * @param geroidSpeed The Speed of the passes Geroid
	 */
	public void decreaseScoreForPassingGeroid(int geroidSpeed){
		setScore(score - passingGeroidDecrease);
	}

	public int getScore() {
		return score;
	}
	
	public int getKillBonus() {
		return killBonus;
	}

	public int getPassingGeroidDecrease() {
		return passingGeroidDecrease;
	}

	public static int getSCORE_MIN_VALUE() {
		return SCORE_MIN_VALUE;
	}

	public static int getSCORE_MAX_VALUE() {
		return SCORE_MAX_VALUE;
	}
}
