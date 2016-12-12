package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CollisionHandler {

	private int yRange;
	private HashMap<Geroid, ArrayList<Position>> geroidsCollisionPoints;
	private ArrayList<Position> figureCollisionPoints;
	private Position firstCollisionPoint;
	private Position secondCollisionPoint;
	private Position leftOrHigherPoint;
	private Position rightOrLowerPoint;
	private boolean isTheRightPointOfProjectile;
	private boolean isFigure;

	/**
	 * Creates a CollisionHandler with a certain y-axis-Range
	 * @param yRange, the range of the y-axis
	 */
	public CollisionHandler(int yRange) throws NullPointerException {
		geroidsCollisionPoints = new HashMap<Geroid, ArrayList<Position>>();
		figureCollisionPoints = new ArrayList<Position>();
		this.yRange = yRange;
	}

	/**
	 * Checks if there are any collisions of geroids and figure
	 * 
	 * @param ArrayList<Geroid> with all geroids in game and Figure in game
	 * @return true if there is a geroid, which collided with figure
	 */
	public boolean checkAllGeroidsCollisionWithFigure(Figure figure, ArrayList<Geroid> geroids) {
		Iterator<Geroid> geroidIterator = geroids.iterator();
		while (geroidIterator.hasNext()) {
			Geroid myGeroid = geroidIterator.next();
			isFigure = true;
			for (Position position : figureCollisionPoints) {
				if (checkCollisionWithGeroid(myGeroid, position)) {
					isFigure = false;
					return true;
				}
			}
		}
		isFigure = false;
		return false;
	}

	/**
	 * Checks if there is a collisions of a geroid and a projectile
	 * 
	 * @param  One specific Geroid and Projectile in game
	 * @return true, if there was a collision; false, else
	 * @throws NullPointerExcepiton
	 *             if geroid or projectile is null
	 */
	public boolean checkCollisionWithGeroid(Geroid geroid, Position position) throws NullPointerException {
		if (geroid == null || position == null) {
			throw new NullPointerException();
		}
		if (checkRectangleRange(geroid, position, "y")) {
			if (checkRectangleRange(geroid, position, "x")) {
				if (checkOnWhichLineRectangleRange(geroid, position)) {
					if (checkCollisionBetweenGeroidAndObject(geroid, position)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks if geroid is out of gamefield
	 * 
	 * @param Geroid
	 *            to test
	 * @return true if geroid is out of gamefield
	 * @throws NullPointerExcepiton
	 *             if geroid is null
	 */
	public boolean checkIfGeroidIsOutOfGamefield(Geroid geroid) throws NullPointerException {
		if (geroid == null) {
			throw new NullPointerException();
		}

		if (geroid.getPosition().getyCoordiante() > this.yRange) {
			return true;
		}
		return false;

	}

	/**
	 * Checks if a projectile is out of gamefield
	 * 
	 * @param Projectile
	 *            to test
	 * @return true if projectile is out of gamefield
	 * @throws NullPointerExcepiton
	 *             if projectile is null
	 */
	public boolean checkIfProjectileIsOutOfGamefield(Projectile projectile) throws NullPointerException {
		if (projectile == null) {
			throw new NullPointerException();
		}
		int yCoord = projectile.getPosition().getyCoordiante();
		int yLength = projectile.getPosition().getyLength();

		if ((yCoord + yLength) < 0) {
			return true;
		}
		return false;
	}

	public void addGeroidsCollisionPoints(Geroid geroid) {
		geroidsCollisionPoints.put(geroid, generateCollisionpoints(geroid));
	}

	public void updateCollisionPoints(Geroid geroid) {
		for (Position position : geroidsCollisionPoints.get(geroid)) {
			position.update(geroid.getMovement());
		}
	}

	public void generateFigureCollisionPoints(Figure figure) throws NullPointerException {
		if (figure == null) {
			throw new NullPointerException();
		}
		figureCollisionPoints.clear();
		figureCollisionPoints.add(
				new Position(figure.getPosition().getxCoordiante() + 27, figure.getPosition().getyCoordiante() + 3));
		figureCollisionPoints.add(
				new Position(figure.getPosition().getxCoordiante() + 33, figure.getPosition().getyCoordiante() + 3));
		figureCollisionPoints.add(
				new Position(figure.getPosition().getxCoordiante() + 15, figure.getPosition().getyCoordiante() + 16));
		figureCollisionPoints.add(
				new Position(figure.getPosition().getxCoordiante() + 46, figure.getPosition().getyCoordiante() + 16));
		figureCollisionPoints.add(
				new Position(figure.getPosition().getxCoordiante() + 2, figure.getPosition().getyCoordiante() + 54));
		figureCollisionPoints.add(
				new Position(figure.getPosition().getxCoordiante() + 58, figure.getPosition().getyCoordiante() + 54));
		figureCollisionPoints.add(
				new Position(figure.getPosition().getxCoordiante() + 2, figure.getPosition().getyCoordiante() + 63));
		figureCollisionPoints.add(
				new Position(figure.getPosition().getxCoordiante() + 58, figure.getPosition().getyCoordiante() + 63));
		figureCollisionPoints.add(
				new Position(figure.getPosition().getxCoordiante() + 2, figure.getPosition().getyCoordiante() + 72));
		figureCollisionPoints.add(
				new Position(figure.getPosition().getxCoordiante() + 58, figure.getPosition().getyCoordiante() + 72));
	}

	private boolean checkCollisionBetweenGeroidAndObject(Geroid geroid, Position position) throws NullPointerException {
		if (geroid == null || position == null) {
			throw new NullPointerException();
		}
		int yCoordinateFromPointOnLine = getYCoordinateFromPointOnLine(position, firstCollisionPoint);
		if (checkIfInRange(firstCollisionPoint.getyCoordiante(), yCoordinateFromPointOnLine, position.getyCoordiante())
				|| checkIfInRange(secondCollisionPoint.getyCoordiante(), yCoordinateFromPointOnLine,
						position.getyCoordiante())) {
			return true;
		}
		if (Math.abs(firstCollisionPoint.getyCoordiante() - secondCollisionPoint.getyCoordiante()) < 20) {
			if (firstCollisionPoint.getxCoordiante() < secondCollisionPoint.getxCoordiante()) {
				if (checkIfInRange(firstCollisionPoint.getxCoordiante(), secondCollisionPoint.getxCoordiante(),
						position.getxCoordiante())) {
					return true;
				}
			}
			if (checkIfInRange(secondCollisionPoint.getxCoordiante(), firstCollisionPoint.getxCoordiante(),
					position.getxCoordiante())) {
				return true;
			}
		}
		return false;
	}

	private int getYCoordinateFromPointOnLine(Position position, Position higher) {
		double xRange = (double) Math.abs(firstCollisionPoint.getxCoordiante() - secondCollisionPoint.getxCoordiante());
		double yRange = (double) Math.abs(firstCollisionPoint.getyCoordiante() - secondCollisionPoint.getyCoordiante());
		double rate = (double) ((Math.abs(position.getxCoordiante() - higher.getxCoordiante())) / xRange);
		int yCoordinateFromPointOnLine;
		if (isTheRightPointOfProjectile) {
			rate = (double) ((Math
					.abs(position.getxCoordiante() + Projectile.PROJECTILE_SIZE - higher.getxCoordiante())) / xRange);
			isTheRightPointOfProjectile = false;
		}
		yCoordinateFromPointOnLine = higher.getyCoordiante() + (int) Math.round(rate * yRange);
		return yCoordinateFromPointOnLine;
	}

	private boolean checkOnWhichLineRectangleRange(Geroid geroid, Position position) throws NullPointerException {
		if (geroid == null || position == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < geroidsCollisionPoints.get(geroid).size(); i++) {
			checkIfLastPoint(geroid, i);
			if (leftOrHigherPoint.getxCoordiante() > rightOrLowerPoint.getxCoordiante()) {
				swapPoints();
			}
			if (!isFigure) {
				if (checkIfInRange(leftOrHigherPoint.getxCoordiante(), rightOrLowerPoint.getxCoordiante(),
						position.getxCoordiante() + Projectile.PROJECTILE_SIZE)) {
					this.isTheRightPointOfProjectile = true;
					
					if (leftOrHigherPoint.getyCoordiante() > rightOrLowerPoint.getyCoordiante()) {
						swapPoints();
					}
					if (checkIfInRange(leftOrHigherPoint.getyCoordiante(), rightOrLowerPoint.getyCoordiante(),
							position.getyCoordiante())) {
						this.firstCollisionPoint = leftOrHigherPoint;
						this.secondCollisionPoint = rightOrLowerPoint;
						return true;
					}
				}
			}
			if (checkIfInRange(leftOrHigherPoint.getxCoordiante(), rightOrLowerPoint.getxCoordiante(),
					position.getxCoordiante())) {
				if (leftOrHigherPoint.getyCoordiante() > rightOrLowerPoint.getyCoordiante()) {
					swapPoints();
				}
				if (checkIfInRange(leftOrHigherPoint.getyCoordiante(), rightOrLowerPoint.getyCoordiante(),
						position.getyCoordiante())) {
					this.firstCollisionPoint = leftOrHigherPoint;
					this.secondCollisionPoint = rightOrLowerPoint;
					return true;
				}
			}
		}
		return false;
	}

	private void swapPoints() {
		Position swap = leftOrHigherPoint;
		leftOrHigherPoint = rightOrLowerPoint;
		rightOrLowerPoint = swap;
	}

	private void checkIfLastPoint(Geroid geroid, int i) {
		if (i == geroidsCollisionPoints.get(geroid).size() - 1) {
			leftOrHigherPoint = geroidsCollisionPoints.get(geroid).get(i);
			rightOrLowerPoint = geroidsCollisionPoints.get(geroid).get(0);
		} else {
			leftOrHigherPoint = geroidsCollisionPoints.get(geroid).get(i);
			rightOrLowerPoint = geroidsCollisionPoints.get(geroid).get(i + 1);
		}
	}

	private ArrayList<Position> selectLimitPositions(ArrayList<Position> positions, String xory)
			throws NullPointerException {
		if (positions == null) {
			throw new NullPointerException();
		}
		Position min = positions.get(0);
		Position max = positions.get(0);

		if (xory.equals("x")) {
			for (int i = 1; i < positions.size(); i++) {
				if (positions.get(i).getxCoordiante() < min.getxCoordiante()) {
					min = positions.get(i);
				}
				if (positions.get(i).getxCoordiante() > max.getxCoordiante()) {
					max = positions.get(i);
				}
			}
		} else {
			for (int i = 1; i < positions.size(); i++) {
				if (positions.get(i).getyCoordiante() < min.getyCoordiante()) {
					min = positions.get(i);
				}
				if (positions.get(i).getyCoordiante() > max.getyCoordiante()) {
					max = positions.get(i);
				}
			}
		}

		ArrayList<Position> returnArray = new ArrayList<Position>();
		returnArray.add(min);
		returnArray.add(max);

		return returnArray;
	}

	private boolean checkIfInRange(int low, int hi, int search) {
		if (search >= low & hi >= search) {
			return true;
		}
		return false;
	}

	private boolean checkRectangleRange(Geroid geroid, Position position, String xory) throws NullPointerException {
		if (geroid == null || position == null || xory == null) {
			throw new NullPointerException();
		}
		int checkPositionValue;
		int mostLeftOrTop;
		int mostRightOrLowest;
		ArrayList<Position> sortedList;
		if (xory.equals("x")) {
			sortedList = selectLimitPositions(geroidsCollisionPoints.get(geroid), xory);
			checkPositionValue = position.getxCoordiante();
			mostLeftOrTop = sortedList.get(0).getxCoordiante();
			mostRightOrLowest = sortedList.get(1).getxCoordiante();
		} else {
			sortedList = selectLimitPositions(geroidsCollisionPoints.get(geroid), xory);
			checkPositionValue = position.getyCoordiante();
			mostLeftOrTop = sortedList.get(0).getyCoordiante();
			mostRightOrLowest = sortedList.get(1).getyCoordiante();
		}
		if (isFigure) {
			if (checkIfInRange(mostLeftOrTop, mostRightOrLowest, checkPositionValue))
				return true;
		} else if (checkIfInRange(mostLeftOrTop, mostRightOrLowest, checkPositionValue)
				|| checkIfInRange(mostLeftOrTop, mostRightOrLowest, checkPositionValue + Projectile.PROJECTILE_SIZE))
			return true;
		return false;
	}

	private ArrayList<Position> generateCollisionpoints(Geroid geroid) {
		ArrayList<Position> collisionPoints = new ArrayList<Position>();
		switch (geroid.getId()) {
		case 1:
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 23,
					geroid.getPosition().getyCoordiante() + 27));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 79,
					geroid.getPosition().getyCoordiante() + 43));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 63,
					geroid.getPosition().getyCoordiante() + 100));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 11,
					geroid.getPosition().getyCoordiante() + 81));
			break;
		case 2:
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 17,
					geroid.getPosition().getyCoordiante() + 47));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 36,
					geroid.getPosition().getyCoordiante() + 100));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 88,
					geroid.getPosition().getyCoordiante() + 81));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 69,
					geroid.getPosition().getyCoordiante() + 28));
			break;
		case 3:
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 21,
					geroid.getPosition().getyCoordiante() + 32));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 81,
					geroid.getPosition().getyCoordiante() + 70));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 29,
					geroid.getPosition().getyCoordiante() + 100));
			break;
		case 4:
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 59,
					geroid.getPosition().getyCoordiante() + 25));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 28,
					geroid.getPosition().getyCoordiante() + 68));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 71,
					geroid.getPosition().getyCoordiante() + 100));
			break;
		case 5:
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 31,
					geroid.getPosition().getyCoordiante() + 35));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 96,
					geroid.getPosition().getyCoordiante() + 56));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 4,
					geroid.getPosition().getyCoordiante() + 95));
			break;
		}
		return collisionPoints;
	}
}