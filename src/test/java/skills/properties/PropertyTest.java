package skills.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PropertyTest {
	private Property toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new Property("testProperty", "testDescription", 100);
	}

	@Test
	public void testIsAdvantage() {
		assertTrue(toTest.isAdvantage());
		assertFalse(toTest.isDisadvantage());
	}

	@Test
	public void testIsDisadvantage() {
		toTest = new Property("testProperty", "testDescription", -100);
		assertTrue(toTest.isDisadvantage());
		assertFalse(toTest.isAdvantage());
	}

	@Test
	public void testGetCostAdvantages() {
		assertEquals(100, toTest.getLearningCosts());
	}

	@Test
	public void testGetCostDisadvantage() {
		toTest = new Property("testProperty", "testDescription", -100);
		assertEquals(100, toTest.getLearningCosts());
	}

	

}
