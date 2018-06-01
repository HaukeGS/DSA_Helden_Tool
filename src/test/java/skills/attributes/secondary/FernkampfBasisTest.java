package skills.attributes.secondary;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import skills.attributes.primary.Geschicklichkeit;
import skills.attributes.primary.Intuition;
import skills.attributes.primary.Koerperkraft;
import skills.attributes.primary.PrimaryAttribute;

public class FernkampfBasisTest {

	private FernkampfBasis toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new FernkampfBasis();
		toTest.calculateBasis(mockPrimaryAttributes());
	}

	@Test
	public void testCalculateBasis() {
		assertEquals(8, toTest.getLevel());
		assertEquals(0, toTest.getMaxLevel());
	}

	private List<PrimaryAttribute> mockPrimaryAttributes() {
		final PrimaryAttribute dexterity = Mockito.mock(Geschicklichkeit.class);
		when(dexterity.getLevel()).thenReturn(13);
		when(dexterity.getName()).thenReturn(Geschicklichkeit.NAME);
		final PrimaryAttribute intuition = Mockito.mock(Intuition.class);
		when(intuition.getLevel()).thenReturn(13);
		when(intuition.getName()).thenReturn(Intuition.NAME);
		final PrimaryAttribute strength = Mockito.mock(Koerperkraft.class);
		when(strength.getLevel()).thenReturn(13);
		when(strength.getName()).thenReturn(Koerperkraft.NAME);
		return Arrays.asList(dexterity, intuition, strength);
	}
}
