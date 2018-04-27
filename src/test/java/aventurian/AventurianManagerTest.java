package aventurian;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Observer;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import skills.Language;
import skills.properties.BadProperty;
import skills.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class AventurianManagerTest extends BaseTest {
	AventurianManager toTest;
	@Mock
	AttributesAventurianManager attributes;
	@Mock
	LanguageAventurianManager languages;
	@Mock
	PropertyAventurianManager properties;
	@Mock
	RaceAventurianManager races;

	@Before
	public void setUp() throws Exception {
		toTest = new AventurianManager(Optional.of(mockedAventurian), attributes, languages, properties, races, mockedDatabase);
	}
	
	@Test
	public void testCreateNewAventurian() {
		final Observer obs = mock(Observer.class);
		toTest.registerObserver(obs);
		toTest.createNewAventurian("test", 16500, Race.ORK);
		verify(mockedDatabase).reset();
		verify(attributes).changeAventurian(any(Optional.class));
		verify(languages).changeAventurian(any(Optional.class));
		verify(properties).changeAventurian(any(Optional.class));
		verify(races).changeAventurian(any(Optional.class));
		verify(races).buyRaceMods(Race.ORK);
		verify(obs).update(any(Aventurian.class), any());
	}

	@Test
	public void testAddProperty() {
		toTest.addProperty(mock(Property.class));
		verify(properties).addProperty(any(Property.class));
	}

	@Test
	public void increaseProperty() {
		toTest.increaseProperty(mock(BadProperty.class));
		verify(properties).increaseProperty(any(BadProperty.class));
	}

	@Test
	public void decreaseProperty() {
		toTest.decreaseProperty(mock(BadProperty.class));
		verify(properties).decreaseProperty(any(BadProperty.class));
	}

	@Test
	public void removeProperty() {
		toTest.removeProperty(mock(Property.class));
		verify(properties).removeProperty(any(Property.class));
	}

	@Test
	public void increaseLanguage() {
		toTest.increaseLanguage(mock(Language.class));
		verify(languages).increaseLanguage(any(Language.class));
	}

	@Test
	public void decreaseLanguage() {
		toTest.decreaseLanguage(mock(Language.class));
		verify(languages).decreaseLanguage(any(Language.class));
	}

	@Test
	public void addLanguage() {
		toTest.addLanguage(mock(Language.class));
		verify(languages).addLanguage(any(Language.class));
	}

	@Test
	public void addLanguageAsNativeTongue() {
		toTest.addLanguageAsNativeTongue(mock(Language.class));
		verify(languages).addLanguageAsNativeTongue(any(Language.class));
	}

	@Test
	public void removeLanguage() {
		toTest.removeLanguage(mock(Language.class));
		verify(languages).removeLanguage(any(Language.class));
	}

	@Test
	public void increasePrimaryAttribute() {
		toTest.increasePrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE);
		verify(attributes).increasePrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE);
	}

	@Test
	public void decreasePrimaryAttribute() {
		toTest.decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE);
		verify(attributes).decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE);
	}

	@Test
	public void increaseSecondaryAttribute() {
		toTest.increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
		verify(attributes).increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
	}

	@Test
	public void decreaseSecondaryAttribute() {
		toTest.decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
		verify(attributes).decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS);
	}

	@Test
	public void setName() {
		toTest.setName("");
		verify(mockedAventurian).setName("");

	}

}
