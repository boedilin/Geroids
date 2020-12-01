package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;


public class Geroid {

	private Position position;
	private Movement movement;
	private int id;
	
	/**
	 * Creates a geroid with a certain position and Movement
	 * @param position The start Position of the geroid
	 * @param movement The Movement pattern of the geroid 
	 */
	public Geroid(int id, Position position, Movement movement) {
		this.id = id;
		this.position = position;
		this.movement = movement;
	}

	/**
	 * Moves the Geroid object for one Tick. Movement pattern is specified in Movement object.
	 */
	public void move() {
		position.update(movement);
	}
	
	/**
	 * Returns a JSONObject representation of the Geroid for further usage. Included attributes: position
	 * @return JSONObject representing current Geroid.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject(){
	
			JSONObject obj = new JSONObject();
			
			obj.put("position", this.position.toJSONObject());
			obj.put("id", this.id);
		
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
	public int getId() {
		return id;
	}
}
