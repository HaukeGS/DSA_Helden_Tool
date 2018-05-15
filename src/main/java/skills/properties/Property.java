package skills.properties;

import skills.LinearIncreasableSkill;

public class Property extends LinearIncreasableSkill {

	public Property(String name, String description, int cost) {
		super(name, description, cost, 1, 1);
	}

	public Property(String name, String description, int cost, int minLevel, int maxLevel) {
		super(name, description, cost, minLevel, maxLevel);
	}

	public boolean isAdvantage() {
		return cost >= 0;
	}

	public boolean isDisadvantage() {
		return !isAdvantage();
	}

	@Override
	public int getLearningCosts() {
		if (isAdvantage())
			return cost;
		return cost * -1;
	}

}
