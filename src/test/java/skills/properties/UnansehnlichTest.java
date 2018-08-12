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
public class UnansehnlichTest {
	private Unansehnlich toTest;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new Unansehnlich();
	}

	@Test
	public void testIsAllowedToHave() {
		when(av.hasSkill(GutAussehend.NAME)).thenReturn(false);
		assertTrue(toTest.isAllowedToHave(av));
		when(av.hasSkill(GutAussehend.NAME)).thenReturn(true);
		assertFalse(toTest.isAllowedToHave(av));
	}

	@Test
	public void testGetUpgradeCosts() {
		assertEquals(500, toTest.getUpgradeCosts());
	}
	
	@Test
	public void testGetDowngradeRefund() {
		toTest.increase();
		assertEquals(500, toTest.getDowngradeRefund());
	}

	@Test
	public void testGetTotalCosts() {
		assertEquals(toTest.getLevel() * 500 - toTest.getLearningCosts(), toTest.getTotalCosts());
		toTest.increase();
		assertEquals(toTest.getLevel() * 500 - toTest.getLearningCosts(), toTest.getTotalCosts());
	}

}
