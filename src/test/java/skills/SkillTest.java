package skills;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import aventurian.Aventurian;

public class SkillTest {
	private static final int COSTS = 50;
	private Skill toTest;
	private final String name = "blub";

	@Before
	public void setUp() throws Exception {
		toTest = new Skill(name, "description", COSTS) {
		};
	}

	@Test
	public void testGain() {
		final Aventurian mock = mock(Aventurian.class);
		toTest.gain(mock);
		verifyZeroInteractions(mock);
	}

	@Test
	public void testLose() {
		final Aventurian mock = mock(Aventurian.class);
		toTest.lose(mock);
		verifyZeroInteractions(mock);
	}

	@Test
	public void testIsAllowedToHave() {
		final Aventurian mock = mock(Aventurian.class);
		assertTrue(toTest.isAllowedToHave(mock));
	}

	@Test
	public void testCompareTo() {
		final Skill o = mock(Skill.class);
		when(o.getName()).thenReturn("bla");
		final int expected = name.compareTo("bla");
		final int actual = toTest.compareTo(o);
		assertEquals(expected, actual);
	}

}
