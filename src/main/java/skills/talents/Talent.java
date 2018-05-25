package skills.talents;

import aventurian.Aventurian;
import aventurian.LevelCostCalculator.Column;
import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import skills.LookupIncreasableSkill;

public class Talent extends LookupIncreasableSkill {

	private final PRIMARY_ATTRIBUTE a1;
	private final PRIMARY_ATTRIBUTE a2;
	private final PRIMARY_ATTRIBUTE a3;

	public Talent(String name, String description, int minLevel, int maxLevel, Column c, PRIMARY_ATTRIBUTE a1,
			PRIMARY_ATTRIBUTE a2, PRIMARY_ATTRIBUTE a3) {
		super(name, description, c, minLevel, maxLevel);
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
	}

	@Override
	public boolean isAbleToIncrease(Aventurian a) {
		return (level - 3) < a.getMaximumOf(a1, a2, a3);
	}
}
