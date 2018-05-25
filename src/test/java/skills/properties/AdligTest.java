package skills.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AdligTest {
	
	private Adlig toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new Adlig();
	}

	@Test
	public void testGetUpgradeCosts() {
		assertEquals(100, toTest.getUpgradeCosts());
		toTest.increase();
		assertEquals(150, toTest.getUpgradeCosts());
	}
	
	@Test
	public void testGetDowngradeRefund() {
		toTest.increase();
		toTest.increase();
		assertEquals(150, toTest.getDowngradeRefund());
		toTest.decrease();
		assertEquals(100, toTest.getDowngradeRefund());
	}
	
	@Test
	public void testGetTotalCosts() {
		assertEquals(250, toTest.getTotalCosts());
		toTest.increase();
		assertEquals(350, toTest.getTotalCosts());
		toTest.increase();
		assertEquals(500, toTest.getTotalCosts());
	}

}
