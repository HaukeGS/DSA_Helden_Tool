package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Garethi extends Language {
	static final String NAME = "Garethi";
	static final String DESCRIPTION = "wichtigste Sprache auf Welt";
	static final Predicate<Aventurian> REQ = (Aventurian a) -> a.getPrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE) >= 13;

	public Garethi() {
		super(NAME, DESCRIPTION, REQ, 5, 50);
	}

}
