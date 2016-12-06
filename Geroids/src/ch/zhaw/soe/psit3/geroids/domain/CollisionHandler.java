package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Iterator;

public class CollisionHandler {

	private int xRange;
	private final int DEFAULTXRANGE = 1000;
	
	public CollisionHandler(int xRange) {
		if(xRange < 0) {
			this.xRange = DEFAULTXRANGE;
		} else {
			this.xRange = xRange;
		}
	}

	/**
	 * Checks if there are any collisions of geroids and figure
	 * 
	 * @param ArrayList<Geroid> with all geroids in game and Figure in game
	 * @return true if there is a geroid, which collided with figure
	 */
	public boolean checkAllGeroidsCollisionWithFigure(ArrayList<Geroid> geroids, Figure figure) {
		Iterator<Geroid> geroidIterator = geroids.iterator();
		while (geroidIterator.hasNext()) {
			Geroid myGeroid = geroidIterator.next();
			if (checkIfGeroidIsCollidingWithFigure(myGeroid, figure)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if there is a collisions of a geroid and a projectile
	 * 
	 * @param  One specific Geroid and Projectile in game
	 * @return true, if there was a collision; false, else
	 * @throws NullPointerExcepiton if geroid or projectile is null
	 */
	public boolean checkIfGeroidIsCollidingWithProjectile(Geroid geroid, Projectile projectile) throws NullPointerException {
		if(geroid == null || projectile == null) {
			throw new NullPointerException();
		}
		int projectileLeftMostPosition = projectile.getPosition().getxCoordiante();
		int projectileRightMostPosition = projectileLeftMostPosition + projectile.getPosition().getxLength();
		int geroidLeftMostPoint = geroid.getPosition().getxCoordiante();
		int geroidRightMostPoint = geroidLeftMostPoint + geroid.getPosition().getxLength();

		int projectileHighestPoint = projectile.getPosition().getyCoordiante();
		int geroidLowestPoint = geroid.getPosition().getyCoordiante() + geroid.getPosition().getyLength();

		if (isInBetween(projectileLeftMostPosition, projectileRightMostPosition, geroidRightMostPoint)
				| isInBetween(projectileLeftMostPosition, projectileRightMostPosition, geroidLeftMostPoint)
				| isInBetween(geroidLeftMostPoint, geroidRightMostPoint, projectileLeftMostPosition)) { // xcoords

			if (projectileHighestPoint < geroidLowestPoint) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	private boolean checkIfGeroidIsCollidingWithFigure(Geroid geroid, Figure figure) {
		int figureLeftMostPoint = figure.getPosition().getxCoordiante();
		int figureRightMostPoint = figureLeftMostPoint + figure.getPosition().getxLength();
		int geroidLeftMostPoint = geroid.getPosition().getxCoordiante();
		int geroidRightMostPoint = geroidLeftMostPoint + geroid.getPosition().getxLength();

		int figureHighestPoint = figure.getPosition().getyCoordiante();
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
	 * @param Geroid to test
	 * @return true if geroid is out of gamefield
	 * @throws NullPointerExcepiton if geroid is null
	 */
	public boolean checkIfGeroidIsOutOfGamefield(Geroid geroid) throws NullPointerException {
		if(geroid == null) {
			throw new NullPointerException();
		}

		if (geroid.getPosition().getyCoordiante() > this.xRange) {
			return true;
		}
		return false;

	}

	/**
	 * Checks if a projectile is out of gamefield
	 * 
	 * @param Projectile to test
	 * @return true if projectile is out of gamefield
	 * @throws NullPointerExcepiton if projectile is null
	 */
	public boolean checkIfProjectileIsOutOfGamefield(Projectile projectile) throws NullPointerException {
		if(projectile == null) {
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
	 * Returns xRange
	 * 
	 * @return  xRange
	 */
	public int getXRange() {
		return xRange;
	}
}