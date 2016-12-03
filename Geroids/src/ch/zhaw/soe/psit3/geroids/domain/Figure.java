package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

public class Figure {


	private Position position;
	private Skin skin;
	private Type type;
	private static final Movement MOVEMENT_FOR_PROJECTILE = new Movement(0,-10);
	private final int PROJECTILE_SIZE = 10;

	public Figure(Type type, Skin skin, int x, int y){
		this.skin = skin;
		this.type = type;
		this.position = new Position(x, y);
	}
	
	public Figure(Position position){
		this.position = position;
	}
	
	/**
	 * Moves the figure to the Left
	 * @param amount Amount of pixels the figure should move to the left
	 */
	public void moveLeft(){
		if (getPosition().getxCoordiante() - Game.MOVE_WIDTH >= Game.LEFT_BOARDER) {
			position.setxCoordiante(this.position.getxCoordiante()-Game.MOVE_WIDTH);
		}
	}
	
	/**
	 * Moves the figure to the right
	 * @param amount Amount of pixels the figure should move to the right
	 */
	public void moveRight(){
		if (getPosition().getxCoordiante() + Game.MOVE_WIDTH <= Game.X_WIDTH - getPosition().getxLength()) {
			position.setxCoordiante(this.position.getxCoordiante()+Game.MOVE_WIDTH);
		}
	}
	
	/**
	 * Shoots a projectile out of the top middle of the figure. The projectile has a y-Speed of -10 pixel/tick
	 * @return the projectile that has been shoot
	 */
	public Projectile shoot(){
		
		int xPosInMiddleOfFigure = this.position.getxCoordiante() + this.position.getxLength()/2;
		int xPosMiddleWithProjectileWidth = xPosInMiddleOfFigure - PROJECTILE_SIZE/2;
		
		Position pos = new Position(xPosMiddleWithProjectileWidth, this.position.getyCoordiante()-10,PROJECTILE_SIZE,PROJECTILE_SIZE);
		Projectile projectile = new Projectile(pos, MOVEMENT_FOR_PROJECTILE);
		return projectile;
		
	}
	
	/**
	 * Translates the Object inclusive the position attribute into a JSONObject
	 * @return JSONObject of figure
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject(){
		JSONObject obj = new JSONObject();
		
		obj.put("position", this.position.toJSONObject());

		return obj;
	}

	public Position getPosition() {

		return this.position;
	}
	//for tests only
	public int getXCoordinate(){
		return position.getxCoordiante();
	}
	//for tests only
	public int getYCoordinate(){
		return position.getyCoordiante();
	}
}
