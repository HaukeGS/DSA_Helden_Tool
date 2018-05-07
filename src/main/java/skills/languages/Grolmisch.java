package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Grolmisch extends Language {
	static final String NAME = "Grolmisch";
	static final String DESCRIPTION = "Das Grolmische ist wegen seiner Schnalz- und Knacklaute für nicht-Grolme nur schwer zu erlernen. \nDie Sprache ist schriftlos.";

	public Grolmisch() {
		super(NAME, DESCRIPTION, 4, 100);
	}

}