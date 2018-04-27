package skills.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import aventurian.Aventurian;
import skills.Skill;
import skills.properties.Property;

import static aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE.*;
import static org.mockito.Mockito.*;

public class PropertyTest {
	private Property toTest;
	private static final Consumer<Aventurian> ON_GAIN = (Aventurian a) -> {
		a.increasePrimaryAttribute(COURAGE);
	};
	private static final Consumer<Aventurian> ON_LOOSE = (Aventurian a) -> {
		a.decrasePrimaryAttribute(COURAGE);
	};
	private static Predicate<Aventurian> REQUIREMENT = (Aventurian a) -> {
		return a.getPrimaryAttribute(COURAGE) < a
				.getMaxOfPrimaryAttribute(COURAGE);
	};

	@Before
	public void setUp() throws Exception {
		toTest = new Property("testProperty", "testDescription", 100, ON_GAIN,
				ON_LOOSE, REQUIREMENT);
	}

	@Test
	public void testIsAdvantage() {
		assertTrue(toTest.isAdvantage());
		assertFalse(toTest.isDisadvantage());
	}

	@Test
	public void testIsDisadvantage() {
		toTest = new Property("testProperty", "testDescription", -100, ON_GAIN,
				ON_GAIN, REQUIREMENT);
		assertTrue(toTest.isDisadvantage());
		assertFalse(toTest.isAdvantage());
	}

	@Test
	public void testGetCostAdvantages() {
		assertEquals(100, toTest.getLearningCosts());
	}
	
	@Test
	public void testGetCostDisadvantage() {
		toTest = new Property("testProperty", "testDescription", -100, ON_GAIN,
				ON_GAIN, REQUIREMENT);
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
		Aventurian mock = mock(Aventurian.class);
		toTest.gain(mock);
		verify(mock).increasePrimaryAttribute(COURAGE);
	}

	@Test
	public void testLose() {
		Aventurian mock = mock(Aventurian.class);
		toTest.lose(mock);
		verify(mock).decrasePrimaryAttribute(COURAGE);
	}

	@Test
	public void testIsAllowed() {
		Aventurian mock = mock(Aventurian.class);
		when(mock.getPrimaryAttribute(COURAGE)).thenReturn(8);
		when(mock.getMaxOfPrimaryAttribute(COURAGE)).thenReturn(14);
		assertTrue(toTest.isAllowed(mock));

		when(mock.getPrimaryAttribute(COURAGE)).thenReturn(18);
		assertFalse(toTest.isAllowed(mock));
	}

	@Test
	public void testEquals() {
		Skill anotherButSame = new Property("testProperty", "", 1, ON_GAIN,
				ON_LOOSE, REQUIREMENT);
		Skill anotherButDifferent = new Property("other", "", 1, ON_GAIN,
				ON_LOOSE, REQUIREMENT);
		assertTrue(toTest.equals(toTest));
		assertTrue(toTest.equals(anotherButSame));
		assertFalse(toTest.equals(anotherButDifferent));
	}

}
