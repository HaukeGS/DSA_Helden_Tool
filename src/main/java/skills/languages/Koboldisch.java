package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Koboldisch extends Language {
	static final String NAME = "Koboldisch";
	static final String DESCRIPTION = "Das sehr schnelle Koboldische kann nur von Schelmen oder mit Kenntnis des AXXELERATUS erlernt werden. \nDie Sprache ist schriftlos.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Koboldisch() {
		super(NAME, DESCRIPTION, REQ, 4, 40);
	}
	
	@Override
	public boolean isAllowed(Aventurian a) {
		return a.isMage();
	}

}