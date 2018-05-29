package skills.attributes.primary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import skills.attributes.primary.PrimaryAttribute;
import testsuites.categories.CannotRunHeadless;

public class PrimaryAttributeTest {

	private PrimaryAttribute toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new PrimaryAttribute("test", "");
	}

	@Test
	public void testIncreaseMaxLevel() {
		int actual = toTest.getMaxLevel();
		int expected = PrimaryAttribute.MAX;
		assertEquals(expected, actual);
		toTest.increaseMaxLevel();
		actual = toTest.getMaxLevel();
		expected = PrimaryAttribute.MAX + 1;
	}

	@Test
	public void testIncreaseMinLevel() {
		int actual = toTest.getMinLevel();
		int expected = PrimaryAttribute.MIN;
		assertEquals(expected, actual);
		toTest.increaseMinLevel();
		actual = toTest.getMinLevel();
		expected = PrimaryAttribute.MIN + 1;
	}

	@Test
	public void testDecreaseMaxLevel() {
		int actual = toTest.getMaxLevel();
		int expected = PrimaryAttribute.MAX;
		assertEquals(expected, actual);
		toTest.decreaseMaxLevel();
		actual = toTest.getMaxLevel();
		expected = PrimaryAttribute.MAX - 1;
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseMaxLevelLowerThanMinLevel() {
		for (int i = 0; i < PrimaryAttribute.MAX; i++)
			toTest.decreaseMaxLevel();
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseMinLevelHigherThanMaxLevel() {
		for (int i = 0; i < PrimaryAttribute.MAX; i++)
			toTest.increaseMinLevel();
	}

	@Test
	@Category(CannotRunHeadless.class)
	public void testDecreaseMinLevelNegative() {
		for (int i = 0; i < PrimaryAttribute.MIN; i++)
			toTest.decreaseMinLevel();
		toTest.decreaseMinLevel();
		fail("HAUKE: is negative minLevel possible?");
	}

}
