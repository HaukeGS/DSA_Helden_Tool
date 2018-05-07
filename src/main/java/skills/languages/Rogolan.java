package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Rogolan extends Language {
	static final String NAME = "Rogolan";
	static final String DESCRIPTION = "Das Rogolan ist die gemeine Zwergensprache, die von den Zwergen in ihren unterirdischen Königreichen gesprochen wird. Sie hat sich aus dem altzwergischen Angram entwickelt. \nDie Sprache wird in den Zwergenrunen geschrieben.";

	public Rogolan() {
		super(NAME, DESCRIPTION, 5, 50);
	}

}