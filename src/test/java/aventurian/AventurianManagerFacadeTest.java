package aventurian;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skills.attributes.primary.PrimaryAttribute;
import skills.attributes.secondary.SecondaryAttribute;
import skills.languages.Language;
import skills.properties.BadProperty;
import skills.properties.Property;

@RunWith(MockitoJUnitRunner.class)
public class AventurianManagerFacadeTest extends BaseTest {
	AventurianManagerFacade toTest;
	@Mock
	AttributesAventurianManager attributes;
	@Mock
	LanguageAventurianManager languages;
	@Mock
	PropertyAventurianManager properties;
	@Mock
	RaceAventurianManager races;
	@Mock
	MiscelleanousAventurianManager misc;

	@Before
	public void setUp() throws Exception {
		toTest = new AventurianManagerFacade(Optional.of(mockedAventurian), attributes, languages, properties, races, misc,
				mockedDatabase, mockedLogger);
	}

	@Test
	public void testCreateNewAventurian() {
		toTest.createNewAventurian("test", 16500, Race.ORK);
		verify(mockedDatabase).reset();
		verify(attributes).changeAventurian(any(Aventurian.class));
		verify(languages).changeAventurian(any(Aventurian.class));
		verify(properties).changeAventurian(any(Aventurian.class));
		verify(misc).changeAventurian(any(Aventurian.class));
		verify(races).changeAventurian(any(Aventurian.class));
		verify(attributes).addAttributes();
		verify(races).buyRaceMods(Race.ORK);
	}

	@Test
	public void testAddProperty() {
		toTest.add(mock(Property.class));
		verify(properties).addProperty(any(Property.class));
	}

	@Test
	public void testCanAddProperty() {
		toTest.canAdd(mock(Property.class));
		verify(properties).canAdd(any(Property.class));
	}

	@Test
	public void testIncreaseProperty() {
		toTest.increase(mock(BadProperty.class));
		verify(properties).increaseProperty(any(BadProperty.class));
	}
	
	@Test
	public void testIncreasePropertyWithoutPay() {
		final Property p = mock(Property.class);
		toTest.increasePropertyWithoutPay(p);
		verify(properties).increasePropertyWithoutPay(p);
	}
	
	@Test
	public void testIncreaseSecondaryAttributeWithoutPay() {
		final SecondaryAttribute s = mock(SecondaryAttribute.class);
		toTest.increaseSecondaryAttributeWithoutPay(s);
		verify(attributes).increaseSecondaryAttributeWithoutPay(s);
	}

	@Test
	public void testCannotIncreaseProperty() {
		toTest.canIncrease(mock(BadProperty.class));
		verify(properties).canIncrease(any(BadProperty.class));
	}

	@Test
	public void testDecreaseProperty() {
		toTest.decrease(mock(BadProperty.class));
		verify(properties).decreaseProperty(any(BadProperty.class));
	}

	@Test
	public void testCanDecreaseProperty() {
		toTest.canDecreaseProperty(mock(BadProperty.class));
		verify(properties).canDecrease(any(BadProperty.class));
	}

	@Test
	public void testCannotRemoveProperty() {
		toTest.canRemove(mock(BadProperty.class));
		verify(properties).canRemove(any(BadProperty.class));
	}

	@Test
	public void removeProperty() {
		toTest.remove(mock(Property.class));
		verify(properties).removeProperty(any(Property.class));
	}

	@Test
	public void increaseLanguage() {
		toTest.increase(mock(Language.class));
		verify(languages).increaseLanguage(any(Language.class));
	}

	@Test
	public void decreaseLanguage() {
		toTest.decrease(mock(Language.class));
		verify(languages).decreaseLanguage(any(Language.class));
	}

	@Test
	public void addLanguage() {
		toTest.add(mock(Language.class));
		verify(languages).addLanguage(any(Language.class));
	}

