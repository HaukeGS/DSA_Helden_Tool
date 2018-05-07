package skills.languages;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Garethi extends Language {
	static final String NAME = "Garethi";
	static final String DESCRIPTION = "Garethi ist die wichtigste Verkehrssprache auf Aventurien. Sie wird im ganzen Mittelland gesprochen, sowieso teilweise in den angrenzenden Regionen. Sie wird in den verschiedenen Regionen mit den Dialekten Horathi, Bornlšndisch, Brabaci, Maraskani, Alberned, Andergastisch, Charypto und Gatamo gesprochen. \nDie Sprache wird in Kusliker Zeichen geschrieben.";

	public Garethi() {
		super(NAME, DESCRIPTION, 5, 50);
	}

}
