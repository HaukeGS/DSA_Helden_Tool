package aventurian;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import skills.properties.BadProperty;
import skills.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class PropertyAventurianManagerTest extends BaseTest {

	PropertyAventurianManager toTest;

	@Before
	public void setUp() throws Exception {
		toTest = new PropertyAventurianManager(mockedFacade, mockedDatabase, mockedLogger);
		toTest.changeAventurian(mockedAventurian);
	}

	@Test
	public void testCanAddPropertyAdvantageExceedsMaxPointsInAdvantages() {
		final Property p = createPropertyMock(true, true, true);
		when(mockedAventurian.getPointsInAdvantages()).thenReturn(PropertyAventurianManager.MAX_POINTS_IN_ADVANTAGES);

		assertFalse(toTest.canAdd(p));
	}

	private Property createPropertyMock(boolean isAllowedToHave, boolean isAdvantage, boolean isAllowedToAdd) {
		final Property p = mock(Property.class);
		when(p.isAllowedToHave(mockedAventurian)).thenReturn(isAllowedToHave);
		when(p.isAllowedToAdd(mockedAventurian)).thenReturn(isAllowedToAdd);
		when(p.isAdvantage()).thenReturn(isAdvantage);
		when(p.isDisadvantage()).thenReturn(!isAdvantage);
		when(p.getName()).thenReturn("testProperty");
		when(p.getTotalCosts()).thenReturn(200);
		return p;
	}

	@Test
	public void testCanAddPropertyAdvantageNotAllowed() {
		final Property p = createPropertyMock(true, true, false);
		assertFalse(toTest.canAdd(p));
	}

	@Test
	public void testAddPropertyDisdvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, false, true);

		toTest.addProperty(p);

		verify(mockedAventurian).add(p);
		verify(mockedAventurian).refund(anyInt());

	}

	@Test(expected = IllegalStateException.class)
	public void testAddPropertyDisdvantageAllNotConditionsMet() {
		final Property p = createPropertyMock(false, false, false);

		toTest.addProperty(p);

		verify(mockedAventurian, never()).add(p);
		verify(mockedAventurian, never()).refund(anyInt());

	}

	@Test
	public void testCanAddPropertyDisadvantageExceedMaxPointsOutDisadvantages() {
		final Property p = createPropertyMock(true, false, true);
		when(mockedAventurian.getPointsOutDisadvantages())
				.thenReturn(PropertyAventurianManager.MAX_POINTS_OUT_DISADVANTAGES);

		assertFalse(toTest.canAdd(p));
	}

	@Test
	public void testCanAddPropertyAlreadyHasSkill() {
		final Property p = createPropertyMock(true, true, true);
		when(mockedAventurian.hasSkill(p)).thenReturn(true);
		assertFalse(toTest.canAdd(p));

	}

	@Test
	public void testAddBadPropertyAllConditionsMet() {
		final BadProperty p = createBadPropertyMock(true);
		when(mockedAventurian.getBadPropertySum()).thenReturn(5);

		toTest.addProperty(p);

		verify(mockedAventurian).refund(anyInt());
		verify(mockedAventurian).add(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBadPropertyExceedingMaxPointsOutDisadvantages() {
		final BadProperty p = createBadPropertyMock(true);
		when(mockedAventurian.getPointsOutDisadvantages())
				.thenReturn(PropertyAventurianManager.MAX_POINTS_OUT_DISADVANTAGES);
		toTest.addProperty(p);
	}

	private BadProperty createBadPropertyMock(boolean isAllowed, boolean isIncreasable) {
		final BadProperty p = mock(BadProperty.class);
		when(p.isAllowedToHave(mockedAventurian)).thenReturn(isAllowed);
		when(p.isAllowedToAdd(mockedAventurian)).thenReturn(isAllowed);
		when(p.isAllowedToIncrease(mockedAventurian)).thenReturn(isIncreasable);
		when(p.getLevel()).thenReturn(5);
		when(p.getTotalCosts()).thenReturn(250);
		when(p.getName()).thenReturn("testBadProperty");
		when(p.isDisadvantage()).thenReturn(true);
		return p;
	}

	private BadProperty createBadPropertyMock(boolean isAllowed) {
		return createBadPropertyMock(isAllowed, true);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBadPropertyBadPropertySumTooHigh() {
		final BadProperty p = createBadPropertyMock(true);
		when(mockedAventurian.getBadPropertySum()).thenReturn(PropertyAventurianManager.MAX_BAD_PROPERTIES_SUM);

		toTest.addProperty(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBadPropertyAlreadyHasSkill() {
		final BadProperty p = createBadPropertyMock(true);
		when(mockedAventurian.hasSkill(p)).thenReturn(true);
		toTest.addProperty(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveBadPropertyNotOwned() {
		final BadProperty p = createBadPropertyMock(true);
		when(mockedAventurian.hasSkill(p)).thenReturn(false);

		toTest.removeProperty(p);
	}

	@Test
	public void testRemoveBadProperty() {

		final BadProperty p = createBadPropertyMock(true);
		when(mockedAventurian.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);

		verify(mockedAventurian).remove(p);
		verify(mockedAventurian).pay(anyInt());
	}

	@Test
	public void testRemoveIncreasedBadProperty() {

		final BadProperty p = createBadPropertyMock(true);
		when(p.isAllowedToDecrease()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockedAventurian.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);
		verify(mockedAventurian, times(2)).decreaseSkill(p);
		verify(mockedAventurian).remove(p);
		verify(mockedAventurian, times(3)).pay(anyInt());
	}

	@Test
	public void testRemoveIncreasedAdvantage() {

		final Property p = createPropertyMock(true, true, true);
		when(p.isAllowedToDecrease()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(mockedAventurian.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);
		verify(mockedAventurian, times(2)).decreaseSkill(p);
		verify(mockedAventurian).remove(p);
		verify(mockedAventurian, times(3)).refund(anyInt());
	}

	@Test
	public void testDecreaseDisadvantage() {
		final BadProperty bp = createBadPropertyMock(true);
		when(mockedAventurian.hasSkill(bp)).thenReturn(true);
		when(bp.isAllowedToDecrease()).thenReturn(true).thenReturn(true).thenReturn(false);

		toTest.decreaseProperty(bp);

		verify(mockedAventurian).decreaseSkill(bp);
		verify(mockedAventurian).pay(anyInt());
		verify(mockedAventurian, never()).remove(bp);
	}

	@Test
	public void testDecreaseAdvantage() {
		final Property p = createPropertyMock(true, true, true);
		when(mockedAventurian.hasSkill(p)).thenReturn(true);
		when(p.isAllowedToDecrease()).thenReturn(true).thenReturn(true).thenReturn(false);

		toTest.decreaseProperty(p);

		verify(mockedAventurian).decreaseSkill(p);
		verify(mockedAventurian).refund(anyInt());
		verify(mockedAventurian, never()).remove(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreasePropertyNotOwned() {
		final BadProperty bp = createBadPropertyMock(true);
		when(mockedAventurian.hasSkill(bp)).thenReturn(false);
		toTest.decreaseProperty(bp);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreasePropertyNotDecreasable() {
		final BadProperty bp = createBadPropertyMock(true);
		when(mockedAventurian.hasSkill(bp)).thenReturn(true);
		when(bp.isAllowedToDecrease()).thenReturn(false);
		toTest.decreaseProperty(bp);
	}

	@Test
	public void testIncreaseBadPropertyAllConditionsMet() {
		final BadProperty bp = createBadPropertyMock(true, true);
		when(mockedAventurian.getBadPropertySum()).thenReturn(PropertyAventurianManager.MAX_BAD_PROPERTIES_SUM - 1);
		when(mockedAventurian.hasSkill(bp)).thenReturn(true);
		toTest.increaseProperty(bp);

		verify(mockedAventurian).increaseSkill(bp);
		verify(mockedAventurian).refund(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBadPropertyNotIncreasable() {
		final BadProperty bp = createBadPropertyMock(true, false);
		when(mockedAventurian.hasSkill(bp)).thenReturn(true);
		toTest.increaseProperty(bp);
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBadPropertySumExceeded() {
		final BadProperty bp = createBadPropertyMock(true, true);
		when(mockedAventurian.getBadPropertySum()).thenReturn(PropertyAventurianManager.MAX_BAD_PROPERTIES_SUM);
		when(mockedAventurian.hasSkill(bp)).thenReturn(true);
		toTest.increaseProperty(bp);

	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBadPropertyNotAllowed() {
		final BadProperty bp = createBadPropertyMock(false, true);
		when(mockedAventurian.hasSkill(bp)).thenReturn(true);
		toTest.increaseProperty(bp);

	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBadPropertyNotOwned() {
		final BadProperty bp = createBadPropertyMock(true, true);
		when(mockedAventurian.hasSkill(bp)).thenReturn(false);

		toTest.increaseProperty(bp);
	}

	@Test
	public void testIncreasePropertyAdvantageAllConditionsMet() {
		final Property advantage = createPropertyMock(true, true, true);
		when(mockedAventurian.hasSkill(advantage)).thenReturn(true);
		when(advantage.isAllowedToIncrease(mockedAventurian)).thenReturn(true);

		toTest.increaseProperty(advantage);
		verify(mockedAventurian).increaseSkill(advantage);
		verify(mockedAventurian).pay(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreasePropertyNotAllowed() {
		final Property advantage = createPropertyMock(false, true, true);

		toTest.increaseProperty(advantage);

	}

	@Test
	public void testRemovePropertyAdvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, true, true);
		when(mockedAventurian.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);

		verify(mockedAventurian).remove(p);
		verify(mockedAventurian).refund(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testRemovePropertyNotOwned() {

		final Property p = createPropertyMock(true, true, true);
		when(mockedAventurian.hasSkill(p)).thenReturn(false);

		toTest.removeProperty(p);
	}

	@Test
	public void testRemovePropertyDisadvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, false, true);
		when(mockedAventurian.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);

		verify(mockedAventurian).remove(p);
		verify(mockedAventurian).pay(anyInt());
	}

	@Test
	public void testAddPropertyAdvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, true, true);

		toTest.addProperty(p);

		verify(mockedAventurian).add(p);
		verify(mockedAventurian).pay(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testAddPropertyAdvantageNotAllConditionsMet() {
		final Property p = createPropertyMock(true, true, false);

		toTest.addProperty(p);

		verify(mockedAventurian, never()).add(p);
		verify(mockedAventurian, never()).pay(anyInt());
	}

	@Test
	public void testCanAddAllConditionsMet() {
		final Property p = createPropertyMock(true, true, true);
		assertTrue(toTest.canAdd(p));
	}

	@Test
	public void testIncreasePropertyWithoutPayAllConditionsMet() {
		final Property p = createPropertyMock(true, true, true);
		when(mockedAventurian.hasSkill(p)).thenReturn(true);
		when(p.isAllowedToIncrease(mockedAventurian)).thenReturn(true);

		toTest.increasePropertyWithoutPay(p);

		verify(mockedAventurian).increaseSkill(p);
		verify(mockedAventurian, never()).pay(anyInt());

	}

	@Test(expected = IllegalStateException.class)
	public void testIncreasePropertyWithoutPayNotAllConditionsMet() {
		final Property p = createPropertyMock(false, true, true);

		toTest.increasePropertyWithoutPay(p);

		verify(mockedAventurian, never()).increaseSkill(p);
		verify(mockedAventurian, never()).pay(anyInt());

	}
}
