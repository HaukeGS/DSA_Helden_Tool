package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Rssahh extends Language {
	static final String NAME = "Rssahh";
	static final String DESCRIPTION = "Das Rssahh ist die gemeine Sprache der Achaz. Für Nicht-Achaz ist die Sprache äußerst schwierig zu erlernen. \nDie Sprache wird in den 5000 Chrmk-Zeichen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Rssahh() {
		super(NAME, DESCRIPTION, REQ, 5, 100);
	}
}