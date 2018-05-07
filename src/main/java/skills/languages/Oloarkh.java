package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Oloarkh extends Language {
	static final String NAME = "Oloarkh";
	static final String DESCRIPTION = "Das Oloarkh ist die gemeine Sprache der Orks. Sie ist sehr simpel und hat keinen besonders groﬂen Wortschatz. \nDie Sprache ist schriftlos.";

	public Oloarkh() {
		super(NAME, DESCRIPTION, 3, 30);
	}

}