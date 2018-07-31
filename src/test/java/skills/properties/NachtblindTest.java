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
public class NachtblindTest {
	private Nachtblind toTest;
	@Mock
	Aventurian av;

	@Before
	public void setUp() throws Exception {
		toTest = new Nachtblind();
	}

	@Test
	public void testIsAllowedToHave() {
		when(av.hasSkill(Daemmerungssicht.NAME)).thenReturn(false);
		assertTrue(toTest.isAllowedToHave(av));
		when(av.hasSkill(Daemmerungssicht.NAME)).thenReturn(true);
		assertFalse(toTest.isAllowedToHave(av));
	}

}
