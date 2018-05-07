package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Rabensprache extends Language {
	static final String NAME = "Rabensprache";
	static final String DESCRIPTION = "Die Rabensprache ist die Geheimsprache der Boronkirche in Al'Anfa. \nDie Sprache wird in den Kusliker Zeichen oder den 56 Silbenzeichen des Tulamidya geschrieben.";

	public Rabensprache() {
		super(NAME, DESCRIPTION, 4, 40);
	}

}