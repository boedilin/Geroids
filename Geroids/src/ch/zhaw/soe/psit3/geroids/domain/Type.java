package ch.zhaw.soe.psit3.geroids.domain;

public abstract class Type {

	private String typeName;
	private Movement movement;
	private int lifeBonus;

	public int getLifeBonus() {
		return lifeBonus;
	}

	public Movement getMovement() {
		return movement;
	}

}
