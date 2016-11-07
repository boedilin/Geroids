package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Date;

import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class Game extends Thread{

	private Account account;
	private Gamefield gamefield;
	private Playscore score;
	private Figure figure;
	private boolean isRunning = false;
	private int xRange = 1000;
	private int yRange = 1000;
	private MyWebSocketHandler websocketHandler;
	private CollisionHandler collisionHandler;

	public Game(Account account, Gamefield gamefield, MyWebSocketHandler websocketHandler){
		this.gamefield = new Gamefield(xRange, yRange);
		this.score = new Playscore(0, this);
		this.figure = new Figure(this, account.getActiveType(), account.getActiveSkin(), xRange/2, 0);
		this.collisionHandler = new CollisionHandler(gamefield, figure);
		this.websocketHandler = websocketHandler;
		websocketHandler.sendMessage("hello! you start a new game"+new Date().getTime());
		start();
	}
	
	public void run() {
		isRunning = true;
		while(isRunning){
			collisionHandler.checkCollision();
			checkFigureAction();
			isRunning = collisionHandler.noCollision();
		};
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

	public Gamefield getGamefield() {
		return gamefield;
	}
	
	public void checkFigureAction(){
		if(true//Abfrage ob Schiess-Knopf gedr�ckt
		   ||
		  true//Abfrage ob Links- oder Rechts-Knopf gedr�ckt
		   ){
			if(true){ //Abfrage ob Schiess-Knopf gedr�ckt
				figure.setShooting(true);
			}
			else if (true){//Abfrage ob Links-Knopf gedr�ckt
				figure.setMovingLeft(true);
				}
			else
			{
				figure.setMovingRight(true);
			}
		}
	}

	public ArrayList<Projectile> getProjectiles() {
		return gamefield.getProjectileList();
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public ArrayList<Geroid> getGeroids() {
		return gamefield.getGeroidList();
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
	
}
