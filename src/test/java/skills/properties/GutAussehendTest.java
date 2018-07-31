package skills.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.Aventurian;

@RunWith(MockitoJUnitRunner.class)
public class GutAussehendTest {
	private GutAussehend toTest;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new GutAussehend();
	}

	@Test
	public void testIsAllowedToHave() {
		when(av.hasSkill(Unansehnlich.NAME)).thenReturn(false);
		assertTrue(toTest.isAllowedToHave(av));
		when(av.hasSkill(Unansehnlich.NAME)).thenReturn(true);
		assertFalse(toTest.isAllowedToHave(av));
	}

	@Test
	public void testGetUpgradeCosts() {
		assertEquals(toTest.getLearningCosts(), toTest.getUpgradeCosts());
		toTest.increase();
		assertEquals(toTest.getLearningCosts(), toTest.getUpgradeCosts());
	}
	
	@Test
	public void testGetDowngradeRefund() {
		toTest.increase();
		assertEquals(toTest.getLearningCosts(), toTest.getDowngradeRefund());
		toTest.increase();
		assertEquals(toTest.getLearningCosts(), toTest.getDowngradeRefund());
	}

	@Test
	public void testGetTotalCosts() {
		assertEquals(toTest.getLevel() * toTest.getLearningCosts(), toTest.getTotalCosts());
		toTest.increase();
		assertEquals(toTest.getLevel() * toTest.getLearningCosts(), toTest.getTotalCosts());
	}

}
