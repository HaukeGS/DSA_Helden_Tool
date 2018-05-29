package skills.attributes.primary;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Koerperkraft extends PrimaryAttribute {
	public static final String NAME = "K�rperkraft";

	public Koerperkraft() {
		super(NAME, "");
	}

}
