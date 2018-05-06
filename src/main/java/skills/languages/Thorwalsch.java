package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Thorwalsch extends Language {
	static final String NAME = "Thorwalsch";
	static final String DESCRIPTION = "Das Thorwalsch ist die Sprache der rauen Thorwaler im Norden Aventuriens. \nDie Sprache wird in den Kusliker Zeichen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Thorwalsch() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}