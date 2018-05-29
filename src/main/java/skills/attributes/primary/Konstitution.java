package skills.attributes.primary;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Konstitution extends PrimaryAttribute {
	public static final String NAME = "Konstitution";

	public Konstitution() {
		super(NAME, "");
	}

}
