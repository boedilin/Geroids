package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Projectile extends Thread{

	private Game game;
	private Position position;
	private Movement movement;
	private static int MOVEMENT_PER_TIME_IN_MS = 100;
	private boolean isAlive = true;
	
	public Projectile(Game game, Position position, Movement movement) {
		super();
		this.game = game;
		this.position = position;
		this.movement = movement;
		start();
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	private void updatePosition(){
		this.position.setyCoordiante(this.position.getyCoordiante() + this.movement.getySpeed());
	}
	
	
	/**
	 * Gets called when creating a Thread with a Projecile runnable. 
	 * Update thr projectiles position at the given this.fps rate until DUMMY_COLLISION_OCCURED is true
	 * 
	 * @see java.lang.Runnable#run()
	 **/
	public void run(){
		while(isAlive){ 
			try {
				updatePosition();
				Thread.sleep(MOVEMENT_PER_TIME_IN_MS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void hit() {
		isAlive = false;
		game.getGamefield().getProjectileList().remove(this);
	}
	
}
