package skills;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class IncreasableSkillTest {

	private static final int COSTS = 50;
	private static final int MIN_LEVEL = 1;
	private static final int MAX_LEVEL = 5;
	IncreasableSkill toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new IncreasableSkill("test", "description", COSTS, MIN_LEVEL, MAX_LEVEL) {
		};
	}

	@Test
	public void testIncrease() {
		assertEquals(1, toTest.getLevel());
		toTest.increase();
		assertEquals(2, toTest.getLevel());
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseExceedMaximum() {
		while (toTest.isAllowedToIncrease(null))
			toTest.increase();
		toTest.increase();
	}

	@Test
	public void testDecrease() {
		// first increase to be able to decrease
		toTest.increase();
		assertEquals(2, toTest.getLevel());
		toTest.decrease();
		assertEquals(1, toTest.getLevel());
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseExceedMinimum() {
		toTest.decrease();
	}

	@Test
	public void testGetLevel() {
		assertEquals(1, toTest.getLevel());
	}

	@Test
	public void testGetUpgradeCost() {
		assertEquals(1, toTest.getLevel());
		assertEquals(100, toTest.getUpgradeCosts());

		toTest.increase();
		assertEquals(2, toTest.getLevel());
		assertEquals(150, toTest.getUpgradeCosts());

		toTest.increase();
		assertEquals(3, toTest.getLevel());
		assertEquals(200, toTest.getUpgradeCosts());

		toTest.increase();
		assertEquals(4, toTest.getLevel());
		assertEquals(250, toTest.getUpgradeCosts());

	}

	@Test
	public void testGetTotalCost() {
		assertEquals(1, toTest.getLevel());
		assertEquals(50, toTest.getTotalCosts());

		toTest.increase();
		assertEquals(2, toTest.getLevel());
		assertEquals(150, toTest.getTotalCosts());

		toTest.increase();
		assertEquals(3, toTest.getLevel());
		assertEquals(300, toTest.getTotalCosts());

		toTest.increase();
		assertEquals(4, toTest.getLevel());
		assertEquals(500, toTest.getTotalCosts());

		toTest.increase();
		assertEquals(5, toTest.getLevel());
		assertEquals(750, toTest.getTotalCosts());

	}

	@Test
	public void testGetDowngradeCost() {
		while (toTest.isAllowedToIncrease(null))
			toTest.increase();

		assertEquals(5, toTest.getLevel());
		assertEquals(250, toTest.getDowngradeRefund());

		toTest.decrease();
		assertEquals(4, toTest.getLevel());
		assertEquals(200, toTest.getDowngradeRefund());

		toTest.decrease();
		assertEquals(3, toTest.getLevel());
		assertEquals(150, toTest.getDowngradeRefund());

		toTest.decrease();
		assertEquals(2, toTest.getLevel());
		assertEquals(100, toTest.getDowngradeRefund());
	}

	@Test
	public void testGetLearningCost() {
		assertEquals(COSTS, toTest.getLearningCosts());
		toTest.increase();
		assertEquals(COSTS, toTest.getLearningCosts());
	}

	@Test
	public void testIsAllowedToIncrease() {
		assertTrue(toTest.isAllowedToIncrease(null));

		for (int i = 1; i < MAX_LEVEL; i++)
			toTest.increase();

		assertFalse(toTest.isAllowedToIncrease(null));
	}

	@Test
	public void testIsAllowedToDecrease() {
		assertFalse(toTest.isAllowedToDecrease());

		toTest.increase();

		assertTrue(toTest.isAllowedToDecrease());
	}
}
