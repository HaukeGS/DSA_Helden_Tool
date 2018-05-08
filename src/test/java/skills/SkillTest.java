package skills;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import aventurian.Aventurian;

public class SkillTest {
	private static final int COSTS = 50;
	private Skill toTest;
	private final String name = "blub";
	private static final Predicate<Aventurian> REQUIREMENT = (Aventurian a) -> {
		return true;
	};
	private static final Consumer<Aventurian> EMPTY = (Aventurian a) -> {
	};

	@Before
	public void setUp() throws Exception {
		toTest = new Skill(name, "description", EMPTY, EMPTY, REQUIREMENT, COSTS) {
		};
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
