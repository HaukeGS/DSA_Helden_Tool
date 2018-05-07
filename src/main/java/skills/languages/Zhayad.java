package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Zhayad extends Language {
	static final String NAME = "Zhayad";
	static final String DESCRIPTION = "Das Zhayad wird nur von eitlen Magiern genutzt. Es hei�t nur in dieser Sprache k�nne man mit D�monen kommunizieren. \nDie Sprache verwendet eigene Silben- und Lautzeichen.";

	public Zhayad() {
		super(NAME, DESCRIPTION,  4, 40);
	}

}