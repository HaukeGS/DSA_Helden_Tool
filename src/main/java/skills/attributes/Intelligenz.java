package skills.attributes;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Intelligenz extends PrimaryAttribute {
	public static final String NAME = "Intelligenz";

	public Intelligenz() {
		super(NAME, "");
	}

}
