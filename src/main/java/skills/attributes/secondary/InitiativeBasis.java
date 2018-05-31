package skills.attributes.secondary;

import java.util.List;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.primary.Agilitaet;
import skills.attributes.primary.Intuition;
import skills.attributes.primary.Mut;
import skills.attributes.primary.PrimaryAttribute;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class InitiativeBasis extends SecondaryAttribute {
	static final String NAME = "INI-Basiswert";

	public InitiativeBasis() {
		super(NAME, "");
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> a) {
		final int courage = getLevelOf(a, Mut.NAME);
		final int intuition = getLevelOf(a, Intuition.NAME);
		final int agility = getLevelOf(a, Agilitaet.NAME);
		basisLevel = round((courage + intuition + agility) / 5.0);
	}

}
