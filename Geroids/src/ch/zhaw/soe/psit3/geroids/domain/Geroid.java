package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;


public class Geroid {

	private String name;
	private int id;
	private String shape;
	private Position position;
	private Movement movement;

	
	
	public Geroid(String name, int id, String shape, Position position, Movement movement) {
		super();
		this.name = name;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	/**
	 * Returns a JSON representation of the Geroid. Included attributes: name, id, shape, position
	 * @return
	 */
	public String toJSON(){
		
		
		//Beispiel:
				JSONObject obj = new JSONObject();
				
				
			    obj.put("name", name);
			    obj.put("id", id);
			    obj.put("shape", shape);
			    obj.put("position", position.getPositionArray());

			    System.out.print(obj);
			      
			      //System.out.print(); calls the toString method.
			      // obj.toJSONString(); machts explizit. 
				return obj.toJSONString();
				
	}
}
