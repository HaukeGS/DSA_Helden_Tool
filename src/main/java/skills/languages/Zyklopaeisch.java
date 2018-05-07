package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Zyklopaeisch extends Language {
	static final String NAME = "Zyklopaeisch";
	static final String DESCRIPTION = "Zyklopaeisch ist die gemeine Sprache auf den Zyklopeninseln. Sie wurde eigenst�ndig aus dem Aureliani entwickelt und erst in neuerer Zeit wieder stark mit Lehnw�rtern aus dem Garethi 'verunreinigt'. Au�erhalb der Zyklopeninseln wird es nur noch von Sprackwissenschaftlern gesprochen. \nDie Sprache wird in Kusliker Zeichen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Zyklopaeisch() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}
