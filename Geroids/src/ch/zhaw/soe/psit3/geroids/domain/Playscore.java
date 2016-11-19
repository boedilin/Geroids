package ch.zhaw.soe.psit3.geroids.domain;

public class Playscore {
	
	private int score;
	private int killBonus = 100;
	private int timeBonusPerSecond = 10;
	private boolean geroidKilled = false;
	
	public Playscore(int score){
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void addingScoreIfGeroidKilled(){
		if(geroidKilled){
			score += killBonus;
			geroidKilled = false;
		}
	}
	
	private void addingScoreForTimeBonus(){
		this.score += timeBonusPerSecond;
	}
	
	public void setGeroidKilled(){
		geroidKilled = true;
	}
}
