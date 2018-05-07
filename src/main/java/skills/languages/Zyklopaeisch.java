package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Zyklopaeisch extends Language {
	static final String NAME = "Zyklopaeisch";
	static final String DESCRIPTION = "Zyklopaeisch ist die gemeine Sprache auf den Zyklopeninseln. Sie wurde eigenständig aus dem Aureliani entwickelt und erst in neuerer Zeit wieder stark mit Lehnwörtern aus dem Garethi 'verunreinigt'. Außerhalb der Zyklopeninseln wird es nur noch von Sprackwissenschaftlern gesprochen. \nDie Sprache wird in Kusliker Zeichen geschrieben.";

	public Zyklopaeisch() {
		super(NAME, DESCRIPTION,  5, 50);
	}

}
