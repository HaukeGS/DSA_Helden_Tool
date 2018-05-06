package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Goblinisch extends Language {
	static final String NAME = "Goblinisch";
	static final String DESCRIPTION = "Das Goblinisch ist die simple Sprache der Goblins. \nDie Sprache ist schriftlos.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Goblinisch() {
		super(NAME, DESCRIPTION, REQ, 3, 30);
	}

}