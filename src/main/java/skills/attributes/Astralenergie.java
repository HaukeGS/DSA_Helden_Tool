package skills.attributes;

import java.util.List;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class Astralenergie extends SecondaryAttribute {
	static final String NAME = "Astralenergie";

	public Astralenergie() {
		super(NAME, "", minLevel, maxLevel);
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> a) {
		final int courage = getLevelOf(a, Mut.NAME);
		final int intuition = getLevelOf(a, Intuition.NAME);
		final int charisma = getLevelOf(a, Charisma.NAME);
		level = round((courage + intuition + charisma) / 2.0);
		maxBoughtLevelModifier = round(charisma / 2.0);
	}

}
