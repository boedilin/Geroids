package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

public class Projectile {

	private Position position;
	public final static int PROJECTILE_SIZE = 10;
	private final int MOVEMENT_SPEED = -10;
	private Movement movement = new Movement(0, MOVEMENT_SPEED);

	/**
	 * Creates a Prjectile with a given start position
	 * @param position
	 */
	public Projectile(Position position) {
		this.position = position;
	}

	/**
	 * Updates the position of the object by the values of the Movement object;
	 */

	public void move() {
		position.update(movement);
	}

	/**
	 * Returns a JSON  representation of the projectile for further usage. 
	 * @return JSONObject representing current Projectile.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {

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
