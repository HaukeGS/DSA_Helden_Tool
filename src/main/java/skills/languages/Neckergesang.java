package skills.languages;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Neckergesang extends Language {
	static final String NAME = "Neckergesang";
	static final String DESCRIPTION = "Der teilweise telepathische Neckergesang kann nur mit dem Zauber GEDANKENBILDER ELFENRUF zur Perfektion gebracht werden. \nDie Sprache ist schriftlos.";

	public Neckergesang() {
		super(NAME, DESCRIPTION, 5, 50);
	}
	
	@Override
	public boolean isAllowedToHave(Aventurian a) {
		if (getLevel() <= 4)
			return true;
		if (a.hasSkill("Gedankenbilder Elfenruf"))
			return true;
		return false;
	}
	
	@Override
	public boolean isAbleToIncrease(Aventurian a) {
		if (getLevel() < 4)
			return true;
		if (a.hasSkill("Gedankenbilder Elfenruf"))
			return true;
		return false;
	}

}