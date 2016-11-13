// TODO implement shoot method


package ch.zhaw.soe.psit3.geroids.domain;

import java.util.concurrent.locks.Lock;

import org.json.simple.JSONObject;

import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class Figure {


	private Position position;
	private Skin skin;
	private Type type;
	private MyWebSocketHandler websocketHandler;


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
		
		Movement mov = new Movement(0, -10);
		int xPosInMiddleOfFigure = this.position.getxCoordiante() + this.position.getxLength()/2;
		
		Position pos = new Position(xPosInMiddleOfFigure, this.position.getyCoordiante()-10,20,20);
		Projectile projectile = new Projectile(pos, mov);
		game.getProjectiles().add(projectile);
	}
	
	@SuppressWarnings("unchecked")
	public String toJSON(){
		JSONObject obj = new JSONObject();
		
		obj.put("position", this.position.toJSONObject());
		//System.out.println(obj.toJSONString());
		return obj.toJSONString();
		
	}

	public Position getPosition() {

		return this.position;
	}

	

}
