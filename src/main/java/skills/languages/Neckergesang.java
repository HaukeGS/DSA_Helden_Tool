package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Neckergesang extends Language {
	static final String NAME = "Neckergesang";
	static final String DESCRIPTION = "^Der teilweise thelepatische Neckergesang kann nur mit dem Zauber GEDANKENBILDER ELFENRUF zur Perfektion gebracht werden. \nDie Sprache ist schriftlos.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Neckergesang() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}
	
	@Override
	public boolean isAllowed(Aventurian a) {
		if (getLevel() < 4)
			return true;
		if (a.hasSkill("Gedankenbilder Elfenruf"))
			return true;
		return false;
	}

}