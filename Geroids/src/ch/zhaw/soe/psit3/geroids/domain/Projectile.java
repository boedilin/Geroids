//TODO implement ToJSON method

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

	/**
	 * Updates the position of the object for one timestep, according to his movement object.
	 */
	private void updatePosition(){
		this.position.setyCoordiante(this.position.getyCoordiante() + this.movement.getySpeed());
		this.position.setxCoordiante(this.position.getxCoordiante() + this.movement.getxSpeed());

	}
	
	
	/**
	 * Gets called when creating a Thread with a Projectile runnable. 
	 * Update the Position of the Projectile every 100ms as long isAlive = true;
	 * 
	 **/
	@Override
	public void run(){
		while(isAlive){
			try {
				updatePosition();
				sleep(MOVEMENT_PER_TIME_IN_MS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * Changes the isAlive flag to false thus stopping the next execution of the run function. 
	 * Removes this Object from the projectileList the projectile was in.
	 */
	public void hit() {
		isAlive = false;
		game.getGamefield().getProjectileList().remove(this);
	}
	
}
