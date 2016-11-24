package ch.zhaw.soe.psit3.geroids.domain;

public class Playscore {
	
	private int score;
	private static int killBonus = 100;
	private static int timeBonusPerSecond = 10;
	private static int passingGeroidDecrease = 5;
	private static int SCORE_MIN_VALUE = 0;
	private static int SCORE_MAX_VALUE = Integer.MAX_VALUE;
	private boolean geroidKilled = false;
	
	public Playscore(int score){
		setScore(score);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		if(checkMaxValue(score))
			this.score = SCORE_MAX_VALUE;
		if(checkMinValue(score))
			this.score = SCORE_MIN_VALUE;
		else{
		this.score = score;
		}
	}
	
	public boolean checkMaxValue(int score){
		if(score > SCORE_MAX_VALUE){
			return true;
		}
		return false;
	}

	public boolean checkMinValue(int score){
		if(score < SCORE_MIN_VALUE){
			return true;
		}
		return false;
	}
	
	public void addingScoreIfGeroidKilled(){
		if(geroidKilled){
			setScore(score += killBonus);
			geroidKilled = false;
		}
	}
	
	private void addingScoreForTimeBonus(){
		setScore(score += timeBonusPerSecond);
	}
	
	public void setGeroidKilled(boolean state){
		geroidKilled = state;
	}
	
	public void decreaseScoreForPassingGeroid(){
		setScore(score -= passingGeroidDecrease);
	}

	public int getKillBonus() {
		return killBonus;
	}

	public void setKillBonus(int killBonus) {
		this.killBonus = killBonus;
	}

	public int getTimeBonusPerSecond() {
		return timeBonusPerSecond;
	}

	public void setTimeBonusPerSecond(int timeBonusPerSecond) {
		this.timeBonusPerSecond = timeBonusPerSecond;
	}

	public int getPassingGeroidDecrease() {
		return passingGeroidDecrease;
	}

	public void setPassingGeroidDecrease(int passingGeroidDecrease) {
		this.passingGeroidDecrease = passingGeroidDecrease;
	}

	public boolean isGeroidKilled() {
		return geroidKilled;
	}

	public static int getSCORE_MIN_VALUE() {
		return SCORE_MIN_VALUE;
	}

	public static int getSCORE_MAX_VALUE() {
		return SCORE_MAX_VALUE;
	}
}
