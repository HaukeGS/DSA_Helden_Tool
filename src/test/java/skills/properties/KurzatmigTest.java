package skills.properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.Aventurian;
import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;

@RunWith(MockitoJUnitRunner.class)
public class KurzatmigTest {
	private Kurzatmig toTest;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new Kurzatmig();
	}

	@Test
	public void testIsAllowedToHave() {
		when(av.hasSkill(Ausdauernd.NAME)).thenReturn(false);
		assertTrue(toTest.isAllowedToHave(av));
		when(av.hasSkill(Ausdauernd.NAME)).thenReturn(true);
		assertFalse(toTest.isAllowedToHave(av));
	}

	@Test
	public void testAtGain() {
		toTest.atGain(av);
		verify(av).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.EXHAUSTIONTHRESHHOLD, 1);
	}

	@Test
	public void testAtLose() {
		toTest.atLose(av);
		verify(av).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.EXHAUSTIONTHRESHHOLD, 1);
	}
}
