package aventurian;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import database.Database;
import skills.Skill;
import skills.attributes.primary.PrimaryAttribute;
import skills.attributes.secondary.SecondaryAttribute;
import skills.languages.Language;
import skills.properties.Property;

public class AventurianManager implements Observer {

	private final LanguageAventurianManager languageManager;
	private final PropertyAventurianManager propertyManager;
	private final AttributesAventurianManager attributesManager;
	private final RaceAventurianManager raceManager;
	private final MiscelleanousAventurianManager miscManager;
	private final Database database;
	private Optional<Aventurian> aventurian = Optional.empty();

	public AventurianManager(Database db) {
		this.languageManager = new LanguageAventurianManager(aventurian, db);
		this.propertyManager = new PropertyAventurianManager(aventurian, db);
		this.attributesManager = new AttributesAventurianManager(aventurian, db);
		this.raceManager = new RaceAventurianManager(aventurian, db, propertyManager, attributesManager);
		this.miscManager = new MiscelleanousAventurianManager(aventurian, db);
		this.database = db;
		registerObserver(this);
	}

	/**
	 * Do not use in production code! Use only for testing purposes
	 * 
	 * @param a
	 *            the mock of an aventurian
	 * @param attributes
	 *            the mock of an {@link AttributesAventurianManager}
	 * @param languages
	 *            the mock of a {@link LanguageAventurianManager}
	 * @param properties
	 *            the mock of a {@link PropertyAventurianManager}
	 */
	AventurianManager(Optional<Aventurian> a, AttributesAventurianManager attributes,
			LanguageAventurianManager languages, PropertyAventurianManager properties, RaceAventurianManager races,
			MiscelleanousAventurianManager misc, Database db) {
		this.attributesManager = attributes;
		this.propertyManager = properties;
		this.languageManager = languages;
		this.raceManager = races;
		this.miscManager = misc;
		this.database = db;
		registerObserver(this);
	}

	public void createNewAventurian(String name, int startingAP, Race race) {
		database.reset();
		this.aventurian = Optional.of(new Aventurian(name, startingAP, race));
		attributesManager.changeAventurian(aventurian);
		propertyManager.changeAventurian(aventurian);
		languageManager.changeAventurian(aventurian);
		raceManager.changeAventurian(aventurian);
		miscManager.changeAventurian(aventurian);
		attributesManager.addAttributes();
		raceManager.buyRaceMods(race);
	}

	public void increasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		this.attributesManager.increasePrimaryAttribute(a);
	}

	public void decreasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		this.attributesManager.decreasePrimaryAttribute(a);
	}

	public void increaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		this.attributesManager.increaseSecondaryAttribute(a);
	}

	public void decreaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		this.attributesManager.decreaseSecondaryAttribute(a);
	}

	public void add(Property p) {
		this.propertyManager.addProperty(p);
	}

	public boolean canAdd(Property p) {
		return propertyManager.canAdd(p);
	}

	public void increase(Property p) {
		this.propertyManager.increaseProperty(p);
	}

	public boolean canIncrease(Property p) {
		return this.propertyManager.canIncrease(p);
	}

	public void increase(Language l) {
		languageManager.increaseLanguage(l);
	}

	public boolean canIncrease(Language l) {
		return languageManager.canIncrease(l);
	}

	public void decrease(Language l) {
		languageManager.decreaseLanguage(l);
	}

	public boolean canDecrease(Language l) {
		return languageManager.canDecrease(l);
	}

	public void add(Language l) {
		languageManager.addLanguage(l);
	}

	public boolean canAdd(Language l) {
		return languageManager.canAdd(l);
	}

	public void addLanguageAsNativeTongue(Language l) {
		languageManager.addLanguageAsNativeTongue(l);
	}

	public boolean canAddLanguageAsNativeTongue(Language l) {
		return languageManager.canAddAsNativeTongue(l);
	}

	public void remove(Language l) {
		languageManager.removeLanguage(l);
	}

	public void remove(Property p) {
		this.propertyManager.removeProperty(p);
	}

	public void setName(String name) {
		miscManager.setName(name);
	}

	public void registerObserver(Observer o) {
		miscManager.registerObserver(o);
	}

	public void decrease(Property p) {
		propertyManager.decreaseProperty(p);
	}

	public boolean canDecreaseProperty(Property p) {
		return propertyManager.canDecrease(p);
	}

	public boolean canRemove(Property p) {
		return propertyManager.canRemove(p);
	}

	public boolean canRemove(Language l) {
		return languageManager.canRemoveLanguage(l);
	}

	// skillToRemove is only not null when there is a skill whose requirements are
	// not met
	// anymore -> remove it
	@Override
	public void update(Observable o, Object skillToRemove) {
		if (o instanceof Aventurian && skillToRemove instanceof Skill) {
			final Skill toRemove = (Skill) skillToRemove;
			if (toRemove instanceof Property) {
				final Property p = (Property) toRemove;
				if (p.isAllowedToDecrease())
					decrease(p);
				else
					remove(p);
			} else if (toRemove instanceof Language) {
				final Language l = (Language) toRemove;
				if (l.isAllowedToDecrease())
					decrease(l);
				else
					remove(l);
			}
		}

	}

	public void saveAventurian(File file) {
		// TODO Auto-generated method stub

	}

	public void increase(PrimaryAttribute a) {
		attributesManager.increasePrimaryAttribute(a);
	}

	public void decrease(PrimaryAttribute a) {
		attributesManager.decreasePrimaryAttribute(a);

	}

	public void increase(SecondaryAttribute a) {
		attributesManager.increaseSecondaryAttribute(a);
	}

	public void decrease(SecondaryAttribute a) {
		attributesManager.decreaseSecondaryAttribute(a);
	}

	public boolean canDecrease(SecondaryAttribute a) {
		return attributesManager.canDecrease(a);
	}

	public boolean canIncrease(SecondaryAttribute a) {
		return attributesManager.canIncrease(a);
	}

	public boolean canDecrease(PrimaryAttribute a) {
		return attributesManager.canDecrease(a);
	}

	public boolean canIncrease(PrimaryAttribute a) {
		return attributesManager.canIncrease(a);
	}

}
