package skills.attributes;

import java.util.List;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class Lebenspunkte extends SecondaryAttribute {
	static final String NAME = "Lebenspunkte";

	public Lebenspunkte() {
		super(NAME, "", minLevel, maxLevel);
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> a) {
		final int constitution = getLevelOf(a, Konstitution.NAME);
		final int strength = getLevelOf(a, Koerperkraft.NAME);
		level = round((constitution * 2 + strength) / 2.0);
		maxBoughtLevelModifier = round(constitution / 2.0);

	}

}
