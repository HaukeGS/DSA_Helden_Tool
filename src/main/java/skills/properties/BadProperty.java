package skills.properties;

public class BadProperty extends Property {
	static final int MIN_LEVEL = 5;
	static final int MAX_LEVEL = 12;

	public BadProperty(String name, String description, int cost) {
		super(name, description, cost, MIN_LEVEL, MAX_LEVEL);
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
	public int getUpgradeCosts() {
		return getLearningCosts();
	}

	@Override
	public String toString() {
		return getName() + " " + getLevel() + " (" + getLearningCosts() + " AP pro Punkt)";
	}
}
