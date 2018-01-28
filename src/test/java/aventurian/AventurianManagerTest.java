package aventurian;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import database.Database;
import skills.BadProperty;
import skills.Language;
import skills.Property;

@RunWith(MockitoJUnitRunner.class)
public class AventurianManagerTest {
	AventurianManager toTest;
	@Mock
	Aventurian a;
	@Mock
	AttributesAventurianManager attributes;
	@Mock
	LanguageAventurianManager languages;
	@Mock
	PropertyAventurianManager properties;
	@Mock
	RaceAventurianManager races;
	@Mock
	Database db;

	@Before
	public void setUp() throws Exception {
		toTest = new AventurianManager(a, attributes, languages, properties, races, db);
	}

	@Test
	public void testAddProperty() {
		toTest.addProperty(mock(Property.class));
		verify(properties).addProperty(any(Property.class));
	}

	@Test
	public void addBadProperty() {
		toTest.addBadProperty(mock(BadProperty.class));
		verify(properties).addBadProperty(any(BadProperty.class));
	}

	@Test
	public void removeBadProperty() {
		toTest.removeBadProperty(mock(BadProperty.class));
		verify(properties).removeBadProperty(any(BadProperty.class));
	}

	@Test
	public void increaseBadProperty() {
		toTest.increaseBadProperty(mock(BadProperty.class));
		verify(properties).increaseBadProperty(any(BadProperty.class));
	}

	@Test
	public void decreaseBadProperty() {
		toTest.decreaseBadProperty(mock(BadProperty.class));
		verify(properties).decreaseBadProperty(any(BadProperty.class));
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
		verify(a).setName("");

	}

}
