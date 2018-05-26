package aventurian;

import static aventurian.LevelCostCalculator.Column.H;

import java.util.Optional;

import database.Database;

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

}
