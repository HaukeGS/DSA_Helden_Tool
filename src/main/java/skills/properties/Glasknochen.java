package skills.properties;

import aventurian.Aventurian;
import aventurian.AventurianManagerFacade;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.secondary.SecondaryAttribute;
import skills.attributes.secondary.Wundschwelle;

@InstantiableSkill(SkillType.PROPERTY)
public class Glasknochen extends Property {

	private static final String DESCRIPTION = "Verringert die Wundschwelle um 2. \n Kann nicht zusammen mit 'Eisern' gewählt werden.";
	static final String NAME = "Glasknochen";

	public Glasknochen() {
		super(NAME, DESCRIPTION, -500);
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return !a.hasSkill(Eisern.NAME);
	}

	@Override
	public void atGain(AventurianManagerFacade a) {
		final SecondaryAttribute s = a.getDatabase().getSecondaryAttribute(Wundschwelle.NAME);
		a.decreaseSecondaryAttributeMod(s, 2);
	}

	@Override
	public void atLose(AventurianManagerFacade a) {
		final SecondaryAttribute s = a.getDatabase().getSecondaryAttribute(Wundschwelle.NAME);
		a.increaseSecondaryAttributeMod(s, 2);
	}

}
