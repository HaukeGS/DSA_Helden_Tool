package skills;

public abstract class LinearIncreasableSkill extends IncreasableSkill {

	protected final int cost;

	public LinearIncreasableSkill(String name, String description, int cost, int minLevel, int maxLevel) {
		super(name, description, minLevel, maxLevel);
		this.cost = cost;
	}

	protected int getLearningCosts() {
		return cost;
	}

	public int getUpgradeCosts() {
		return (level + 1) * getLearningCosts();
	}

	public int getDowngradeRefund() {
		return level * getLearningCosts();
	}

	@Override
	public int getTotalCosts() {
		return (level * (level + 1) / 2) * getLearningCosts();
	}

	@Override
	public String toString() {
		return getName() + " (" + getTotalCosts() + ")";
	}

}
