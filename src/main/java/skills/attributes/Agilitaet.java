package skills.attributes;

import aventurian.LevelCostCalculator.Column;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PRIMARY_ATTRIBUTE)
public class Agilitaet extends PrimaryAttribute {
	public static final String NAME = "Agilität";

	public Agilitaet() {
		super(NAME, "", Column.H);
	}

}
