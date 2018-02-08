package database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.Race;
import skills.Property;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseTest {

	private Database toTest;

	@Before
	public void setUp() {
		toTest = new Database();
	}

	@Test
	public void testGetRaceSkills() {
		List<Property> raceSkills = toTest.getSkillsFor(null);
		assertTrue(raceSkills.isEmpty());

		raceSkills = toTest.getSkillsFor(Race.THORWALAN);
		assertEquals(raceSkills.size(), 1);

	}

	@Test
	public void testGetRaceHitPointMod() {
		int hpMod = toTest.getHitPointsModFor(null);
		assertEquals(0, hpMod);

		hpMod = toTest.getHitPointsModFor(Race.THORWALAN);
		assertEquals(11, hpMod);
	}
	
	@Test
	public void testGetRaceMagicResistanceMod() {
		int mrMod = toTest.getMagicResistanceModFor(null);
		assertEquals(0, mrMod);

		mrMod = toTest.getMagicResistanceModFor(Race.THORWALAN);
		assertEquals(-5, mrMod);
	}

}
