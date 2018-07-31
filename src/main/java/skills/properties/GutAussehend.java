package skills.properties;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class GutAussehend extends Property {

	private static final String DESCRIPTION = "Erleichtert passende Proben auf Interaktionstalente um 2/4/6 Punkte.";
	static final String NAME = "Gutes Aussehen";

	public GutAussehend() {
		super(NAME, DESCRIPTION, 250, 1, 3);
	}
	
	@Override
	public int getUpgradeCosts() {
		return getLearningCosts();
	}
	
	@Override
	public int getDowngradeRefund() {
		return getLearningCosts();
	}
	
	@Override
	public int getTotalCosts() {
		return getLevel() * getLearningCosts();
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return !a.hasSkill(Unansehnlich.NAME);
	}

}
