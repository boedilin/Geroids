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
		Expansion expansion = new Expansion(5,5);
		Position position = new Position(10, 10);
		Movement movement = new Movement(3, 4);
		
		Geroid geroid = new Geroid("someGeroid", 1234, "square", position, movement);

geroid.toJSON();		
	}

}
