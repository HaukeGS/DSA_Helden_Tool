package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Zelemja extends Language {
	static final String NAME = "Zelemja";
	static final String DESCRIPTION = "Diese alte Sprache ist aus dem Rssahh enstanden und wird nur noch in der Gegend um Selem gesprochen. \nDie Sprache wird in den 5000 Chrmk-Zeichen geschrieben.";

	public Zelemja() {
		super(NAME, DESCRIPTION,  5, 50);
	}

}
