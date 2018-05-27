package skills.attributes;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Koerperkraft extends PrimaryAttribute {
	static final String NAME = "Körperkraft";

	public Koerperkraft() {
		super(NAME, "");
	}

}
