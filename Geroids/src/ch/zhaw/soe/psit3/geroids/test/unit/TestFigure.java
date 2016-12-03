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
	
	@Before
	public void setUp() throws Exception {
		Position positionMock = mock(Position.class);
		figure = new Figure(positionMock);
	}
	
	@Test
	public void moveLeftNotOverTheGamefield(){
		figure.moveLeft(Game.X_WIDTH+1);
		System.out.print(figure.getXCoordinate());
		assertEquals(0, figure.getXCoordinate());
	}
	
	@Test
	public void moveRightNotOverTheGamefield(){
		figure.moveRight(11+1);
		
		assertEquals(0, figure.getXCoordinate());
	}
}
