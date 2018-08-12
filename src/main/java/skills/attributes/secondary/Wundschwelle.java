package skills.attributes.secondary;

import java.util.List;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.primary.Konstitution;
import skills.attributes.primary.PrimaryAttribute;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class Wundschwelle extends SecondaryAttribute {
	public static final String NAME = "Wundschwelle";

	public Wundschwelle() {
		super(NAME, "");
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> a) {
		final int constitution = getLevelOf(a, Konstitution.NAME);
		basisLevel = round(constitution / 2.0);
	}

}
