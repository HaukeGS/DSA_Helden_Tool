package skills.attributes.secondary;

import java.util.List;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.primary.PrimaryAttribute;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class Karmaenergie extends SecondaryAttribute {

	static final String NAME = "Karmaenergie";

	public Karmaenergie() {
		super(NAME, "");
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> a) {
		basisLevel = 0;
	}

}
