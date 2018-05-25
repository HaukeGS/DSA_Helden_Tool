package skills.properties;

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
public class ArkanophobieTest {
	private Arkanophobie toTest;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new Arkanophobie();
	}

	@Test
	public void testIsAllowedToHave() {
		when(av.isMage()).thenReturn(false);
		assertTrue(toTest.isAllowedToHave(av));
		when(av.isMage()).thenReturn(true);
		assertFalse(toTest.isAllowedToHave(av));
	}
}
