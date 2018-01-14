package aventurian;

abstract class BaseAventurianManager {
	protected Aventurian aventurian;
	protected final LevelCostCalculator calculator;

	BaseAventurianManager(Aventurian a) {
		this.calculator = new LevelCostCalculator();
		this.aventurian = a;

	}

	protected final boolean canPay(int cost) {
		return aventurian.canPay(cost);
	}

	protected final void pay(int cost) {
		aventurian.pay(cost);
	}

	protected final void refund(int refund) {
		aventurian.refund(refund);
	}

}
