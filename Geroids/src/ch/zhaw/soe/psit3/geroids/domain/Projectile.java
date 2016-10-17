package ch.zhaw.soe.psit3.geroids.domain;

public class Projectile {

	private Position position;
	private Movement movement;

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

}
