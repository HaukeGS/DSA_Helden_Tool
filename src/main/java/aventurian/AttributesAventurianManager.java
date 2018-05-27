package aventurian;

import static aventurian.LevelCostCalculator.Column.H;

import java.util.Optional;

import database.Database;
import skills.attributes.PrimaryAttribute;
import skills.attributes.SecondaryAttribute;

class AttributesAventurianManager extends BaseAventurianManager {

	AttributesAventurianManager(Optional<Aventurian> a, Database db) {
		super(a, db);
	}

	public void increasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		aventurian.ifPresent(av -> {
			final int cost = calculator.getCost(av.getPrimaryAttribute(a), av.getPrimaryAttribute(a) + 1, H);
			if (av.isPrimaryAttributesLowerThanThreshhold() && av.isPrimaryAttributeIncreasable(a)) {
				av.increasePrimaryAttribute(a);
				pay(cost);
			}
		});
	}

	public void decreasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		aventurian.ifPresent(av -> {
			final int cost = calculator.getRefund(av.getPrimaryAttribute(a), av.getPrimaryAttribute(a) - 1, H);
			if (av.isPrimaryAttributeDecreasable(a)) {
				av.decrasePrimaryAttribute(a);
				refund(cost);
			}
		});
	}

	public void increaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		aventurian.ifPresent(av -> {
			if (av.isSecondaryAttributeIncreasableByBuy(a)) {
				final int cost = av.getSecondaryAttributeCost(a);
				av.increaseSecondaryAttributeByBuy(a);
				pay(cost);
			}
		});
	}

	public void decreaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
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

	public void increasePrimaryAttribute(PrimaryAttribute a) {
		if (!canIncrease(a))
			throw new IllegalStateException("requirements not met for increasing " + a.getName());
		pay(a.getUpgradeCosts());
		increase(a);
	}

	public void decreasePrimaryAttribute(PrimaryAttribute a) {
		if (!canDecrease(a))
			throw new IllegalStateException("requirements not met for decreasing " + a.getName());
		refund(a.getDowngradeRefund());
		decrease(a);
	}

	public void increaseSecondaryAttribute(SecondaryAttribute a) {
		if (!canIncrease(a))
			throw new IllegalStateException("requirements not met for increasing " + a.getName());
		pay(a.getUpgradeCosts());
		increase(a);

	}

	public void decreaseSecondaryAttribute(SecondaryAttribute a) {
		if (!canDecrease(a))
			throw new IllegalStateException("requirements not met for decreasing " + a.getName());
		refund(a.getDowngradeRefund());
		decrease(a);

	}

	public boolean canDecrease(SecondaryAttribute a) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canIncrease(SecondaryAttribute a) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canDecrease(PrimaryAttribute a) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canIncrease(PrimaryAttribute a) {
		// TODO Auto-generated method stub
		return false;
	}

}
