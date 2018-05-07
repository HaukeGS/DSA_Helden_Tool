package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Tocamuyacy extends Language {
	static final String NAME = "Tocamuyacy";
	static final String DESCRIPTION = "Das Tocamuyacy ist die Sprache der Tocamuyac. \nDie Sprache ist schriftlos.";

	public Tocamuyacy() {
		super(NAME, DESCRIPTION, 4, 40);
	}

}