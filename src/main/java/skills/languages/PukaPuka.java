package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class PukaPuka extends Language {
	static final String NAME = "Puka Puka";
	static final String DESCRIPTION = "Das Puka Puka ist die Sprache der Waldinsel-Utulus. \nDie Sprache ist schriftlos.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public PukaPuka() {
		super(NAME, DESCRIPTION, REQ, 4, 40);
	}

}