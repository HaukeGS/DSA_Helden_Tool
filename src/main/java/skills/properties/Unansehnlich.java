package skills.properties;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Unansehnlich extends Property {

	private static final String DESCRIPTION = "Erschwert passende Proben auf Interaktionstalente um 2/5 Punkte.";
	static final String NAME = "Unansehnlich";

	public Unansehnlich() {
		super(NAME, DESCRIPTION, -250, 1, 2);
	}
	
	@Override
	public int getUpgradeCosts() {
		return 500;
	}
	
	@Override
	public int getDowngradeRefund() {
		return 500;
	}
	
	@Override
	public int getTotalCosts() {
		return getLevel() * 500 - getLearningCosts();
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return !a.hasSkill(GutAussehend.NAME);
	}

}
