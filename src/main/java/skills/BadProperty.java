package skills;

import java.util.function.Predicate;

import aventurian.Aventurian;

public class BadProperty extends Property {
	private static final int MIN_LEVEL = 5;
	private static final int MAX_LEVEL = 12;

	public BadProperty(String name, String description, int cost, Predicate<Aventurian> requirement) {
		super(name, description, cost, EMPTY, EMPTY, requirement, MIN_LEVEL, MAX_LEVEL);
		if (cost >= 0)
			throw new IllegalArgumentException("Costs must be less than zero for BadProperties!");
	}

	@Override
	public int getTotalCosts() {
		return level * getLearningCosts();
	}

	@Override
	public int getDowngradeRefund() {
		return getLearningCosts();
	}

	@Override
	public int getUpgradeCost() {
		return getLearningCosts();
	}
}
