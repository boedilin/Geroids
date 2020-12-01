package ch.zhaw.soe.psit3.geroids.test.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Movement;

public class TestMovement {
	private Movement movement;
	
	@Before
	public void setUp(){
		movement = new Movement(5, 5);
	}

	@Test
	public void testIncreaseXSpeedBy10() {
		movement.changeXSpeedByGivenFactor(10);
		assertEquals(15, movement.getxSpeed());
	}
	
	@Test
	public void testDecreaseYSpeedBy10() {
		movement.changeYSpeedByGivenFactor(-10);
		assertEquals(-5, movement.getySpeed());
	}
	
	@Test
	public void testIncreaseXSpeedBy0() {
		movement.changeXSpeedByGivenFactor(0);
		assertEquals(15, movement.getxSpeed());
	}
	
	@Test
	public void testDecreaseYSpeedBy0() {
		movement.changeYSpeedByGivenFactor(0);
		assertEquals(-5, movement.getySpeed());
	}
	
	@Test
	public void testIncreaseYSpeedBy20() {
		movement.changeYSpeedByGivenFactor(20);
		assertEquals(15, movement.getySpeed());
	}


}
