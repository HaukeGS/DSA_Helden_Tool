package skills.attributes;

import aventurian.LevelCostCalculator.Column;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Intelligenz extends PrimaryAttribute {
	public static final String NAME = "Intelligenz";

	public Intelligenz() {
		super(NAME, "", Column.H);
	}

}
