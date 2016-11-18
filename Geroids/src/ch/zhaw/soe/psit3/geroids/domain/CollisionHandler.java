package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;
import java.util.Iterator;

public class CollisionHandler {
		
		private ArrayList<Geroid> geroids;
		private Figure figure;
		private ArrayList<Projectile> projectiles;
		//private int xRange = 1000;
		private int yRange = 1000;
		
		public CollisionHandler(Figure figure, ArrayList<Geroid> geroids, ArrayList<Projectile> projectiles){
			this.geroids = geroids;
			this.figure = figure;
			this.projectiles = projectiles;
			
		}

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

		public Object[] checkAllGeroidsCollisionWithProjectiles() {
			Iterator<Geroid> geroidIterator = geroids.iterator();
			while (geroidIterator.hasNext()) {
				Geroid myGeroid = geroidIterator.next();
				Iterator<Projectile> projectileIterator = projectiles.iterator();
				while (projectileIterator.hasNext()) {
					Projectile myProjectile = projectileIterator.next();
					if (checkIfGeroidIsCollidingWithProjectile(myGeroid, myProjectile)) {
						geroidIterator.remove();
						projectileIterator.remove();

					}
				}
			}
			 Object[] figures = new Object[2]; 
			 figures[0] = geroids;
			 figures[1] = projectiles;
			 return figures;
		}
		
		private boolean checkIfGeroidIsCollidingWithProjectile(Geroid geroid, Projectile projectile) {
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
		
		public boolean checkIfGeroidIsOutOfGamefield(int geroidIndex) {

			if (geroids.get(geroidIndex).getPosition().getyCoordiante() > this.yRange) {
				return true;
			}
			return false;

		}
		
		public boolean checkIfProjectileIsOutOfGamefield(int projectileIndex) {
			int yCoord = projectiles.get(projectileIndex).getPosition().getyCoordiante();
			int yLength = projectiles.get(projectileIndex).getPosition().getyLength();

			if ((yCoord + yLength) < 0) {
				return true;
			}
			return false;
		}
		
		public void updateFigures(ArrayList<Geroid> geroids, Figure figure, ArrayList<Projectile> projectiles){
			this.geroids = geroids;
			this.figure = figure;
			this.projectiles = projectiles;
		}
}
