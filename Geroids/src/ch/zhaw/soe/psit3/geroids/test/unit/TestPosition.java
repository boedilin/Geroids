package ch.zhaw.soe.psit3.geroids.test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Movement;
import ch.zhaw.soe.psit3.geroids.domain.Position;

public class TestPosition {
	
	private Position positionWithoutSpecificLength;
	private Position positionWithSpecificLength;
	
	@Before
	public void setUp(){
		positionWithoutSpecificLength = new Position(5, 5);
		positionWithSpecificLength = new Position(0,0,0,0);
	}

	@Test
	public void testUpdatePositionWithoutSpecificLength() {
		Movement movement = new Movement(1,1);
		positionWithoutSpecificLength.update(movement);
		assertEquals(6, positionWithoutSpecificLength.getxCoordiante());
		assertEquals(6, positionWithoutSpecificLength.getyCoordiante());
	}
	
	@Test
	public void testUpdatePositionWithoutSpecificLengthMovement0() {
		Movement movement = new Movement(0,0);
		positionWithoutSpecificLength.update(movement);
		assertEquals(6, positionWithoutSpecificLength.getxCoordiante());
		assertEquals(6, positionWithoutSpecificLength.getyCoordiante());
	}
	
	@Test
	public void testUpdatePositionWithSpecificLengthMovement0() {
		Movement movement = new Movement(0,0);
		positionWithSpecificLength.update(movement);
		assertEquals(0, positionWithSpecificLength.getxCoordiante());
		assertEquals(0, positionWithSpecificLength.getyCoordiante());
	}
	
	@Test
	public void testUpdatePositionWithSpecificLength() {
		Movement movement = new Movement(-1,1);
		positionWithSpecificLength.update(movement);
		assertEquals(-1, positionWithSpecificLength.getxCoordiante());
		assertEquals(1, positionWithSpecificLength.getyCoordiante());
	}

}
