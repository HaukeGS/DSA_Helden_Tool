package skills.attributes.primary;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Klugheit extends PrimaryAttribute {
	public static final String NAME = "Intelligenz";

	public Klugheit() {
		super(NAME, "");
	}

}
