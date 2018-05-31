package skills.attributes.secondary;

import java.util.List;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.primary.Geschicklichkeit;
import skills.attributes.primary.Intuition;
import skills.attributes.primary.Koerperkraft;
import skills.attributes.primary.PrimaryAttribute;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class FernkampfBasis extends SecondaryAttribute {
	static final String NAME = "FK-Basiswert";

	public FernkampfBasis() {
		super(NAME, "");
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> a) {
		final int strength = getLevelOf(a, Koerperkraft.NAME);
		final int intuition = getLevelOf(a, Intuition.NAME);
		final int dexterity = getLevelOf(a, Geschicklichkeit.NAME);
		basisLevel = round((strength + intuition + dexterity) / 5.0);

	}

}
