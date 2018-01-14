package aventurian;

import static aventurian.LevelCostCalculator.COLUMN.H;

class AttributesAventurianManager extends BaseAventurianManager {

	AttributesAventurianManager(Aventurian a) {
		super(a);
	}

	public void increasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		final int cost = calculator.getCost(aventurian.getPrimaryAttribute(a), aventurian.getPrimaryAttribute(a) + 1,
				H);
		if (canPay(cost) && aventurian.isPrimaryAttributesLowerThanThreshhold()
				&& aventurian.isPrimaryAttributeIncreasable(a)) {
			aventurian.increasePrimaryAttribute(a);
			pay(cost);
		}
	}

	public void decreasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		final int cost = calculator.getRefund(aventurian.getPrimaryAttribute(a), aventurian.getPrimaryAttribute(a) - 1,
				H);
		if (aventurian.isPrimaryAttributeDecreasable(a)) {
			aventurian.decrasePrimaryAttribute(a);
			refund(cost);
		}
	}

	public void increaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		if (aventurian.isSecondaryAttributeIncreasableByBuy(a)) {
			final int cost = aventurian.getSecondaryAttributeCost(a);
			if (aventurian.canPay(cost)) {
				aventurian.increaseSecondaryAttributeByBuy(a);
				pay(cost);
			}
		}
	}

	public void decreaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		if (aventurian.isSecondaryAttributeDecreasableByBuy(a)) {
			final int cost = aventurian.getSecondaryAttributeCost(a);
			aventurian.decreaseSecondaryAttributeByBuy(a);
			refund(cost);
		}
	}

}
