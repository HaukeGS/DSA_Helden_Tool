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
import aventurian.AventurianManagerFacade;

@RunWith(MockitoJUnitRunner.class)
public class SkillTest {
	private Skill toTest;
	private static final String NAME = "blub";
	private static final String DESCRIPTION = "blub";

	@Mock
	Aventurian mockedAventurian;

	@Mock
	AventurianManagerFacade mockedFacade;

	@Before
	public void setUp() throws Exception {
		toTest = new Skill(NAME, DESCRIPTION) {
			@Override
			public int getTotalCosts() {
				return 0;
			}
		};
	}

	@Test
	public void testGain() {
		toTest.atGain(mockedFacade);
		verifyZeroInteractions(mockedFacade);
	}

	@Test
	public void testLose() {
		toTest.atLose(mockedFacade);
		verifyZeroInteractions(mockedFacade);
	}

	@Test
	public void testIsAllowedToHave() {
		assertTrue(toTest.isAllowedToHave(mockedAventurian));
	}

	@Test
	public void testIsAllowedToAdd() {
		assertTrue(toTest.isAllowedToAdd(mockedAventurian));
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
			public int getTotalCosts() {
				return 0;
			}
		};
		final Skill anotherButDifferent = new Skill("fg", "sdfn") {
			@Override
			public int getTotalCosts() {
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
