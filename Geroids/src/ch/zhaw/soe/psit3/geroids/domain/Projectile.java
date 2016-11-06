package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Projectile implements Runnable{

	private Position position;
	private Movement movement;
	private Timer timer = new Timer();
	private int fps;
	
	private boolean DUMMY_COLLISION_OCCURED = false; // Replace by boolean with real collision detections
	
	public Projectile(Position position, Movement movement, int fps) {
		super();
		this.position = position;
		this.movement = movement;
		this.fps = fps;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	private void updatePosition(){
		this.position.setxCoordiante(this.position.getxCoordiante() + this.movement.getxSpeed());
		this.position.setyCoordiante(this.position.getyCoordiante() + this.movement.getySpeed());

	}
	
	
	/**
	 * Gets called when creating a Thread with a Projecile runnable. 
	 * Update thr projectiles position at the given this.fps rate until DUMMY_COLLISION_OCCURED is true
	 * 
	 * @see java.lang.Runnable#run()
	 **/
	@Override
	public void run(){
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				updatePosition();
				if(DUMMY_COLLISION_OCCURED){
					timer.cancel();
				}
			}
		},0,new Long(1000/fps));
		
	}
}
