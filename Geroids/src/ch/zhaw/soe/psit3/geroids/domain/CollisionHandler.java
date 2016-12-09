package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CollisionHandler {

	private ArrayList<Geroid> geroids;
	private Figure figure;
	private int yRange = 1000;
	private HashMap<Geroid, ArrayList<Position>> geroidsCollisionPoints;
	private ArrayList<Position> figureCollisionPoints;
	private Position firstCollisionPoint;
	private Position secondCollisionPoint;
	private boolean isTheRightPointOfProjectile;
	private boolean isFigure;

	public CollisionHandler(Figure figure, ArrayList<Geroid> geroids) throws NullPointerException {
		if (figure == null || geroids == null) {
			throw new NullPointerException();
		}
		this.geroids = geroids;
		this.figure = figure;
		geroidsCollisionPoints = new HashMap<Geroid, ArrayList<Position>>();
		figureCollisionPoints = new ArrayList<Position>();
	}

	/**
	 * Checks if there are any collisions of geroids and figure
	 * 
	 * @return true if there is a geroid, which collided with figure
	 */
	public boolean checkAllGeroidsCollisionWithFigure() {
		Iterator<Geroid> geroidIterator = geroids.iterator();
		while (geroidIterator.hasNext()) {
			Geroid myGeroid = geroidIterator.next();
			isFigure = true;
			for(Position position : figureCollisionPoints){
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
	 * @return true, if there was a collision; false, else
	 * @throws NullPointerExcepiton
	 *             if geroid or projectile is null
	 */
	public boolean checkCollisionWithGeroid(Geroid geroid, Position position)
			throws NullPointerException {
		if (geroid == null || position == null) {
			throw new NullPointerException();
		}
		// int projectileLeftMostPosition =
		// projectile.getPosition().getxCoordiante();
		// int projectileRightMostPosition = projectileLeftMostPosition +
		// projectile.getPosition().getxLength();
		// int geroidLeftMostPoint = geroid.getPosition().getxCoordiante();
		// int geroidRightMostPoint = geroidLeftMostPoint +
		// geroid.getPosition().getxLength();
		//
		// int projectileHighestPoint =
		// projectile.getPosition().getyCoordiante();
		// int geroidLowestPoint = geroid.getPosition().getyCoordiante() +
		// geroid.getPosition().getyLength();
		//
		// if (isInBetween(projectileLeftMostPosition,
		// projectileRightMostPosition, geroidRightMostPoint)
		// | isInBetween(projectileLeftMostPosition,
		// projectileRightMostPosition, geroidLeftMostPoint)
		// | isInBetween(geroidLeftMostPoint, geroidRightMostPoint,
		// projectileLeftMostPosition)) { // xcoords
		//
		// if (projectileHighestPoint < geroidLowestPoint) {
		// return true;
		// } else {
		// return false;
		// }
		// } else {
		// return false;
		// }
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

	private boolean checkIfGeroidIsCollidingWithFigure(Geroid geroid) {
		int figureLeftMostPoint = this.figure.getPosition().getxCoordiante();
		int figureRightMostPoint = figureLeftMostPoint + this.figure.getPosition().getxLength();
		int geroidLeftMostPoint = geroid.getPosition().getxCoordiante();
		int geroidRightMostPoint = geroidLeftMostPoint + geroid.getPosition().getxLength();

		int figureHighestPoint = this.figure.getPosition().getyCoordiante();
		int geroidLowestPoint = geroid.getPosition().getyCoordiante() + geroid.getPosition().getyLength();

		if (isInBetween(figureLeftMostPoint, figureRightMostPoint, geroidRightMostPoint)
				| isInBetween(figureLeftMostPoint, figureRightMostPoint, geroidLeftMostPoint)
				| isInBetween(geroidLeftMostPoint, geroidRightMostPoint, figureLeftMostPoint)) { // xcoords

			if (figureHighestPoint < geroidLowestPoint) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean isInBetween(int leftBorder, int rightBorder, int searchedNumber) {
		if (searchedNumber > leftBorder & searchedNumber < rightBorder) {
			return true;
		} else {
			return false;
		}
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

	/**
	 * Updates figure, geroids
	 * 
	 * @param List
	 *            of geroids, figure
	 * @throws NullPointerExcepiton
	 *             if geroid or figure is null
	 */
	public void updateFigures(ArrayList<Geroid> geroids, Figure figure) throws NullPointerException {
		if (geroids == null || figure == null) {
			throw new NullPointerException();
		}
		this.geroids = geroids;
		this.figure = figure;
		generateFigureCollisionPoints(figure);
	}

	public void addGeroidsCollisionPoints(Geroid geroid) {
		geroidsCollisionPoints.put(geroid, generateCollisionpoints(geroid));
	}

	public void updateCollisionPoints(Geroid geroid) {
		for (Position position : geroidsCollisionPoints.get(geroid)) {
			position.update(geroid.getMovement());
		}
	}

	private void generateFigureCollisionPoints(Figure figure){
		figureCollisionPoints.clear();
		figureCollisionPoints.add(new Position(figure.getPosition().getxCoordiante() + 27, figure.getPosition().getyCoordiante() + 3));
		figureCollisionPoints.add(new Position(figure.getPosition().getxCoordiante() + 33, figure.getPosition().getyCoordiante() + 3));
		figureCollisionPoints.add(new Position(figure.getPosition().getxCoordiante() + 15, figure.getPosition().getyCoordiante() + 16));
		figureCollisionPoints.add(new Position(figure.getPosition().getxCoordiante() + 46, figure.getPosition().getyCoordiante() + 16));
		figureCollisionPoints.add(new Position(figure.getPosition().getxCoordiante() + 2, figure.getPosition().getyCoordiante() + 54));
		figureCollisionPoints.add(new Position(figure.getPosition().getxCoordiante() + 58, figure.getPosition().getyCoordiante() + 54));
		figureCollisionPoints.add(new Position(figure.getPosition().getxCoordiante() + 2, figure.getPosition().getyCoordiante() + 63));
		figureCollisionPoints.add(new Position(figure.getPosition().getxCoordiante() + 58, figure.getPosition().getyCoordiante() + 63));
		figureCollisionPoints.add(new Position(figure.getPosition().getxCoordiante() + 2, figure.getPosition().getyCoordiante() + 72));
		figureCollisionPoints.add(new Position(figure.getPosition().getxCoordiante() + 58, figure.getPosition().getyCoordiante() + 72));
	}
	
	private boolean checkCollisionBetweenGeroidAndObject(Geroid geroid, Position position) {
		Position higher = firstCollisionPoint;
		if (firstCollisionPoint.getyCoordiante() > secondCollisionPoint.getyCoordiante()) {
			higher = secondCollisionPoint;
		}
		int yCoordinateFromPointOnLine = getYCoordinateFromPointOnLine(position, higher);
		if (checkIfInRange(higher.getyCoordiante(),yCoordinateFromPointOnLine+2, position.getyCoordiante())){
			return true;
		}
		if (firstCollisionPoint.getyCoordiante() < secondCollisionPoint.getyCoordiante()) {
			if (higher.getyCoordiante() <= position.getyCoordiante()
					& position.getyCoordiante() >= yCoordinateFromPointOnLine+1) {
				return true;
			}
		}
		if (secondCollisionPoint.getyCoordiante() <= position.getyCoordiante()
				& position.getyCoordiante() >= yCoordinateFromPointOnLine+1) {
			return true;
		}
		return false;
	}

	private int getYCoordinateFromPointOnLine(Position position, Position higher) {
		double xRange = (double) Math.abs(firstCollisionPoint.getxCoordiante() - secondCollisionPoint.getxCoordiante());
		double yRange = (double) Math.abs(firstCollisionPoint.getyCoordiante() - secondCollisionPoint.getyCoordiante());
		double rate = (double) Math.abs(position.getxCoordiante() - higher.getxCoordiante()) / xRange;
		int yCoordinateFromPointOnLine;
		if (isTheRightPointOfProjectile) {
			rate = (double) Math.abs(position.getxCoordiante()+1 + Projectile.PROJECTILE_SIZE
					- higher.getxCoordiante()) / xRange;
			isTheRightPointOfProjectile = false;
		}
		yCoordinateFromPointOnLine = higher.getyCoordiante() + (int) Math.round(rate * yRange);
		return yCoordinateFromPointOnLine;
	}
	
	private boolean checkOnWhichLineRectangleRange(Geroid geroid, Position position) {
		Position leftOrHigherPoint;
		Position rightOrLowerPoint;
		for (int i = 0; i < geroidsCollisionPoints.get(geroid).size(); i++) {
			if (i == geroidsCollisionPoints.get(geroid).size() - 1) {
				leftOrHigherPoint = geroidsCollisionPoints.get(geroid).get(i);
				rightOrLowerPoint = geroidsCollisionPoints.get(geroid).get(0);
			}else{
				leftOrHigherPoint = geroidsCollisionPoints.get(geroid).get(i);
				rightOrLowerPoint = geroidsCollisionPoints.get(geroid).get(i+1);
			}
			if (leftOrHigherPoint.getxCoordiante() > rightOrLowerPoint.getxCoordiante()) {
				Position swap = leftOrHigherPoint;
				leftOrHigherPoint = rightOrLowerPoint;
				rightOrLowerPoint = swap;
			}

			if(isFigure){
				if (checkIfInRange(leftOrHigherPoint.getxCoordiante(), rightOrLowerPoint.getxCoordiante(), position.getxCoordiante())){
					if (leftOrHigherPoint.getyCoordiante() > rightOrLowerPoint.getyCoordiante()) {
						Position swap = leftOrHigherPoint;
						leftOrHigherPoint = rightOrLowerPoint;
						rightOrLowerPoint = swap;
					}
					if (checkIfInRange(leftOrHigherPoint.getyCoordiante(), rightOrLowerPoint.getyCoordiante(), position.getyCoordiante())) {
						this.firstCollisionPoint = leftOrHigherPoint;
						this.secondCollisionPoint = rightOrLowerPoint;
						return true;
					}
				}
			}
			
			if (checkIfInRange(leftOrHigherPoint.getxCoordiante(), rightOrLowerPoint.getxCoordiante(), position.getxCoordiante())
					|| checkIfInRange(leftOrHigherPoint.getxCoordiante(), rightOrLowerPoint.getxCoordiante(),
							position.getxCoordiante() + Projectile.PROJECTILE_SIZE)) {
				if (checkIfInRange(leftOrHigherPoint.getxCoordiante(), rightOrLowerPoint.getxCoordiante(),
						position.getxCoordiante() + Projectile.PROJECTILE_SIZE)) {
					this.isTheRightPointOfProjectile = true;
				}
				if (leftOrHigherPoint.getyCoordiante() > rightOrLowerPoint.getyCoordiante()) {
					Position swap = leftOrHigherPoint;
					leftOrHigherPoint = rightOrLowerPoint;
					rightOrLowerPoint = swap;
				}
				if (checkIfInRange(leftOrHigherPoint.getyCoordiante(), rightOrLowerPoint.getyCoordiante(), position.getyCoordiante())) {
					this.firstCollisionPoint = leftOrHigherPoint;
					this.secondCollisionPoint = rightOrLowerPoint;
					return true;
				}
			}
		}
		return false;
	}

	private ArrayList<Position> selectXLimitPositions(ArrayList<Position> positions) {
		Position min = positions.get(0);
		Position max = positions.get(0);

		for (int i = 1; i < positions.size(); i++) {
			if (positions.get(i).getxCoordiante() < min.getxCoordiante()) {
				min = positions.get(i);
			}
			if (positions.get(i).getxCoordiante() > max.getxCoordiante()) {
				max = positions.get(i);
			}
		}
		ArrayList<Position> returnArray = new ArrayList<Position>();
		returnArray.add(min);
		returnArray.add(max);

		return returnArray;
	}

	private ArrayList<Position> selectYLimitPositions(ArrayList<Position> positions) {
		Position min = positions.get(0);
		Position max = positions.get(0);

		for (int i = 1; i < positions.size(); i++) {
			if (positions.get(i).getyCoordiante() < min.getyCoordiante()) {
				min = positions.get(i);
			}
			if (positions.get(i).getyCoordiante() > max.getyCoordiante()) {
				max = positions.get(i);
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

	private boolean checkRectangleRange(Geroid geroid, Position position, String xory) {
		int checkPositionValue;
		int mostLeftOrTop;
		int mostRightOrLowest;
		ArrayList<Position> sortedList;
		if (xory.equals("x")) {
			sortedList = selectXLimitPositions(geroidsCollisionPoints.get(geroid));
			checkPositionValue = position.getxCoordiante();
			mostLeftOrTop = sortedList.get(0).getxCoordiante();
			mostRightOrLowest = sortedList.get(1).getxCoordiante();
		} else {
			sortedList = selectYLimitPositions(geroidsCollisionPoints.get(geroid));
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
					geroid.getPosition().getyCoordiante() +81));
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
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 25,
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
					geroid.getPosition().getyCoordiante() + 62));
			collisionPoints.add(new Position(geroid.getPosition().getxCoordiante() + 4,
					geroid.getPosition().getyCoordiante() + 95));
			break;
		}
		return collisionPoints;
	}
}
