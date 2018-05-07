package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Nujuka extends Language {
	static final String NAME = "Nujuka";
	static final String DESCRIPTION = "Das Nujuka wird von den Nivesen gesprochen. \nDie Sprache wird in keiner Schrift geschrieben.";

	public Nujuka() {
		super(NAME, DESCRIPTION, 4, 40);
	}

}