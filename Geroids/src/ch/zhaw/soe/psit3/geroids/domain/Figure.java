package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

public class Figure {


	private Position position;
	private Skin skin;
	private Type type;
	private final Movement MOVEMENT_FOR_PROJECTILE = new Movement(0,-10);

	public Figure(Type type, Skin skin, int x, int y){
		this.skin = skin;
		this.type = type;
		this.position = new Position(x, y);
	}
	
	public Figure(Position position){
		this.position = position;
	}
	
	public void moveLeft(int amount){
		this.position.setxCoordiante(this.position.getxCoordiante()-amount);
	}
	
	public void moveRight(int amount){
		this.position.setxCoordiante(this.position.getxCoordiante()+amount);
	}
	
	public void shoot(Game game){
		
		int xPosInMiddleOfFigure = this.position.getxCoordiante() + this.position.getxLength()/2;
		
		Position pos = new Position(xPosInMiddleOfFigure, this.position.getyCoordiante()-10,20,20);
		Projectile projectile = new Projectile(pos, MOVEMENT_FOR_PROJECTILE);
		game.getProjectiles().add(projectile);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject(){
		JSONObject obj = new JSONObject();
		
		obj.put("position", this.position.toJSONObject());

		return obj;
	}

	public Position getPosition() {

		return this.position;
	}
}
