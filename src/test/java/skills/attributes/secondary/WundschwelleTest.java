package skills.attributes.secondary;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import skills.attributes.primary.Konstitution;
import skills.attributes.primary.PrimaryAttribute;

public class WundschwelleTest {

	private Wundschwelle toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new Wundschwelle();
		toTest.calculateBasis(mockPrimaryAttributes());
	}

	@Test
	public void testCalculateBasis() {
		assertEquals(7, toTest.getLevel());
		assertEquals(0, toTest.getMaxLevel());
	}

	private List<PrimaryAttribute> mockPrimaryAttributes() {
		final PrimaryAttribute constitution = Mockito.mock(Konstitution.class);
		when(constitution.getLevel()).thenReturn(13);
		when(constitution.getName()).thenReturn(Konstitution.NAME);
		return Arrays.asList(constitution);
	}
}
