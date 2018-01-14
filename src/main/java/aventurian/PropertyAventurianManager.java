package aventurian;

import skills.BadProperty;
import skills.Property;

class PropertyAventurianManager extends BaseAventurianManager {
	static final int MAX_BAD_PROPERTIES_SUM = 25;
	static final int MAX_POINTS_IN_ADVANTAGES = 2500;
	static final int MAX_POINTS_OUT_DISADVANTAGES = 2500;

	PropertyAventurianManager(Aventurian a) {
		super(a);
	}

	void addBadProperty(BadProperty p) {
		if (aventurian.hasSkill(p))
			throw new IllegalStateException("has already skill " + p.getName());
		final int cost = p.getCost();
		if (aventurian.getBadPropertySum() + p.getLevel() <= MAX_BAD_PROPERTIES_SUM && p.isAllowed(aventurian)
				&& aventurian.getPointsOutDisadvantages() + (cost * p.getLevel()) <= MAX_POINTS_OUT_DISADVANTAGES) {
			refund(cost * p.getLevel());
			aventurian.add(p);
		}
	}

	void addProperty(Property p) {
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

	void increaseBadProperty(BadProperty p) {
		if (!aventurian.hasSkill(p))
			throw new IllegalStateException("cannot increase skill " + p.getName());
		if (p.isIncreasable() && aventurian.getBadPropertySum() + 1 <= MAX_BAD_PROPERTIES_SUM) {
			p.increase();
			pay(p.getCost());
		}
	}

	void removeBadProperty(BadProperty p) {
		if (!aventurian.hasSkill(p))
			throw new IllegalStateException("cannot remove skill " + p.getName());
		while (p.isDecreasable()) {
			decreaseBadProperty(p);
		}

		aventurian.remove(p);
		refund(p.getCost() * p.getLevel());
	}

	void decreaseBadProperty(BadProperty p) {
		if (!p.isDecreasable())
			throw new IllegalStateException("cannot further decrease level of " + p.getName());
		if (!aventurian.hasSkill(p))
			throw new IllegalStateException("cannot decrease skill which is not owned: " + p.getName());
		p.decrease();
		refund(p.getCost());
	}

	void removeProperty(Property p) {
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

}
