package ch.zhaw.soe.psit3.geroids.test.unit;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Game;
import ch.zhaw.soe.psit3.geroids.domain.Position;
import ch.zhaw.soe.psit3.geroids.domain.Projectile;

public class TestFigure {
	private Figure figure;
	Position positionMock = mock(Position.class);
	Projectile projectileMock = mock(Projectile.class);
	
	@Before
	public void setUp() throws Exception {
		figure = spy(new Figure(positionMock));
	}
	
	//check if moveLeft() cross the left game boarder
	@Test
	public void neverCrossLeftBoarder(){
		when(positionMock.getxCoordiante()).thenReturn(Game.LEFT_BOARDER);
		figure.moveLeft();
		verify(positionMock, times(0)).setxCoordiante(anyInt());		
	}
	
	//check if moveRight() cross the right game boarder
	@Test
	public void neverCrossRightBoarder(){
		when(positionMock.getxCoordiante()).thenReturn(Game.RIGHT_BOARDER - Game.X_WIDTH_FIGURE);
		figure.moveRight();
		verify(positionMock, times(0)).setxCoordiante(anyInt());		
	}
	
	//check if moveLeft() sets the right argument for the new position of figure
	@Test
	public void moveLeft(){
		when(positionMock.getxCoordiante()).thenReturn(Game.X_WIDTH/2);
		figure.moveLeft();
		verify(positionMock).setxCoordiante(Game.X_WIDTH/2 - Game.MOVE_WIDTH);
	}
	
	//check if moveRight() sets the right argument for the new position of figure
	@Test
	public void moveRight(){
		when(positionMock.getxCoordiante()).thenReturn(Game.X_WIDTH/2);
		figure.moveRight();
		verify(positionMock).setxCoordiante(Game.X_WIDTH/2 + Game.MOVE_WIDTH);
	}
	
	@Test
	public void projectileFiresInMiddleOfFigure(){
		when(positionMock.getxCoordiante()).thenReturn(Game.X_WIDTH/2);
		when(positionMock.getxLength()).thenReturn(Game.X_WIDTH_FIGURE);
		when(positionMock.getyCoordiante()).thenReturn(Game.Y_HEIGHT_FIGURE-10);
		when(figure.shoot()).thenReturn(projectileMock);
	}

}
