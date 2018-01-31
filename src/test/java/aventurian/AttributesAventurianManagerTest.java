package aventurian;

import static aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE.COURAGE;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;

@RunWith(MockitoJUnitRunner.class)
public class AttributesAventurianManagerTest extends BaseTest {

	private AttributesAventurianManager toTest;

	@Before
	public void setUp() throws Exception {
		when(aventurian.canPay(anyInt())).thenReturn(true);
		toTest = new AttributesAventurianManager(Optional.of(aventurian), db);
	}

	@Test
	public void testIncreasePrimaryAttributeAllConditionsMet() {
		setUpMockForPrimaryAttributesTest(true, true, true);
		toTest.increasePrimaryAttribute(COURAGE);
		verify(aventurian).increasePrimaryAttribute(COURAGE);
		verify(aventurian).pay(anyInt());
	}

	private void setUpMockForPrimaryAttributesTest(boolean canPay, boolean currentSmallerThanMax,
			boolean sumSmallerThanMax) {
		when(aventurian.canPay(anyInt())).thenReturn(canPay);
		when(aventurian.isPrimaryAttributesLowerThanThreshhold()).thenReturn(sumSmallerThanMax);
		// when(a.getSumOfPrimaryAttributes()).thenReturn(
		// sumSmallerThanMax ? Aventurian.MAX_ATTRIBUTES_SUM - 1 :
		// Aventurian.MAX_ATTRIBUTES_SUM);
		// when(a.getMaxOfPrimaryAttribute(COURAGE)).thenReturn(PrimaryAttributes.MAX);
		when(aventurian.isPrimaryAttributeIncreasable(COURAGE)).thenReturn(currentSmallerThanMax);
	}

	@Test
	public void testIncreasePrimaryAttributeTooExpensive() {
		setUpMockForPrimaryAttributesTest(false, true, true);
		toTest.increasePrimaryAttribute(COURAGE);
		verify(aventurian, never()).increasePrimaryAttribute(COURAGE);
		verify(aventurian, never()).pay(anyInt());
	}

	@Test
	public void testIncreasePrimaryAttributeCurrentAtMaximum() {
		setUpMockForPrimaryAttributesTest(true, false, true);
		toTest.increasePrimaryAttribute(COURAGE);
		verify(aventurian, never()).increasePrimaryAttribute(COURAGE);
		verify(aventurian, never()).pay(anyInt());
	}

	@Test
	public void testIncreasePrimaryAttributeSumTooBig() {
		setUpMockForPrimaryAttributesTest(true, true, false);
		toTest.increasePrimaryAttribute(COURAGE);
		verify(aventurian, never()).increasePrimaryAttribute(COURAGE);
		verify(aventurian, never()).pay(anyInt());
	}

	@Test
	public void testDecreasePrimaryAttributAlreadyAtMinimum() {
		when(aventurian.getPrimaryAttribute(COURAGE)).thenReturn(PrimaryAttributes.MIN);
		toTest.decreasePrimaryAttribute(COURAGE);
		verify(aventurian, never()).decrasePrimaryAttribute(COURAGE);

	}

	@Test
	public void testDecreasePrimaryAttributAllConditionsMet() {
		when(aventurian.getPrimaryAttribute(COURAGE)).thenReturn(PrimaryAttributes.MIN + 1);
		when(aventurian.isPrimaryAttributeDecreasable(COURAGE)).thenReturn(true);
		toTest.decreasePrimaryAttribute(COURAGE);
		verify(aventurian).decrasePrimaryAttribute(COURAGE);
		verify(aventurian).refund(anyInt());

	}

	@Test
	public void testIncreaseSecondaryAttributeNotIncreasable() {
		when(aventurian.isSecondaryAttributeIncreasableByBuy(SECONDARY_ATTRIBUTE.HITPOINTS)).thenReturn(false);
		toTest.increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
		verify(aventurian, never()).pay(anyInt());
		verify(aventurian, never()).increaseSecondaryAttributeByBuy(SECONDARY_ATTRIBUTE.HITPOINTS);
	}

	@Test
	public void testIncreaseSecondaryAttributeTooExpensive() {
		when(aventurian.isSecondaryAttributeIncreasableByBuy(SECONDARY_ATTRIBUTE.ASTRALPOINTS)).thenReturn(true);
		when(aventurian.getSecondaryAttributeCost(SECONDARY_ATTRIBUTE.ASTRALPOINTS)).thenReturn(50);
		when(aventurian.canPay(50)).thenReturn(false);
		toTest.increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.ASTRALPOINTS);
		verify(aventurian, never()).pay(anyInt());
		verify(aventurian, never()).increaseSecondaryAttributeByBuy(SECONDARY_ATTRIBUTE.ASTRALPOINTS);
	}

	@Test
	public void testIncreaseSecondaryAttributeAllConditionsMet() {
		when(aventurian.isSecondaryAttributeIncreasableByBuy(SECONDARY_ATTRIBUTE.MAGICRESISTANCE)).thenReturn(true);
		when(aventurian.getSecondaryAttributeCost(SECONDARY_ATTRIBUTE.MAGICRESISTANCE)).thenReturn(100);
		when(aventurian.canPay(100)).thenReturn(true);
		toTest.increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE);
		verify(aventurian).pay(anyInt());
		verify(aventurian).increaseSecondaryAttributeByBuy(SECONDARY_ATTRIBUTE.MAGICRESISTANCE);
	}

	@Test
	public void testDecreaseSecondaryAttributeNotDecreasable() {
		toTest.decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
		verify(aventurian, never()).decreaseSecondaryAttributeByBuy(SECONDARY_ATTRIBUTE.HITPOINTS);
		verify(aventurian, never()).pay(anyInt());
	}

	@Test
	public void testDecreaseSecondaryAttributeAllConditionsMet() {
		when(aventurian.isSecondaryAttributeDecreasableByBuy(SECONDARY_ATTRIBUTE.ASTRALPOINTS)).thenReturn(true);
		toTest.decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.ASTRALPOINTS);
		verify(aventurian).decreaseSecondaryAttributeByBuy(SECONDARY_ATTRIBUTE.ASTRALPOINTS);
		verify(aventurian).refund(anyInt());
	}

}