	@Test
	public void addLanguageAsNativeTongue() {
		toTest.addLanguageAsNativeTongue(mock(Language.class));
		verify(languages).addLanguageAsNativeTongue(any(Language.class));
	}

	@Test
	public void removeLanguage() {
		toTest.remove(mock(Language.class));
		verify(languages).removeLanguage(any(Language.class));
	}

	@Test
	public void increasePrimaryAttribute() {
		final PrimaryAttribute a = mock(PrimaryAttribute.class);
		toTest.increase(a);
		verify(attributes).increasePrimaryAttribute(a);
	}

	@Test
	public void testDecreasePrimaryAttribute() {
		final PrimaryAttribute a = mock(PrimaryAttribute.class);
		toTest.decrease(a);
		verify(attributes).decreasePrimaryAttribute(a);
	}

	@Test
	public void testCanIncreasePrimaryAttribute() {
		final PrimaryAttribute a = mock(PrimaryAttribute.class);
		toTest.canIncrease(a);
		verify(attributes).canIncrease(a);
	}

	@Test
	public void testCanDecreasePrimaryAttribute() {
		final PrimaryAttribute a = mock(PrimaryAttribute.class);
		toTest.canDecrease(a);
		verify(attributes).canDecrease(a);
	}

	@Test
	public void testIncreaseSecondaryAttribute() {
		final SecondaryAttribute a = mock(SecondaryAttribute.class);
		toTest.increase(a);
		verify(attributes).increaseSecondaryAttribute(a);
	}

	@Test
	public void testDecreaseSecondaryAttribute() {
		final SecondaryAttribute a = mock(SecondaryAttribute.class);
		toTest.decrease(a);
		verify(attributes).decreaseSecondaryAttribute(a);
	}

	@Test
	public void testCanDecreaseSecondaryAttribute() {
		final SecondaryAttribute a = mock(SecondaryAttribute.class);
		toTest.canDecrease(a);
		verify(attributes).canDecrease(a);
	}

	@Test
	public void testCanIncreaseSecondaryAttribute() {
		final SecondaryAttribute a = mock(SecondaryAttribute.class);
		toTest.canIncrease(a);
		verify(attributes).canIncrease(a);
	}

	@Test
	public void testCanAddLanguge() {
		final Language l = mock(Language.class);
		toTest.canAdd(l);
		verify(languages).canAdd(l);
	}

	@Test
	public void testCanAddLangugeAsNativeTongue() {
		final Language l = mock(Language.class);
		toTest.canAddLanguageAsNativeTongue(l);
		verify(languages).canAddAsNativeTongue(l);
	}

	@Test
	public void testCanIncreaseLanguge() {
		final Language l = mock(Language.class);
		toTest.canIncrease(l);
		verify(languages).canIncrease(l);
	}

	@Test
	public void testCanDecreaseLanguge() {
		final Language l = mock(Language.class);
		toTest.canDecrease(l);
		verify(languages).canDecrease(l);
	}

	@Test
	public void testCanRemoveLanguage() {
		final Language l = mock(Language.class);
		toTest.canRemove(l);
		verify(languages).canRemoveLanguage(l);
	}

	@Test
	public void setName() {
		toTest.setName("");
		verify(misc).setName("");
	}

	@Test
	public void testUpdate() {
		toTest.update(mockedAventurian, null);
		verifyZeroInteractions(properties);
		verifyZeroInteractions(languages);
		verifyZeroInteractions(attributes);

		toTest.update(null, null);
		verifyZeroInteractions(properties);
		verifyZeroInteractions(languages);
		verifyZeroInteractions(attributes);
	}

	@Test
	public void testUpdateWithSkillToRemove() {
		final Language l = mock(Language.class);
		toTest.update(mockedAventurian, l);
		verify(languages).removeLanguage(l);
		final Property p = mock(Property.class);
		toTest.update(mockedAventurian, p);
		verify(properties).removeProperty(p);
	}

}