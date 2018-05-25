package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Ausruestungsvorteil extends Property {

	private static final String DESCRIPTION = "Der Charakter erhält pro Level mehr Ausrüstung im Wert von 15 Dukaten.";
	static final String NAME = "Ausrüstungsvorteil";
	
	public Ausruestungsvorteil() {
		super(NAME, DESCRIPTION, 50, 1, 100);
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
}
