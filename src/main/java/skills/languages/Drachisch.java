package skills.languages;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Drachisch extends Language {
	static final String NAME = "Drachisch";
	static final String DESCRIPTION = "Das Drachische ist nur mit Kenntnis von Gedankenlese- und Sendezauber zu erlernen. \nDie Sprache ist schriftlos.";

	public Drachisch() {
		super(NAME, DESCRIPTION, 5, 50);
	}
	
	@Override
	public boolean isAllowed(Aventurian a) {
		return a.isMage();
	}

}