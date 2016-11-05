package ch.zhaw.soe.psit3.geroids.domain;

public class Figure {

	private Position position;
	private Skin skin;
	private Type type;
	
	public Figure(Type type, Skin skin, int x, int y){
		this.skin = skin;
		this.type = type;
		this.position = new Position(x, y);
	}
	
	//zum Testen
	public String getName(){
		return "figure";
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
