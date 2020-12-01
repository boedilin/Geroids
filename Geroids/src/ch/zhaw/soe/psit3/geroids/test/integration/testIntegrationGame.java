package ch.zhaw.soe.psit3.geroids.test.integration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Game;
import ch.zhaw.soe.psit3.geroids.domain.Position;
import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class testIntegrationGame {
	private final int FIGURE_X_POS = 100;
	private final int FIGURE_Y_POS = 898;
	private final int FIGURE_X_LENGTH = 61;
	private final int FIGURE_Y_LENGTH = 90;
	private Figure figure;;
	private MyWebSocketHandler myWebSocketHandler;
	private Game game;
	
	
	@Before
	public void setUp() throws Exception {
		figure = new Figure(new Position(FIGURE_X_POS, FIGURE_Y_POS, FIGURE_X_LENGTH, FIGURE_Y_LENGTH));
		myWebSocketHandler = new MyWebSocketHandler();
		game = new Game(myWebSocketHandler);
		game.setFigure(figure);
		
	}

	@Test
	public void testUpdateFigureValidLeft() {
		game.receiveMessage("65");
		assertEquals(FIGURE_X_POS-10, figure.getPosition().getxCoordiante());
	}
	
	@Test
	public void testUpdateFigureValidRight() {
		game.receiveMessage("68");
		assertEquals(FIGURE_X_POS+10, figure.getPosition().getxCoordiante());

	}
	
	@Test
	public void testUpdateFigureValidShoot() {
		game.receiveMessage("32");
		assertEquals(1, game.getProjectiles().size());
		
	}
	
	@Test
	public void testUpdateFigureInvalidNull() {
		game.receiveMessage(null);
		assertEquals(0, game.getProjectiles().size());

	}
	
	@Test
	public void testUpdateFigureInvalidLetter() {
		game.receiveMessage("NaN");
		assertEquals(0, game.getProjectiles().size());

	}

}
