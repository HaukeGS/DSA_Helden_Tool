package aventurian;

import static aventurian.LevelCostCalculator.Column.H;

import java.util.Optional;

import database.Database;
import skills.attributes.primary.PrimaryAttribute;
import skills.attributes.secondary.SecondaryAttribute;

class AttributesAventurianManager extends BaseAventurianManager {

	AttributesAventurianManager(Optional<Aventurian> a, Database db) {
		super(a, db);
	}

	void increasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		aventurian.ifPresent(av -> {
			final int cost = calculator.getCost(av.getPrimaryAttribute(a), av.getPrimaryAttribute(a) + 1, H);
			if (av.isPrimaryAttributesLowerThanThreshhold() && av.isPrimaryAttributeIncreasable(a)) {
				av.increasePrimaryAttribute(a);
				pay(cost);
			}
		});
	}

	void decreasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		aventurian.ifPresent(av -> {
			final int cost = calculator.getRefund(av.getPrimaryAttribute(a), av.getPrimaryAttribute(a) - 1, H);
			if (av.isPrimaryAttributeDecreasable(a)) {
				av.decrasePrimaryAttribute(a);
				refund(cost);
			}
		});
	}

	void increaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		aventurian.ifPresent(av -> {
			if (av.isSecondaryAttributeIncreasableByBuy(a)) {
				final int cost = av.getSecondaryAttributeCost(a);
				av.increaseSecondaryAttributeByBuy(a);
				pay(cost);
			}
		});
	}

	void decreaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		aventurian.ifPresent(av -> {
			if (av.isSecondaryAttributeDecreasableByBuy(a)) {
				final int cost = av.getSecondaryAttributeCost(a);
				av.decreaseSecondaryAttributeByBuy(a);
				refund(cost);
			}
		});
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

	public void decreaseRaceMod(SecondaryAttribute a, int mod) {
		a.decreaseMod(mod);
		refund(a.getDowngradeRefund() * mod);
	}

	public void increaseRaceMod(SecondaryAttribute a, int mod) {
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
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, a)//
				|| IS_NOT_INCREASABLE.test(av, a)).orElse(true);
	}

}
