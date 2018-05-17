package skills;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.Aventurian;

@RunWith(MockitoJUnitRunner.class)
public class SkillTest {
	private Skill toTest;
	private static final String NAME = "blub";
	private static final String DESCRIPTION = "blub";

	@Mock
	Aventurian mock;

	@Before
	public void setUp() throws Exception {
		toTest = new Skill(NAME, DESCRIPTION) {
			@Override
			int getTotalCosts() {
				return 0;
			}
		};
	}

	@Test
	public void testGain() {
		toTest.gain(mock);
		verifyZeroInteractions(mock);
	}

	@Test
	public void testLose() {
		toTest.lose(mock);
		verifyZeroInteractions(mock);
	}

	@Test
	public void testIsAllowedToHave() {
		assertTrue(toTest.isAllowedToHave(mock));
	}

	@Test
	public void testIsAllowedToAdd() {
		assertTrue(toTest.isAllowedToAdd(mock));
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
	public void testToString() {
		assertEquals(NAME, toTest.toString());
	}

	@Test
	public void testEquals() {
		final Skill anotherButSame = new Skill(NAME, "sdfn") {
			@Override
			int getTotalCosts() {
				return 0;
			}
		};
		final Skill anotherButDifferent = new Skill("fg", "sdfn") {
			@Override
			int getTotalCosts() {
				return 0;
			}
		};
		assertTrue(toTest.equals(toTest));
		assertTrue(toTest.equals(anotherButSame));
		assertFalse(toTest.equals(anotherButDifferent));
		assertFalse(toTest.equals(null));
	}

	@Test
	public void testGetDescription() {
		assertEquals(DESCRIPTION, toTest.getDescription());
	}

}
