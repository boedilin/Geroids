package ch.zhaw.soe.psit3.geroids.test.unit;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Game;
import ch.zhaw.soe.psit3.geroids.domain.Movement;
import ch.zhaw.soe.psit3.geroids.domain.Position;
import ch.zhaw.soe.psit3.geroids.domain.Projectile;

public class TestFigure {
	private Figure figure;
	Position positionMock = mock(Position.class);
	Projectile projectileMock = mock(Projectile.class);
	
	@Before
	public void setUp() throws Exception {
		figure = new Figure(positionMock);
	}
	
	//check if moveLeft() cross the left game boarder
	@Test
	public void testNeverCrossLeftBoarder(){
		when(positionMock.getxCoordiante()).thenReturn(Game.LEFT_BOARDER);
		figure.moveLeft();
		verify(positionMock, times(0)).setxCoordiante(anyInt());		
	}
	
	//check if moveRight() cross the right game boarder
	@Test
	public void testNeverCrossRightBoarder(){
		when(positionMock.getxCoordiante()).thenReturn(Game.RIGHT_BOARDER - Figure.X_WIDTH_FIGURE);
		figure.moveRight();
		verify(positionMock, times(0)).setxCoordiante(anyInt());		
	}
	
	//check if moveLeft() sets the right argument for the new position of figure
	@Test
	public void testMoveLeft(){
		when(positionMock.getxCoordiante()).thenReturn(Game.X_WIDTH/2);
		figure.moveLeft();
		verify(positionMock).setxCoordiante(Game.X_WIDTH/2 - Figure.MOVE_WIDTH);
	}
	
	//check if moveRight() sets the right argument for the new position of figure
	@Test
	public void testMoveRight(){
		when(positionMock.getxCoordiante()).thenReturn(Game.X_WIDTH/2);
		figure.moveRight();
		verify(positionMock).setxCoordiante(Game.X_WIDTH/2 + Figure.MOVE_WIDTH);
	}

}
