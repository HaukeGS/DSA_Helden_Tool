package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.BADPROPERTY)
public class Jaehzorn extends BadProperty {

	public Jaehzorn() {
		super("Jaehzorn", "wirklich wuetend", -75, NOREQUIREMENT);
	}

}
