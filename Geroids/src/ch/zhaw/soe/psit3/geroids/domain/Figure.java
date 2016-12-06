package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

public class Figure {


	private Position position;
	private Skin skin;
	private Type type;
	public static final int X_WIDTH_FIGURE = 60;
	public static final int Y_HEIGHT_FIGURE = 90;
	public static final int MOVE_WIDTH = 10;

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
	 */
	public void moveLeft(){
		if (getPosition().getxCoordiante() - Figure.MOVE_WIDTH >= Game.LEFT_BOARDER) {
			position.setxCoordiante(position.getxCoordiante()-Figure.MOVE_WIDTH);
		}
	}
	
	/**
	 * Moves the figure to the right
	 */
	public void moveRight(){
		if (getPosition().getxCoordiante() + Figure.MOVE_WIDTH <= Game.RIGHT_BOARDER - Figure.X_WIDTH_FIGURE) {
			position.setxCoordiante(position.getxCoordiante()+Figure.MOVE_WIDTH);
		}
	}
	
	/**
	 * Shoots a projectile out of the top middle of the figure. The projectile has a y-Speed of -10 pixel/tick
	 * @return the projectile that has been shoot
	 */
	public Projectile shoot(){
		
		int xPosInMiddleOfFigure = this.position.getxCoordiante() + this.position.getxLength()/2;
		int xPosMiddleWithProjectileWidth = xPosInMiddleOfFigure - Projectile.PROJECTILE_SIZE/2;
		
		Position pos = new Position(xPosMiddleWithProjectileWidth,
				this.position.getyCoordiante()-Projectile.PROJECTILE_SIZE,
				Projectile.PROJECTILE_SIZE,
				Projectile.PROJECTILE_SIZE);
		Projectile projectile = new Projectile(pos);
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
