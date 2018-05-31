package skills.attributes.secondary;

import java.util.List;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.primary.Agilitaet;
import skills.attributes.primary.Intuition;
import skills.attributes.primary.Koerperkraft;
import skills.attributes.primary.PrimaryAttribute;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class ParadeBasis extends SecondaryAttribute {
	static final String NAME = "PA-Basiswert";

	public ParadeBasis() {
		super(NAME, "");
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> a) {
		final int agility = getLevelOf(a, Agilitaet.NAME);
		final int intuition = getLevelOf(a, Intuition.NAME);
		final int strength = getLevelOf(a, Koerperkraft.NAME);
		basisLevel = round((agility + intuition + strength) / 5.0);
	}

}
