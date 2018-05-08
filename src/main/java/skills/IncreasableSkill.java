package skills;

import aventurian.Aventurian;

public abstract class IncreasableSkill extends Skill {
	protected int level;
	protected final int maxLevel;
	protected final int minLevel;

	public IncreasableSkill(String name, String description,  int cost, int minLevel,
			int maxLevel) {
		super(name, description, cost);
		this.maxLevel = maxLevel;
		this.minLevel = minLevel;
		this.level = minLevel;
	}

	public void increase() {
		if (level >= maxLevel)
			throw new IllegalStateException("IncreasableSkill cannot be over max level!");
		level++;
	}

	public void decrease() {
		if (level <= minLevel)
			throw new IllegalStateException("IncreasableSkill level cannot be less than min level!");
		level--;
	}

	public int getUpgradeCosts() {
		return (level + 1) * getLearningCosts();
	}

	public int getDowngradeRefund() {
		return level * getLearningCosts();
	}

	public int getTotalCosts() {
		return (level * (level + 1) / 2) * getLearningCosts();
	}

	public int getLevel() {
		return level;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public boolean isAllowedToIncrease(Aventurian a) {
		return level < maxLevel;
	}

	public boolean isAllowedToDecrease() {
		return level > minLevel;
	}
	
	@Override
	public String toString() {
		return getName() + " (" + getTotalCosts() + ")";
	}



}
