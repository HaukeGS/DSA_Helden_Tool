package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Zhulchummaqra extends Language {
	static final String NAME = "Zhulchummaqra";
	static final String DESCRIPTION = "Das Zhulchummaqra ist die Sprache der Trollzocker und ist aus dem Ur-Tulamidya entstanden. \nDie Sprache wird in keiner Schrift geschrieben.";

	public Zhulchummaqra() {
		super(NAME, DESCRIPTION,  4, 40);
	}

}