package skills;

import skills.LevelCostCalculator.Column;

public class LookupIncreasableSkill extends IncreasableSkill {

	private final Column c;

	private static LevelCostCalculator lookupTable = new LevelCostCalculator();

	public LookupIncreasableSkill(String name, String description, Column c,
			int minLevel, int maxLevel) {
		super(name, description, minLevel, maxLevel);
		this.c = c;
	}

	@Override
	public int getUpgradeCosts() {
		return lookupTable.getCost(level, level + 1, c);
	}

	@Override
	public int getDowngradeRefund() {
		return lookupTable.getRefund(level, level - 1, c);
	}

	@Override
	public int getTotalCosts() {
		return lookupTable.getCost(minLevel, level, c);
	}

}
