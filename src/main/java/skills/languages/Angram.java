package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Angram extends Language {
	static final String NAME = "Angram";
	static final String DESCRIPTION = "Das Angram ist die alte Sprache der Zwergen. Sie wird von den Angrosch-Priestern gesprochen. \nUm die Sprache über die zweite Stufe hinaus zu steigern ist die Sonderfertigkeit Sprachenkunde erforderlich. \nDie Sprache wird in den Angram-Glyphen, einer kryptischen Bilderschrift, geschrieben, die sehr viel subjektive Interpretation erfordert.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Angram() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}
	
	@Override
	public boolean isAllowed(Aventurian a) {
		if (getLevel() < 2)
			return true;
		if (a.hasSkill("Sprachenkunde"))
			return true;
		return false;
	}

}