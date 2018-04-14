package skills;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import aventurian.Aventurian;

public class BadPropertyTest {
	private BadProperty toTest;
	private static Predicate<Aventurian> requirement = (Aventurian a) -> {
		return true;
	};

	@Before
	public void setUp() throws Exception {
		toTest = new BadProperty("testBadProperty", "testDescription", -100,
				requirement);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidCosts() {
		toTest = new BadProperty("testBadProperty", "testDescription", 100, requirement);
	}
	
	@Test
	public void testIsDisadvantage() {
		assertTrue(toTest.isDisadvantage());
	}
	
	@Test
	public void testCorrectMinAndMaxLevel() {
		assertEquals(5, toTest.getMinLevel());
		assertEquals(12, toTest.getMaxLevel());
	}

}
