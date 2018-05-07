package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Alaani extends Language {
	static final String NAME = "Alaani";
	static final String DESCRIPTION = "Das komplexe Alaani ist die Sprache der Nordbarden. \nDie Sprache wird in Kusliker Zeichen oder dem Alten Alaani geschrieben.	";

	public Alaani() {
		super(NAME, DESCRIPTION, 5, 50);
	}

}