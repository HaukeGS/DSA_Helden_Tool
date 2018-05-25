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
		if (!canAdd(p))
			throw new IllegalStateException("requirements not met for adding " + p.getName());
		if (p.isAdvantage())
			pay(p.getTotalCosts());
		else
			refund(p.getTotalCosts());
		add(p);

	}

	boolean canAdd(Property p) {
		return !aventurian.map(av -> HAS_SKILL.test(av, p)//
				|| IS_NOT_ALLOWED.test(av, p)//
				|| EXCEEDS_MAX_ADVANTAGEPOINTS.test(av, p)//
				|| EXCEEDS_MAX_DISADVANTAGEPOINTS.test(av, p) //
				|| EXCEEDS_MAX_BADPROPERTYLEVELS.test(av, p)).orElse(true);

	}

	void removeProperty(Property p) {
		if (!canRemove(p))
			throw new IllegalStateException("requirements not met for removing " + p.getName());
		if (p.isAdvantage()) {
			decreaseAdvantageToMinimum(p);
			remove(p);
			refund(p.getTotalCosts());
		} else {
			decreaseDisadvantageToMinimum(p);
			remove(p);
			pay(p.getTotalCosts());
		}
	}

	// is this correct?
	boolean canRemove(Property p) {
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, p)).orElse(true);
	}

	private void decreaseDisadvantageToMinimum(Property p) {
		while (p.isAllowedToDecrease()) {
			pay(p.getDowngradeRefund());
			decrease(p);
		}
	}

	void decreaseProperty(Property p) {
		if (!canDecrease(p))
			throw new IllegalStateException("requirements not met for decreasing " + p.getName());
		if (p.isDisadvantage()) {
			pay(p.getDowngradeRefund());
			decrease(p);
		} else {
			refund(p.getDowngradeRefund());
			decrease(p);
		}
	}

	private void decreaseAdvantageToMinimum(Property p) {
		while (p.isAllowedToDecrease()) {
			refund(p.getDowngradeRefund());
			decrease(p);
		}
	}

	void increaseProperty(Property p) {
		if (!canIncrease(p))
			throw new IllegalStateException("requirements not met for increasing " + p.getName());
		if (p.isAdvantage()) {
			pay(p.getUpgradeCosts());
			increase(p);
		} else {
			refund(p.getUpgradeCosts());
			increase(p);
		}
	}

	boolean canIncrease(Property p) {
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, p)//
				|| IS_NOT_ALLOWED.test(av, p)//
				|| IS_NOT_INCREASABLE.test(av, p)//
				|| EXCEEDS_MAX_BADPROPERTYLEVELS_BY_INCREASE.test(av, p)).orElse(true);
	}

	boolean canDecrease(Property p) {
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, p)//
				|| IS_NOT_DECREASABLE.test(p)).orElse(true);
	}

}