package aventurian;

import java.util.Optional;
import java.util.function.BiPredicate;

import database.Database;
import skills.properties.BadProperty;
import skills.properties.Property;

class PropertyAventurianManager extends BaseAventurianManager {
	static final int MAX_BAD_PROPERTIES_SUM = 25;
	static final int MAX_POINTS_IN_ADVANTAGES = 2500;
	static final int MAX_POINTS_OUT_DISADVANTAGES = 2500;
	private final BiPredicate<Aventurian, Property> CANNOT_PAY_ADVANTAGE_TOTAL_COSTS = (av, p) -> p.isAdvantage()
			&& !av.canPay(p.getTotalCosts());
	private final BiPredicate<Aventurian, Property> CANNOT_PAY_ADVANTAGE_UPGRADE_COSTS = (av, p) -> p.isAdvantage()
			&& !av.canPay(p.getUpgradeCosts());
	private final BiPredicate<Aventurian, Property> EXCEEDS_MAX_ADVANTAGEPOINTS = (av, p) -> p.isAdvantage()
			&& av.getPointsInAdvantages() + p.getTotalCosts() > MAX_POINTS_IN_ADVANTAGES;
	private final BiPredicate<Aventurian, Property> EXCEEDS_MAX_DISADVANTAGEPOINTS = (av, p) -> p.isDisadvantage()
			&& av.getPointsOutDisadvantages() + p.getTotalCosts() > MAX_POINTS_OUT_DISADVANTAGES;
	private final BiPredicate<Aventurian, Property> EXCEEDS_MAX_BADPROPERTYLEVELS = (av, p) -> p instanceof BadProperty
			&& (p.getLevel() + av.getBadPropertySum() > MAX_BAD_PROPERTIES_SUM);
	private final BiPredicate<Aventurian, Property> EXCEEDS_MAX_BADPROPERTYLEVELS_BY_INCREASE = (av,
			p) -> p instanceof BadProperty && av.getBadPropertySum() >= MAX_BAD_PROPERTIES_SUM;

	PropertyAventurianManager(Optional<Aventurian> a, Database db) {
		super(a, db);
	}

	void addProperty(Property p) {
		aventurian.ifPresent(av -> {
			if (IS_NOT_ALLOWED.test(av, p) || CANNOT_PAY_ADVANTAGE_TOTAL_COSTS.test(av, p) || HAS_SKILL.test(av, p))
				throw new IllegalStateException("Basic requirements not met " + p.getName());
			if (EXCEEDS_MAX_ADVANTAGEPOINTS.test(av, p) || EXCEEDS_MAX_DISADVANTAGEPOINTS.test(av, p)
					|| EXCEEDS_MAX_BADPROPERTYLEVELS.test(av, p))
				throw new IllegalStateException("Property requirements not met " + p.getName());

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
		if (cannotIncrease(p))
			throw new IllegalStateException("requirements not met for increasing " + p.getName());
		if (p.isAdvantage()) {
			p.increase();
			pay(p.getUpgradeCosts());
		} else {
			p.increase();
			refund(p.getUpgradeCosts());
		}
	}

	boolean cannotIncrease(Property p) {
		return aventurian.map(av -> HAS_NOT_SKILL.test(av, p)//
				|| IS_NOT_ALLOWED.test(av, p)//
				|| IS_NOT_INCREASABLE.test(p)//
				|| CANNOT_PAY_ADVANTAGE_UPGRADE_COSTS.test(av, p)//
				|| EXCEEDS_MAX_BADPROPERTYLEVELS_BY_INCREASE.test(av, p)).orElse(true);
	}
}