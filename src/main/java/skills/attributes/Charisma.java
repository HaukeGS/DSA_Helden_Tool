package skills.attributes;

import aventurian.LevelCostCalculator.Column;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Charisma extends PrimaryAttribute {
	public static final String NAME = "Charisma";

	public Charisma() {
		super(NAME, "", Column.H);
	}

}
