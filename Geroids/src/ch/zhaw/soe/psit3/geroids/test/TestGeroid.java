package ch.zhaw.soe.psit3.geroids.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Expansion;
import ch.zhaw.soe.psit3.geroids.domain.Geroid;
import ch.zhaw.soe.psit3.geroids.domain.Movement;
import ch.zhaw.soe.psit3.geroids.domain.Position;

public class TestGeroid {

	@Before
	public void setUp() throws Exception {


	}

	@Test
	public void test() {
		Expansion expansion = new Expansion(2,2);
		Position position = new Position(10, 10);
		Movement movement = new Movement(3, 4);
		position.setExpansion(expansion);
		Geroid geroid = new Geroid("someGeroid", 1234, "square", position, movement);
		System.out.println("{\"shape\":\"square\",\"id\":1234,\"position\":[\"10:10\",\"10:11\",\"11:10\",\"11:11\"]}");
		System.out.println(geroid.toJSON());
		assertTrue("{\"shape\":\"square\",\"id\":1234,\"position\":[\"10:10\",\"10:11\",\"11:10\",\"11:11\"]}".equals(geroid.toJSON()));

	}

}
