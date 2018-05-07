package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Rssahh extends Language {
	static final String NAME = "Rssahh";
	static final String DESCRIPTION = "Das Rssahh ist die gemeine Sprache der Achaz. F�r Nicht-Achaz ist die Sprache �u�erst schwierig zu erlernen. \nDie Sprache wird in den 5000 Chrmk-Zeichen geschrieben.";

	public Rssahh() {
		super(NAME, DESCRIPTION, 5, 100);
	}
}