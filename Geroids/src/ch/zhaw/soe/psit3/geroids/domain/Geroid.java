package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

public abstract class Geroid {

	private String name;
	private int id;
	private String shape;
	private Position position;
	private Movement movement;

	public void move() {
		
		//Beispiel:
		JSONObject obj = new JSONObject();
		
		String name = "kaderli";
	    obj.put("name", name);
	    obj.put("num", new Integer(100));
	    obj.put("balance", new Double(1000.21));
	    obj.put("is_vip", new Boolean(true));

	    System.out.print(obj);
	      
	      //System.out.print(); calls the toString method.
	      // obj.toJSONString(); machts explizit. 
		obj.toJSONString();
		
	}

	public void die() {

	}
}
