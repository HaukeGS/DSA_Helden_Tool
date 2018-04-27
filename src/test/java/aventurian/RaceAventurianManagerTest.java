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
import skills.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class RaceAventurianManagerTest extends BaseTest {
	private RaceAventurianManager toTest;
	@Mock
	private PropertyAventurianManager mockedPropertyManager;

	@Before
	public void setUp() throws Exception {
		toTest = new RaceAventurianManager(Optional.of(mockedAventurian), mockedDatabase, mockedPropertyManager);

	}

	@Test
	public void testBuyRaceModsHitAndMagicPositive() {
		when(mockedDatabase.getHitPointsModFor(any(Race.class))).thenReturn(11);
		when(mockedDatabase.getMagicResistanceModFor(any(Race.class))).thenReturn(5);
		when(mockedDatabase.getSkillsFor(any(Race.class))).thenReturn(Arrays.asList(mock(Property.class), mock(Property.class)));

		when(mockedAventurian.canPay(any(Integer.class))).thenReturn(true);

		toTest.buyRaceMods(Race.THORWALAN);

		verify(mockedAventurian, atLeastOnce()).canPay(any(Integer.class));
		verify(mockedAventurian, times(2)).pay(any(Integer.class));
		verify(mockedAventurian).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, 5);
		verify(mockedAventurian).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, 11);
		verify(mockedPropertyManager, atLeastOnce()).addProperty(any(Property.class));
	}

	@Test
	public void testBuyRaceModsHitAndMagicNegative() {
		when(mockedDatabase.getHitPointsModFor(any(Race.class))).thenReturn(-11);
		when(mockedDatabase.getMagicResistanceModFor(any(Race.class))).thenReturn(-5);
		when(mockedDatabase.getSkillsFor(any(Race.class))).thenReturn(Arrays.asList(mock(Property.class), mock(Property.class)));

		toTest.buyRaceMods(Race.THORWALAN);

		verify(mockedAventurian, times(2)).refund(any(Integer.class));
		verify(mockedAventurian).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, 5);
		verify(mockedAventurian).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, 11);
		verify(mockedPropertyManager, atLeastOnce()).addProperty(any(Property.class));
	}

}
