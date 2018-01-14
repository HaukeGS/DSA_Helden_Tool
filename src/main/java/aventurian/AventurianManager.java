package aventurian;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import skills.BadProperty;
import skills.Language;
import skills.Property;

public class AventurianManager {

	private Aventurian aventurian;
	private final List<Observer> observers;
	private final LanguageAventurianManager languageManager;
	private final PropertyAventurianManager propertyManager;
	private final AttributesAventurianManager attributesManager;

	/**
	 * Do not use in production code! Use only for testing purposes
	 * 
	 * @param a
	 *            the (mock of an) aventurian
	 */
	AventurianManager(Aventurian a) {
		this(a, new AttributesAventurianManager(a), new LanguageAventurianManager(a), new PropertyAventurianManager(a));
	}

	public AventurianManager() {
		this(new Aventurian("testAventurian", 16500));
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
	AventurianManager(Aventurian a, AttributesAventurianManager attributes, LanguageAventurianManager languages,
			PropertyAventurianManager properties) {
		this.aventurian = a;
		this.attributesManager = attributes;
		this.propertyManager = properties;
		this.languageManager = languages;
		this.observers = new ArrayList<>();
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

	public void addBadProperty(BadProperty p) {
		this.propertyManager.addBadProperty(p);
	}

	public void removeBadProperty(BadProperty p) {
		this.propertyManager.removeBadProperty(p);
	}

	public void increaseBadProperty(BadProperty p) {
		this.propertyManager.increaseBadProperty(p);
	}

	public void decreaseBadProperty(BadProperty p) {
		this.propertyManager.decreaseBadProperty(p);
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
		aventurian.setName(name);

	}

	public void saveAventurian(File f) throws JAXBException {
		final JAXBContext context = JAXBContext.newInstance(Aventurian.class);
		final Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// Marshalling and saving XML to the file.
		m.marshal(aventurian, System.out);
		// m.marshal(aventurian, new File("aventurian.xml"));
		m.marshal(aventurian, f);
	}

	public void registerObserver(Observer o) {
		this.observers.add(o);
		addObserversToAventurian();
	}

	private void addObserversToAventurian() {
		aventurian.deleteObservers();
		observers.forEach(aventurian::addObserver);
		aventurian.notifyObserversAndSetChanged();
	}

	public void loadAventurian(File f) {
		try {
			final JAXBContext context = JAXBContext.newInstance(Aventurian.class);
			final Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			this.aventurian = (Aventurian) um.unmarshal(f);
			addObserversToAventurian();
		} catch (final Exception e) { // catches ANY exception
			e.printStackTrace();
		}

	}

}
