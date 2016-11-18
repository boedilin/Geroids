package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.Synthesizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class Game {

	private static final int MAXIMUM_SHOOT_SPEED = 300;
	private long timestampPreviousShot;
	private Account account;
	private ArrayList<Geroid> geroids;
	private Playscore score;
	private Figure figure;
	private ArrayList<Projectile> projectiles;
	private CollisionHandler collisionHandler;

	private boolean isName = true;
	private boolean isRunning = false;
	private MyWebSocketHandler webSocketHandler;
	Thread gameThread;
	private final int MAX_COUNT_GEROIDS = 10;
	private final int LENGTH_OF_TICK_IN_MS = 15;

	public Game(MyWebSocketHandler websocketHandler) {
		this.webSocketHandler = websocketHandler;
		// uncommented cause of parse error. send this as json format
		// websocketHandler.sendMessage("Connected to Server");
		this.geroids = new ArrayList<Geroid>();
		this.projectiles = new ArrayList<Projectile>();
		this.figure = new Figure(new Position(100, 898, 61, 90));
		this.account = new Account("MyName" + System.currentTimeMillis());
		this.collisionHandler = new CollisionHandler(figure, geroids, projectiles);
	}

	/**
	 * Starts the game. Will update the gamefield
	 * 
	 */
	public void startGame() {
		// uncommented cause of parse error. send this as json format
		// webSocketHandler.sendMessage("Server: Starting Game");
		isRunning = true;
		gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("started run");
				int counter = 1;
				while (isRunning) {
					if (counter % 10 == 0) {
						// gernerate a geroid every 10 ticks
						generateGeroid();
					}
					// update all values of figure, geroids and projectiles
					updateGamefield();
					// sends all new values
					sendNewValues();
					// System.out.println(counter2++);
					counter++;
					trySleep(LENGTH_OF_TICK_IN_MS);
				}
			}
		}, "gameThread");
		gameThread.start();
		// webSocketHandler.sendMessage("Server: Gamethread Started");
	}

	/*
	 * Main update class. Propagates to the different update Methods.
	 */

	private void updateGamefield() {
		collisionHandler.updateFigures(geroids, figure, projectiles);
		// updateFigure();
		updateGeroids();
		updateProjectiles();
		handleCollisions();
	}
	/*
	 * Updates the figure corresponding the the command in the commandQueue. a
	 * => move Left d = > move Right Space => shoot.
	 */

	@SuppressWarnings("unchecked")
	private void handleCollisions(){
		if(collisionHandler.checkAllGeroidsCollisionWithFigure()){
			isRunning = false;
		}
		Object[] figures = collisionHandler.checkAllGeroidsCollisionWithProjectiles();
		geroids = (ArrayList<Geroid>) figures[0];
		projectiles = (ArrayList<Projectile>) figures[1];
		
	}
	
	private void updateFigure(String command) {
		if(command.length()>2){
			account.setNickname(command);
			//isName = false;
		}
		switch (command) {
		case "65":
			figure.moveLeft(10);
			break;
		case "68":
			figure.moveRight(10);
			break;
		case "32":
			if (System.currentTimeMillis() >= timestampPreviousShot + MAXIMUM_SHOOT_SPEED) {
				timestampPreviousShot = System.currentTimeMillis();
				projectiles.add(figure.shoot());
			}
			break;
		}
	}

	/*
	 * updates all geroids in geroids attribute (ArrayList)
	 */
	private void updateGeroids() {
		for (int i = 0; i < geroids.size(); i++) {
			if(collisionHandler.checkIfGeroidIsOutOfGamefield(i))
				removeGeroid(i);
		}

		for (Geroid myGeroid : geroids) {
			myGeroid.move();
		}
	}

	/*
	 * Updates all Projectiles in projectiles attribute(ArrayList)
	 */
	private void updateProjectiles() {

		for (int i = 0; i < projectiles.size(); i++) {
			if(collisionHandler.checkIfProjectileIsOutOfGamefield(i))
				removeProjectile(i);
		}

		for (Projectile myProjectile : projectiles) {
			myProjectile.move();
		}
	}

	/*
	 * Sennds the new Values of Figure, all Geroids and all Projectiles via
	 * webSocketHandler to the Client.
	 */
	@SuppressWarnings("unchecked")
	private void sendNewValues() {
		JSONObject obj = new JSONObject();
		obj.put("Figure", figure.toJSONObject());
		obj.put("Geroids", this.geroidsToJSONArray());
		obj.put("Projectiles", this.projectilesToJSONArray());
		obj.put("Gameover", !isRunning);
		obj.put("Name", this.account.getNickname());
		webSocketHandler.sendMessage(obj.toJSONString());

	}

	/*
	 * Generates a geroid if less on playfiled than MAX_COUNT_GEROIDS
	 */
	private void generateGeroid() {
		if (geroids.size() < MAX_COUNT_GEROIDS) {
			Position pos = new Position(new Random().nextInt(900), 0, 80, 100);
			Movement mov = new Movement(0, new Random().nextInt(10) + 1);
			Geroid geroid = new Geroid(pos, mov);
			geroids.add(geroid);
		}
	}

	private void trySleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private JSONArray geroidsToJSONArray() {

		JSONArray array = new JSONArray();

		for (Geroid myGeroid : geroids) {
			array.add(myGeroid.toJSONObject());
		}

		return array;

	}


	@SuppressWarnings("unchecked")
	private JSONArray projectilesToJSONArray() {

		JSONArray array = new JSONArray();

		for (Projectile projectile : projectiles) {
			array.add(projectile.toJSONObject());
		}

		return array;
	}

	// Message from websocket respectively from client
	// do something with the input from the user
	// preferable protocol is JSON //read the mail from jens fischer WEB3 <->
	// valentin how to manage messages with identifiers
	public void receiveMessage(String message) {
		updateFigure(message);
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	/**
	 * removes a specific projectile
	 * @param index of the projectile
	 */
	private void removeProjectile(int projectileIndex){
		projectiles.remove(projectileIndex);
	}

	/**
	 * removes a specific geroid
	 * @param index of the geroid
	 */
	private void removeGeroid(int geroidIndex){
		geroids.remove(geroidIndex);
	}

}
