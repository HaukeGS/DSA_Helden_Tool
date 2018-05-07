package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Ruuz extends Language {
	static final String NAME = "Ruuz";
	static final String DESCRIPTION = "Das Ruuz ist das 'Ur-Maraskanische' und wird auch nur noch auf der Insel gesprochen. \nDie Sprache wird in den 300 Wort- und Silbenzeichen geschrieben.";

	public Ruuz() {
		super(NAME, DESCRIPTION, 5, 50);
	}

}