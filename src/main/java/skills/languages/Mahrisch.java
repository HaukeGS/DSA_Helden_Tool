package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Mahrisch extends Language {
	static final String NAME = "Mahrisch";
	static final String DESCRIPTION = "Das Mahrisch ist die Meeressprache. \nDie Sprache wird in den Mahrischen Glyphen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Mahrisch() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}