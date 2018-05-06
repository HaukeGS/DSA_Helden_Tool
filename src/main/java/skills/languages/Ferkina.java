package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Ferkina extends Language {
	static final String NAME = "Ferkina";
	static final String DESCRIPTION = "Das Ferkina ist die Sprache des gleichnamigen Volkes. \nDie Sprache wird in keiner Schrift geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Ferkina() {
		super(NAME, DESCRIPTION, REQ, 4, 40);
	}

}