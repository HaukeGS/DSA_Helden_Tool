package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Rogolan extends Language {
	static final String NAME = "Rogolan";
	static final String DESCRIPTION = "Das Rogolan ist die gemeine Zwergensprache, die von den Zwergen in ihren unterirdischen Königreichen gesprochen wird. \nDie Sprache wird in den Zwergenrunen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Rogolan() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}