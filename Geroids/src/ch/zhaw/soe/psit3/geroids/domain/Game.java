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
	private final int LENGTH_OF_TICK_IN_MS = 10;

	public Game(Account account, Gamefield gamefield, MyWebSocketHandler websocketHandler) {
		this.account = account;
		this.gamefield = new Gamefield(xRange, yRange);
		this.score = new Playscore(0, this);
		this.figure = new Figure(this, account.getActiveType(), account.getActiveSkin(), xRange / 2, 0);
		this.projectiles = this.gamefield.getProjectileList();
		this.geroids = this.gamefield.getGeroidList();
		this.webSocketHandler = websocketHandler;
		websocketHandler.sendMessage("Connected to Server");
	}

	public Game(MyWebSocketHandler websocketHandler) {
		this.webSocketHandler = websocketHandler;
		websocketHandler.sendMessage("Connected to Server");
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
		webSocketHandler.sendMessage("Server: Starting Game");
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
					//update all values of figure, geroids and projectiles
					updateGamefield();
					//sends all new values
					sendNewValues();

					counter++;
					trySleep(LENGTH_OF_TICK_IN_MS);
				}
			}
		}, "gameThread");
		gameThread.start();
		webSocketHandler.sendMessage("Server: Gamethread Started");
	}

	private String getCommandFromGamefield() {
		try {
			String first = gamefield.getCommandQueue().getFirst();
			return first;
		} catch (NoSuchElementException e) {
			// empty list
		}
		return null;
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
		System.out.println("COMMAND: " + command);
		if (command != null) {
			switch (command) {
			case "a":
				System.out.println("Moving figure to the Left");
				figure.moveLeft(10);
				break;
			case "d":
				System.out.println("Moving figure to the right");
				figure.moveRight(10);
				break;
			case " ":
				System.out.println("Shooting");
				figure.shoot(this);
				break;
			}
		} else {
			System.out.println("command was null");
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
		obj.put("Figure", figure.toJSON());
		obj.put("Geroids", this.toJSONStringGeroids());
		obj.put("Projectiles", this.toJSONStringProjectiles());
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

	
	@SuppressWarnings({ "unused", "unchecked" })
	private String toJSONStringGeroids() {

		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();

		for (Geroid myGeroid : geroids) {
			arr.add(myGeroid.toJSONObject());
		}

		obj.put("name", "geroidList");
		obj.put("geroids", arr);
		return obj.toJSONString();

	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private String toJSONStringProjectiles() {

		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();

		for (Projectile projectile : projectiles) {
			arr.add(projectile.toJSONObject());
		}

		obj.put("name", "projectileList");
		obj.put("projectiles", arr);
		return obj.toJSONString();

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
