package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Zhayad extends Language {
	static final String NAME = "Zhayad";
	static final String DESCRIPTION = "Das Zhayad wird nur von eitlen Magiern genutzt. Es heißt nur in dieser Sprache könne man mit Dämonen kommunizieren. \nDie Sprache verwendet eigene Silben- und Lautzeichen.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Zhayad() {
		super(NAME, DESCRIPTION, REQ, 4, 40);
	}

}