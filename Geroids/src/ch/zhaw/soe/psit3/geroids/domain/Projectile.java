//TODO implement ToJSON method

package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

/**
 * 
 * @author Matthias
 * Allows creating a projectile and setting his behaviour.
 */

public class Projectile{

	private Game game;
	private Position position;
	private Movement movement;

	
	public Projectile(Game game, Position position, Movement movement) {
		this.game = game;
		this.position = position;
		this.movement = movement;
	}

	/**
	 * Updates the position of the object. Amount of change in position is described in movement object.
	 */
	
	private void move(){
		position.update(movement);
	}

	/**
	 * Removes this Object from the projectileList the projectile was in. 
	 * Occurs when it hit a geroid or is outside the borders of the gamefield.
	 */
	
	public void hit() {
		game.getGamefield().getProjectileList().remove(this);
	}
	
	
	/**
	 * Returns a JSON String representation of the projectile. 
	 * @return String Object in JSON Format representing current Projectile.
	 */
	public String toJSON(){
			
			JSONObject JSONposition = position.toJSONObject();
			return JSONposition.toJSONString();	
	}
	
	/**
	 * Returns a JSON  representation of the projectile for further usage. 
	 * @return JSONObject representing current Projectile.
	 */
	public JSONObject toJSONObject(){
			
			JSONObject JSONposition = position.toJSONObject();
			return JSONposition;
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

	
}
