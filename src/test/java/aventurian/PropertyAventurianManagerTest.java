package aventurian;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skills.BadProperty;
import skills.Property;

@RunWith(MockitoJUnitRunner.class)
public class PropertyAventurianManagerTest {
	@Mock
	Aventurian a;
	PropertyAventurianManager toTest;

	@Before
	public void setUp() throws Exception {
		when(a.canPay(anyInt())).thenReturn(true);
		toTest = new PropertyAventurianManager(a);
	}

	@Test
	public void testAddPropertyAdvantageExceedsMaxPointsInAdvantages() {
		final Property p = createPropertyMock(true, true);
		when(a.canPay(anyInt())).thenReturn(true);
		when(a.getPointsInAdvantages()).thenReturn(PropertyAventurianManager.MAX_POINTS_IN_ADVANTAGES);

		toTest.addProperty(p);

		verify(a, never()).add(p);
		verify(a, never()).pay(anyInt());
		verify(p, never()).gain(a);
	}

	private Property createPropertyMock(boolean isAllowed, boolean isAdvantage) {
		final Property p = mock(Property.class);
		when(p.isAllowed(a)).thenReturn(isAllowed);
		when(p.isAdvantage()).thenReturn(isAdvantage);
		when(p.isDisadvantage()).thenReturn(!isAdvantage);
		when(p.getName()).thenReturn("testProperty");
		when(p.getCost()).thenReturn(200);
		return p;
	}

	@Test
	public void testAddPropertyAdvantageTooExpensive() {
		final Property p = createPropertyMock(true, true);
		when(a.canPay(anyInt())).thenReturn(false);

		toTest.addProperty(p);

		verify(a, never()).add(p);
		verify(a, never()).pay(anyInt());
		verify(p, never()).gain(a);
	}

	@Test
	public void testAddPropertyAdvantageNotAllowed() {
		final Property p = createPropertyMock(false, true);

		toTest.addProperty(p);

		verify(a, never()).add(p);
		verify(a, never()).pay(anyInt());
		verify(p, never()).gain(a);

	}

