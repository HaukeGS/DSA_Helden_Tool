package skills.attributes.secondary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import aventurian.Aventurian;
import skills.attributes.primary.Koerperkraft;
import skills.attributes.primary.Konstitution;
import skills.attributes.primary.PrimaryAttribute;
import skills.attributes.secondary.Lebenspunkte;

public class LebenspunkteTest {
	private Lebenspunkte toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new Lebenspunkte();
		toTest.calculateBasis(mockPrimaryAttributes());
	}

	@Test
	public void testCalculateBasis() {
		assertEquals(16, toTest.getLevel());
		assertEquals(5, toTest.getMaxLevel());
	}

	private List<PrimaryAttribute> mockPrimaryAttributes() {
		final PrimaryAttribute constitution = Mockito.mock(Konstitution.class);
		when(constitution.getName()).thenReturn("Konstitution");
		when(constitution.getLevel()).thenReturn(10);
		final PrimaryAttribute strength = mock(Koerperkraft.class);
		when(strength.getName()).thenReturn("Körperkraft");
		when(strength.getLevel()).thenReturn(12);
		return Arrays.asList(constitution, strength);
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
		expected = 50;
		actual = toTest.getTotalCosts();
		assertEquals(expected, actual);

		toTest.increase();
		expected = 100;
		actual = toTest.getTotalCosts();
		assertEquals(expected, actual);
	}

}
