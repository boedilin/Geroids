package ch.zhaw.soe.psit3.geroids.domain;

public class Position {

	private int xCoordiante;
	private int yCoordiante;
	
	
	public Position(int xCoordiante, int yCoordiante) {
		super();
		this.xCoordiante = xCoordiante;
		this.yCoordiante = yCoordiante;
	}
	
	public int getxCoordiante() {
		return xCoordiante;
	}
	public void setxCoordiante(int xCoordiante) {
		this.xCoordiante = xCoordiante;
	}
	public int getyCoordiante() {
		return yCoordiante;
	}
	public void setyCoordiante(int yCoordiante) {
		this.yCoordiante = yCoordiante;
	}
	

}
