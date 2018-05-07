package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Fuechsisch extends Language {
	static final String NAME = "F�chsisch";
	static final String DESCRIPTION = "Das F�chsische ist eine Gaunersprache. \nAu�er einzelnen Zinken ist die Sprache schriftlos.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Fuechsisch() {
		super(NAME, DESCRIPTION, REQ, 3, 40);
	}

}