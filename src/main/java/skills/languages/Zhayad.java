package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Zhayad extends Language {
	static final String NAME = "Zhayad";
	static final String DESCRIPTION = "Das Zhayad wird nur von eitlen Magiern genutzt. Es heißt nur in dieser Sprache könne man mit Dämonen kommunizieren. \nDie Sprache verwendet eigene Silben- und Lautzeichen.";

	public Zhayad() {
		super(NAME, DESCRIPTION,  4, 40);
	}

}