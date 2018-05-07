package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Rissoal extends Language {
	static final String NAME = "Rissoal";
	static final String DESCRIPTION = "Das Rissoal wird auf beiden Seiten des Meeres der Sieben Winde gesprochen. \nDie Sprache ist schriftlos.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Rissoal() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}