package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Goblinisch extends Language {
	static final String NAME = "Goblinisch";
	static final String DESCRIPTION = "Das Goblinisch ist die simple Sprache der Goblins. \nDie Sprache ist schriftlos.";

	public Goblinisch() {
		super(NAME, DESCRIPTION, 3, 30);
	}

}