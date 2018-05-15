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
		toTest = new AttributesAventurianManager(Optional.of(mockedAventurian), mockedDatabase);
	}

	@Test
	public void testIncreasePrimaryAttributeAllConditionsMet() {
		setUpMockForPrimaryAttributesTest(true, true, true);
		toTest.increasePrimaryAttribute(COURAGE);
		verify(mockedAventurian).increasePrimaryAttribute(COURAGE);
		verify(mockedAventurian).pay(anyInt());
	}

	private void setUpMockForPrimaryAttributesTest(boolean canPay, boolean currentSmallerThanMax,
			boolean sumSmallerThanMax) {
		when(mockedAventurian.isPrimaryAttributesLowerThanThreshhold()).thenReturn(sumSmallerThanMax);
		// when(a.getSumOfPrimaryAttributes()).thenReturn(
		// sumSmallerThanMax ? Aventurian.MAX_ATTRIBUTES_SUM - 1 :
		// Aventurian.MAX_ATTRIBUTES_SUM);
		// when(a.getMaxOfPrimaryAttribute(COURAGE)).thenReturn(PrimaryAttributes.MAX);
		when(mockedAventurian.isPrimaryAttributeIncreasable(COURAGE)).thenReturn(currentSmallerThanMax);
	}



	@Test
	public void testIncreasePrimaryAttributeCurrentAtMaximum() {
		setUpMockForPrimaryAttributesTest(true, false, true);
		toTest.increasePrimaryAttribute(COURAGE);
		verify(mockedAventurian, never()).increasePrimaryAttribute(COURAGE);
		verify(mockedAventurian, never()).pay(anyInt());
	}

	@Test
	public void testIncreasePrimaryAttributeSumTooBig() {
		setUpMockForPrimaryAttributesTest(true, true, false);
		toTest.increasePrimaryAttribute(COURAGE);
		verify(mockedAventurian, never()).increasePrimaryAttribute(COURAGE);
		verify(mockedAventurian, never()).pay(anyInt());
	}

	@Test
	public void testDecreasePrimaryAttributAlreadyAtMinimum() {
		when(mockedAventurian.getPrimaryAttribute(COURAGE)).thenReturn(PrimaryAttributes.MIN);
		toTest.decreasePrimaryAttribute(COURAGE);
		verify(mockedAventurian, never()).decrasePrimaryAttribute(COURAGE);

	}

	@Test
	public void testDecreasePrimaryAttributAllConditionsMet() {
		when(mockedAventurian.getPrimaryAttribute(COURAGE)).thenReturn(PrimaryAttributes.MIN + 1);
		when(mockedAventurian.isPrimaryAttributeDecreasable(COURAGE)).thenReturn(true);
		toTest.decreasePrimaryAttribute(COURAGE);
		verify(mockedAventurian).decrasePrimaryAttribute(COURAGE);
		verify(mockedAventurian).refund(anyInt());

	}

	@Test
	public void testIncreaseSecondaryAttributeNotIncreasable() {
		when(mockedAventurian.isSecondaryAttributeIncreasableByBuy(SECONDARY_ATTRIBUTE.HITPOINTS)).thenReturn(false);
		toTest.increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
		verify(mockedAventurian, never()).pay(anyInt());
		verify(mockedAventurian, never()).increaseSecondaryAttributeByBuy(SECONDARY_ATTRIBUTE.HITPOINTS);
	}

	@Test
	public void testIncreaseSecondaryAttributeAllConditionsMet() {
		when(mockedAventurian.isSecondaryAttributeIncreasableByBuy(SECONDARY_ATTRIBUTE.MAGICRESISTANCE))
				.thenReturn(true);
		when(mockedAventurian.getSecondaryAttributeCost(SECONDARY_ATTRIBUTE.MAGICRESISTANCE)).thenReturn(100);
		toTest.increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE);
		verify(mockedAventurian).pay(anyInt());
		verify(mockedAventurian).increaseSecondaryAttributeByBuy(SECONDARY_ATTRIBUTE.MAGICRESISTANCE);
	}

	@Test
	public void testDecreaseSecondaryAttributeNotDecreasable() {
		toTest.decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
		verify(mockedAventurian, never()).decreaseSecondaryAttributeByBuy(SECONDARY_ATTRIBUTE.HITPOINTS);
		verify(mockedAventurian, never()).pay(anyInt());
	}

	@Test
	public void testDecreaseSecondaryAttributeAllConditionsMet() {
		when(mockedAventurian.isSecondaryAttributeDecreasableByBuy(SECONDARY_ATTRIBUTE.ASTRALPOINTS)).thenReturn(true);
		toTest.decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.ASTRALPOINTS);
		verify(mockedAventurian).decreaseSecondaryAttributeByBuy(SECONDARY_ATTRIBUTE.ASTRALPOINTS);
		verify(mockedAventurian).refund(anyInt());
	}

}
