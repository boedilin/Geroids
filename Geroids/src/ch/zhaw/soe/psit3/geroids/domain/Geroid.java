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

	public void die() {

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
