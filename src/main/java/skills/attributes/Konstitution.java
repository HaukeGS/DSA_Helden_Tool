package skills.attributes;

import aventurian.LevelCostCalculator.Column;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Konstitution extends PrimaryAttribute {
	static final String NAME = "Konstitution";

	public Konstitution() {
		super(NAME, "", Column.H);
	}

}
