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
import skills.attributes.primary.Charisma;
import skills.attributes.primary.Intuition;
import skills.attributes.primary.Mut;
import skills.attributes.primary.PrimaryAttribute;

public class AstralenergieTest {
	private Astralenergie toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new Astralenergie();
		toTest.calculateBasis(mockPrimaryAttributes());
	}

	@Test
	public void testCalculateBasis() {
		assertEquals(17, toTest.getLevel());
		assertEquals(6, toTest.getMaxLevel());
	}

	private List<PrimaryAttribute> mockPrimaryAttributes() {
		final PrimaryAttribute intuition = Mockito.mock(Intuition.class);
		when(intuition.getName()).thenReturn(Intuition.NAME);
		when(intuition.getLevel()).thenReturn(10);
		final PrimaryAttribute courage = mock(Mut.class);
		when(courage.getName()).thenReturn(Mut.NAME);
		when(courage.getLevel()).thenReturn(12);
		final PrimaryAttribute charisma = mock(Charisma.class);
		when(charisma.getName()).thenReturn(Charisma.NAME);
		when(charisma.getLevel()).thenReturn(12);
		return Arrays.asList(intuition, courage, charisma);
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
	
	@Test
	public void testIsAllowedToDecrease() {
		assertTrue(toTest.isAllowedToDecrease());
		for(int i = 0; i<17;i++)
			toTest.decrease();
		assertFalse(toTest.isAllowedToDecrease());
	}

}
