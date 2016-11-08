//TODO implement ToJSON method

package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;

/**
 * 
 * @author Matthias
 * Allows creating a projectile and setting his behaviour.
 */

public class Projectile{

	private Game game;
	private Position position;
	private Movement movement;
	private static int MOVEMENT_PER_TIME_IN_MS = 100;
	private boolean isAlive = true;
	
	public Projectile(Game game, Position position, Movement movement) {
		this.game = game;
		this.position = position;
		this.movement = movement;
		
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
	 * Updates the position of the object for one timestep. Duration of timestep is defined by attribute MOVEMENT_PER_TIME_IN_MS. 
	 * Amount of change in position is described in movement attribute.
	 */
	
	private void updatePosition(){
		this.position.setyCoordiante(this.position.getyCoordiante() + this.movement.getySpeed());
		this.position.setxCoordiante(this.position.getxCoordiante() + this.movement.getxSpeed());
	}

	/**
	 * Removes this Object from the projectileList the projectile was in. 
	 * Occurs when it hit a geroid or is outside the borders of the gamefield.
	 */
	public void hit() {
		isAlive = false;
		game.getGamefield().getProjectileList().remove(this);
	}
	
}
