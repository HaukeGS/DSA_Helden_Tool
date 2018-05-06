package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Ologhaijan extends Language {
	static final String NAME = "Ologhaijan";
	static final String DESCRIPTION = "Das Ologhaijan ist das 'Hochorkisch'. Wenn auch nur wenig komplexer wird diese Sprache doch meist nur von den Orkschamanen gesprochen. \nDie Sprache ist schriftlos.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Ologhaijan() {
		super(NAME, DESCRIPTION, REQ, 4, 40);
	}

}