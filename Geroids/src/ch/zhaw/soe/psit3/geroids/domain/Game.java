package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Random;

import javax.sound.midi.Synthesizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class Game {

	private static final int MAXIMUM_SHOOT_SPEED = 300;
	private long timestampPreviousShot;
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
	private MyWebSocketHandler webSocketHandler;
	Thread gameThread;
	private final int MAX_COUNT_GEROIDS = 10;
	private final int LENGTH_OF_TICK_IN_MS = 25;
	int maxRuntime = 3000;

	public Game(MyWebSocketHandler websocketHandler) {
		this.webSocketHandler = websocketHandler;
		//uncommented cause of parse error. send this as json format
		//websocketHandler.sendMessage("Connected to Server");
		this.gamefield = new Gamefield(xRange, yRange);
		this.geroids = new ArrayList<Geroid>();
		this.projectiles = new ArrayList<Projectile>();
		this.figure = new Figure(new Position(100, 500, 100, 100));
	}

	
	/**
	 * Starts the game. Will update the gamefield
	 * 
	 */
	public void startGame() {
		//uncommented cause of parse error. send this as json format
		//webSocketHandler.sendMessage("Server: Starting Game");
		isRunning = true;
		gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("started run");
				int counter = 1;
				int counter2 = 0;
				long timePrevious = System.currentTimeMillis();
				while (System.currentTimeMillis() - timePrevious < maxRuntime) {
					
					if (counter % 10 == 0) {
						// gernerate a geroid every 10 ticks
						generateGeroid();
					}
					//update all values of figure, geroids and projectiles
					updateGamefield();
					//sends all new values
					sendNewValues();
					//System.out.println(counter2++);
					counter++;
					try {
						Thread.sleep(LENGTH_OF_TICK_IN_MS);
						//System.out.println(System.currentTimeMillis()-timePrevious);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(counter);
			}
		}, "gameThread");
		gameThread.start();
		//webSocketHandler.sendMessage("Server: Gamethread Started");
	}

	/*
	 * Main update class. Propagates to the different update Methods.
	 */
	
	private void updateGamefield() {
		//updateFigure();
		updateGeroids();
		updateProjectiles();
	}
/*
 * Updates the figure corresponding the the command in the commandQueue.
 * a => move Left
 * d = > move Right
 * Space => shoot.
 */

	private void updateFigure(String command) {
			switch (command) {
			case "65":
				figure.moveLeft(10);
				break;
			case "68":
				figure.moveRight(10);
				break;
			case "32":
				if(System.currentTimeMillis() >= timestampPreviousShot + MAXIMUM_SHOOT_SPEED){
					timestampPreviousShot = System.currentTimeMillis();
					figure.shoot(this);
				}
				break;
			}
	}

	/*
	 * updates all geroids in geroids attribute (ArrayList)
	 */
	private void updateGeroids() {
		for (Geroid myGeroid : geroids) {
			myGeroid.move();
		}
	}

	
	/*
	 * Updates all Projectiles in projectiles attribute(ArrayList)
	 */
	private void updateProjectiles() {
		for (Projectile myProjectile : projectiles) {
			myProjectile.move();
		}
	}

	/*
	 * Sennds the new Values of Figure, all Geroids and all Projectiles via webSocketHandler to the Client.
	 */
	@SuppressWarnings("unchecked")
	private void sendNewValues() {
		JSONObject obj = new JSONObject();
		obj.put("Figure", figure.toJSONObject());
		obj.put("Geroids", this.geroidsToJSONArray());
		obj.put("Projectiles", this.projectilesToJSONArray());
		webSocketHandler.sendMessage(obj.toJSONString());

	}

	/*
	 * Generates a geroid if less on playfiled than MAX_COUNT_GEROIDS
	 */
	private void generateGeroid() {
		if (geroids.size() < MAX_COUNT_GEROIDS) {
			Position pos = new Position(new Random().nextInt(400), 0, 100, 100);
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
	
	@SuppressWarnings({ "unused", "unchecked" })
	private JSONArray projectilesToJSONArray() {

		JSONArray array = new JSONArray();

		for (Projectile projectile : projectiles) {
			array.add(projectile.toJSONObject());
		}

		return array;
	}
	
	
	private void checkCollision() {
		for (int i = 0; i < gamefield.getGeroidList().size(); i++) {
			if (checkIfGeroidIsCollidingWithFigure(i)) {
				isRunning = false;
				// hier kommt noch die Methode f�r den GameOverBanner
			}
			for (int j = 0; j < gamefield.getProjectileList().size(); j++) {
				if (checkIfGeroidIsCollidingWithProjectile(i, j)) {
					geroids.get(i).die(gamefield);
					projectiles.get(j).hit(this);
				}
			}
		}
	}

	// Message from websocket respectively from client
	// do something with the input from the user
	// preferable protocol is JSON //read the mail from jens fischer WEB3 <->
	// valentin how to manage messages with identifiers
	public void receiveMessage(String message) {
		System.out.println("Server received message: " + message);
		updateFigure(message);
	}

	private boolean checkIfGeroidIsCollidingWithProjectile(int geroidsIndex, int projectilesIndex) {
		if (geroids.get(geroidsIndex).getPosition().getyCoordiante() - 1 == projectiles.get(projectilesIndex)
				.getPosition().getyCoordiante()
				|| geroids.get(geroidsIndex).getPosition().getyCoordiante() + 1 == projectiles.get(projectilesIndex)
						.getPosition().getyCoordiante()) {
			return true;
		}
		return false;
	}

	private boolean checkIfGeroidIsCollidingWithFigure(int geroidIndex) {
		if (geroids.get(geroidIndex).getPosition().getyCoordiante() == figure.getPosition().getyCoordiante() + 1
				&& geroids.get(geroidIndex).getPosition().getxCoordiante() == figure.getPosition().getxCoordiante()) {
			return true;
		}
		return false;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public Gamefield getGamefield() {
		return gamefield;
	}


}
