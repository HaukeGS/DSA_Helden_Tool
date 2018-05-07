package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Ologhaijan extends Language {
	static final String NAME = "Ologhaijan";
	static final String DESCRIPTION = "Das Ologhaijan ist das 'Hochorkisch'. Wenn auch nur wenig komplexer wird diese Sprache doch meist nur von den Orkschamanen gesprochen. \nDie Sprache ist schriftlos.";

	public Ologhaijan() {
		super(NAME, DESCRIPTION, 4, 40);
	}

}