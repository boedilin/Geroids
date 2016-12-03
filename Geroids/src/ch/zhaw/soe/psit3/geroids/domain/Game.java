package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class Game {

	private static final int X_LENGTH = 1000;
	private static final int GEROID_WIDTH = 80;
	private static final int GEROID_HEIGHT = 100;
	private static final int TOP_OF_SCREEN = 0;
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
		this.figure = new Figure(new Position(470, 898, 61, 90));
		this.account = new Account("MyName" + System.currentTimeMillis());
		this.collisionHandler = new CollisionHandler(figure, geroids);
		this.score = new Playscore();
	}

	/**
	 * Starts the gamethread. New Thread will run until isRunning is false(got
	 * hit by a Geroid). Updates Gamefield.
	 * 
	 */
	public void startGame() {
		isRunning = true;
		gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("started run");
				int counter = 1;
				while (isRunning) {
					if (counter % 10 == 0) {
						generateGeroid();
					}
					updateGamefield();
					sendNewValues();
					counter++;
					trySleep(LENGTH_OF_TICK_IN_MS);
				}
			}
		}, "gameThread");
		gameThread.start();
	}

	/*
	 * Main update class. Propagates to the different update Methods.
	 */

	private void updateGamefield() {
		collisionHandler.updateFigures(geroids, figure);
		updateGeroids();
		updateProjectiles();
		handleCollisions();
	}
	/*
	 * Updates the figure corresponding the the command in the commandQueue. a
	 * => move Left d = > move Right Space => shoot.
	 */

	@SuppressWarnings("unchecked")
	private void handleCollisions() {
		if (collisionHandler.checkAllGeroidsCollisionWithFigure()) {
			isRunning = false;
		}
		checkAllGeroidsCollisionWithProjectiles();
	}

	/**
	 * Checks if there are any collisions of geroids and projectiles
	 * 
	 * @return Object-array, where array[0] = updated list of geroids and
	 *         array[1] = updated list of projectiles
	 */
	private void checkAllGeroidsCollisionWithProjectiles() {
		Iterator<Geroid> geroidIterator = geroids.iterator();
		while (geroidIterator.hasNext()) {
			Geroid myGeroid = geroidIterator.next();
			synchronized (projectiles) {
				Iterator<Projectile> projectileIterator = projectiles.iterator();
				while (projectileIterator.hasNext()) {
					Projectile myProjectile = projectileIterator.next();
					if (collisionHandler.checkIfGeroidIsCollidingWithProjectile(myGeroid, myProjectile)) {
						score.addingScoreIfGeroidKilled(myGeroid.getMovement().getySpeed());
						geroidIterator.remove();
						projectileIterator.remove();
						break;
					}

				}
			}
		}

	}

	private void updateFigure(String command) {
		if (command.length() > 2) {
			account.setNickname(command);
			// isName = false;
		}

		int figureXPos = figure.getPosition().getxCoordiante();
		int figureXLength = figure.getPosition().getxLength();

		switch (command) {
		case "65":
			moveLeftIfPossible(figureXPos);
			break;
		case "68":
			moveRightIfPossible(figureXPos, figureXLength);
			break;
		case "32":
			if (System.currentTimeMillis() >= timestampPreviousShot + MAXIMUM_SHOOT_SPEED) {
				timestampPreviousShot = System.currentTimeMillis();
				synchronized (projectiles) {
					projectiles.add(figure.shoot());
				}
			}
			break;
		}
	}

	private void moveLeftIfPossible(int figureXPos) {
		if (figureXPos - 10 >= 0) {
			figure.moveLeft(10);
		} else {
			figure.moveLeft(figureXPos);
		}
	}

	private void moveRightIfPossible(int figureXPos, int figureXLength) {
		if (figureXPos + figureXLength + 10 <= X_LENGTH) {
			figure.moveRight(10);
		} else {
			figure.moveRight(X_LENGTH - figureXPos - figureXLength);
		}
	}

	/*
	 * updates all geroids in geroids attribute (ArrayList)
	 */
	private void updateGeroids() {

		Iterator<Geroid> geroidIterator = geroids.iterator();
		while (geroidIterator.hasNext()) {
			Geroid currentGeroid = geroidIterator.next();
			if (collisionHandler.checkIfGeroidIsOutOfGamefield(currentGeroid)) {
				score.decreaseScoreForPassingGeroid(currentGeroid.getMovement().getySpeed());
				geroidIterator.remove();
			}
		}

		for (Geroid myGeroid : geroids) {
			myGeroid.move();
		}
	}

	/*
	 * Updates all Projectiles in projectiles attribute(ArrayList)
	 */
	private void updateProjectiles() {

		synchronized (projectiles) {
			Iterator<Projectile> projectileIterator = projectiles.iterator();

			while (projectileIterator.hasNext()) {

				Projectile currentProjectile = projectileIterator.next();

				if (collisionHandler.checkIfProjectileIsOutOfGamefield(currentProjectile)) {
					projectileIterator.remove();
				}
			}

		}
		synchronized (projectiles) {
			for (Projectile myProjectile : projectiles) {
				myProjectile.move();
			}
		}
	}

	/*
	 * Sends the new Values of Figure, all Geroids and all Projectiles via
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
		obj.put("Score", score.getScore());
		webSocketHandler.sendMessage(obj.toJSONString());

	}

	/*
	 * Generates a geroid if less on playfiled than MAX_COUNT_GEROIDS
	 */
	private void generateGeroid() {
		if (geroids.size() < MAX_COUNT_GEROIDS) {
			Position pos = new Position(new Random().nextInt(900), TOP_OF_SCREEN, GEROID_WIDTH, GEROID_HEIGHT);
			Movement mov = new Movement(0, new Random().nextInt(10) + 1);
			Geroid geroid = new Geroid(pos, mov);
			// synchronized (geroids) {
			geroids.add(geroid);
			// }
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

		synchronized (projectiles) {
			for (Projectile projectile : projectiles) {
				array.add(projectile.toJSONObject());
			}
		}

		return array;
	}

	public void receiveMessage(String message) {
		if (message != null) {
			updateFigure(message);
		} else {
			System.err.println("Class game received null message. Message will be ignored.");
		}

	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setFigure(Figure figure) {
		this.figure = figure;
	}

	public ArrayList<Geroid> getGeroids() {
		return geroids;
	}

	public boolean isRunning() {
		return isRunning;
	}

}
