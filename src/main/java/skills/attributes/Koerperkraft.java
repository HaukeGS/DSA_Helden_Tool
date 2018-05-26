package skills.attributes;

import aventurian.LevelCostCalculator.Column;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Koerperkraft extends PrimaryAttribute {
	static final String NAME = "Körperkraft";

	public Koerperkraft() {
		super(NAME, "", Column.H);
	}

}
