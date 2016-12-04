package ch.zhaw.soe.psit3.geroids.test.unit;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Geroid;
import ch.zhaw.soe.psit3.geroids.domain.Movement;
import ch.zhaw.soe.psit3.geroids.domain.Position;

public class TestGeroid {

	private Geroid geroid;
	private Position position;
	private Movement movement;
	private JSONObject json;
	
	@Before
	public void setUp() throws Exception {
	position = new Position(10, 20);
	movement = new Movement(2, 3);
	geroid = new Geroid(position, movement);
	}
	
	@Test
	public void testMove(){
		geroid.move();
		assertEquals(23, geroid.getPosition().getyCoordiante());
		assertEquals(12, geroid.getPosition().getxCoordiante());
	}
	
	@Test
	public void testToJSONObject(){
		geroid.move();
		geroid.move();
		geroid.move();
		json = geroid.toJSONObject();
		assertEquals(29,((JSONObject) json.get("position")).get("yStart"));
		assertEquals(16,((JSONObject) json.get("position")).get("xStart"));
	}
	

//	@Test
//	public void test() {
//	
//		Position position = new Position(10, 10);
//		Movement movement = new Movement(3, 4);
//		Geroid geroid = new Geroid(1234, "square", position, movement);
//		System.out.println("{\"shape\":\"square\",\"id\":1234,\"position\":[\"10:10\",\"10:11\",\"11:10\",\"11:11\"]}");
//		System.out.println(geroid.toJSONString());
//		assertTrue("{\"shape\":\"square\",\"id\":1234,\"position\":[\"10:10\",\"10:11\",\"11:10\",\"11:11\"]}".equals(geroid.toJSONString()));
//
//	}

}
