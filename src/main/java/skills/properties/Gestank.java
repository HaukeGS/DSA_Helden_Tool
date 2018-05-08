package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Gestank extends Property {

	public Gestank() {
		super("Gestank", "bestialischer Gestank", -150, EMPTY, EMPTY);
	}

}
