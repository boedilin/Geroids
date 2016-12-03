package ch.zhaw.soe.psit3.geroids.test.unit;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Game;
import ch.zhaw.soe.psit3.geroids.domain.Position;

public class TestFigure {
	private Figure figure;
	Position positionMock = mock(Position.class);
	
	@Before
	public void setUp() throws Exception {
		figure = new Figure(positionMock);
	}
	
	@Test
	public void moveLeftNotOverTheGamefield(){
		when(positionMock.getxCoordiante()).thenReturn(Game.LEFT_BOARDER);
		figure.moveLeft();
		verify(positionMock, times(0)).setxCoordiante(anyInt());		
	}
	
	@Test
	public void moveRightNotOverTheGamefield(){
		when(positionMock.getxCoordiante()).thenReturn(Game.RIGHT_BOARDER - Game.X_WIDTH_FIGURE);
		figure.moveRight();
		verify(positionMock, times(0)).setxCoordiante(anyInt());		
	}
	
	

}
