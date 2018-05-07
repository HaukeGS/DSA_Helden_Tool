package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Mohisch extends Language {
	static final String NAME = "Mohisch";
	static final String DESCRIPTION = "Das Mohisch ist die Sprache der Waldmenschen aus den Dschungeln im S�den Aventuriens. \nDie Sprache ist schriftlos.";

	public Mohisch() {
		super(NAME, DESCRIPTION, 4, 40);
	}

}