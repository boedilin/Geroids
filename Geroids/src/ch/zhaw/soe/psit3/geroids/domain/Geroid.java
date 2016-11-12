//TODO implement ToJSON() method

package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

/**
 * 
 * @author Matthias
 *
 * Allows creating and setting behaviour of a geroid.
 */

public class Geroid {

	private int id;
	private String shape;
	private Position position;
	private Movement movement;
	
	
	public Geroid(int id, String shape, Position position, Movement movement) {
		this.id = id;
		this.shape = shape;
		this.position = position;
		this.movement = movement;
	}
	
	public Geroid(Position position, Movement movement) {
		this.position = position;
		this.movement = movement;
	}

	public void move() {
		position.update(movement);
		
	}

	public void die(Gamefield gamefield) {
		gamefield.getGeroidList().remove(this);
	}
	
	/**
	 * Returns a JSON String representation of the Geroid. Included attributes: id, shape, position
	 * @return String Object in JSON Format representing current Geroid.
	 */
	@SuppressWarnings("unchecked")
	public String toJSON(){
			
			JSONObject JSONposition = position.toJSONObject();
			
			JSONObject obj = new JSONObject();
			obj.put("name", "Geroid");
			obj.put("position", JSONposition.toJSONString());
  
			return obj.toJSONString();	
	}
	
	/**
	 * Returns a JSONObject representation of the Geroid for further usage. Included attributes: id, shape, position
	 * @return JSONObject representing current Geroid.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject(){
			
	
			JSONObject obj = new JSONObject();
			obj.put("name", "Geroid");
			obj.put("position", this.position.toJSONObject());
  
		
			return obj;
	}
	

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
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
