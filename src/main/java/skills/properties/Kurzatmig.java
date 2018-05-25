package skills.properties;

import aventurian.Aventurian;
import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Kurzatmig extends Property {

	private static final String DESCRIPTION = "";
	static final String NAME = "Kurzatmig";

	public Kurzatmig() {
		super(NAME, DESCRIPTION, -150);
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return !a.hasSkill(Ausdauernd.NAME);
	}

	@Override
	public void atGain(Aventurian a) {
		a.decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.EXHAUSTIONTHRESHHOLD, 1);
	}

	@Override
	public void atLose(Aventurian a) {
		a.increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.EXHAUSTIONTHRESHHOLD, 1);
	}
}
