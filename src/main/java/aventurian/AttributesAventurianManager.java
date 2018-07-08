package aventurian;

import java.util.function.Predicate;

import database.Database;
import logging.Logger;
import skills.attributes.primary.PrimaryAttribute;
import skills.attributes.secondary.SecondaryAttribute;

class AttributesAventurianManager extends BaseAventurianManager {
	static final int MAX_PRIMARY_ATTRIBUTES_SUM = 101;
	private final static Predicate<Aventurian> EXCEEDS_MAX_SUM = av -> av
			.getSumOfPrimaryAttributes() >= MAX_PRIMARY_ATTRIBUTES_SUM;

	AttributesAventurianManager(AventurianManagerFacade aventurianManagerFacade, Database db, Logger logger) {
		super(aventurianManagerFacade, db, logger);
	}

	void addAttributes() {
		database.getPrimaryAttributes().forEach(this::add);
		database.getSecondaryAttributes().forEach(this::add);
		aventurian.ifPresent(Aventurian::updateSecondaryAttributes);
	}

	void increasePrimaryAttribute(PrimaryAttribute a) {
		if (!canIncrease(a))
			throw new IllegalStateException("requirements not met for increasing " + a.getName());
		pay(a.getUpgradeCosts());
		increase(a);
	}

	void decreasePrimaryAttribute(PrimaryAttribute a) {
		if (!canDecrease(a))
			throw new IllegalStateException("requirements not met for decreasing " + a.getName());
		refund(a.getDowngradeRefund());
		decrease(a);
	}

	void increaseSecondaryAttribute(SecondaryAttribute a) {
		if (!canIncrease(a))
			throw new IllegalStateException("requirements not met for increasing " + a.getName());
		pay(a.getUpgradeCosts());
		increase(a);
	}

	void applyRaceMod(SecondaryAttribute a, int mod) {
		if (mod >= 0) {
			increaseRaceMod(a, mod);
		} else {
			decreaseRaceMod(a, Math.abs(mod));
		}
	}

	void decreaseRaceMod(SecondaryAttribute a, int mod) {
		a.decreaseMod(mod);
		refund(a.getDowngradeRefund() * mod);
	}

	void increaseRaceMod(SecondaryAttribute a, int mod) {
		a.increaseMod(mod);
		pay(a.getUpgradeCosts() * mod);
	}

	void decreaseSecondaryAttribute(SecondaryAttribute a) {
		if (!canDecrease(a))
			throw new IllegalStateException("requirements not met for decreasing " + a.getName());
		refund(a.getDowngradeRefund());
		decrease(a);
	}

	boolean canDecrease(SecondaryAttribute a) {
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, a)//
				|| IS_NOT_DECREASABLE.test(a)).orElse(true);
	}

	boolean canIncrease(SecondaryAttribute a) {
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, a)//
				|| IS_NOT_INCREASABLE.test(av, a)).orElse(true);
	}

	boolean canDecrease(PrimaryAttribute a) {
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, a)//
				|| IS_NOT_DECREASABLE.test(a)).orElse(true);
	}

	boolean canIncrease(PrimaryAttribute a) {
		return !(aventurian.map(av -> HAS_NOT_SKILL.test(av, a)//
				|| IS_NOT_INCREASABLE.test(av, a)//
				|| EXCEEDS_MAX_SUM.test(av)).orElse(true));
	}

	void increaseSecondaryAttributeWithoutPay(SecondaryAttribute s) {
		if (!canIncrease(s))
			throw new IllegalStateException("requirements not met for increasing " + s.getName());
		increase(s);
	}

	void decreaseSecondaryAttributeWithoutRefund(SecondaryAttribute a) {
		if (!canDecrease(a))
			throw new IllegalStateException("requirements not met for decreasing " + a.getName());
		decrease(a);
	}

}
