package ch.zhaw.soe.psit3.geroids.domain;

import java.util.concurrent.locks.Lock;

public class Figure extends Thread{

	private Game game;
	private Lock lock;
	private Position position;
	private Skin skin;
	private Type type;
	private boolean isShooting = false;
	private static int MOVING_DELAY_IN_MS = 500;
	private static int NO_MOVING = 0;
	private static int MOVING_LEFT = 1;
	private static int MOVING_RIGHT = 2;
	
	public Figure(Game game, Type type, Skin skin, int x, int y){
		this.game = game;
		this.skin = skin;
		this.type = type;
		this.position = new Position(x, y);
		start();
	}
	
	public void run(){
		while(game.isRunning()){
			if(checkIfMoving() != 0){
				try {
					lock.lock();
					if(checkIfMoving() == 1){
						moveLeft();
					}
					else if (checkIfMoving() == 2){
						moveRight();
					}
					sleep(MOVING_DELAY_IN_MS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public int checkIfMoving(){//<<<hier kommt ein "Checking-Parameter", falls links oder rechts gedrückt wurde
		if(){//<<< hier kommt die Abfrage für den Parameter
			moveLeft();
			return MOVING_LEFT;
		}
		else if (){
			moveRight();
			return MOVING_RIGHT;
		}
		return NO_MOVING;
		
	}
	
	public void moveLeft(){
		this.position.setxCoordiante(this.position.getxCoordiante()-1);
	}
	
	public void moveRight(){
		this.position.setxCoordiante(this.position.getxCoordiante()+1);
	}
	
	public boolean shoot(){
		return isShooting = true;
	}
	
	//zum Testen
	public String getName(){
		return "figure";
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
