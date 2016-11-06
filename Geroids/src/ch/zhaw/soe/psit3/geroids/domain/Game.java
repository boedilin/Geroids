package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Date;

import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class Game extends Thread{

	private Account account;
	private Gamefield gamefield;
	private ArrayList<Geroid> geroids;
	private Playscore score;
	private Figure figure;
	private ArrayList<Projectile> projectiles;
	private boolean isRunning = false;
	private boolean collisionWithFigure = false;
	private int xRange = 1000;
	private int yRange = 1000;
	private MyWebSocketHandler websocketHandler;

	public Game(Account account, Gamefield gamefield, MyWebSocketHandler websocketHandler){
		this.gamefield = new Gamefield(xRange, yRange);
		this.score = new Playscore(0, this);
		this.figure = new Figure(this, account.getActiveType(), account.getActiveSkin(), xRange/2, 0);
		this.projectiles = this.gamefield.getProjectileList();
		this.geroids = this.gamefield.getGeroidList();
		this.websocketHandler = websocketHandler;
		websocketHandler.sendMessage("hello! you start a new game"+new Date().getTime());
		start();
	}
	
	public void run() {
		while(isRunning){
			checkCollision();
			checkFigureAction();
		};
	}
	
	private void checkCollision(){
		for(int i = 0; i < gamefield.getGeroidList().size();i++){
			if(checkIfGeroidIsCollidingWithFigure(i)){
				isRunning = false;
				//hier kommt noch die Methode für den GameOverBanner
			}
			for(int j = 0; j< gamefield.getProjectileList().size();j++){
				if(checkIfGeroidIsCollidingWithProjectile(i, j))
				{
					geroids.get(i).die(gamefield);
					projectiles.get(j).hit();
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

	public Gamefield getGamefield() {
		return gamefield;
	}
	
	public void checkFigureAction(){
		if(true//Abfrage ob Schiess-Knopf gedrückt
		   ||
		  true//Abfrage ob Links- oder Rechts-Knopf gedrückt
		   ){
			if(true){ //Abfrage ob Schiess-Knopf gedrückt
				figure.setShooting(true);
			}
			else if (true){//Abfrage ob Links-Knopf gedrückt
				figure.setMovingLeft(true);
				}
			else
			{
				figure.setMovingRight(true);
			}
		}
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ArrayList<Geroid> getGeroids() {
		return geroids;
	}

	public void setGeroids(ArrayList<Geroid> geroids) {
		this.geroids = geroids;
	}

	public Playscore getScore() {
		return score;
	}

	public void setScore(Playscore score) {
		this.score = score;
	}

	public Figure getFigure() {
		return figure;
	}

	public void setFigure(Figure figure) {
		this.figure = figure;
	}

	public boolean isCollisionWithFigure() {
		return collisionWithFigure;
	}

	public void setCollisionWithFigure(boolean collisionWithFigure) {
		this.collisionWithFigure = collisionWithFigure;
	}

	public int getxRange() {
		return xRange;
	}

	public void setxRange(int xRange) {
		this.xRange = xRange;
	}

	public int getyRange() {
		return yRange;
	}

	public void setyRange(int yRange) {
		this.yRange = yRange;
	}

	public MyWebSocketHandler getWebsocketHandler() {
		return websocketHandler;
	}

	public void setWebsocketHandler(MyWebSocketHandler websocketHandler) {
		this.websocketHandler = websocketHandler;
	}

	public void setGamefield(Gamefield gamefield) {
		this.gamefield = gamefield;
	}

	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
}
