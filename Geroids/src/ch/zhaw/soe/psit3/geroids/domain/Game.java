package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;

public class Game {

	private Account account;
	private Gamefield gamefield;
	private ArrayList<Geroid> geroids;
	private Playscore score;
	private Figure figure;
	private boolean isRunning = false;
	private boolean collisionWithProjectile = false;
	private boolean collisionWithFigure = false;
	private int xRange = 1000;
	private int yRange = 1000;

	public Game(Account account, Gamefield gamefield){
		this.gamefield = new Gamefield(xRange, yRange);
		this.score = new Playscore(0);
		this.figure = new Figure(account.getActiveType(), account.getActiveSkin(), xRange/2, 0);
		this.geroids = this.gamefield.getGeroidList();
		start();
	}
	
	public void start() {
		new Thread();
		{
			this.start();
			
			while(isRunning){
				
			}
			this.end();
		};
	}

	public void end() {
		isRunning = false;
	}
	
	private boolean checkCollision(Gamefield gamefield){
		for(int i = 0; i< gamefield.getGeroidList().size();i++){
			for(int j = 0; j< gamefield.getGeroidList().size();j++){
				if(i == j){
					break;
				}
				if(
						geroids.get(i).getPosition().getyCoordiante() ==  figure.getPosition().getyCoordiante()+1 
						&& 
						geroids.get(i).getPosition().getxCoordiante() == figure.getPosition().getxCoordiante()
					){
					
				}
				if( //Checking if the Geroids will collide in the next step
					((geroids.get(i).getPosition().getyCoordiante() - 1 == geroids.get(j).getPosition().getyCoordiante()) 
					|| 
					(geroids.get(i).getPosition().getyCoordiante() + 1 == geroids.get(j).getPosition().getyCoordiante()))
					)
				{
					if(geroids.get(j).getPosition())
				}
					
			}
			
		}
	}
	
	public boolean isRunning(){
		return isRunning;
	}
}
