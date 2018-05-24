package skills.properties;

import static org.junit.Assert.assertEquals;
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
		when(av.isMage()).thenReturn(false);
	}

	@Test
	public void testIsAllowedToHave() {
		assertEquals(true, toTest.isAllowedToHave(av));
		when(av.isMage()).thenReturn(true);
		assertEquals(false, toTest.isAllowedToHave(av));
	}
}
