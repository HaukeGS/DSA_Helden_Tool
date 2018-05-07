package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Mahrisch extends Language {
	static final String NAME = "Mahrisch";
	static final String DESCRIPTION = "Das Mahrisch ist die Meeressprache. \nDie Sprache wird in den Mahrischen Glyphen geschrieben.";

	public Mahrisch() {
		super(NAME, DESCRIPTION, 5, 50);
	}

}