package skills.attributes;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Charisma extends PrimaryAttribute {
	public static final String NAME = "Charisma";

	public Charisma() {
		super(NAME, "");
	}

}
