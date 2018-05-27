package skills.attributes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LebenspunkteTest {
	private Lebenspunkte toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new Lebenspunkte();
	}

	@Test
	public void testGetUpgradeCost() {
		final int expected = 50;
		int actual = toTest.getUpgradeCosts();
		assertEquals(expected, actual);

		toTest.increase();
		actual = toTest.getUpgradeCosts();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetDowngradeRefund() {
		final int expected = 50;
		int actual = toTest.getDowngradeRefund();
		assertEquals(expected, actual);
		
		toTest.increase();
		toTest.increase();
		
		actual = toTest.getDowngradeRefund();
		assertEquals(expected, actual);

		toTest.decrease();
		actual = toTest.getDowngradeRefund();
		assertEquals(expected, actual);
	}

}
