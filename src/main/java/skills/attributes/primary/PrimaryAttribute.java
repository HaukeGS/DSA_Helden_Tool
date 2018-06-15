package skills.attributes.primary;

import aventurian.LevelCostCalculator.Column;
import skills.LookupIncreasableSkill;

public class PrimaryAttribute extends LookupIncreasableSkill {
	static final int MAX = 14;
	static final int MIN = 8;

	public PrimaryAttribute(String name, String description) {
		super(name, description, Column.H, MIN, MAX);
	}

	public void increaseMaxLevel() {
		this.maxLevel += 1;
	}

	public void increaseMinLevel() {
		if (minLevel >= maxLevel)
			throw new IllegalStateException("minLevel cannot be greater than maxLevel");
		this.minLevel += 1;
	}

	public void decreaseMaxLevel() {
		if (maxLevel <= minLevel)
			throw new IllegalStateException("maxLevel cannot be lower than minLevel");
		this.maxLevel -= 1;
	}

	public void decreaseMinLevel() {
		if (minLevel <= 0)
			throw new IllegalStateException("minLevel cannot be lower than 0");
		this.minLevel -= 1;
	}
}
