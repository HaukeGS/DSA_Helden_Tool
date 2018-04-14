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

	void addProperty(Property p) {
		aventurian.ifPresent(av -> {
			if (av.hasSkill(p))
				throw new IllegalStateException("has already skill " + p.getName());
			if (!p.isAllowed(av))
				throw new IllegalStateException("requirements not met " + p.getName());
			if (!av.canPay(p.getTotalCosts()))
				throw new IllegalStateException("cannot pay for " + p.getName());
			if (p.isAdvantage() && p.getTotalCosts() + av.getPointsInAdvantages() > MAX_POINTS_IN_ADVANTAGES)
				throw new IllegalStateException("cannot exceed max points in advantages: " + MAX_POINTS_IN_ADVANTAGES);
			if (p.isDisadvantage() && p.getTotalCosts() + av.getPointsOutDisadvantages() > MAX_POINTS_OUT_DISADVANTAGES)
				throw new IllegalStateException(
						"cannot exceed max points out disadvantages: " + MAX_POINTS_OUT_DISADVANTAGES);
			if (p instanceof BadProperty && (p.getLevel() + av.getBadPropertySum() > MAX_BAD_PROPERTIES_SUM))
				throw new IllegalStateException("cannot exceed bad properties sum: " + MAX_BAD_PROPERTIES_SUM);
			if (p.isAdvantage())
				pay(p.getTotalCosts());
			else
				refund(p.getTotalCosts());
			add(p);
		});
	}

	void removeProperty(Property p) {
		aventurian.ifPresent(av -> {
			if (!av.hasSkill(p))
				throw new IllegalStateException("does not own skill " + p.getName());
			if (p.isAdvantage()) {
				decreaseAdvantageToMinimum(p);
				remove(p);
				refund(p.getTotalCosts());
			} else {
				decreaseDisadvantesToMinimum(p);
				remove(p);
				pay(p.getTotalCosts());
			}
		});

	}

	private void decreaseDisadvantesToMinimum(Property p) {
		while (p.isDecreasable()) {
			p.decrease();
			pay(p.getDowngradeRefund());
		}
	}

	void decreaseProperty(Property p) {
		aventurian.ifPresent(av -> {
			if (!p.isDecreasable())
				throw new IllegalStateException("cannot further decrease level of " + p.getName());
			if (!av.hasSkill(p))
				throw new IllegalStateException("cannot decrease skill which is not owned: " + p.getName());
			if (p.isDisadvantage()) {
				p.decrease();
				pay(p.getDowngradeRefund());
			} else {
				p.decrease();
				refund(p.getDowngradeRefund());
			}
		});
	}

	private void decreaseAdvantageToMinimum(Property p) {
		while (p.isDecreasable()) {
			p.decrease();
			refund(p.getDowngradeRefund());
		}
	}

	void increaseProperty(Property p) {
		aventurian.ifPresent(av -> {
			if (!av.hasSkill(p))
				throw new IllegalStateException("cannot increase skill which is not owned: " + p.getName());
			if (!p.isIncreasable())
				throw new IllegalStateException("cannot further increase level of " + p.getName());
			if (!av.canPay(p.getUpgradeCost()))
				throw new IllegalStateException("cannot pay for " + p.getName());
			if (p instanceof BadProperty && av.getBadPropertySum() >= MAX_BAD_PROPERTIES_SUM)
				throw new IllegalStateException("cannot exceed bad properties sum: " + MAX_BAD_PROPERTIES_SUM);

			if (p.isAdvantage()) {
				p.increase();
				pay(p.getUpgradeCost());
			} else {
				p.increase();
				refund(p.getUpgradeCost());
			}
		});
	}
}