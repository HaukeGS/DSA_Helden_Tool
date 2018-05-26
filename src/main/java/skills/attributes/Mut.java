package skills.attributes;

import aventurian.LevelCostCalculator.Column;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Mut extends PrimaryAttribute {
	final static String NAME = "Mut";

	public Mut() {
		super(NAME, "", Column.H);
	}

}
