package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import org.json.simple.JSONObject;

public class Position {

	private int xCoordiante;
	private int yCoordiante;
	private ArrayList<Position> positionArray = new ArrayList<Position>();
	private int xLength;
	private int yLength;

	public Position(int xCoordiante, int yCoordiante) {
		super();
		this.xCoordiante = xCoordiante;
		this.yCoordiante = yCoordiante;
		this.xLength = 1;
		this.yLength = 1;
		createPositionArray();
	}

	public Position(int xCoordiante, int yCoordiante, int xLength, int yLength) {
		super();
		this.xCoordiante = xCoordiante;
		this.yCoordiante = yCoordiante;
		this.xLength = xLength;
		this.yLength = yLength;
		createPositionArray();
	}

	private void createPositionArray() {
		for (int x = 1; x <= xLength; x++) {
			for (int y = 1; y < yLength; y++) {
				positionArray.add(new Position(xCoordiante + x, yCoordiante + y));
			}
		}
	}

	public ArrayList<Position> getPositionArray() {
		return positionArray;
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
	
	
	/**
	 * Converts the Position into a JSON String. String contains the start Coordinates for x any y as well as the x and y Length relative to the start point
	 * @return JSON representation of the Position object.
	 */
	@SuppressWarnings("unchecked")
	public String toJSON(){
		
		JSONObject myPosition = new JSONObject();
		myPosition.put("xStart", xCoordiante);
		myPosition.put("yStart", yCoordiante);
		myPosition.put("xLength", xLength);
		myPosition.put("yLength", yLength);
		
		return myPosition.toJSONString();
		
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
	

}
