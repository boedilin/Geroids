package ch.zhaw.soe.psit3.geroids.test.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Game;
import ch.zhaw.soe.psit3.geroids.domain.Position;
import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class TestSystemFigure {

	private final int FIGURE_X_POS = 100;
	private final int FIGURE_Y_POS = 898;
	private final int FIGURE_X_LENGTH = 61;
	private final int FIGURE_Y_LENGTH = 90;
	private Figure figure;;
	private MyWebSocketHandler myWebSocketHandler;
	private Game game;

	@Before
	public void setUp() throws Exception {
		figure = new Figure(new Position(FIGURE_X_POS,
				FIGURE_Y_POS,
				FIGURE_X_LENGTH,
				FIGURE_Y_LENGTH));
		myWebSocketHandler = new MyWebSocketHandler();
		game = new Game(myWebSocketHandler);
		game.setFigure(figure);
	}
	
	@Test
	public void testFigureMoveLeft() {
		game.receiveMessage("65");
		assertEquals(FIGURE_X_POS - Figure.MOVE_WIDTH, figure.getPosition().getxCoordiante());
	}
	
	@Test
	public void testFigureMoveLeftRight() {
		game.receiveMessage("68");
		assertEquals(FIGURE_X_POS + Figure.MOVE_WIDTH, figure.getPosition().getxCoordiante());
	}
	
	@Test
	public void testFigureShoot() {
		game.receiveMessage("32");
		assertEquals(1, game.getProjectiles().size());
	}
	
	@Test
	public void testFigureNeverCrossRightBoarder() {
		int stepsToCrossTheBoarder = Game.X_WIDTH/Figure.MOVE_WIDTH + 1;
		while(stepsToCrossTheBoarder >= 0){
			game.receiveMessage("68");
			stepsToCrossTheBoarder--;
		}
		assertTrue(figure.getXCoordinate() <= Game.RIGHT_BOARDER-Figure.X_WIDTH_FIGURE);
	}
	
	//check if moveLeft() cross the left game boarder
		@Test
		public void testNeverCrossLeftBoarder(){
			int stepsToCrossTheBoarder = Game.X_WIDTH/Figure.MOVE_WIDTH + 1;
			while(stepsToCrossTheBoarder >= 0){
				game.receiveMessage("65");
				stepsToCrossTheBoarder--;
			}
			assertTrue(figure.getXCoordinate() >= Game.LEFT_BOARDER);
		}

}
