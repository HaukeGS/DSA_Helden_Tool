package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Tulamidya extends Language {
	static final String NAME = "Tulamidya";
	static final String DESCRIPTION = "Tulamidya ist nach Garethi die wichtigste H�ndler- und Verkehrssprache. Sie wird im ganzen Tulamidenland und in den umliegenden Regionen gesprochen. \nDie Sprache wird in Kusliker Zeichen, den Geheiligten Glyphen von Unau oder in den 56 Silbenzeichen des Tulamidya geschrieben.";

	public Tulamidya() {
		super(NAME, DESCRIPTION, 5, 50);
	}

}
