package ch.zhaw.soe.psit3.geroids.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Playscore;

public class TestPlayscore {
	
	private Playscore score;


	@Before
	public void setUp() throws Exception {
		score = new Playscore(0);
		score.setKillBonus(10);
		score.setPassingGeroidDecrease(5);
		score.setTimeBonusPerSecond(100);
	}

	
	//Max Value = Integer.MaxValue
	//Min Value = 0
	@Test
	public void testScoreRange() {
		score.setScore(16);
		assertEquals(16, score.getScore());
		score.setScore(score.getSCORE_MAX_VALUE()+1);
		assertEquals(0, score.getScore());
		score.setScore(-1);
		assertEquals(0, score.getScore());
	}
	
	
	@Test
	public void testAddingScoreIfGeroidKilled() {
		score.setGeroidKilled(true);
		score.addingScoreIfGeroidKilled();
		assertEquals(score.getScore(),10);
		score.setGeroidKilled(false);
		score.addingScoreIfGeroidKilled();
		assertEquals(score.getScore(),10);
	}
	
}
