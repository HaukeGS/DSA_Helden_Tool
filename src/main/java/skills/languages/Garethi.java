package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Garethi extends Language {
	static final String NAME = "Garethi";
	static final String DESCRIPTION = "Garethi ist die wichtigste Verkehrssprache auf Aventurien. Sie wird im ganzen Mittelland gesprochen, sowieso teilweise in den angrenzenden Regionen. Sie wird in den verschiedenen Regionen mit den Dialekten Horathi, Bornländisch, Brabaci, Maraskani, Alberned, Andergastisch, Charypto und Gatamo gesprochen. \nDie Sprache wird in Kusliker Zeichen geschrieben.";
	static final Predicate<Aventurian> REQ = NOREQUIREMENT;

	public Garethi() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}
