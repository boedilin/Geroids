package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

public class Figure {

	private Position position;
	public static final int X_WIDTH_FIGURE = 60;
	public static final int Y_HEIGHT_FIGURE = 90;
	public static final int MOVE_WIDTH = 10;

	/**
	 * Creates a Figure at the given startPosition
	 * @param position Start Position of the Figure object.
	 */
	public Figure(Position position) {
		this.position = position;
	}

	/**
	 * Moves the figure to the left. Amount is set in constant MOVE_WIDTH.
	 */
	public void moveLeft() {
		if (getPosition().getxCoordiante() - Figure.MOVE_WIDTH >= Game.LEFT_BOARDER) {
			position.setxCoordiante(position.getxCoordiante() - Figure.MOVE_WIDTH);
		}
	}

	/**
	 * Moves the figure to the right. Amount is set in constant MOVE_WIDTH.
	 */
	public void moveRight() {
		if (getPosition().getxCoordiante() + Figure.MOVE_WIDTH <= Game.RIGHT_BOARDER - Figure.X_WIDTH_FIGURE) {
			position.setxCoordiante(position.getxCoordiante() + Figure.MOVE_WIDTH);
		}
	}

	/**
	 * Shoots a projectile out of the top middle of the figure.
	 * @return the projectile object that has been created.
	 */
	public Projectile shoot() {

		int xPosInMiddleOfFigure = this.position.getxCoordiante() + this.position.getxLength() / 2;
		int xPosMiddleWithProjectileWidth = xPosInMiddleOfFigure - Projectile.PROJECTILE_SIZE / 2;

		Position pos = new Position(xPosMiddleWithProjectileWidth,
				this.position.getyCoordiante() - Projectile.PROJECTILE_SIZE, Projectile.PROJECTILE_SIZE,
				Projectile.PROJECTILE_SIZE);
		Projectile projectile = new Projectile(pos);
		return projectile;

	}

	/**
	 * Translates the Object inclusive the position attribute into a JSONObject
	 * @return JSONObject of figure
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();

		obj.put("position", this.position.toJSONObject());

		return obj;
	}

	public Position getPosition() {
		return this.position;
	}

	public int getXCoordinate() {
		return position.getxCoordiante();
	}

	public int getYCoordinate() {
		return position.getyCoordiante();
	}
}
