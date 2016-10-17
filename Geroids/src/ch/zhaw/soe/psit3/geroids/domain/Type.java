package ch.zhaw.soe.psit3.geroids.domain;

public abstract class Type {

	private String typeName;
	private int movementBonus;
	private int lifeBonus;
	private int damageBonus;

	public int getMovementBonus() {
		return movementBonus;
	}

	public int getLifeBonus() {
		return lifeBonus;
	}

	public int getDamageBonus() {
		return damageBonus;
	}

}
