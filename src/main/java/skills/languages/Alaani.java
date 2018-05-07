package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Alaani extends Language {
	static final String NAME = "Alaani";
	static final String DESCRIPTION = "Das komplexe Alaani ist die Sprache der Nordbarden. \nDie Sprache wird in Kusliker Zeichen oder dem Alten Alaani geschrieben.	";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Alaani() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}