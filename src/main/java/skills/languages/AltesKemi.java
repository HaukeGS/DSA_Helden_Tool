package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class AltesKemi extends Language {
	static final String NAME = "Altes Kemi";
	static final String DESCRIPTION = "Das Alte Kemi ist eigentlich ausgestorben. Derzeit wird es von den kemschen Autorit�ten k�nstlich wiederbelebt. \nDie Sprache wird (oder wurde) in den nur daf�r verwendeten Zeichen geschrieben.";

	public AltesKemi() {
		super(NAME, DESCRIPTION, 5, 50);
	}

}