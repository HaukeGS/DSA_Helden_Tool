package aventurian;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skills.attributes.secondary.Lebenspunkte;
import skills.attributes.secondary.Magieresistenz;
import skills.attributes.secondary.SecondaryAttribute;
import skills.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class RaceAventurianManagerTest extends BaseTest {
	private RaceAventurianManager toTest;

	@Mock
	private SecondaryAttribute hitpoints;
	@Mock
	private SecondaryAttribute magicResistance;
	@Mock
	private Property mockedProperty;

	@Before
	public void setUp() throws Exception {
		toTest = new RaceAventurianManager(mockedFacade, mockedDatabase, mockedLogger);
		toTest.changeAventurian(mockedAventurian);
		when(mockedDatabase.getSecondaryAttribute(Lebenspunkte.NAME)).thenReturn(hitpoints);
		when(mockedDatabase.getSecondaryAttribute(Magieresistenz.NAME)).thenReturn(magicResistance);
		when(mockedDatabase.getSkillsFor(Race.THORWALAN)).thenReturn(Arrays.asList(mockedProperty));
	}

	@Test
	public void testBuyRaceModsHitAndMagicPositive() {

		when(mockedDatabase.getHitPointsModFor(any(Race.class))).thenReturn(11);
		when(mockedDatabase.getMagicResistanceModFor(any(Race.class))).thenReturn(5);
		toTest.buyRaceMods(Race.THORWALAN);

		verify(mockedFacade, times(11)).increase(hitpoints);
		verify(mockedFacade, times(5)).increase(magicResistance);
		verify(mockedFacade, atLeastOnce()).add(mockedProperty);
	}

	@Test
	public void testBuyRaceModsHitAndMagicNegative() {
		when(mockedDatabase.getHitPointsModFor(any(Race.class))).thenReturn(-11);
		when(mockedDatabase.getMagicResistanceModFor(any(Race.class))).thenReturn(-5);

		toTest.buyRaceMods(Race.THORWALAN);

		verify(mockedFacade, times(11)).decrease(hitpoints);
		verify(mockedFacade, times(5)).decrease(magicResistance);
		verify(mockedFacade, atLeastOnce()).add(mockedProperty);
	}

}
