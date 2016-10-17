package ch.zhaw.soe.psit3.geroids.domain;

public class Microtransaction {
	private int id;
	private Account buyer;
	private Microtransaction type;

	public enum Type {
		TYPE1, TYPE2
	}
}
