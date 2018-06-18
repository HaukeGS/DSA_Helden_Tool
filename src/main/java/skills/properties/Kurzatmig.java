package skills.properties;

import aventurian.Aventurian;
import aventurian.AventurianManagerFacade;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.secondary.Erschoepfungsschwelle;

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
	public void atGain(AventurianManagerFacade m) {
		m.decreaseWithoutRefund(m.getDatabase().getSecondaryAttribute(Erschoepfungsschwelle.NAME));
	}

	@Override
	public void atLose(AventurianManagerFacade m) {
		m.increaseWithoutPay(m.getDatabase().getSecondaryAttribute(Erschoepfungsschwelle.NAME));
	}
}
