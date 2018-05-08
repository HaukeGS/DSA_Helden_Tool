package skills.languages;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class UrTulamidya extends Language {
	static final String NAME = "Ur-Tulamidya";
	static final String DESCRIPTION = "Das alte Ur-Tulamidya wird schon lange nicht mehr aktiv gesprochen, sondern genau wie das Bosparano nur noch in der Wissenschaft und vor allem der Magie verwendet. Einige Magierakademien im tulamidischen Raum lehren auf Ur-Tulamidya. \nUm es über die zweite Stufe hinaus zu steigern ist die Sonderfertigkeit Sprachenkunde erforderlich.\nDie Sprache wird in den 56 Silbenzeichen des Tulamidya, den alten 300 Wort- und Silbenzeichen oder den uralten 5000 Chrmk-Zeichen geschrieben.";

	public UrTulamidya() {
		super(NAME, DESCRIPTION,  5, 50);
	}
	
	@Override
	public boolean isAllowedToHave(Aventurian a) {
		if (getLevel() <= 3)
			return true;
		if (a.hasSkill("Sprachenkunde"))
			return true;
		return false;
	}

	@Override
	public boolean isAllowedToIncrease(Aventurian a) {
		if (!super.isAllowedToIncrease(a))
			return false;
		if (getLevel() < 3)
			return true;
		if (a.hasSkill("Sprachenkunde"))
			return true;
		return false;
	}

}
