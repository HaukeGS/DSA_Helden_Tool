package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Fuechsisch extends Language {
	static final String NAME = "Füchsisch";
	static final String DESCRIPTION = "Das Füchsische ist eine Gaunersprache. \nAußer einzelnen Zinken ist die Sprache schriftlos.";

	public Fuechsisch() {
		super(NAME, DESCRIPTION, 3, 40);
	}

}