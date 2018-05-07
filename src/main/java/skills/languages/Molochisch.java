package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Molochisch extends Language {
	static final String NAME = "Molochisch";
	static final String DESCRIPTION = "Das Molochische ist nur bei der eigenen Volksgruppe bekannt. \nDie Sprache ist schriftlos.";

	public Molochisch() {
		super(NAME, DESCRIPTION, 4, 80);
	}

}