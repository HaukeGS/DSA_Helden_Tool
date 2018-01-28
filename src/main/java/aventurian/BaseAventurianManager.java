package aventurian;

import java.util.Optional;

import database.Database;

abstract class BaseAventurianManager {	
	
	protected final Database database;
	protected Optional<Aventurian> aventurian;
	protected final LevelCostCalculator calculator;

	BaseAventurianManager(Optional<Aventurian> a, Database db) {
		this.calculator = new LevelCostCalculator();
		this.aventurian = a;
		this.database = db;

	}
	
	protected void changeAventurian(Optional<Aventurian> a) {
		this.aventurian = a;
	}

	protected final boolean canPay(int cost) {
		return aventurian.map(a -> a.canPay(cost)).orElse(false);
	}

	protected final void pay(int cost) {
		aventurian.ifPresent(a -> a.pay(cost));
	}

	protected final void refund(int refund) {
		aventurian.ifPresent(a -> a.refund(refund));
	}

}
