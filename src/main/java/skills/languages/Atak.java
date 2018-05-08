package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Atak extends Language {
	static final String NAME = "Atak";
	static final String DESCRIPTION = "Das Atak ist eine Zeichen- und Geb�rdensprache, die speziell von tulamidischen H�ndlern wie auch fast aventurienweit von Dieben und Einbrechern genutzt wird. \nDie Sprache ist schriftlos.";

	public Atak() {
		super(NAME, DESCRIPTION, 3, 40);
	}

}