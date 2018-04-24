package skills;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import aventurian.Aventurian;

public class BadPropertyTest {
	private static final int COST = -100;
	private BadProperty toTest;
	private static Predicate<Aventurian> requirement = (Aventurian a) -> {
		return true;
	};

	@Before
	public void setUp() throws Exception {
		toTest = new BadProperty("testBadProperty", "testDescription", COST, requirement);
	}

	@Test(expected = IllegalArgumentException.class)
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

	@Test
	public void testGetTotalCost() {
		assertEquals(BadProperty.MIN_LEVEL * -COST, toTest.getTotalCosts());
		toTest.increase();
		assertEquals((BadProperty.MIN_LEVEL + 1) * -COST, toTest.getTotalCosts());
	}

	@Test
	public void testGetUpgradeCosts() {
		assertEquals(-COST, toTest.getUpgradeCosts());
		toTest.increase();
		assertEquals(-COST, toTest.getUpgradeCosts());
	}
	
	@Test
	public void testGetDowngradeRefund() {
		assertEquals(-COST, toTest.getDowngradeRefund());
		toTest.increase();
		assertEquals(-COST, toTest.getDowngradeRefund());
		
	}

}
