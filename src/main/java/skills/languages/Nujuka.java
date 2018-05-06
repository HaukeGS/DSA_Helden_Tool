package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Nujuka extends Language {
	static final String NAME = "Nujuka";
	static final String DESCRIPTION = "Das Nujuka wird von den Nivesen gesprochen. \nDie Sprache wird in keiner Schrift geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Nujuka() {
		super(NAME, DESCRIPTION, REQ, 4, 40);
	}

}