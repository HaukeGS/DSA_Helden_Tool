package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Tulamidya extends Language {
	static final String NAME = "Tulamidya";
	static final String DESCRIPTION = "Tulamidya ist nach Garethi die wichtigste Händler- und Verkehrssprache. Sie wird im ganzen Tulamidenland und in den umliegenden Regionen gesprochen. \nDie Sprache wird in Kusliker Zeichen, den Geheiligten Glyphen von Unau oder in den 56 Silbenzeichen des Tulamidya geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Tulamidya() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}
