package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Molochisch extends Language {
	static final String NAME = "Molochisch";
	static final String DESCRIPTION = "Das Molochische ist nur bei der eigenen Volksgruppe bekannt. \nDie Sprache ist schriftlos.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Molochisch() {
		super(NAME, DESCRIPTION, REQ, 4, 80);
	}

}