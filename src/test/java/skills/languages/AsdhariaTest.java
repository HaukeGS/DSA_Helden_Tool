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
public class AsdhariaTest {
	private Asdharia toTest;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new Asdharia();
	}

	@Test
	public void testFulfillOptionalRequirement() {
		assertTrue(toTest.fulfillOptionalRequirement(av));
		toTest.increase();
		assertFalse(toTest.fulfillOptionalRequirement(av));
		when(av.hasSkill("Sprachenkunde")).thenReturn(true);
		assertTrue(toTest.fulfillOptionalRequirement(av));
		toTest.increase();
		assertFalse(toTest.fulfillOptionalRequirement(av));
		when(av.hasSkill("Zweistimmiger Gesang")).thenReturn(true);
		assertTrue(toTest.fulfillOptionalRequirement(av));
	}
	
	@Test
	public void testFulfillOptionalRequirementOnlySecondProperty() {
		assertTrue(toTest.fulfillOptionalRequirement(av));
		toTest.increase();
		assertFalse(toTest.fulfillOptionalRequirement(av));
		when(av.hasSkill("Zweistimmiger Gesang")).thenReturn(true);
		assertTrue(toTest.fulfillOptionalRequirement(av));
		toTest.increase();
		assertTrue(toTest.fulfillOptionalRequirement(av));
		toTest.increase();
		assertTrue(toTest.fulfillOptionalRequirement(av));
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
		assertFalse(toTest.isAllowedToHave(av));
		when(av.hasSkill("Zweistimmiger Gesang")).thenReturn(true);
		assertTrue(toTest.isAllowedToHave(av));
		
	}
	
	@Test
	public void testIsAllowedOnlySecondProperty() {
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertFalse(toTest.isAllowedToHave(av));
		when(av.hasSkill("Zweistimmiger Gesang")).thenReturn(true);
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertTrue(toTest.isAllowedToHave(av));
		toTest.increase();
		assertTrue(toTest.isAllowedToHave(av));
		
	}

}
