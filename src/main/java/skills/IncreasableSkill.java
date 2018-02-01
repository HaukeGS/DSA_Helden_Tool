package skills;

import java.util.function.Consumer;
import java.util.function.Predicate;

import aventurian.Aventurian;

public abstract class IncreasableSkill extends Skill {
	protected int level;
	protected final int maxLevel;
	protected final int minLevel;

	public IncreasableSkill(String name, String description, Consumer<Aventurian> effectOnGain,
			Consumer<Aventurian> effectOnLose, Predicate<Aventurian> requirement, int cost, int maxLevel,
			int minLevel) {
		super(name, description, effectOnGain, effectOnLose, requirement, cost);
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
		if (level <= 1)
			throw new IllegalStateException("IncreasableSkill level cannot be less than min level!");
		level--;
	}
	
	public int getUpgradeCost() {
		return (level + 1) * cost;
	}

	public int getDowngradeRefund() {
		return level * cost;
	}

	public int getTotalCost() {
		return (level * (level + 1) / 2) * cost;
	}

	public int getLevel() {
		return level;
	}

	public boolean isIncreasable() {
		return level < maxLevel;
	}

	public boolean isDecreasable() {
		return level > minLevel;
	}

}
