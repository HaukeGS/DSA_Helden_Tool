package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Tocamuyacy extends Language {
	static final String NAME = "Tocamuyacy";
	static final String DESCRIPTION = "Das Tocamuyacy ist die Sprache der Tocamuyac. \nDie Sprache ist schriftlos.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Tocamuyacy() {
		super(NAME, DESCRIPTION, REQ, 4, 40);
	}

}