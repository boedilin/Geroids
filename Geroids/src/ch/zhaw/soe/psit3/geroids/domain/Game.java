package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
	private ArrayList<Geroid> geroids;
	private Playscore score;
	private Figure figure;
	private ArrayList<Projectile> projectiles;

	private boolean isName = true;
	private boolean isRunning = false;
	private boolean collisionWithFigure = false;
	private int xRange = 1000;
	private int yRange = 1000;
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
		this.account = new Account();
		this.account.setNickname("MyName" + System.currentTimeMillis());
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
		// updateFigure();
		updateGeroids();
		updateProjectiles();
		checkCollision();
	}
	/*
	 * Updates the figure corresponding the the command in the commandQueue. a
	 * => move Left d = > move Right Space => shoot.
	 */

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
			RemoveIfGeroidIsOutOfGamefield(i);
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
			RemoveIfProjectileIsOutOfGamefield(i);
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

	private void checkCollision() {
		Iterator<Geroid> geroidIterator = geroids.iterator();
		while (geroidIterator.hasNext()) {
			Geroid myGeroid = geroidIterator.next();
			if (checkIfGeroidIsCollidingWithFigure(myGeroid)) {
				isRunning = false;
				// hier kommt noch die Methode f�r den GameOverBanner
			}

			Iterator<Projectile> projectileIterator = projectiles.iterator();
			while (projectileIterator.hasNext()) {
				Projectile myProjectile = projectileIterator.next();
				if (checkIfGeroidIsCollidingWithProjectile(myGeroid, myProjectile)) {
					geroidIterator.remove();
					projectileIterator.remove();

				}
			}
		}

	}

	// Message from websocket respectively from client
	// do something with the input from the user
	// preferable protocol is JSON //read the mail from jens fischer WEB3 <->
	// valentin how to manage messages with identifiers
	public void receiveMessage(String message) {
		updateFigure(message);
	}

	private boolean checkIfGeroidIsCollidingWithProjectile(Geroid geroid, Projectile projectile) {
		int projectileLeftMostPosition = projectile.getPosition().getxCoordiante();
		int projectileRightMostPosition = projectileLeftMostPosition + projectile.getPosition().getxLength();
		int geroidLeftMostPoint = geroid.getPosition().getxCoordiante();
		int geroidRightMostPoint = geroidLeftMostPoint + geroid.getPosition().getxLength();

		int projectileHighestPoint = projectile.getPosition().getyCoordiante();
		int geroidLowestPoint = geroid.getPosition().getyCoordiante() + geroid.getPosition().getyLength();

		if (isInBetween(projectileLeftMostPosition, projectileRightMostPosition, geroidRightMostPoint)
				| isInBetween(projectileLeftMostPosition, projectileRightMostPosition, geroidLeftMostPoint)
				| isInBetween(geroidLeftMostPoint, geroidRightMostPoint, projectileLeftMostPosition)) { // xcoords

			if (projectileHighestPoint < geroidLowestPoint) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	private boolean checkIfGeroidIsCollidingWithFigure(Geroid geroid) {
		int figureLeftMostPoint = this.figure.getPosition().getxCoordiante();
		int figureRightMostPoint = figureLeftMostPoint + this.figure.getPosition().getxLength();
		int geroidLeftMostPoint = geroid.getPosition().getxCoordiante();
		int geroidRightMostPoint = geroidLeftMostPoint + geroid.getPosition().getxLength();

		int figureHighestPoint = this.figure.getPosition().getyCoordiante();
		int geroidLowestPoint = geroid.getPosition().getyCoordiante() + geroid.getPosition().getyLength();

		if (isInBetween(figureLeftMostPoint, figureRightMostPoint, geroidRightMostPoint)
				| isInBetween(figureLeftMostPoint, figureRightMostPoint, geroidLeftMostPoint)
				| isInBetween(geroidLeftMostPoint, geroidRightMostPoint, figureLeftMostPoint)) { // xcoords

			if (figureHighestPoint < geroidLowestPoint) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean isInBetween(int leftBorder, int rightBorder, int searchedNumber) {
		if (searchedNumber > leftBorder & searchedNumber < rightBorder) {
			return true;
		} else {
			return false;
		}
	}

	private void RemoveIfGeroidIsOutOfGamefield(int geroidIndex) {

		if (this.geroids.get(geroidIndex).getPosition().getyCoordiante() > this.yRange) {
			geroids.remove(geroidIndex);
		}

	}

	private void RemoveIfProjectileIsOutOfGamefield(int projectileIndex) {
		int yCoord = this.projectiles.get(projectileIndex).getPosition().getyCoordiante();
		int yLength = this.projectiles.get(projectileIndex).getPosition().getyLength();

		if ((yCoord + yLength) < 0) {
			projectiles.remove(projectileIndex);
		}
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}


}
