package ch.zhaw.soe.psit3.geroids.domain;

import java.util.concurrent.locks.Lock;

public class Figure extends Thread{

	private Game game;
	private Lock lock;
	private Position position;
	private Skin skin;
	private Type type;
	private static int SHOOTING_DELAY_IN_MS = 300;
	private static int MOVING_DELAY_IN_MS = 500;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean shooting = false;
	
	public Figure(Game game, Type type, Skin skin, int x, int y){
		this.game = game;
		this.skin = skin;
		this.type = type;
		this.position = new Position(x, y);
		start();
	}
	
	public void run(){
		while(game.isRunning()){
			if(shooting){
				try {
					lock.lock();
					shoot();
					sleep(SHOOTING_DELAY_IN_MS);
					lock.unlock();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(movingLeft || movingRight){
				try {
					lock.lock();
					if(movingLeft){
						moveLeft();
					}
					else if (movingRight){
						moveRight();
					}
					sleep(MOVING_DELAY_IN_MS);
					lock.unlock();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}

	public void moveLeft(){
		this.position.setxCoordiante(this.position.getxCoordiante()-1);
		movingLeft = false;
	}
	
	public void moveRight(){
		this.position.setxCoordiante(this.position.getxCoordiante()+1);
		movingRight = false;
	}
	
	public void shoot(){
		game.getProjectiles().add(new Projectile(game, new Position(game.getFigure().getPosition().getxCoordiante(), game.getFigure().getPosition().getyCoordiante()), game.getAccount().getActiveType().getMovement()));
		shooting = false;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
