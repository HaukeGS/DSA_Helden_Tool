package skills.attributes.secondary;

import java.util.List;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.primary.Intelligenz;
import skills.attributes.primary.Konstitution;
import skills.attributes.primary.Mut;
import skills.attributes.primary.PrimaryAttribute;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class Magieresistenz extends SecondaryAttribute {

	static final String NAME = "Magieresistenz";

	public Magieresistenz() {
		super(NAME, "");
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> primaryAttributes) {
		final int intelligence = get(primaryAttributes, Intelligenz.NAME).getLevel();
		final int constitution = get(primaryAttributes, Konstitution.NAME).getLevel();
		final int courage = get(primaryAttributes, Mut.NAME).getLevel();

		basisLevel = round((courage + intelligence + constitution) / 5.0);
		maxLevel = round(courage / 2.0);
	}

}
