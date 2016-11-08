package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;

public class CollisionHandler {
	
	private ArrayList<Geroid> geroids;
	private Gamefield gamefield;
	private Figure figure;
	private ArrayList<Projectile> projectiles;
	private boolean collisionWithFigure = false;
	
	public CollisionHandler(Gamefield gamefield, Figure figure){
		this.gamefield = gamefield;
		this.geroids = this.gamefield.getGeroidList();
		this.figure = figure;
		this.projectiles = this.gamefield.getProjectileList();
		
	}

	public void checkCollision(){
		for(int i = 0; i < gamefield.getGeroidList().size();i++){
			if(checkIfGeroidIsCollidingWithFigure(i)){
				collisionWithFigure = true;
				//hier kommt noch die Methode f�r den GameOverBanner
			}
			for(int j = 0; j< gamefield.getProjectileList().size();j++){
				if(checkIfGeroidIsCollidingWithProjectile(i, j))
				{
					geroids.get(i).die(gamefield);
					projectiles.get(j).hit();
				}
			}
		}
	}
	
	private boolean checkIfGeroidIsCollidingWithProjectile(int geroidsIndex, int projectilesIndex){
		if(
				geroids.get(geroidsIndex).getPosition().getyCoordiante() - 1 == projectiles.get(projectilesIndex).getPosition().getyCoordiante()
				||
				geroids.get(geroidsIndex).getPosition().getyCoordiante() + 1 == projectiles.get(projectilesIndex).getPosition().getyCoordiante()
				)
		{
			return true;
		}
			return false;
	}
	
	private boolean checkIfGeroidIsCollidingWithFigure(int geroidIndex){
		if(		geroids.get(geroidIndex).getPosition().getyCoordiante() == figure.getPosition().getyCoordiante()+1 
				&& 
				geroids.get(geroidIndex).getPosition().getxCoordiante() == figure.getPosition().getxCoordiante()
			){
			return true;
		}
		return false;
	}
	
	public boolean isCollisionWithFigure() {
		return collisionWithFigure;
	}

	public void setCollisionWithFigure(boolean collisionWithFigure) {
		this.collisionWithFigure = collisionWithFigure;
	}
}
