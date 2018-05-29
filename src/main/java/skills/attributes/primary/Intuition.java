package skills.attributes.primary;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Intuition extends PrimaryAttribute {
	public static final String NAME = "Intuition";

	public Intuition() {
		super(NAME, "");
	}

}
