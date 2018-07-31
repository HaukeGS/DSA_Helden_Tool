package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Daemmerungssicht extends Property {

	private static final String DESCRIPTION = "Charaktere mit Dämmerungssicht halbieren die Abzüge durch fehlendes Licht.\nCharaktere mit Nachtsicht ignorieren 6 Stufen fehlendes Licht.\nBeides wirkt nicht bei absoluter Dunkelheit.";
	static final String NAME = "Dämmerungssicht";
	
	public Daemmerungssicht() {
		super(NAME, DESCRIPTION, 250, 1, 2);
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
}
