package ch.zhaw.soe.psit3.geroids.domain;

public class Figure {

	private Game game;
	private Position position;
	private Skin skin;
	private Type type;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean shooting = false;
	
	public Figure(Game game, Type type, Skin skin, int x, int y){
		this.game = game;
		this.skin = skin;
		this.type = type;
		this.position = new Position(x, y);
	}
	
	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}

	public void moveLeft(){
		this.position.setxCoordiante(this.position.getxCoordiante()-1);
		movingLeft = false;
	}
	
	public void moveRight(){
		this.position.setxCoordiante(this.position.getxCoordiante()+1);
		movingRight = false;
	}
	
	public void shoot(){
		game.getProjectiles().add(new Projectile(game, new Position(game.getFigure().getPosition().getxCoordiante(), game.getFigure().getPosition().getyCoordiante()), game.getAccount().getActiveType().getMovement()));
		shooting = false;
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
