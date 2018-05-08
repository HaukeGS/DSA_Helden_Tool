package skills.languages;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Fuechsisch extends Language {
	static final String NAME = "F�chsisch";
	static final String DESCRIPTION = "Das F�chsische ist eine Gaunersprache. \nAu�er einzelnen Zinken ist die Sprache schriftlos.";

	public Fuechsisch() {
		super(NAME, DESCRIPTION, 3, 40);
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {

		if (a.hasSkill(Aureliani.NAME))
			return true;
		return false;
	}

}