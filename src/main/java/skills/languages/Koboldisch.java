package skills.languages;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Koboldisch extends Language {
	static final String NAME = "Koboldisch";
	static final String DESCRIPTION = "Das sehr schnelle Koboldische kann nur von Schelmen oder mit Kenntnis des AXXELERATUS erlernt werden. \nDie Sprache ist schriftlos.";

	public Koboldisch() {
		super(NAME, DESCRIPTION, 4, 40);
	}
	
	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return a.isMage();
	}
	
	@Override
	public boolean isAllowedToAdd(Aventurian a) {
		return a.isMage();
	}

}