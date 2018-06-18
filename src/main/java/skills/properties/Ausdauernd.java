package skills.properties;

import aventurian.Aventurian;
import aventurian.AventurianManagerFacade;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.secondary.Erschoepfungsschwelle;
import skills.attributes.secondary.SecondaryAttribute;

@InstantiableSkill(SkillType.PROPERTY)
public class Ausdauernd extends Property {

	private static final String DESCRIPTION = "Erhöht die Erschöpfungsschwelle um 1. \n Kann nicht zusammen mit 'Kurzatmig' gewählt werden.";
	static final String NAME = "Ausdauernd";

	public Ausdauernd() {
		super(NAME, DESCRIPTION, 150);
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return !a.hasSkill(Kurzatmig.NAME);
	}

	@Override
	public void atGain(AventurianManagerFacade a) {
		final SecondaryAttribute s = a.getDatabase().getSecondaryAttributes().stream()
				.filter(sa -> sa.getName().equals(Erschoepfungsschwelle.NAME)).findFirst()
				.orElseThrow(() -> new IllegalStateException(""));
		a.increaseSecondaryAttributeWithoutPay(s);
	}

	@Override
	public void atLose(AventurianManagerFacade a) {
		final SecondaryAttribute s = a.getDatabase().getSecondaryAttributes().stream()
				.filter(sa -> sa.getName().equals(Erschoepfungsschwelle.NAME)).findFirst()
				.orElseThrow(() -> new IllegalStateException(""));
		a.decreaseSecondaryAttributeWithoutRefund(s);
	}

}
