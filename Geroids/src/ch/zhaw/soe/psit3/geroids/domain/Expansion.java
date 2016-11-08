package ch.zhaw.soe.psit3.geroids.domain;
/*
 * Defines the Expansion of the body relatively to his Position point;
 * For Prototype just square Expansions where root is top left point of expansion
 * 
 */
public class Expansion {
	private int xExpansion;
	private int yExpansion;
	
	public Expansion(int xExpansion,int yExpansion){
		this.xExpansion = xExpansion;
		this.yExpansion = yExpansion;
	}
	
	public Expansion(int sqaresize){
		this.xExpansion = sqaresize;
		this.yExpansion = sqaresize;
	}
	
	public int getxExpansion() {
		return xExpansion;
	}
	public void setxExpansion(int xExpansion) {
		this.xExpansion = xExpansion;
	}
	public int getyExpansion() {
		return yExpansion;
	}
	public void setyExpansion(int yExpansion) {
		this.yExpansion = yExpansion;
	}
	
	

}
