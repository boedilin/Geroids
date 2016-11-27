package ch.zhaw.soe.psit3.geroids.test.unit;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Game;
import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class TestGame {
	private Figure mockFigure;;
	private MyWebSocketHandler mockWebSocketHandler;
	private Game game;
	
	
	@Before
	public void setUp() throws Exception {
		mockFigure = mock(Figure.class);
		mockWebSocketHandler = mock(MyWebSocketHandler.class);
		game = new Game(mockWebSocketHandler);
		game.setFigure(mockFigure);
		
	}

	@Test
	public void testUpdateFigureValidLeft() {
		game.receiveMessage("65");
		verify(mockFigure).moveLeft(10);

	}
	
	@Test
	public void testUpdateFigureValidRight() {
		game.receiveMessage("68");
		verify(mockFigure).moveRight(10);	

	}
	
	@Test
	public void testUpdateFigureValidShoot() {
		game.receiveMessage("32");
		verify(mockFigure).shoot();	


	}
	
	@Test
	public void testUpdateFigureInvalidNull() {
		game.receiveMessage(null);
		verify(mockFigure, times(0)).shoot();	
		verify(mockFigure, times(0)).moveRight(10);
		verify(mockFigure, times(0)).moveLeft(10);

	}
	
	@Test
	public void testUpdateFigureInvalidLetter() {
		game.receiveMessage("NaN");
		verify(mockFigure, times(0)).shoot();	
		verify(mockFigure, times(0)).moveRight(10);
		verify(mockFigure, times(0)).moveLeft(10);
	}
}
