package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Zelemja extends Language {
	static final String NAME = "Zelemja";
	static final String DESCRIPTION = "Diese alte Sprache wird nur noch in der Gegend um Selem gesprochen. \nDie Sprache wird in den 5000 Chrmk-Zeichen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Zelemja() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}
