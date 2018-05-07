package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Ferkina extends Language {
	static final String NAME = "Ferkina";
	static final String DESCRIPTION = "Das Ferkina ist die Sprache des gleichnamigen Volkes und ist aus dem Ur-Tulamidya entstanden. \nDie Sprache wird in keiner Schrift geschrieben.";

	public Ferkina() {
		super(NAME, DESCRIPTION, 4, 40);
	}

}