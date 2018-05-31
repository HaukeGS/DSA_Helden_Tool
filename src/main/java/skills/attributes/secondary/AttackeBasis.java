package skills.attributes.secondary;

import java.util.List;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.primary.Agilitaet;
import skills.attributes.primary.Koerperkraft;
import skills.attributes.primary.Mut;
import skills.attributes.primary.PrimaryAttribute;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class AttackeBasis extends SecondaryAttribute {
	static final String NAME = "AT-Basiswert";

	public AttackeBasis() {
		super(NAME, "");
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> a) {
		final int courage = getLevelOf(a, Mut.NAME);
		final int agility = getLevelOf(a, Agilitaet.NAME);
		final int strength = getLevelOf(a, Koerperkraft.NAME);
		basisLevel = round((courage + agility + strength) / 5.0);
	}

}
