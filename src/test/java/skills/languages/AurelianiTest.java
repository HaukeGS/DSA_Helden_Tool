package skills.languages;

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
public class AurelianiTest {
	private Aureliani toTest;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new Aureliani();
	}

	@Test
	public void testFulfillOptionalRequirement() {
		assertTrue(toTest.specificRequirementsMet(av));
		toTest.increase();
		assertFalse(toTest.specificRequirementsMet(av));
		when(av.hasSkill("Sprachenkunde")).thenReturn(true);
		assertTrue(toTest.specificRequirementsMet(av));
		toTest.increase();
		assertTrue(toTest.specificRequirementsMet(av));
	}

	@Test
	public void testIsAllowedToHave() {
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertFalse(toTest.isAllowedToHave(av));
		
		when(av.hasSkill("Sprachenkunde")).thenReturn(true);
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertTrue(toTest.isAllowedToHave(av));
	}

}
