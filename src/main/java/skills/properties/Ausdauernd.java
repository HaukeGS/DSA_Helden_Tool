package skills.properties;

import aventurian.Aventurian;
import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Ausdauernd extends Property {

	private static final String DESCRIPTION = "Erh�ht die Ersch�pfungsschwelle um 1. \n Kann nicht zusammen mit 'Kurzatmig' gew�hlt werden.";
	static final String NAME = "Ausdauernd";
	
	public Ausdauernd() {
		super(NAME, DESCRIPTION, 150);
	}
	
	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return !a.hasSkill(Kurzatmig.NAME);
	}
	
	@Override
	public void atGain(Aventurian a) {
		a.increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.EXHAUSTIONTHRESHHOLD, 1);
	}
	
	@Override
	public void atLose(Aventurian a) {
		a.decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.EXHAUSTIONTHRESHHOLD, 1);
	}
	
}
