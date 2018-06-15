package aventurian;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import skills.attributes.primary.Agilitaet;
import skills.attributes.primary.Charisma;
import skills.attributes.primary.Geschicklichkeit;
import skills.attributes.primary.Intuition;
import skills.attributes.primary.Klugheit;
import skills.attributes.primary.Koerperkraft;
import skills.attributes.primary.Konstitution;
import skills.attributes.primary.Mut;
import skills.attributes.primary.PrimaryAttribute;
import skills.attributes.secondary.Astralenergie;
import skills.attributes.secondary.Lebenspunkte;
import skills.attributes.secondary.Magieresistenz;
import skills.attributes.secondary.SecondaryAttribute;

@RunWith(MockitoJUnitRunner.class)
public class AttributesAventurianManagerTest extends BaseTest {

	private AttributesAventurianManager toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new AttributesAventurianManager(mockedFacade, mockedDatabase, mockedLogger);
		toTest.changeAventurian(mockedAventurian);
	}

	@Test
	public void testAddAttributes() {
		when(mockedDatabase.getPrimaryAttributes()).thenReturn(primaryAttributes());
		when(mockedDatabase.getSecondaryAttributes()).thenReturn(secondaryAttributes());
		toTest.addAttributes();
		verify(mockedAventurian, times(8)).add(any(PrimaryAttribute.class));
		verify(mockedAventurian, times(3)).add(any(SecondaryAttribute.class));
		verify(mockedAventurian).updateSecondaryAttributes();
		verify(mockedAventurian, never()).pay(anyInt());
	}

	private List<SecondaryAttribute> secondaryAttributes() {
		return Arrays.asList(mock(Magieresistenz.class), mock(Lebenspunkte.class), mock(Astralenergie.class));
	}

	private List<PrimaryAttribute> primaryAttributes() {
		return Arrays.asList(mock(Agilitaet.class), mock(Geschicklichkeit.class), mock(Klugheit.class),
				mock(Konstitution.class), mock(Mut.class), mock(Koerperkraft.class), mock(Intuition.class),
				mock(Charisma.class));
	}

	@Test
	public void testCanIncreaseSecondaryAttribute() {
		final SecondaryAttribute a = createSecondaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		assertTrue(toTest.canIncrease(a));
	}

	@Test
	public void testCanIncreaseSecondaryAttributeHasNotSkill() {
		final SecondaryAttribute a = createSecondaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(false);
		assertFalse(toTest.canIncrease(a));
	}

	@Test
	public void testCanIncreaseSecondaryAttributeIsNotIncreasable() {
		final SecondaryAttribute a = createSecondaryAttributeMock(false, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		assertFalse(toTest.canIncrease(a));
	}

	@Test
	public void testCanDecreaseSecondaryAttribute() {
		final SecondaryAttribute a = createSecondaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		assertTrue(toTest.canDecrease(a));
	}

	@Test
	public void testCanDecreaseSecondaryAttributeHasNotSkill() {
		final SecondaryAttribute a = createSecondaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(false);
		assertFalse(toTest.canDecrease(a));
	}

	@Test
	public void testCanDecreaseSecondaryAttributeIsNotDecreasable() {
		final SecondaryAttribute a = createSecondaryAttributeMock(true, false);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		assertFalse(toTest.canDecrease(a));
	}

	@Test
	public void testDecreaseSecondaryAttribute() {
		final SecondaryAttribute a = createSecondaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		toTest.decreaseSecondaryAttribute(a);
		verify(mockedAventurian).refund(anyInt());
		verify(mockedAventurian).decreaseSkill(a);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseSecondaryAttributeCannotBeDecreased() {
		final SecondaryAttribute a = createSecondaryAttributeMock(true, false);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		toTest.decreaseSecondaryAttribute(a);
	}

	@Test
	public void testIncreaseSecondaryAttribute() {
		final SecondaryAttribute a = createSecondaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		toTest.increaseSecondaryAttribute(a);
		verify(mockedAventurian).pay(anyInt());
		verify(mockedAventurian).increaseSkill(a);
	}

	@Test
	public void testIncreaseSecondaryAttributeWithoutPay() {
		final SecondaryAttribute a = createSecondaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		toTest.increaseSecondaryAttributeWithoutPay(a);
		verify(mockedAventurian, never()).pay(anyInt());
		verify(mockedAventurian).increaseSkill(a);
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseSecondaryAttributeCannotBeIncreased() {
		final SecondaryAttribute a = createSecondaryAttributeMock(false, false);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		toTest.increaseSecondaryAttribute(a);
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseSecondaryAttributeWithoutPayCannotBeIncreased() {
		final SecondaryAttribute a = createSecondaryAttributeMock(false, false);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		toTest.increaseSecondaryAttributeWithoutPay(a);
	}

	private SecondaryAttribute createSecondaryAttributeMock(boolean isIncreasable, boolean isDecreasable) {
		final SecondaryAttribute a = mock(SecondaryAttribute.class);
		when(a.isAllowedToIncrease(mockedAventurian)).thenReturn(isIncreasable);
		when(a.isAllowedToDecrease()).thenReturn(isDecreasable);

		return a;
	}

	@Test
	public void testCanIncreasePrimaryAttribute() {
		final PrimaryAttribute a = createPrimaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		assertTrue(toTest.canIncrease(a));
	}

	@Test
	public void testCanIncreasePrimaryAttributeHasNotSkill() {
		final PrimaryAttribute a = createPrimaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(false);
		assertFalse(toTest.canIncrease(a));
	}

	@Test
	public void testCanIncreasePrimaryAttributeIsNotIncreasable() {
		final PrimaryAttribute a = createPrimaryAttributeMock(false, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		assertFalse(toTest.canIncrease(a));
	}

	@Test
	public void testCanDecreasePrimaryAttribute() {
		final PrimaryAttribute a = createPrimaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		assertTrue(toTest.canDecrease(a));
	}

	@Test
	public void testCanDecreasePrimaryAttributeHasNotSkill() {
		final PrimaryAttribute a = createPrimaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(false);
		assertFalse(toTest.canDecrease(a));
	}

	@Test
	public void testCanDecreasePrimaryAttributeIsNotDecreasable() {
		final PrimaryAttribute a = createPrimaryAttributeMock(true, false);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		assertFalse(toTest.canDecrease(a));
	}

	@Test
	public void testDecreasePrimaryAttribute() {
		final PrimaryAttribute a = createPrimaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		toTest.decreasePrimaryAttribute(a);
		verify(mockedAventurian).refund(anyInt());
		verify(mockedAventurian).decreaseSkill(a);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreasePrimaryAttributeCannotBeDecreased() {
		final PrimaryAttribute a = createPrimaryAttributeMock(true, false);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		toTest.decreasePrimaryAttribute(a);
	}

	@Test
	public void testIncreasePrimaryAttribute() {
		final PrimaryAttribute a = createPrimaryAttributeMock(true, true);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		toTest.increasePrimaryAttribute(a);
		verify(mockedAventurian).pay(anyInt());
		verify(mockedAventurian).increaseSkill(a);
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreasePrimaryAttributeCannotBeIncreased() {
		final PrimaryAttribute a = createPrimaryAttributeMock(false, false);
		when(mockedAventurian.hasSkill(a)).thenReturn(true);
		toTest.increasePrimaryAttribute(a);
	}

	private PrimaryAttribute createPrimaryAttributeMock(boolean isIncreasable, boolean isDecreasable) {
		final PrimaryAttribute a = mock(PrimaryAttribute.class);
		when(a.isAllowedToIncrease(mockedAventurian)).thenReturn(isIncreasable);
		when(a.isAllowedToDecrease()).thenReturn(isDecreasable);

		return a;
	}

	@Test
	public void testApplyRaceModPositive() {
		final SecondaryAttribute a = mock(SecondaryAttribute.class);
		toTest.applyRaceMod(a, 5);
		verify(a).increaseMod(5);
		verify(mockedAventurian).pay(anyInt());
	}

	@Test
	public void testApplyRaceModNegative() {
		final SecondaryAttribute a = mock(SecondaryAttribute.class);
		toTest.applyRaceMod(a, -5);
		verify(a).decreaseMod(5);
		verify(mockedAventurian).refund(anyInt());
	}

}
