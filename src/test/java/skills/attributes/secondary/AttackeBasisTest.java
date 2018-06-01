package skills.attributes.secondary;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import skills.attributes.primary.Agilitaet;
import skills.attributes.primary.Koerperkraft;
import skills.attributes.primary.Mut;
import skills.attributes.primary.PrimaryAttribute;

public class AttackeBasisTest {

	private AttackeBasis toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new AttackeBasis();
		toTest.calculateBasis(mockPrimaryAttributes());
	}

	@Test
	public void testCalculateBasis() {
		assertEquals(8, toTest.getLevel());
		assertEquals(0, toTest.getMaxLevel());
	}

	private List<PrimaryAttribute> mockPrimaryAttributes() {
		final PrimaryAttribute agility = Mockito.mock(Agilitaet.class);
		when(agility.getLevel()).thenReturn(13);
		when(agility.getName()).thenReturn(Agilitaet.NAME);
		final PrimaryAttribute courage = Mockito.mock(Mut.class);
		when(courage.getLevel()).thenReturn(13);
		when(courage.getName()).thenReturn(Mut.NAME);
		final PrimaryAttribute strength = Mockito.mock(Koerperkraft.class);
		when(strength.getLevel()).thenReturn(13);
		when(strength.getName()).thenReturn(Koerperkraft.NAME);
		return Arrays.asList(agility, courage, strength);
	}
}
