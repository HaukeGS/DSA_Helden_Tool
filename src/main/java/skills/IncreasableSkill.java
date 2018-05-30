package skills;

import aventurian.Aventurian;

public abstract class IncreasableSkill extends Skill {
	protected int level;
	protected int maxLevel;
	protected int minLevel;

	public IncreasableSkill(String name, String description, int minLevel, int maxLevel) {
		super(name, description);
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.level = minLevel;
	}

	public void increase() {
		if (level >= maxLevel) // TODO ziemlich bloed, dass wir hier nicht if(isAllowedToIncrease) machen
								// konnen...
			throw new IllegalStateException("IncreasableSkill cannot be over max level!");
		level++;
	}

	public void decrease() {
		if (!isAllowedToDecrease())
			throw new IllegalStateException("IncreasableSkill level cannot be less than min level!");
		level--;
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
		return isIncreasable() && isAbleToIncrease(a);
	}

	private boolean isIncreasable() {
		return level < maxLevel;
	}

	protected boolean isAbleToIncrease(Aventurian a) {
		return true;
	}

	public boolean isAllowedToDecrease() {
		return getLevel() > getMinLevel();
	}

	public abstract int getUpgradeCosts();

	public abstract int getDowngradeRefund();

}
