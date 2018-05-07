package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class PukaPuka extends Language {
	static final String NAME = "Puka Puka";
	static final String DESCRIPTION = "Das Puka Puka ist die Sprache der Waldinsel-Utulus. \nDie Sprache ist schriftlos.";

	public PukaPuka() {
		super(NAME, DESCRIPTION, 4, 40);
	}

}