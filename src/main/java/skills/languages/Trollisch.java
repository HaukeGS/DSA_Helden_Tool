package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Trollisch extends Language {
	static final String NAME = "Trollisch";
	static final String DESCRIPTION = "Das Trollische ist die Sprache der Trolle. \nDie Sprache kann in einer komplizierten dreidimentsionlen Bilderschrift 'geschrieben' werden.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Trollisch() {
		super(NAME, DESCRIPTION, REQ, 4, 40);
	}

}