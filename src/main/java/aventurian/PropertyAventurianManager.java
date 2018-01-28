package aventurian;

import java.util.Optional;

import database.Database;
import skills.BadProperty;
import skills.Property;

class PropertyAventurianManager extends BaseAventurianManager {
	static final int MAX_BAD_PROPERTIES_SUM = 25;
	static final int MAX_POINTS_IN_ADVANTAGES = 2500;
	static final int MAX_POINTS_OUT_DISADVANTAGES = 2500;

	PropertyAventurianManager(Optional<Aventurian> a, Database db) {
		super(a, db);
	}

	void addBadProperty(BadProperty p) {
		aventurian.ifPresent(av -> {
			if (av.hasSkill(p))
				throw new IllegalStateException("has already skill " + p.getName());
			final int cost = p.getCost();
			if (av.getBadPropertySum() + p.getLevel() <= MAX_BAD_PROPERTIES_SUM && p.isAllowed(av)
					&& av.getPointsOutDisadvantages() + (cost * p.getLevel()) <= MAX_POINTS_OUT_DISADVANTAGES) {
				refund(cost * p.getLevel());
				av.add(p);
			}			
		});
	}

	void addProperty(Property p) {
		aventurian.ifPresent(av -> {
			if (av.hasSkill(p))
				throw new IllegalStateException("has already skill " + p.getName());
			final int cost = p.getCost();
			if (p.isAllowed(av)) {
				if (p.isAdvantage() && canPay(cost)
						&& av.getPointsInAdvantages() + cost <= MAX_POINTS_IN_ADVANTAGES) {
					pay(cost);
					av.add(p);
				} else if (p.isDisadvantage()
						&& av.getPointsOutDisadvantages() + cost <= MAX_POINTS_OUT_DISADVANTAGES) {
					refund(cost);
					av.add(p);
				}
			}			
		});
	}

	void increaseBadProperty(BadProperty p) {
		aventurian.ifPresent(av -> {
			if (!av.hasSkill(p))
				throw new IllegalStateException("cannot increase skill " + p.getName());
			if (p.isIncreasable() && av.getBadPropertySum() + 1 <= MAX_BAD_PROPERTIES_SUM) {
				p.increase();
				pay(p.getCost());
			}
			
		});
	}

	void removeBadProperty(BadProperty p) {
		aventurian.ifPresent(av -> {
			if (!av.hasSkill(p))
				throw new IllegalStateException("cannot remove skill " + p.getName());
			while (p.isDecreasable()) {
				decreaseBadProperty(p);
			}
			av.remove(p);
			refund(p.getCost() * p.getLevel());
			
		});
	}

	void decreaseBadProperty(BadProperty p) {
		aventurian.ifPresent(av -> {
			if (!p.isDecreasable())
				throw new IllegalStateException("cannot further decrease level of " + p.getName());
			if (!av.hasSkill(p))
				throw new IllegalStateException("cannot decrease skill which is not owned: " + p.getName());
			p.decrease();
			refund(p.getCost());
			
		});
	}

	void removeProperty(Property p) {
		aventurian.ifPresent(av -> {
			if (!av.hasSkill(p))
				throw new IllegalStateException("cannot remove skill " + p.getName());
			final int refund = p.getCost();
			if (p.isAdvantage()) {
				refund(refund);
			} else {
				pay(refund);
			}
			av.remove(p);			
		});
	}

}
