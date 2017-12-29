package aventurian;

import static aventurian.LevelCostCalculator.COLUMN.H;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import skills.BadProperty;
import skills.Language;
import skills.Property;

public class AventurianManager {

	private final Aventurian aventurian;
	private final LevelCostCalculator calculator;

	static final int MAX_BAD_PROPERTIES_SUM = 25;
	static final int MAX_POINTS_IN_ADVANTAGES = 2500;
	static final int MAX_POINTS_OUT_DISADVANTAGES = 2500;
	static final int MAX_ATTRIBUTES_SUM = 101;

	public AventurianManager(Aventurian aventurian) {
		this.aventurian = aventurian;
		this.calculator = new LevelCostCalculator();
	}

	public void increasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		final int cost = calculator.getCost(aventurian.getPrimaryAttribute(a), aventurian.getPrimaryAttribute(a) + 1,
				H);
		if (canPay(cost) && aventurian.getSumOfPrimaryAttributes() < MAX_ATTRIBUTES_SUM
				&& aventurian.getPrimaryAttribute(a) < aventurian.getMaxOfPrimaryAttribute(a)) {
			aventurian.increasePrimaryAttribute(a);
			pay(cost);
		}
	}

	public void decreasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		final int cost = calculator.getRefund(aventurian.getPrimaryAttribute(a), aventurian.getPrimaryAttribute(a) - 1,
				H);
		if (aventurian.getPrimaryAttribute(a) > PrimaryAttributes.MIN) {
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
				p.gain(aventurian);
			} else if (p.isDisadvantage()
					&& aventurian.getPointsOutDisadvantages() + cost <= MAX_POINTS_OUT_DISADVANTAGES) {
				p.gain(aventurian);
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
			p.gain(aventurian);
			aventurian.add(p);
		}
	}

	public void removeBadProperty(BadProperty p) {
		if (!aventurian.hasSkill(p))
			throw new IllegalStateException("cannot remove skill " + p.getName());
		while (p.isDecreasable()) {
			decreaseBadProperty(p);
		}
		p.lose(aventurian);
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
		p.lose(aventurian);
		aventurian.remove(p);

	}

	public void increaseLanguage(Language l) {
		if (!l.isIncreasable())
			throw new IllegalStateException("cannot further increase level of " + l.getName());
		if (!aventurian.hasSkill(l))
			throw new IllegalStateException("cannot increase skill " + l.getName());
		final int cost = l.getUpgradeCost();
		if (canPay(cost) && l.isAllowed(aventurian) && l.isIncreasable()) {
			l.increase();
			pay(cost);
		}
	}

	public void decreaseLanguage(Language l) {
		if (!l.isDecreasable())
			throw new IllegalStateException("cannot further decrease level of " + l.getName());
		if (!aventurian.hasSkill(l))
			throw new IllegalStateException("cannot decrease skill which is not owned: " + l.getName());
		final int refund = l.getDowngradeRefund();
		l.decrease();
		refund(refund);
	}

	public void addLanguage(Language l) {
		if (aventurian.hasSkill(l))
			throw new IllegalStateException("has already skill " + l.getName());
		final int cost = l.getLearningCost();
		if (canPay(cost) && l.isAllowed(aventurian)) {
			aventurian.add(l);
			l.gain(aventurian);
			pay(cost);
		}
	}

	public void removeLanguage(Language l) {
		if (!aventurian.hasSkill(l))
			throw new IllegalStateException("cannot remove skill " + l.getName());
		while (l.isDecreasable()) {
			decreaseLanguage(l);
		}
		l.lose(aventurian);
		aventurian.remove(l);
		refund(l.getLearningCost());
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

	public void savePersonDataToFile() throws JAXBException {
		final JAXBContext context = JAXBContext.newInstance(Aventurian.class);
		final Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		// Marshalling and saving XML to the file.
		m.marshal(aventurian, System.out);
	}
}
