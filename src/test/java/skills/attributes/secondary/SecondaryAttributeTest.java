package skills.attributes.secondary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import aventurian.Aventurian;
import skills.attributes.secondary.SecondaryAttribute;

public class SecondaryAttributeTest {

	SecondaryAttribute toTest;

	@Before
	public void setUp() throws Exception {
		toTest = mock(SecondaryAttribute.class, Mockito.CALLS_REAL_METHODS);
	}

	@Test
	public void testGetTotalCosts() {
		final int expected = 0;
		final int actual = toTest.getTotalCosts();
		assertEquals(expected, actual);
	}

	@Test
	public void testIsAbleToIncrease() {
		assertFalse(toTest.isAbleToIncrease(mock(Aventurian.class)));
	}

	@Test(expected = IllegalStateException.class)
	public void testGetUpgradeCosts() {
		toTest.getUpgradeCosts();
	}

	@Test(expected = IllegalStateException.class)
	public void testDowngradeRefund() {
		toTest.getDowngradeRefund();
	}

	@Test
	public void testGetLevel() {
		final int expected = 0;
		final int actual = toTest.getLevel();
		assertEquals(expected, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncreaseMod() {
		toTest.increaseMod(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecreaseMod() {
		toTest.decreaseMod(-1);
	}

}
