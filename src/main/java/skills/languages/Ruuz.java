package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Ruuz extends Language {
	static final String NAME = "Ruuz";
	static final String DESCRIPTION = "Das Ruuz ist das 'Ur-Maraskanische' und wird auch nur noch auf der Insel gesprochen. \nDie Sprache wird in den 300 Wort- und Silbenzeichen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Ruuz() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}