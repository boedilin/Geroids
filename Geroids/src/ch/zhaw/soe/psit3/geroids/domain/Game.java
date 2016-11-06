package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Date;

import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class Game {

	private Account account;
	private Gamefield gamefield;
	private ArrayList<Geroid> geroids;
	private Playscore score;
	private Figure figure;
	private ArrayList<Projectile> projectiles;
	private boolean isRunning = false;
	private boolean collisionWithProjectile = false;
	private boolean collisionWithFigure = false;
	private int xRange = 1000;
	private int yRange = 1000;
	private MyWebSocketHandler websocketHandler;

	public Game(Account account, Gamefield gamefield, MyWebSocketHandler websocketHandler){
		this.gamefield = new Gamefield(xRange, yRange);
		this.score = new Playscore(0, this);
		this.figure = new Figure(account.getActiveType(), account.getActiveSkin(), xRange/2, 0);
		this.projectiles = this.gamefield.getProjectileList();
		this.geroids = this.gamefield.getGeroidList();
		this.websocketHandler = websocketHandler;
		websocketHandler.sendMessage("hello! you start a new game"+new Date().getTime());
		run();
	}
	
	public void run() {
		new Thread();
		{
			Thread.currentThread().start();
			while(isRunning){
				
			}
		};
	}

	public boolean isGameover() {
		if(collisionWithFigure){
			isRunning = false;
			return true;
		}
		return false;
	}
	
	private void checkCollision(Gamefield gamefield){
		for(int i = 0; i < gamefield.getGeroidList().size();i++){
			if(checkIfGeroidIsCollidingWithFigure(i)){
				collisionWithFigure = true;
			}
			for(int j = 0; j< gamefield.getProjectileList().size();j++){
				if(checkIfGeroidIsCollidingWithProjectile(i, j))
				{
					collisionWithProjectile = true;
					if(geroids.get(j).getPosition()){
						
					}
				}
			}
		}
	}
	
	public boolean isRunning(){
		return isRunning;
	}
	
	//Message from websocket respectively from client
	//do something with the input from the user
	//preferable protocol is JSON		//read the mail from jens fischer WEB3 <-> valentin how to manage messages with identifiers
	public void receiveMessage(String message){
		System.out.println("i work with the message from the user: "+message);
	}
	
	private boolean checkIfGeroidIsCollidingWithProjectile(int geroidsIndex, int projectilesIndex){
		if(
				geroids.get(geroidsIndex).getPosition().getyCoordiante() - 1 == projectiles.get(projectilesIndex).getPosition().getyCoordiante()
				||
				geroids.get(geroidsIndex).getPosition().getyCoordiante() + 1 == projectiles.get(projectilesIndex).getPosition().getyCoordiante()
				)
		{
			return true;
		}
			return false;
	}
	
	private boolean checkIfGeroidIsCollidingWithFigure(int geroidIndex){
		if(		geroids.get(geroidIndex).getPosition().getyCoordiante() == figure.getPosition().getyCoordiante()+1 
				&& 
				geroids.get(geroidIndex).getPosition().getxCoordiante() == figure.getPosition().getxCoordiante()
			){
			return true;
		}
		return false;
	}
}
