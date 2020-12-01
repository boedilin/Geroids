package ch.zhaw.soe.psit3.geroids.test.integration;

import static org.junit.Assert.*;

import org.junit.After;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Game;
import ch.zhaw.soe.psit3.geroids.domain.Position;
import ch.zhaw.soe.psit3.geroids.domain.Projectile;

public class TestIntegrationFigure {

	private Figure figure;
	private Position position;
	
	@Before
	public void setUp() throws Exception {
		position = new Position(
				(Game.X_WIDTH - Figure.X_WIDTH_FIGURE)/2,
				Game.X_WIDTH - Figure.Y_HEIGHT_FIGURE,
				Figure.X_WIDTH_FIGURE,
				Figure.Y_HEIGHT_FIGURE);
		figure = new Figure(position);
	}
	
	@After
	public void resetPositionOfFigure(){
		position = new Position(
				(Game.X_WIDTH - Figure.X_WIDTH_FIGURE)/2,
				Game.X_WIDTH - Figure.Y_HEIGHT_FIGURE,
				Figure.X_WIDTH_FIGURE,
				Figure.Y_HEIGHT_FIGURE);
		figure = new Figure(position);
	}
	
	//check if moveLeft() cross the left game boarder
	@Test
	public void testNeverCrossLeftBoarder(){
		int stepsToCrossTheBoarder = Game.X_WIDTH/Figure.MOVE_WIDTH + 1;
		while(stepsToCrossTheBoarder >= 0){
			figure.moveLeft();
			stepsToCrossTheBoarder--;
		}
		assertTrue(figure.getXCoordinate() >= Game.LEFT_BOARDER);
	}
	
	//check if moveRight() cross the right game boarder
	@Test
	public void testNeverCrossRightBoarder(){
		int stepsToCrossTheBoarder = Game.X_WIDTH/Figure.MOVE_WIDTH + 1;
		while(stepsToCrossTheBoarder >= 0){
			figure.moveRight();
			stepsToCrossTheBoarder--;
		}
		assertTrue(figure.getXCoordinate() <= Game.RIGHT_BOARDER-Figure.X_WIDTH_FIGURE);
	}
	
	//check if figure moves left
	@Test
	public void testMoveLeft(){
		int positionBefore = (Game.X_WIDTH - Figure.X_WIDTH_FIGURE)/2;
		figure.moveLeft();
		assertEquals(figure.getXCoordinate(), positionBefore - Figure.MOVE_WIDTH);
		
	}
	
	//check if figure moves right
	@Test
	public void testMoveRight(){
		int positionBefore = (Game.X_WIDTH - Figure.X_WIDTH_FIGURE)/2;
		figure.moveRight();
		assertEquals(figure.getXCoordinate(), positionBefore + Figure.MOVE_WIDTH);
	}
	
	//check if figure fires a Projectile
	@Test
	public void testFigureFiresAProjectile(){
		assertTrue(figure.shoot() instanceof Projectile);
	}
}
