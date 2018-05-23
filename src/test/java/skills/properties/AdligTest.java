package skills.properties;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdligTest {
	
	Adlig toTest;

	@BeforeEach
	void setUp() throws Exception {
		toTest = new Adlig();
	}

	@Test
	void testGetUpgradeCosts() {
		assertEquals(100, toTest.getUpgradeCosts());
		toTest.increase();
		assertEquals(150, toTest.getUpgradeCosts());
	}
	
	@Test
	void testGetDowngradeRefund() {
		toTest.increase();
		toTest.increase();
		assertEquals(150, toTest.getDowngradeRefund());
		toTest.decrease();
		assertEquals(100, toTest.getDowngradeRefund());
	}
	
	@Test
	void testGetTotalCosts() {
		assertEquals(250, toTest.getTotalCosts());
		toTest.increase();
		assertEquals(350, toTest.getTotalCosts());
		toTest.increase();
		assertEquals(500, toTest.getTotalCosts());
	}

}
