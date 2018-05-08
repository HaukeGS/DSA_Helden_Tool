package skills.properties;

import static aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE.COURAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import aventurian.Aventurian;
import skills.Skill;

public class PropertyTest {
	private Property toTest;
	private static final Consumer<Aventurian> ON_GAIN = (Aventurian a) -> {
		a.increasePrimaryAttribute(COURAGE);
	};
	private static final Consumer<Aventurian> ON_LOOSE = (Aventurian a) -> {
		a.decrasePrimaryAttribute(COURAGE);
	};

	@Before
	public void setUp() throws Exception {
		toTest = new Property("testProperty", "testDescription", 100, ON_GAIN, ON_LOOSE);
	}

	@Test
	public void testIsAdvantage() {
		assertTrue(toTest.isAdvantage());
		assertFalse(toTest.isDisadvantage());
	}

	@Test
	public void testIsDisadvantage() {
		toTest = new Property("testProperty", "testDescription", -100, ON_GAIN, ON_GAIN);
		assertTrue(toTest.isDisadvantage());
		assertFalse(toTest.isAdvantage());
	}

	@Test
	public void testGetCostAdvantages() {
		assertEquals(100, toTest.getLearningCosts());
	}

	@Test
	public void testGetCostDisadvantage() {
		toTest = new Property("testProperty", "testDescription", -100, ON_GAIN, ON_GAIN);
		assertEquals(100, toTest.getLearningCosts());
	}

	@Test
	public void testGetName() {
		assertEquals("testProperty", toTest.getName());
	}

	@Test
	public void testGetDescription() {
		assertEquals("testDescription", toTest.getDescription());
	}

	@Test
	public void testGain() {
		final Aventurian mock = mock(Aventurian.class);
		toTest.gain(mock);
		verify(mock).increasePrimaryAttribute(COURAGE);
	}

	@Test
	public void testLose() {
		final Aventurian mock = mock(Aventurian.class);
		toTest.lose(mock);
		verify(mock).decrasePrimaryAttribute(COURAGE);
	}

	@Test
	public void testIsAllowed() {
		final Aventurian mock = mock(Aventurian.class);
		assertTrue(toTest.isAllowedToHave(mock));
	}

	@Test
	public void testEquals() {
		final Skill anotherButSame = new Property("testProperty", "", 1, ON_GAIN, ON_LOOSE);
		final Skill anotherButDifferent = new Property("other", "", 1, ON_GAIN, ON_LOOSE);
		assertTrue(toTest.equals(toTest));
		assertTrue(toTest.equals(anotherButSame));
		assertFalse(toTest.equals(anotherButDifferent));
	}

}
