package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class UrTulamidya extends Language {
	static final String NAME = "Ur-Tulamidya";
	static final String DESCRIPTION = "Das alte Ur-Tulamidya wird schon lange nicht mehr aktiv gesprochen, sondern genau wie das Bosparano nur noch in der Wissenschaft und vor allem der Magie verwendet. Einige Magierakademien im tulamidischen Raum lehren auf Ur-Tulamidya. \nUm es �ber die zweite Stufe hinaus zu steigern ist die Sonderfertigkeit Sprachenkunde erforderlich.\nDie Sprache wird in den 56 Silbenzeichen des Tulamidya, den alten 300 Wort- und Silbenzeichen oder den uralten 5000 Chrmk-Zeichen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public UrTulamidya() {
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