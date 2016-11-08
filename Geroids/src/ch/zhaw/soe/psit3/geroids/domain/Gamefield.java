package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;

public class Gamefield {

	private Figure figure;
	private ArrayList<Geroid> geroidList;
	private ArrayList<Projectile> projectileList;
	private int xRange;
	private int yRange;

	public Gamefield(int xRange, int yRange){
		this.xRange = xRange;
		this.yRange = yRange;
		this.projectileList = new ArrayList<Projectile>();
		this.geroidList = new ArrayList<Geroid>();
	}
	
	public void addGeroid(Geroid geroid) {
		geroidList.add(geroid);
	}

	public void addProjectile(Projectile projectile) {
		projectileList.add(projectile);
	}
	
	public int getXRange() {
		return xRange;
	}
	
	public void setXRange(int xRange){
		this.xRange = xRange;
	}
	
	public void setYRange(int yRange){
		this.yRange = yRange;
	}

	public int getYRange() {
		return yRange;
	}

	public Figure getFigure() {
		return figure;
	}

	public void setFigure(Figure figure) {
		this.figure = figure;
	}

	public ArrayList<Geroid> getGeroidList() {
		return geroidList;
	}

	public void setGeroidList(ArrayList<Geroid> geroidList) {
		this.geroidList = geroidList;
	}

	public ArrayList<Projectile> getProjectileList() {
		return projectileList;
	}

	public void setProjectileList(ArrayList<Projectile> projectileList) {
		this.projectileList = projectileList;
	}
	
}
