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
		positionMock.setxCoordiante(Game.LEFT_BOARDER);
		figure.moveLeft();
		verify(positionMock, times(0)).setxCoordiante(anyInt());		
	}

}
