package ch.zhaw.soe.psit3.geroids.test.unit;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Game;
import ch.zhaw.soe.psit3.geroids.domain.Position;
import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class TestGame {
	private Figure mockFigure;
	private Position mockPosition;
	private MyWebSocketHandler mockWebSocketHandler;
	private Game game;
	
	
	@Before
	public void setUp() throws Exception {
		mockFigure = mock(Figure.class);
		mockPosition = mock(Position.class);
		mockWebSocketHandler = mock(MyWebSocketHandler.class);
		game = new Game(mockWebSocketHandler);
		game.setFigure(mockFigure);
	
		when(mockFigure.getPosition()).thenReturn(mockPosition);
		when(mockPosition.getxCoordiante()).thenReturn(100);
		when(mockPosition.getxLength()).thenReturn(50);
		
		
	}

	@Test
	public void testUpdateFigureValidLeft() {
		game.receiveMessage("65");
		verify(mockFigure).moveLeft(anyInt());;

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
