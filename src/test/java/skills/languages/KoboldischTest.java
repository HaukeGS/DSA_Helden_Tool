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
public class KoboldischTest {
	private Koboldisch toTest;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new Koboldisch();
	}

	@Test
	public void testIsAllowedToHave() {
		when(av.isMage()).thenReturn(false);
		assertFalse(toTest.isAllowedToHave(av));
		when(av.isMage()).thenReturn(true);
		assertTrue(toTest.isAllowedToHave(av));		
	}

	@Test
	public void testIsAllowedToAdd() {
		when(av.isMage()).thenReturn(false);
		assertFalse(toTest.isAllowedToAdd(av));
		when(av.isMage()).thenReturn(true);
		assertTrue(toTest.isAllowedToAdd(av));		
	}

}
