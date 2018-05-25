package skills.attributes;

import skills.LookupIncreasableSkill;

public class PrimaryAttribute extends LookupIncreasableSkill {
	static final int MAX = 14;
	static final int MIN = 8;

	public PrimaryAttribute(String name, String description, COLUMN c) {
		super(name, description, c, MIN, MAX);
	}

	public void increaseMaxLevel() {
		this.maxLevel += 1;
	}

	public void increaseMinLevel() {
		this.minLevel += 1;
	}

	public void decreaseMaxLevel() {
		this.maxLevel -= 1;
	}

	public void decreaseMinLevel() {
		this.minLevel -= 1;
	}
}
