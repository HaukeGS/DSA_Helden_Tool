package skills.talents;

import aventurian.Aventurian;
import skills.LookupIncreasableSkill;
import skills.LevelCostCalculator.Column;

public class Talent extends LookupIncreasableSkill {

	String a1;
	String a2;
	String a3;

	public Talent(String name, String description, int minLevel, int maxLevel, Column c, String a1, String a2,
			String a3) {
		super(name, description, c, minLevel, maxLevel);
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
	}

	@Override
	public boolean isAbleToIncrease(Aventurian a) {
		return (level - 3) < a.getMaximumOfPrimaryAttributes(a1, a2, a3);
	}
}
