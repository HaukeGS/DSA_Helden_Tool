package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Rissoal extends Language {
	static final String NAME = "Rissoal";
	static final String DESCRIPTION = "Das Rissoal wird auf beiden Seiten des Meeres der Sieben Winde gesprochen. \nDie Sprache ist schriftlos.";

	public Rissoal() {
		super(NAME, DESCRIPTION, 5, 50);
	}

}