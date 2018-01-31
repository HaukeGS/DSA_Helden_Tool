package aventurian;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import skills.Property;

@RunWith(MockitoJUnitRunner.class)
public class RaceAventurianManagerTest extends BaseTest {
	private RaceAventurianManager toTest;
	@Mock
	private PropertyAventurianManager mockedPropertyManager;

	@Before
	public void setUp() throws Exception {
		toTest = new RaceAventurianManager(Optional.of(aventurian), db, mockedPropertyManager);

	}

	@Test
	public void testBuyRaceModsHitAndMagicPositive() {
		when(db.getHitPointsModFor(any(Race.class))).thenReturn(11);
		when(db.getMagicResistanceModFor(any(Race.class))).thenReturn(5);
		when(db.getSkillsFor(any(Race.class))).thenReturn(Arrays.asList(mock(Property.class), mock(Property.class)));

		when(aventurian.canPay(any(Integer.class))).thenReturn(true);

		toTest.buyRaceMods(Race.THORWALAN);

		verify(aventurian, atLeastOnce()).canPay(any(Integer.class));
		verify(aventurian, times(2)).pay(any(Integer.class));
		verify(aventurian).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, 5);
		verify(aventurian).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, 11);
		verify(mockedPropertyManager, atLeastOnce()).addProperty(any(Property.class));
	}

	@Test
	public void testBuyRaceModsHitAndMagicNegative() {
		when(db.getHitPointsModFor(any(Race.class))).thenReturn(-11);
		when(db.getMagicResistanceModFor(any(Race.class))).thenReturn(-5);
		when(db.getSkillsFor(any(Race.class))).thenReturn(Arrays.asList(mock(Property.class), mock(Property.class)));

		toTest.buyRaceMods(Race.THORWALAN);

		verify(aventurian, times(2)).refund(any(Integer.class));
		verify(aventurian).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, 5);
		verify(aventurian).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, 11);
		verify(mockedPropertyManager, atLeastOnce()).addProperty(any(Property.class));
	}

}
