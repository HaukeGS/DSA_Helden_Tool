package skills.properties;

import aventurian.Aventurian;
import aventurian.AventurianManagerFacade;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.secondary.SecondaryAttribute;
import skills.attributes.secondary.Wundschwelle;

@InstantiableSkill(SkillType.PROPERTY)
public class Eisern extends Property {

	private static final String DESCRIPTION = "Erh�ht die Wundschwelle um 1. \n Kann nicht zusammen mit 'Glasknochen' gew�hlt werden.";
	static final String NAME = "Eisern";

	public Eisern() {
		super(NAME, DESCRIPTION, 200);
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return !a.hasSkill(Glasknochen.NAME);
	}

	@Override
	public void atGain(AventurianManagerFacade a) {
		final SecondaryAttribute s = a.getDatabase().getSecondaryAttribute(Wundschwelle.NAME);
		a.increaseWithoutPay(s);
	}

	@Override
	public void atLose(AventurianManagerFacade a) {
		final SecondaryAttribute s = a.getDatabase().getSecondaryAttribute(Wundschwelle.NAME);
		a.decreaseWithoutRefund(s);
	}

}
