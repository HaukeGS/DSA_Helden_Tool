package skills;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	private static final String NAME = "blub";
	private static final String DESCRIPTION = "blub";

	@Before
	public void setUp() throws Exception {
		toTest = new Skill(NAME, DESCRIPTION, COSTS) {
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
		final int expected = NAME.compareTo("bla");
		final int actual = toTest.compareTo(o);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetName() {
		assertEquals(NAME, toTest.getName());
	}

	@Test
	public void testEquals() {
		final Skill anotherButSame = new Skill(NAME, "sdfn", COSTS + 1) {
		};
		final Skill anotherButDifferent = new Skill("fg", "sdfn", COSTS + 1) {
		};
		assertTrue(toTest.equals(toTest));
		assertTrue(toTest.equals(anotherButSame));
		assertFalse(toTest.equals(anotherButDifferent));
	}

	@Test
	public void testGetDescription() {
		assertEquals(DESCRIPTION, toTest.getDescription());
	}

}
