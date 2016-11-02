package ch.zhaw.soe.psit3.geroids.domain;

public class Movement {

	private int xSpeed;
	private int ySpeed;

	/**
	 * Creates Movement Object with two directional speeds
	 * 
	 * @param xSpeed Speed in x direction
	 * @param ySpeed Speed in y direction
	 */
	public Movement(int xSpeed, int ySpeed) {
		super();
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	/**
	 * Increases the X Speed by the given amount
	 * @param x The amount of Speed to increase the current ySpeed
	 */
	public void increaseXSpeed(int x) {
		this.xSpeed = xSpeed + x;
	}

	/**
	 * Increases the XY Speed by the given amount
	 * @param y The amount of Speed to increase the current ySpeed
	 */
	public void increaseYSpeed(int y) {
		this.ySpeed = ySpeed + y;
	}
	

}
