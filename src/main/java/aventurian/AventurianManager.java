package aventurian;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import database.Database;
import skills.Language;
import skills.Property;

public class AventurianManager extends BaseAventurianManager {

	private final List<Observer> observers;
	private final LanguageAventurianManager languageManager;
	private final PropertyAventurianManager propertyManager;
	private final AttributesAventurianManager attributesManager;
	private final RaceAventurianManager raceManager;

	public AventurianManager(Database db) {
		super(Optional.empty(), db);
		this.languageManager = new LanguageAventurianManager(Optional.empty(), db);
		this.propertyManager = new PropertyAventurianManager(Optional.empty(), db);
		this.attributesManager = new AttributesAventurianManager(Optional.empty(), db);
		this.raceManager = new RaceAventurianManager(Optional.empty(), db, this.propertyManager);
		this.observers = new ArrayList<>();
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
			Database db) {
		super(a, db);
		this.attributesManager = attributes;
		this.propertyManager = properties;
		this.languageManager = languages;
		this.raceManager = races;
		this.observers = new ArrayList<>();
	}

	public void createNewAventurian(String name, int startingAP, Race race) {
		database.reset();
		this.aventurian.ifPresent(a -> a.deleteObservers());
		this.aventurian = Optional.of(new Aventurian(name, startingAP, race));
		attributesManager.changeAventurian(aventurian);
		propertyManager.changeAventurian(aventurian);
		languageManager.changeAventurian(aventurian);
		raceManager.changeAventurian(aventurian);
		raceManager.buyRaceMods(race);
		addObserversToAventurian();
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

	public void addProperty(Property p) {
		this.propertyManager.addProperty(p);
	}

	public void increaseProperty(Property p) {
		this.propertyManager.increaseProperty(p);
	}

	public void removeProperty(Property p) {
		this.propertyManager.removeProperty(p);
	}

	public void increaseLanguage(Language l) {
		languageManager.increaseLanguage(l);
	}

	public void decreaseLanguage(Language l) {
		languageManager.decreaseLanguage(l);
	}

	public void addLanguage(Language l) {
		languageManager.addLanguage(l);
	}

	public void addLanguageAsNativeTongue(Language l) {
		languageManager.addLanguageAsNativeTongue(l);
	}

	public void removeLanguage(Language l) {
		languageManager.removeLanguage(l);
	}

	public void setName(String name) {
		aventurian.ifPresent(a -> a.setName(name));

	}

	public void saveAventurian(File f) throws JAXBException {
		// final JAXBContext context = JAXBContext.newInstance(Aventurian.class);
		// final Marshaller m = context.createMarshaller();
		// m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// // Marshalling and saving XML to the file.
		// m.marshal(aventurian, System.out);
		// // m.marshal(aventurian, new File("aventurian.xml"));
		// m.marshal(aventurian, f);
	}

	public void registerObserver(Observer o) {
		this.observers.add(o);
		addObserversToAventurian();
	}

	private void addObserversToAventurian() {
		aventurian.ifPresent(a -> a.deleteObservers());
		aventurian.ifPresent(a -> observers.forEach(a::addObserver));
	}

	public void loadAventurian(File f) {
		// try {
		// final JAXBContext context = JAXBContext.newInstance(Aventurian.class);
		// final Unmarshaller um = context.createUnmarshaller();
		// // Reading XML from the file and unmarshalling.
		// this.aventurian = Optional.of((Aventurian) um.unmarshal(f));
		// addObserversToAventurian();
		//
		// } catch (
		//
		// final Exception e) { // catches ANY exception e.printStackTrace(); }
		// }
	}

	public void decreaseProperty(Property p) {
		propertyManager.decreaseProperty(p);
	}
}
