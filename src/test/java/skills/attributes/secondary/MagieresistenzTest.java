package skills.attributes.secondary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import aventurian.Aventurian;
import skills.attributes.primary.Klugheit;
import skills.attributes.primary.Konstitution;
import skills.attributes.primary.Mut;
import skills.attributes.primary.PrimaryAttribute;

public class MagieresistenzTest {
	private Magieresistenz toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new Magieresistenz();
		toTest.calculateBasis(mockPrimaryAttributes());
	}

	@Test
	public void testCalculateBasis() {
		assertEquals(7, toTest.getLevel());
		assertEquals(6, toTest.getMaxLevel());
	}

	private List<PrimaryAttribute> mockPrimaryAttributes() {
		final PrimaryAttribute intelligence = Mockito.mock(Klugheit.class);
		when(intelligence.getName()).thenReturn(Klugheit.NAME);
		when(intelligence.getLevel()).thenReturn(10);
		final PrimaryAttribute courage = mock(Mut.class);
		when(courage.getName()).thenReturn(Mut.NAME);
		when(courage.getLevel()).thenReturn(12);
		final PrimaryAttribute constitution = mock(Konstitution.class);
		when(constitution.getName()).thenReturn(Konstitution.NAME);
		when(constitution.getLevel()).thenReturn(12);
		return Arrays.asList(intelligence, courage, constitution);
	}

	@Test
	public void testGetUpgradeCost() {
		final int expected = 100;
		int actual = toTest.getUpgradeCosts();
		assertEquals(expected, actual);

		toTest.increase();
		actual = toTest.getUpgradeCosts();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetDowngradeRefund() {
		final int expected = 100;
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

	@Test
	public void testIsAbleToIncrease() {
		assertTrue(toTest.isAbleToIncrease(mock(Aventurian.class)));
	}

	@Test
	public void testGetTotalCosts() {
		int expected = 0;
		int actual = toTest.getTotalCosts();
		assertEquals(expected, actual);

		toTest.increase();
		expected = 100;
		actual = toTest.getTotalCosts();
		assertEquals(expected, actual);

		toTest.increase();
		expected = 200;
		actual = toTest.getTotalCosts();
		assertEquals(expected, actual);
	}

	@Test
	public void testIsAllowedToDecrease() {
		assertTrue(toTest.isAllowedToDecrease());
		for (int i = 0; i < 7; i++)
			toTest.decrease();
		assertFalse(toTest.isAllowedToDecrease());
	}
}
