package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Aureliani extends Language {

	static final String NAME = "Aureliani";
	static final String DESCRIPTION = "\nUm es über die zweite Stufe hinaus zu steigern ist die Sonderfertigkeit Sprachenkunde erforderlich. \nDie Sprache wird in den Imperialen Zeichen der Güldenländer geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Aureliani() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}
	
	@Override
	public boolean isAllowed(Aventurian a) {
		if (getLevel() < 3)
			return true;
		if (a.hasSkill("Sprachenkunde"))
			return true;
		return false;
	}

}