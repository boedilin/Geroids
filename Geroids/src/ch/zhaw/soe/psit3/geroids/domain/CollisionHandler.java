package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CollisionHandler {

	private ArrayList<Geroid> geroids;
	private Figure figure;
	private int yRange = 1000;
	private HashMap<Geroid, ArrayList<Position>> geroidsCollisionPoints;
	private Position firstCollisionPoint;
	private Position secondCollisionPoint;
	private Position collisionPointForCheckingInside;
	private boolean isTheRightPointOfProjectile = false;

	public CollisionHandler(Figure figure, ArrayList<Geroid> geroids) throws NullPointerException {
		if (figure == null || geroids == null) {
			throw new NullPointerException();
		}
		this.geroids = geroids;
		this.figure = figure;
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
			if (checkIfGeroidIsCollidingWithFigure(myGeroid)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if there is a collisions of a geroid and a projectile
	 * 
	 * @return true, if there was a collision; false, else
	 * @throws NullPointerExcepiton
	 *             if geroid or projectile is null
	 */
	public boolean checkIfGeroidIsCollidingWithProjectile(Geroid geroid, Projectile projectile)
			throws NullPointerException {
		if (geroid == null || projectile == null) {
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

		if (isInRectangleRange(geroid, projectile, "y")) {
			if (isInRectangleRange(geroid, projectile, "x")) {
				if (checkOnWhichLineRectangleRange(geroid, projectile)) {
					if (checkCollisionBetweenGeroidAndProjectile(geroid, projectile)) {
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
	}

	public void addGeroidsCollisionPoints(Geroid geroid) {
		geroidsCollisionPoints.put(geroid, generateCollisionpoints(geroid));
	}

	private boolean checkCollisionBetweenGeroidAndProjectile(Geroid geroid, Projectile projectile) {
		int xRange = Math.abs(firstCollisionPoint.getxCoordiante() - secondCollisionPoint.getxCoordiante());
		int yRange = Math.abs(firstCollisionPoint.getyCoordiante() - secondCollisionPoint.getyCoordiante());
		int rate = (projectile.getPosition().getxCoordiante() - firstCollisionPoint.getxCoordiante()) / xRange;
		int yCoordinateFromPointOnLine;
		if (isTheRightPointOfProjectile) {
			rate = (projectile.getPosition().getxCoordiante() + Projectile.PROJECTILE_SIZE
					- firstCollisionPoint.getxCoordiante()) / xRange;
		}
		yCoordinateFromPointOnLine = firstCollisionPoint.getyCoordiante() + rate * yRange;
		if (yCoordinateFromPointOnLine == projectile.getPosition().getyCoordiante()) {
			return true;
		}
		if (yCoordinateFromPointOnLine < collisionPointForCheckingInside.getyCoordiante()) {
			if (isInBetween(collisionPointForCheckingInside.getyCoordiante(), yCoordinateFromPointOnLine,
					projectile.getPosition().getyCoordiante())) {
				return true;
			}
		}
		if (isInBetween(yCoordinateFromPointOnLine, collisionPointForCheckingInside.getyCoordiante(),
				projectile.getPosition().getyCoordiante())) {
			return true;
		}
		return false;
	}

	private boolean checkOnWhichLineRectangleRange(Geroid geroid, Projectile projectile) {
		Position highestPoint;
		Position lowestPoint;
		Position leftPoint;
		Position rightPoint;
		for (int i = 0; i < geroidsCollisionPoints.get(geroid).size(); i++) {
			Position firstPoint;
			Position secondPoint;
			if (i == geroidsCollisionPoints.get(geroid).size() - 1) {
				firstPoint = geroidsCollisionPoints.get(geroid).get(i);
				secondPoint = geroidsCollisionPoints.get(geroid).get(0);
			} else {
				firstPoint = geroidsCollisionPoints.get(geroid).get(i);
				secondPoint = geroidsCollisionPoints.get(geroid).get(i + 1);
			}
			if (firstPoint.getxCoordiante() < secondPoint.getxCoordiante()) {
				leftPoint = firstPoint;
				rightPoint = secondPoint;
			} else {
				rightPoint = firstPoint;
				leftPoint = secondPoint;
			}
			if (projectile.getPosition().getxCoordiante() > leftPoint.getxCoordiante()
					&& projectile.getPosition().getxCoordiante() < rightPoint.getxCoordiante()
					|| projectile.getPosition().getxCoordiante() + Projectile.PROJECTILE_SIZE > leftPoint
							.getxCoordiante()
							&& projectile.getPosition().getxCoordiante() + Projectile.PROJECTILE_SIZE < rightPoint
									.getxCoordiante()) {
				if (projectile.getPosition().getxCoordiante() + Projectile.PROJECTILE_SIZE > leftPoint.getxCoordiante()
						&& projectile.getPosition().getxCoordiante() + Projectile.PROJECTILE_SIZE < rightPoint
								.getxCoordiante()) {
					this.isTheRightPointOfProjectile = true;
				}
				if (firstPoint.getyCoordiante() < secondPoint.getyCoordiante()) {
					lowestPoint = firstPoint;
					highestPoint = secondPoint;
				} else {
					highestPoint = firstPoint;
					lowestPoint = secondPoint;
				}
				if (projectile.getPosition().getyCoordiante() > lowestPoint.getyCoordiante()
						&& projectile.getPosition().getyCoordiante() < highestPoint.getyCoordiante()) {
					this.firstCollisionPoint = firstPoint;
					this.secondCollisionPoint = secondPoint;
					if (isInBetween(0, geroidsCollisionPoints.get(geroid).size(), i)) {
						collisionPointForCheckingInside = geroidsCollisionPoints.get(geroid).get(0);
					} else if (i == 0) {
						collisionPointForCheckingInside = geroidsCollisionPoints.get(geroid)
								.get(geroidsCollisionPoints.get(geroid).size() - 1);
					} else {

					}
					return true;
				}
			}
		}
		return false;
	}

	private boolean isInRectangleRange(Geroid geroid, Projectile projectile, String xory) {
		Position mostLeftOrTop = null;
		Position mostRightOrLowest = null;
		for (int i = 0; i < geroidsCollisionPoints.get(geroid).size(); i++) {
			if (mostLeftOrTop.equals(null)) {
				mostLeftOrTop = geroidsCollisionPoints.get(geroid).get(i);
				mostRightOrLowest = geroidsCollisionPoints.get(geroid).get(i + 1);
			}
			if (xory.equals('x')) {
				if (mostLeftOrTop.getxCoordiante() > mostRightOrLowest.getxCoordiante()) {
					Position swap = mostLeftOrTop;
					mostLeftOrTop = mostRightOrLowest;
					mostRightOrLowest = swap;
				} else if (geroidsCollisionPoints.get(geroid).get(i).getxCoordiante() < mostLeftOrTop.getxCoordiante()
						|| geroidsCollisionPoints.get(geroid).get(i).getxCoordiante() > mostRightOrLowest
								.getxCoordiante()) {
					if (geroidsCollisionPoints.get(geroid).get(i).getxCoordiante() < mostLeftOrTop.getxCoordiante())
						mostLeftOrTop = geroidsCollisionPoints.get(geroid).get(i);
					if (geroidsCollisionPoints.get(geroid).get(i).getxCoordiante() > mostRightOrLowest.getxCoordiante())
						mostRightOrLowest = geroidsCollisionPoints.get(geroid).get(i);
				}

				if (projectile.getPosition().getxCoordiante() > mostLeftOrTop.getxCoordiante()
						& projectile.getPosition().getxCoordiante() < mostRightOrLowest.getxCoordiante()
						|| projectile.getPosition().getxCoordiante() + Projectile.PROJECTILE_SIZE > mostLeftOrTop
								.getxCoordiante()
								& projectile.getPosition().getxCoordiante()
										+ Projectile.PROJECTILE_SIZE < mostRightOrLowest.getxCoordiante())
					return true;
			}

			else if (xory.equals('y')) {
				if (mostLeftOrTop.getyCoordiante() > mostRightOrLowest.getyCoordiante()) {
					Position swap = mostLeftOrTop;
					mostLeftOrTop = mostRightOrLowest;
					mostRightOrLowest = swap;
				} else if (geroidsCollisionPoints.get(geroid).get(i).getyCoordiante() < mostLeftOrTop.getyCoordiante()
						|| geroidsCollisionPoints.get(geroid).get(i).getyCoordiante() > mostRightOrLowest
								.getyCoordiante()) {
					if (geroidsCollisionPoints.get(geroid).get(i).getyCoordiante() < mostLeftOrTop.getyCoordiante())
						mostLeftOrTop = geroidsCollisionPoints.get(geroid).get(i);
					if (geroidsCollisionPoints.get(geroid).get(i).getyCoordiante() > mostRightOrLowest.getyCoordiante())
						mostRightOrLowest = geroidsCollisionPoints.get(geroid).get(i);
				}

				if (projectile.getPosition().getyCoordiante() > mostLeftOrTop.getyCoordiante()
						& projectile.getPosition().getyCoordiante() < mostRightOrLowest.getyCoordiante()
						|| projectile.getPosition().getyCoordiante() + Projectile.PROJECTILE_SIZE > mostLeftOrTop
								.getyCoordiante()
								& projectile.getPosition().getyCoordiante()
										+ Projectile.PROJECTILE_SIZE < mostRightOrLowest.getxCoordiante())
					return true;
			}
		}
		return false;
	}

	private ArrayList<Position> generateCollisionpoints(Geroid geroid) {
		ArrayList<Position> collisionPoints = new ArrayList<Position>();
		switch (geroid.getId()) {
		case 1:
			// Geroid 1 definieren
			break;
		case 2:
			// Geroid 2 definieren
			break;
		case 3:
			// Geroid 3 definieren
			break;
		case 4:
			// Geroid 4 definieren
			break;
		case 5:
			// Geroid 5 definieren
			break;
		}
		return collisionPoints;
	}
}
