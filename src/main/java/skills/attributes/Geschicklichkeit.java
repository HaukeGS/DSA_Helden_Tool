package skills.attributes;

import aventurian.LevelCostCalculator.Column;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Geschicklichkeit extends PrimaryAttribute {
	public static final String NAME = "Geschicklichkeit";

	public Geschicklichkeit() {
		super(NAME, "", Column.H);
	}

}
