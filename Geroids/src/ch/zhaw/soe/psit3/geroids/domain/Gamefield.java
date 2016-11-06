package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;

public class Gamefield {

	private Figure figure;
	private ArrayList<Geroid> geroidList;
	private ArrayList<Projectile> projectileList;
	private int gamefield[][];

	public Gamefield(int xRange, int yRange){
		this.gamefield = new int[xRange][yRange];
		this.projectileList = new ArrayList<Projectile>();
		this.geroidList = new ArrayList<Geroid>();
	}
	
	public void addGeroid(Geroid geroid) {

	}

	public void addProjectile(Projectile projectile) {

	}

	public void addFigure(Figure figure) {

	}

	public int getXRange() {
		return -1;
	}

	public int getYRange() {
		return -1;
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

	public int[][] getGamefield() {
		return gamefield;
	}

	public void setGamefield(int[][] gamefield) {
		this.gamefield = gamefield;
	}

	public ArrayList<Projectile> getProjectileList() {
		return projectileList;
	}

	public void setProjectileList(ArrayList<Projectile> projectileList) {
		this.projectileList = projectileList;
	}
	
}
