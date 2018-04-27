package aventurian;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skills.properties.BadProperty;
import skills.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class PropertyAventurianManagerTest extends BaseTest {
	@Mock
	Aventurian aventurian;

	PropertyAventurianManager toTest;

	@Before
	public void setUp() throws Exception {
		when(aventurian.canPay(anyInt())).thenReturn(true);
		toTest = new PropertyAventurianManager(Optional.of(aventurian), mockedDatabase);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddPropertyAdvantageExceedsMaxPointsInAdvantages() {
		final Property p = createPropertyMock(true, true);
		when(aventurian.canPay(anyInt())).thenReturn(true);
		when(aventurian.getPointsInAdvantages()).thenReturn(PropertyAventurianManager.MAX_POINTS_IN_ADVANTAGES);

		toTest.addProperty(p);
	}

	private Property createPropertyMock(boolean isAllowed, boolean isAdvantage) {
		final Property p = mock(Property.class);
		when(p.isAllowed(aventurian)).thenReturn(isAllowed);
		when(p.isAdvantage()).thenReturn(isAdvantage);
		when(p.isDisadvantage()).thenReturn(!isAdvantage);
		when(p.getName()).thenReturn("testProperty");
		when(p.getTotalCosts()).thenReturn(200);
		return p;
	}

	@Test(expected = IllegalStateException.class)
	public void testAddPropertyAdvantageTooExpensive() {
		final Property p = createPropertyMock(true, true);
		when(aventurian.canPay(anyInt())).thenReturn(false);

		toTest.addProperty(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddPropertyAdvantageNotAllowed() {
		final Property p = createPropertyMock(false, true);
		toTest.addProperty(p);
	}

	@Test
	public void testAddPropertyDisdvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, false);

		toTest.addProperty(p);

		verify(aventurian).add(p);
		verify(aventurian).refund(anyInt());

	}

	@Test(expected = IllegalStateException.class)
	public void testAddPropertyDisadvantageExceedMaxPointsOutDisadvantages() {
		final Property p = createPropertyMock(true, false);
		when(aventurian.getPointsOutDisadvantages()).thenReturn(PropertyAventurianManager.MAX_POINTS_OUT_DISADVANTAGES);

		toTest.addProperty(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddPropertyAlreadyHasSkill() {
		final Property p = createPropertyMock(true, true);
		when(aventurian.hasSkill(p)).thenReturn(true);
		toTest.addProperty(p);

	}

	@Test
	public void testAddBadPropertyAllConditionsMet() {
		final BadProperty p = createBadPropertyMock(true);
		when(aventurian.getBadPropertySum()).thenReturn(5);

		toTest.addProperty(p);

		verify(aventurian).refund(anyInt());
		verify(aventurian).add(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBadPropertyExceedingMaxPointsOutDisadvantages() {
		final BadProperty p = createBadPropertyMock(true);
		when(aventurian.getPointsOutDisadvantages()).thenReturn(PropertyAventurianManager.MAX_POINTS_OUT_DISADVANTAGES);
		toTest.addProperty(p);
	}

	private BadProperty createBadPropertyMock(boolean isAllowed, boolean isIncreasable) {
		final BadProperty p = mock(BadProperty.class);
		when(p.isAllowed(aventurian)).thenReturn(isAllowed);
		when(p.isIncreasable()).thenReturn(isIncreasable);
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
		when(aventurian.getBadPropertySum()).thenReturn(PropertyAventurianManager.MAX_BAD_PROPERTIES_SUM);

		toTest.addProperty(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBadPropertyAlreadyHasSkill() {
		final BadProperty p = createBadPropertyMock(true);
		when(aventurian.hasSkill(p)).thenReturn(true);
		toTest.addProperty(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveBadPropertyNotOwned() {
		final BadProperty p = createBadPropertyMock(true);
		when(aventurian.hasSkill(p)).thenReturn(false);

		toTest.removeProperty(p);
	}

	@Test
	public void testRemoveBadProperty() {

		final BadProperty p = createBadPropertyMock(true);
		when(aventurian.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);

		verify(aventurian).remove(p);
		verify(aventurian).pay(anyInt());
	}

	@Test
	public void testRemoveIncreasedBadProperty() {

		final BadProperty p = createBadPropertyMock(true);
		when(p.isDecreasable()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(aventurian.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);
		verify(p, times(2)).decrease();
		verify(aventurian).remove(p);
		verify(aventurian, times(3)).pay(anyInt());
	}

	@Test
	public void testDecreaseProperty() {
		final BadProperty bp = createBadPropertyMock(true);
		when(aventurian.hasSkill(bp)).thenReturn(true);
		when(bp.isDecreasable()).thenReturn(true).thenReturn(true).thenReturn(false);

		toTest.decreaseProperty(bp);

		verify(bp).decrease();
		verify(aventurian).pay(anyInt());
		verify(aventurian, never()).remove(bp);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreasePropertyNotOwned() {
		final BadProperty bp = createBadPropertyMock(true);
		when(bp.isDecreasable()).thenReturn(true);
		when(aventurian.hasSkill(bp)).thenReturn(false);
		toTest.decreaseProperty(bp);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreasePropertyNotDecreasable() {
		final BadProperty bp = createBadPropertyMock(true);

		toTest.decreaseProperty(bp);
	}

	@Test
	public void testIncreaseBadPropertyAllConditionsMet() {
		final BadProperty bp = createBadPropertyMock(true, true);
		when(aventurian.getBadPropertySum()).thenReturn(PropertyAventurianManager.MAX_BAD_PROPERTIES_SUM - 1);
		when(aventurian.hasSkill(bp)).thenReturn(true);
		toTest.increaseProperty(bp);

		verify(bp).increase();
		verify(aventurian).refund(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBadPropertyNotIncreasable() {
		final BadProperty bp = createBadPropertyMock(true, false);
		when(aventurian.hasSkill(bp)).thenReturn(true);
		toTest.increaseProperty(bp);
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBadPropertySumExceeded() {
		final BadProperty bp = createBadPropertyMock(true, true);
		when(aventurian.getBadPropertySum()).thenReturn(PropertyAventurianManager.MAX_BAD_PROPERTIES_SUM);
		when(aventurian.hasSkill(bp)).thenReturn(true);
		toTest.increaseProperty(bp);

	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBadPropertyNotAllowed() {
		final BadProperty bp = createBadPropertyMock(false, true);
		when(aventurian.hasSkill(bp)).thenReturn(true);
		toTest.increaseProperty(bp);

	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBadPropertyNotOwned() {
		final BadProperty bp = createBadPropertyMock(true, true);
		when(aventurian.hasSkill(bp)).thenReturn(false);

		toTest.increaseProperty(bp);
	}

	@Test
	public void testIncreasePropertyAdvantageAllConditionsMet() {
		final Property advantage = createPropertyMock(true, true);
		when(aventurian.hasSkill(advantage)).thenReturn(true);
		when(advantage.isIncreasable()).thenReturn(true);

		toTest.increaseProperty(advantage);
		verify(advantage).increase();
		verify(aventurian).pay(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreasePropertyAdvantageTooExpensive() {
		final Property advantage = createPropertyMock(true, true);
		when(advantage.isIncreasable()).thenReturn(true);
		when(aventurian.hasSkill(advantage)).thenReturn(true);
		when(aventurian.canPay(anyInt())).thenReturn(false);

		toTest.increaseProperty(advantage);

	}

	@Test(expected = IllegalStateException.class)
	public void testIncreasePropertyNotAllowed() {
		final Property advantage = createPropertyMock(false, true);

		toTest.increaseProperty(advantage);

	}

	@Test
	public void testRemovePropertyAdvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, true);
		when(aventurian.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);

		verify(aventurian).remove(p);
		verify(aventurian).refund(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testRemovePropertyNotOwned() {

		final Property p = createPropertyMock(true, true);
		when(aventurian.hasSkill(p)).thenReturn(false);

		toTest.removeProperty(p);
	}

	@Test
	public void testRemovePropertyDisadvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, false);
		when(aventurian.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);

		verify(aventurian).remove(p);
		verify(aventurian).pay(anyInt());
	}

	@Test
	public void testAddPropertyAdvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, true);

		toTest.addProperty(p);

		verify(aventurian).add(p);
		verify(aventurian).pay(anyInt());
	}
}