	@Test
	public void testAddPropertyDisdvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, false);

		toTest.addProperty(p);

		verify(a).add(p);
		verify(a).refund(anyInt());

	}

	@Test
	public void testAddPropertyDisadvantageExceedMaxPointsOutDisadvantages() {
		final Property p = createPropertyMock(true, false);
		when(a.getPointsOutDisadvantages()).thenReturn(PropertyAventurianManager.MAX_POINTS_OUT_DISADVANTAGES);

		toTest.addProperty(p);

		verify(a, never()).add(p);
		verify(a, never()).refund(anyInt());
		verify(p, never()).gain(a);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddPropertyAlreadyHasSkill() {
		final Property p = createPropertyMock(true, true);
		when(a.hasSkill(p)).thenReturn(true);
		toTest.addProperty(p);

	}

	@Test
	public void testAddBadPropertyAllConditionsMet() {
		final BadProperty p = createBadPropertyMock(true);
		when(a.getBadPropertySum()).thenReturn(5);

		toTest.addBadProperty(p);

		verify(a).refund(anyInt());
		verify(a).add(p);
	}

	@Test
	public void testAddBadPropertyExceedingMaxPointsOutDisadvantages() {
		final BadProperty p = createBadPropertyMock(true);
		when(a.getPointsOutDisadvantages()).thenReturn(PropertyAventurianManager.MAX_POINTS_OUT_DISADVANTAGES);

		toTest.addBadProperty(p);

		verify(p, never()).gain(a);
		verify(a, never()).refund(anyInt());
		verify(a, never()).add(p);
	}

	private BadProperty createBadPropertyMock(boolean isAllowed, boolean isIncreasable) {
		final BadProperty p = mock(BadProperty.class);
		when(p.isAllowed(a)).thenReturn(isAllowed);
		when(p.isIncreasable()).thenReturn(isIncreasable);
		when(p.getLevel()).thenReturn(5);
		when(p.getCost()).thenReturn(50);
		when(p.getName()).thenReturn("testBadProperty");
		return p;
	}

	private BadProperty createBadPropertyMock(boolean isAllowed) {
		return createBadPropertyMock(isAllowed, true);
	}

	@Test
	public void testAddBadPropertyNotAllowed() {
		final BadProperty p = createBadPropertyMock(false);
		when(a.getBadPropertySum()).thenReturn(5);

		toTest.addBadProperty(p);

		verify(p, never()).gain(a);
		verify(a, never()).pay(anyInt());
		verify(a, never()).add(p);
	}

	@Test
	public void testAddBadPropertyBadPropertySumTooHigh() {
		final BadProperty p = createBadPropertyMock(false);
		when(a.getBadPropertySum()).thenReturn(PropertyAventurianManager.MAX_BAD_PROPERTIES_SUM);

		toTest.addBadProperty(p);

		verify(p, never()).gain(a);
		verify(a, never()).pay(anyInt());
		verify(a, never()).add(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBadPropertyAlreadyHasSkill() {
		final BadProperty p = createBadPropertyMock(true);
		when(a.hasSkill(p)).thenReturn(true);
		toTest.addBadProperty(p);
	}

	@Test(expected = IllegalStateException.class)
	public void testRemoveBadPropertyNotOwned() {
		final BadProperty p = createBadPropertyMock(true);
		when(a.hasSkill(p)).thenReturn(false);

		toTest.removeBadProperty(p);
	}

	@Test
	public void testRemoveBadProperty() {

		final BadProperty p = createBadPropertyMock(true);
		when(a.hasSkill(p)).thenReturn(true);

		toTest.removeBadProperty(p);

		verify(a).remove(p);
		verify(a).refund(anyInt());
	}

	@Test
	public void testRemoveIncreasedBadProperty() {

		final BadProperty p = createBadPropertyMock(true);
		when(p.isDecreasable()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(a.hasSkill(p)).thenReturn(true);

		toTest.removeBadProperty(p);
		verify(p).decrease();
		verify(a).remove(p);
		verify(a, times(2)).refund(anyInt());
	}

	@Test
	public void testDecreaseBadProperty() {
		final BadProperty bp = createBadPropertyMock(true);
		when(a.hasSkill(bp)).thenReturn(true);
		when(bp.isDecreasable()).thenReturn(true).thenReturn(true).thenReturn(false);

		toTest.decreaseBadProperty(bp);

		verify(bp).decrease();
		verify(a).refund(anyInt());
		verify(a, never()).remove(bp);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseBadPropertyNotOwned() {
		final BadProperty bp = createBadPropertyMock(true);
		when(bp.isDecreasable()).thenReturn(true);
		when(a.hasSkill(bp)).thenReturn(false);
		toTest.decreaseBadProperty(bp);
	}

	@Test(expected = IllegalStateException.class)
	public void testDecreaseBadPropertyNotDecreasable() {
		final BadProperty bp = createBadPropertyMock(true);

		toTest.decreaseBadProperty(bp);
	}

	@Test
	public void testIncreaseBadPropertyAllConditionsMet() {
		final BadProperty bp = createBadPropertyMock(true, true);
		when(a.getBadPropertySum()).thenReturn(PropertyAventurianManager.MAX_BAD_PROPERTIES_SUM - 1);
		when(a.hasSkill(bp)).thenReturn(true);
		toTest.increaseBadProperty(bp);

		verify(bp).increase();
		verify(a).pay(anyInt());
	}

	@Test
	public void testIncreaseBadPropertyNotIncreasable() {
		final BadProperty bp = createBadPropertyMock(true, false);
		when(a.hasSkill(bp)).thenReturn(true);
		toTest.increaseBadProperty(bp);

		verify(bp, never()).increase();
		verify(a, never()).pay(anyInt());
	}

	@Test
	public void testIncreaseBadPropertySumExceeded() {
		final BadProperty bp = createBadPropertyMock(true, true);
		when(a.getBadPropertySum()).thenReturn(PropertyAventurianManager.MAX_BAD_PROPERTIES_SUM);
		when(a.hasSkill(bp)).thenReturn(true);
		toTest.increaseBadProperty(bp);

		verify(bp, never()).increase();
		verify(a, never()).pay(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testIncreaseBadPropertyNotOwned() {
		final BadProperty bp = createBadPropertyMock(true, true);
		when(a.hasSkill(bp)).thenReturn(false);

		toTest.increaseBadProperty(bp);
	}

	@Test
	public void testRemovePropertyAdvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, true);
		when(a.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);

		verify(a).remove(p);
		verify(a).refund(anyInt());
	}

	@Test(expected = IllegalStateException.class)
	public void testRemovePropertyNotOwned() {

		final Property p = createPropertyMock(true, true);
		when(a.hasSkill(p)).thenReturn(false);

		toTest.removeProperty(p);
	}

	@Test
	public void testRemovePropertyDisadvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, false);
		when(a.hasSkill(p)).thenReturn(true);

		toTest.removeProperty(p);

		verify(a).remove(p);
		verify(a).pay(anyInt());
	}

	@Test
	public void testAddPropertyAdvantageAllConditionsMet() {
		final Property p = createPropertyMock(true, true);

		toTest.addProperty(p);

		verify(a).add(p);
		verify(a).pay(anyInt());
	}

}
