package ch.zhaw.soe.psit3.geroids.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Movement;

public class TestMovement {
	private Movement movement;
	@Before
	public void setUp() throws Exception {
		movement = new Movement(5, 5);
	}

	@Test
	public void testIncreaseXSpeed() {
		movement.increaseXSpeed(10);
		assertEquals(15, movement.getxSpeed());
	}
	
	@Test
	public void testIncreaseYSpeed() {
		movement.increaseYSpeed(-10);
		assertEquals(-5, movement.getySpeed());
	}


}
