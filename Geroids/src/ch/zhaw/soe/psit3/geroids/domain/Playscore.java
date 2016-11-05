package ch.zhaw.soe.psit3.geroids.domain;

public class Playscore {
	
	private Game game;
	private int score;
	private int killBonus = 100;
	private int timeBonusPerSecond = 10;
	private boolean geroidKilled = false;
	
	public Playscore(int score, Game game){
		this.score = score;
		this.game = game;
		new Thread();
		{
			while(game.isRunning())
				addingScoreIfGeroidKilled();
				addingScoreForTimeBonus();
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void addingScoreIfGeroidKilled(){
		if(geroidKilled){
			this.score += killBonus;
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
