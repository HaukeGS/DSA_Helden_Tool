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
import skills.attributes.secondary.Lebenspunkte;
import skills.attributes.secondary.Magieresistenz;
import skills.attributes.secondary.SecondaryAttribute;
import skills.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class RaceAventurianManagerTest extends BaseTest {
	private RaceAventurianManager toTest;
	@Mock
	private PropertyAventurianManager mockedPropertyManager;
	@Mock
	private AttributesAventurianManager mockedAttributesManager;

	@Before
	public void setUp() throws Exception {
		toTest = new RaceAventurianManager(Optional.of(mockedAventurian), mockedDatabase, mockedPropertyManager,
				mockedAttributesManager);

	}

	@Test
	public void testBuyRaceModsHitAndMagicPositive() {
		final SecondaryAttribute magicResistance = mock(SecondaryAttribute.class);
		when(magicResistance.getName()).thenReturn(Magieresistenz.NAME);
		final SecondaryAttribute hitpoints = mock(SecondaryAttribute.class);
		when(hitpoints.getName()).thenReturn(Lebenspunkte.NAME);
		when(mockedDatabase.getSecondaryAttributes()).thenReturn(Arrays.asList(magicResistance, hitpoints));
		when(mockedDatabase.getHitPointsModFor(any(Race.class))).thenReturn(11);
		when(mockedDatabase.getMagicResistanceModFor(any(Race.class))).thenReturn(5);
		when(mockedDatabase.getSkillsFor(any(Race.class)))
				.thenReturn(Arrays.asList(mock(Property.class), mock(Property.class)));

		toTest.buyRaceMods(Race.THORWALAN);

		verify(mockedAventurian, times(2)).pay(any(Integer.class));
		verify(mockedAventurian).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, 5);
		verify(mockedAventurian).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, 11);
		verify(mockedPropertyManager, atLeastOnce()).addProperty(any(Property.class));
	}

	@Test
	public void testBuyRaceModsHitAndMagicNegative() {
		final SecondaryAttribute magicResistance = mock(SecondaryAttribute.class);
		when(magicResistance.getName()).thenReturn(Magieresistenz.NAME);
		final SecondaryAttribute hitpoints = mock(SecondaryAttribute.class);
		when(hitpoints.getName()).thenReturn(Lebenspunkte.NAME);
		when(mockedDatabase.getSecondaryAttributes()).thenReturn(Arrays.asList(magicResistance, hitpoints));
		when(mockedDatabase.getHitPointsModFor(any(Race.class))).thenReturn(-11);
		when(mockedDatabase.getMagicResistanceModFor(any(Race.class))).thenReturn(-5);
		when(mockedDatabase.getSkillsFor(any(Race.class)))
				.thenReturn(Arrays.asList(mock(Property.class), mock(Property.class)));

		toTest.buyRaceMods(Race.THORWALAN);

		verify(mockedAventurian, times(2)).refund(any(Integer.class));
		verify(mockedAventurian).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, 5);
		verify(mockedAventurian).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, 11);
		verify(mockedPropertyManager, atLeastOnce()).addProperty(any(Property.class));
	}

}
