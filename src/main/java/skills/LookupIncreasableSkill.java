package skills;

import aventurian.LevelCostCalculator;
import aventurian.LevelCostCalculator.Column;

public class LookupIncreasableSkill extends IncreasableSkill {

	private final Column c;

	private static LevelCostCalculator lookupTable = new LevelCostCalculator();

	public LookupIncreasableSkill(String name, String description, Column c, int minLevel, int maxLevel) {
		super(name, description, minLevel, maxLevel);
		this.c = c;
	}

	@Override
	int getUpgradeCosts() {
		return lookupTable.getCost(level, level + 1, c);
	}

	@Override
	int getDowngradeRefund() {
		return lookupTable.getRefund(level, level - 1, c);
	}

	@Override
	int getTotalCosts() {
		return lookupTable.getCost(minLevel, level, c);
	}

}
