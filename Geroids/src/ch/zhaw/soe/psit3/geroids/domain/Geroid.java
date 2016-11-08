//TODO implement ToJSON() method

package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import org.json.simple.JSONArray;
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
	private final String  delimeterForJSONPositions = ":";
	
	
	public Geroid(int id, String shape, Position position, Movement movement) {
		super();
		this.id = id;
		this.shape = shape;
		this.position = position;
		this.movement = movement;
	}

	public void move() {
		
		
	}

	public void die(Gamefield gamefield) {
		gamefield.getGeroidList().remove(this);
	}
	
	/**
	 * Returns a JSON representation of the Geroid. Included attributes: id, shape, positions
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toJSON(){
			
			ArrayList<Position> positionArray= position.getPositionArray();
			JSONArray JSONPositionArray = new JSONArray();
			for(Position x : positionArray){
				JSONPositionArray.add(x.getxCoordiante() + delimeterForJSONPositions + x.getyCoordiante() );
			}
			
			JSONObject obj = new JSONObject();
		    obj.put("id", id);
		    obj.put("shape", shape);
			obj.put("position", JSONPositionArray);
  
			return obj.toJSONString();	
	}
	
	public void setShapeSize(int length, int width){
		this.position.setExpansion(length, width);
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
