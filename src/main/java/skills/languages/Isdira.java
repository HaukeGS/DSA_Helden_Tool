package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Isdira extends Language {
	static final String NAME = "Isdira";
	static final String DESCRIPTION = "Das Isdira ist die gemeine Sprache der Elfen. \nNur die Elfen können mit ihrem Zweistimmigen Gesang die Sprache über die dritte Stufe hinaus steigern. \nDie Sprache wird in den 27 elfischen Schriftzeichen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Isdira() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}
	
	@Override
	public boolean isAllowed(Aventurian a) {
		if (getLevel() < 3)
			return true;
		if (a.hasSkill("Zweistimmiger Gesang"))
			return true;
		return false;
	}

}
