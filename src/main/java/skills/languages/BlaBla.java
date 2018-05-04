package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class BlaBla extends Language {
	final static String NAME = "BlaBla";
	final static String DESCRIPTION = "Testsprache. Kannst du mich verstehen?";

	public BlaBla() {
		super(NAME, DESCRIPTION, NOREQUIREMENT, 4, 50);
	}

}
