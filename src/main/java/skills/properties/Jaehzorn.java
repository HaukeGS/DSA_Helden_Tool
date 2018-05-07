package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.BADPROPERTY)
public class Jaehzorn extends BadProperty {
	static final String NAME = "Jähzorn";
	static final String DESCRIPTION = "Bei Missgeschicken oder gekränkter Ehre wird der Charakter raden und handelt irrational. Dies kann schnell dazu führen, dass er sich selbst und seine Kameraden in Gefahr bringt.";

	public Jaehzorn() {
		super(NAME, DESCRIPTION, -75, NOREQUIREMENT);
	}

}
