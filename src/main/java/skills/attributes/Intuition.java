package skills.attributes;

import aventurian.LevelCostCalculator.Column;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Intuition extends PrimaryAttribute {
	static final String NAME = "Intuition";

	public Intuition() {
		super(NAME, "", Column.H);
	}

}
