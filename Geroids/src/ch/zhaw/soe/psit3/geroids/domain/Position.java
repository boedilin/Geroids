package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;

public class Position {

	private int xCoordiante;
	private int yCoordiante;
	private Expansion expansion;
	private ArrayList<Position> positionArray = new ArrayList<Position>();

	public Position(int xCoordiante, int yCoordiante) {
		super();
		this.xCoordiante = xCoordiante;
		this.yCoordiante = yCoordiante;
	}
	
	public Position(int xCoordiante, int yCoordiante, Expansion expansion) {
		super();
		this.xCoordiante = xCoordiante;
		this.yCoordiante = yCoordiante;
		this.expansion = expansion;
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

	/**
	 * Returns an Array of Positions, containing every Position of the body, including Expansion;
	 * @return ArrayList with every Position including expansion
	 */
	public ArrayList<Position> getPositionArray(){
		try {
			System.out.println(expansion.getxExpansion());
		for(int x = 0 ; x <expansion.getxExpansion(); x++){
			for (int y = 0; y < expansion.getyExpansion() ; y++ ){
				positionArray.add(new Position(xCoordiante + x, yCoordiante + y));
			}
		}
		return positionArray;
		
		} catch (NullPointerException npe){
			return positionArray;
		}
	}

}
