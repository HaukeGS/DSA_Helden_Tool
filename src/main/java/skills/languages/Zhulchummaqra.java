package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Zhulchummaqra extends Language {
	static final String NAME = "Zhulchummaqra";
	static final String DESCRIPTION = "Das Zhulchummaqra ist die Sprache der Trollzocker. \nDie Sprache wird in keiner Schrift geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Zhulchummaqra() {
		super(NAME, DESCRIPTION, REQ, 4, 40);
	}

}