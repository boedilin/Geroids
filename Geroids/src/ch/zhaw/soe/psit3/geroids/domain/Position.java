package ch.zhaw.soe.psit3.geroids.domain;

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

	/**
	 * Update x and y coordinate with movement speed.
	 * @param Movement
	 */
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

	/**
	 * Return the x-coordinate.
	 * @return int x-coordinate.
	 */
	public int getxCoordiante() {
		return xCoordiante;
	}

	/**
	 * Set the x-coordinate.
	 * @param xCoordinate
	 */
	public void setxCoordiante(int xCoordiante) {
		this.xCoordiante = xCoordiante;
	}

	/**
	 * Return the y-coordinate.
	 * @return int y-coordinate.
	 */
	public int getyCoordiante() {
		return yCoordiante;
	}

	/**
	 * Set the y-coordinate.
	 * @param yCoordinate
	 */
	public void setyCoordiante(int yCoordiante) {
		this.yCoordiante = yCoordiante;
	}
	
	/**
	 * Return the x-length.
	 * @return int x-length.
	 */
	public int getxLength() {
		return xLength;
	}
	
	/**
	 * Return the y-length.
	 * @return int y-length.
	 */
	public int getyLength() {
		return yLength;
	}
	

	

}
