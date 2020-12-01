package ch.zhaw.soe.psit3.geroids.domain;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class Game {

	public static final int X_WIDTH = 1000;
	public static final int Y_HEIGHT = 1000;
	public static final int GEROID_WIDTH = 80;
	public static final int GEROID_HEIGHT = 100;
	public static final int LEFT_BOARDER = 0;
	public static final int RIGHT_BOARDER = X_WIDTH;
	private static final int TOP_OF_SCREEN = 0;
	private static final int MAXIMUM_SHOOT_SPEED = 300;
	private long timestampPreviousShot;
	private Account account;
	private ArrayList<Geroid> geroids;
	private Playscore score;
	private Figure figure;

	private ArrayList<Projectile> projectiles;
	private CollisionHandler collisionHandler;

	private boolean isRunning = false;
	private MyWebSocketHandler webSocketHandler;
	Thread gameThread;
	private final int MAX_COUNT_GEROIDS = 10;
	private final int LENGTH_OF_TICK_IN_MS = 15;
	private Connection connection = null;
	private int level = 1;

	/**
	 * Creates a new Game with a specific MyWebSockethandler
	 * 
	 * @param websocketHandler
	 *            The websockethandler object the game should communicate with.
	 */
	public Game(MyWebSocketHandler websocketHandler) {
		this.webSocketHandler = websocketHandler;
		this.geroids = new ArrayList<Geroid>();
		this.projectiles = new ArrayList<Projectile>();
		this.figure = new Figure(new Position((X_WIDTH - Figure.X_WIDTH_FIGURE) / 2, X_WIDTH - Figure.Y_HEIGHT_FIGURE,
				Figure.X_WIDTH_FIGURE, Figure.Y_HEIGHT_FIGURE));
		this.account = new Account("MyName" + System.currentTimeMillis());
		this.collisionHandler = new CollisionHandler(Y_HEIGHT);
		this.score = new Playscore();

        try {
            String dbURL3 = "jdbc:postgresql://ec2-54-235-89-113.compute-1.amazonaws.com:5432/dcr3lknftj4n6j?sslmode=require";
            Properties parameters = new Properties();
            parameters.put("user", "emadnzteospxpj");
            parameters.put("password", "lBeoj_V8XMdzrS5fxa_-fbKhh8");
 
            connection = DriverManager.getConnection(dbURL3, parameters);
            if (connection != null) {
                System.out.println("Connected to database #3");
            }
 
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
	}

	/**
	 * Starts the gamethread. New Thread will run until isRunning is false(got
	 * hit by a Geroid). Updates Gamefield by itself.
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
					if (counter % 3600 == 0) {
						score.addingScoreForTimeBonus(50);
						level += 1;
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

	private void updateGamefield() {
		collisionHandler.generateFigureCollisionPoints(figure);
		updateGeroids();
		updateProjectiles();
		handleCollisions();
	}

	private void handleCollisions() {
		if (collisionHandler.checkAllGeroidsCollisionWithFigure(figure, geroids)) {
			isRunning = false;
		}
		checkAllGeroidsCollisionWithProjectiles();
	}

	private void checkAllGeroidsCollisionWithProjectiles() {
		Iterator<Geroid> geroidIterator = geroids.iterator();
		while (geroidIterator.hasNext()) {
			Geroid myGeroid = geroidIterator.next();
			synchronized (projectiles) {
				Iterator<Projectile> projectileIterator = projectiles.iterator();
				while (projectileIterator.hasNext()) {
					Projectile myProjectile = projectileIterator.next();
					if (collisionHandler.checkCollisionWithGeroid(myGeroid, myProjectile.getPosition())) {
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
		}

		switch (command) {
		case "65":
			figure.moveLeft();
			break;
		case "68":
			figure.moveRight();
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
			collisionHandler.updateCollisionPoints(myGeroid);
		}
	}

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

	@SuppressWarnings("unchecked")
	private void sendNewValues() {
		/*
		JSONObject obj = new JSONObject();
		obj.put("Figure", figure.toJSONObject());
		obj.put("Geroids", this.geroidsToJSONArray());
		obj.put("Projectiles", this.projectilesToJSONArray());
		obj.put("Gameover", !isRunning);
		obj.put("Name", this.account.getNickname());

		webSocketHandler.sendMessage(obj.toJSONString());*/
		MsgPacktest test = new MsgPacktest();
		ByteBuffer packedData = null;
		try {
			packedData = test.basicUsage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		webSocketHandler.sendMessage(packedData);
		obj.put("Score", score.getScore());
		obj.put("Level", level);
		webSocketHandler.sendMessage(obj.toJSONString());

	}

	private void generateGeroid() {
		if (geroids.size() < MAX_COUNT_GEROIDS) {
			Position pos = new Position(new Random().nextInt(900), TOP_OF_SCREEN, GEROID_WIDTH, GEROID_HEIGHT);
			Movement mov = new Movement(0, new Random().nextInt(10) + 1);
			Geroid geroid = new Geroid(new Random().nextInt(5) + 1, pos, mov);
			// synchronized (geroids) {
			geroids.add(geroid);
			collisionHandler.addGeroidsCollisionPoints(geroid);
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

	/**
	 * Receives a message and checks for null values.
	 * 
	 * @param message
	 *            Message to be received. If null, will get an entry in
	 *            sys.err.out and will have no other effect.
	 */

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

	public Playscore getScore() {
		return score;
	}

}
