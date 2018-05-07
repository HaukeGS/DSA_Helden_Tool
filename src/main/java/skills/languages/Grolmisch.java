package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Grolmisch extends Language {
	static final String NAME = "Grolmisch";
	static final String DESCRIPTION = "Das Grolmische ist wegen seiner Schnalz- und Knacklaute für nicht-Grolme nur schwer zu erlernen. \nDie Sprache ist schriftlos.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Grolmisch() {
		super(NAME, DESCRIPTION, REQ, 4, 100);
	}

}