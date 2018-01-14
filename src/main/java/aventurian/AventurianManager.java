package aventurian;

import static aventurian.LevelCostCalculator.COLUMN.H;

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
	private final LevelCostCalculator calculator;
	private final List<Observer> observers;
	private LanguageAventurianManager languageManager;

	static final int MAX_BAD_PROPERTIES_SUM = 25;
	static final int MAX_POINTS_IN_ADVANTAGES = 2500;
	static final int MAX_POINTS_OUT_DISADVANTAGES = 2500;

	/**
	 * Do not use in production code! Use only for testing purposes
	 * 
	 * @param a
	 *            the (mock of an) aventurian
	 */
	AventurianManager(Aventurian a) {
		this.calculator = new LevelCostCalculator();
		this.observers = new ArrayList<>();
		this.aventurian = a;

	}

	public AventurianManager() {
		this(new Aventurian("testAventurian", 16500));
		this.languageManager = new LanguageAventurianManager(aventurian);
	}

	public void increasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		final int cost = calculator.getCost(aventurian.getPrimaryAttribute(a), aventurian.getPrimaryAttribute(a) + 1,
				H);
		if (canPay(cost) && aventurian.isPrimaryAttributesLowerThanThreshhold()
				&& aventurian.isPrimaryAttributeIncreasable(a)) {
			aventurian.increasePrimaryAttribute(a);
			pay(cost);
		}
	}

	public void decreasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		final int cost = calculator.getRefund(aventurian.getPrimaryAttribute(a), aventurian.getPrimaryAttribute(a) - 1,
				H);
		if (aventurian.isPrimaryAttributeDecreasable(a)) {
			aventurian.decrasePrimaryAttribute(a);
			refund(cost);
		}
	}

	public void addProperty(Property p) {
		if (aventurian.hasSkill(p))
			throw new IllegalStateException("has already skill " + p.getName());
		final int cost = p.getCost();
		if (p.isAllowed(aventurian)) {
			if (p.isAdvantage() && canPay(cost)
					&& aventurian.getPointsInAdvantages() + cost <= MAX_POINTS_IN_ADVANTAGES) {
				pay(cost);
				aventurian.add(p);
			} else if (p.isDisadvantage()
					&& aventurian.getPointsOutDisadvantages() + cost <= MAX_POINTS_OUT_DISADVANTAGES) {
				refund(cost);
				aventurian.add(p);
			}
		}
	}

	public void addBadProperty(BadProperty p) {
		if (aventurian.hasSkill(p))
			throw new IllegalStateException("has already skill " + p.getName());
		final int cost = p.getCost();
		if (aventurian.getBadPropertySum() + p.getLevel() <= MAX_BAD_PROPERTIES_SUM && p.isAllowed(aventurian)
				&& aventurian.getPointsOutDisadvantages() + (cost * p.getLevel()) <= MAX_POINTS_OUT_DISADVANTAGES) {
			refund(cost * p.getLevel());
			aventurian.add(p);
		}
	}

	public void removeBadProperty(BadProperty p) {
		if (!aventurian.hasSkill(p))
			throw new IllegalStateException("cannot remove skill " + p.getName());
		while (p.isDecreasable()) {
			decreaseBadProperty(p);
		}

		aventurian.remove(p);
		refund(p.getCost() * p.getLevel());
	}

	public void increaseBadProperty(BadProperty p) {
		if (!aventurian.hasSkill(p))
			throw new IllegalStateException("cannot increase skill " + p.getName());
		if (p.isIncreasable() && aventurian.getBadPropertySum() + 1 <= MAX_BAD_PROPERTIES_SUM) {
			p.increase();
			pay(p.getCost());
		}
	}

	public void decreaseBadProperty(BadProperty p) {
		if (!p.isDecreasable())
			throw new IllegalStateException("cannot further decrease level of " + p.getName());
		if (!aventurian.hasSkill(p))
			throw new IllegalStateException("cannot decrease skill which is not owned: " + p.getName());
		p.decrease();
		refund(p.getCost());
	}

	public void removeProperty(Property p) {
		if (!aventurian.hasSkill(p))
			throw new IllegalStateException("cannot remove skill " + p.getName());
		final int refund = p.getCost();
		if (p.isAdvantage()) {
			refund(refund);
		} else {
			pay(refund);
		}

		aventurian.remove(p);

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

	private boolean canPay(int cost) {
		return aventurian.canPay(cost);
	}

	private void pay(int cost) {
		aventurian.pay(cost);
	}

	private void refund(int refund) {
		aventurian.refund(refund);
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
