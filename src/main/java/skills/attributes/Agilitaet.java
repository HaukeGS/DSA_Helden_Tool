package skills.attributes;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Agilitaet extends PrimaryAttribute {
	public static final String NAME = "Agilität";

	public Agilitaet() {
		super(NAME, "");
	}

}
