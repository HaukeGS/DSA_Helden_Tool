package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class AltesKemi extends Language {
	static final String NAME = "Altes Kemi";
	static final String DESCRIPTION = "Das Alte Kemi ist eigentlich ausgestorben. Derzeit wird es von den kemschen Autoritäten künstlich wiederbelebt. \nDie Sprache wird (oder wurde) in den nur dafür verwendeten Zeichen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public AltesKemi() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}