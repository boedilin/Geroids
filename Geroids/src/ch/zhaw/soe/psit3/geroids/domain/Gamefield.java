package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;

public class Gamefield {

	private Figure figure;
	private ArrayList<Geroid> geroidList;
	private int gamefield[][];

	public void addGeroid(Geroid geroid) {

	}

	public void addProjectile(Projectile projectile) {

	}

	public void addFigure(Figure figure) {

	}

	public int getXSize() {
		return -1;
	}

	public int getYSize() {
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
}
