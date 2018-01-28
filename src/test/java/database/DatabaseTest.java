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
		List<Property> raceSkills = toTest.getRaceSkills(Race.MIDDLEGUY);
		assertTrue(raceSkills.isEmpty());
		raceSkills = toTest.getRaceSkills(Race.THORWALAN);
		assertEquals(raceSkills.size(), 1);
		
	}

}
