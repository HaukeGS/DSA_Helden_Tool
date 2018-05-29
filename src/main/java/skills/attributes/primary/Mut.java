package skills.attributes.primary;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Mut extends PrimaryAttribute {
	public final static String NAME = "Mut";

	public Mut() {
		super(NAME, "");
	}

}
