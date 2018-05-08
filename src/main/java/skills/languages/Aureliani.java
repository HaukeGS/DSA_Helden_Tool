package skills.languages;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Aureliani extends Language {

	static final String NAME = "Aureliani";
	static final String DESCRIPTION = "\nUm es über die zweite Stufe hinaus zu steigern ist die Sonderfertigkeit Sprachenkunde erforderlich. \nDie Sprache wird in den Imperialen Zeichen der Güldenländer geschrieben.";

	public Aureliani() {
		super(NAME, DESCRIPTION, 5, 50);
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {
		if (getLevel() <= 3)
			return true;
		if (a.hasSkill("Sprachenkunde"))
			return true;
		return false;
	}

	public boolean isAllowedToIncrease(Aventurian a) {
		if (!super.isAllowedToIncreasase(a))
			return false;
		if (getLevel() < 3)
			return true;
		if (a.hasSkill("Sprachenkunde"))
			return true;
		return false;
	}

}