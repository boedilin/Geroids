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

	public int getScore() {
		return score;
	}
	
	protected void setScore(int score) {
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
	
	public void addingScoreIfGeroidKilled(int geroidSpeed){
		setScore(score + killBonus*geroidSpeed);
	}
	
	public void decreaseScoreForPassingGeroid(int geroidSpeed){
		setScore(score - passingGeroidDecrease);
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
