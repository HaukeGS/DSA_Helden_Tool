package skills.properties;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AusruestungsvorteilTest {
	private Ausruestungsvorteil toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new Ausruestungsvorteil();
	}

	@Test
	public void testGetUpgradeCosts() {
		assertEquals(toTest.getLearningCosts(), toTest.getUpgradeCosts());
		toTest.increase();
		assertEquals(50, toTest.getUpgradeCosts());
		toTest.increase();
		toTest.increase();
		assertEquals(toTest.getLearningCosts(), toTest.getUpgradeCosts());
		toTest.increase();
		toTest.increase();
		toTest.increase();
		toTest.increase();
		toTest.increase();
		toTest.increase();
		assertEquals(50, toTest.getUpgradeCosts());
	}
	
	@Test
	public void testGetDowngradeRefund() {
		for (int i = 0; i < 50; i++)
			toTest.increase();
		assertEquals(50, toTest.getDowngradeRefund());
		toTest.decrease();
		assertEquals(toTest.getLearningCosts(), toTest.getDowngradeRefund());
		toTest.decrease();
		toTest.decrease();
		assertEquals(50, toTest.getDowngradeRefund());
		toTest.decrease();
		toTest.decrease();
		toTest.decrease();
		toTest.decrease();
		toTest.decrease();
		toTest.decrease();
		toTest.decrease();
		toTest.decrease();
		assertEquals(toTest.getLearningCosts(), toTest.getDowngradeRefund());
	}

	@Test
	public void testGetTotalCosts() {
		assertEquals(toTest.getLevel()*toTest.getLearningCosts(), toTest.getTotalCosts());
		toTest.increase();
		assertEquals(toTest.getLevel()*toTest.getLearningCosts(), toTest.getTotalCosts());
	}
}
