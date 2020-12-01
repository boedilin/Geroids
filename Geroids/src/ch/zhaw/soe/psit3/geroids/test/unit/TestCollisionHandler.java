package ch.zhaw.soe.psit3.geroids.test.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.CollisionHandler;


public class TestCollisionHandler {
	
	@Test
	public void testCollisionHandlerNegativeYRange() {
		CollisionHandler handler = new CollisionHandler(-5);
		assertEquals(1000, handler.getYRange());
		
	}
	
	@Test
	public void testCollisionHandlerPositiveYRange() {
		CollisionHandler handler = new CollisionHandler(50);
		assertEquals(50, handler.getYRange());
		
	}
	
	@Test
	public void testCollisionHandlerZeroRange() {
		CollisionHandler handler = new CollisionHandler(0);
		assertEquals(1000, handler.getYRange());
		
	}
	
	

}
