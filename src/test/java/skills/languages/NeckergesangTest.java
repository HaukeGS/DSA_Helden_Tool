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
public class NeckergesangTest {
	private Neckergesang toTest;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new Neckergesang();
	}

	@Test
	public void testFulfillOptionalRequirement() {
		assertTrue(toTest.isAbleToIncrease(av));
		toTest.increase();
		assertTrue(toTest.isAbleToIncrease(av));
		toTest.increase();
		assertTrue(toTest.isAbleToIncrease(av));
		toTest.increase();
		assertFalse(toTest.isAbleToIncrease(av));

		when(av.hasSkill("Gedankenbilder Elfenruf")).thenReturn(true);
		
		toTest.increase();
		assertTrue(toTest.isAbleToIncrease(av));
	}

	@Test
	public void testIsAllowedToHave() {
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertFalse(toTest.isAllowedToHave(av));

		when(av.hasSkill("Gedankenbilder Elfenruf")).thenReturn(true);
		
		assertTrue(toTest.isAllowedToHave(av));
	}

}
