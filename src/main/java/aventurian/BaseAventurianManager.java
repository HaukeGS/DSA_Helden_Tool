package aventurian;

abstract class BaseAventurianManager {
	protected Aventurian aventurian;
	protected final LevelCostCalculator calculator;

	BaseAventurianManager(Aventurian a) {
		this.calculator = new LevelCostCalculator();
		this.aventurian = a;

	}

	protected boolean canPay(int cost) {
		return aventurian.canPay(cost);
	}

	protected void pay(int cost) {
		aventurian.pay(cost);
	}

	protected void refund(int refund) {
		aventurian.refund(refund);
	}

}
