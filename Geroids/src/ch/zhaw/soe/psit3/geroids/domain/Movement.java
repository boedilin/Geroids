
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
	 * Changes the X Speed by the provided factor
	 * @param x The factor the X Speed should be increased
	 */
	public void changeXSpeedByGivenFactor(int x) {
		this.xSpeed = xSpeed * x;
	}

	/**
	 * Changes the Y Speed by the given amount
	 * @param y The factor the Y Speed should be increased
	 */
	public void changeYSpeedByGivenFactor(int y) {
		this.ySpeed = ySpeed * y;
	}
	

}
