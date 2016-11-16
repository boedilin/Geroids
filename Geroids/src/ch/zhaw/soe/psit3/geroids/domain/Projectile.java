//TODO implement ToJSON method

package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

/**
 * 
 * @author Matthias
 * Allows creating a projectile and setting his behaviour.
 */

public class Projectile{

	private Position position;
	private Movement movement;

	public Projectile(Position position, Movement movement) {
		this.position = position;
		this.movement = movement;
	}

	/**
	 * Updates the position of the object. Amount of change in position is described in movement object.
	 */
	
	public void move(){
		position.update(movement);
	}


	/**
	 * Returns a JSON  representation of the projectile for further usage. 
	 * @return JSONObject representing current Projectile.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject(){
			
			JSONObject obj = new JSONObject();

			obj.put("position", this.position.toJSONObject());
			return obj;
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
