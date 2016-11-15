package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import org.json.simple.JSONObject;

public class Position {

	private int xCoordiante;
	private int yCoordiante;
	private int xLength = 100;
	private int yLength = 100;

	public Position(int xCoordiante, int yCoordiante) {
		this.xCoordiante = xCoordiante;
		this.yCoordiante = yCoordiante;
		this.xLength = 1;
		this.yLength = 1;
	}

	public Position(int xCoordiante, int yCoordiante, int xLength, int yLength) {
		this.xCoordiante = xCoordiante;
		this.yCoordiante = yCoordiante;
		this.xLength = xLength;
		this.yLength = yLength;
	}


	
	public void update(Movement movement){
		this.xCoordiante += movement.getxSpeed();
		this.yCoordiante += movement.getySpeed();
	}
	
	/**
	 * Converts the Position into a JSONObject for further usage. Contains the start Coordinates for x any y as well as the x and y Length relative to the start point
	 * @return JSONObject Representation of the Position object.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject(){
		
		JSONObject myPosition = new JSONObject();
		myPosition.put("xStart", xCoordiante);
		myPosition.put("yStart", yCoordiante);
		myPosition.put("xLength", xLength);
		myPosition.put("yLength", yLength);
		
		return myPosition;
		
	}


	public int getxCoordiante() {
		return xCoordiante;
	}

	public void setxCoordiante(int xCoordiante) {
		this.xCoordiante = xCoordiante;
	}

	public int getyCoordiante() {
		return yCoordiante;
	}

	public void setyCoordiante(int yCoordiante) {
		this.yCoordiante = yCoordiante;
	}
	
	public int getxLength() {
		return xLength;
	}
	
	public int getyLength() {
		return yLength;
	}
	

	

}
