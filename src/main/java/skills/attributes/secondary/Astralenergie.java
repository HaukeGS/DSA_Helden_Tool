package skills.attributes.secondary;

import java.util.List;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;
import skills.attributes.primary.Charisma;
import skills.attributes.primary.Intuition;
import skills.attributes.primary.Mut;
import skills.attributes.primary.PrimaryAttribute;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class Astralenergie extends SecondaryAttribute {
	static final String NAME = "Astralenergie";

	public Astralenergie() {
		super(NAME, "");
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> a) {
		final int courage = getLevelOf(a, Mut.NAME);
		final int intuition = getLevelOf(a, Intuition.NAME);
		final int charisma = getLevelOf(a, Charisma.NAME);
		basisLevel = round((courage + intuition + charisma) / 2.0);
		maxLevel = round(charisma / 2.0);
	}

	@Override
	public int getUpgradeCosts() {
		return 50;
	}

	@Override
	public int getDowngradeRefund() {
		return 50;
	}

	@Override
	public int getTotalCosts() {
		return level * 50;
	}

	@Override
	protected boolean isAbleToIncrease(Aventurian a) {
		return true;
	}

	@Override
	public boolean isAllowedToDecrease() {
		return getLevel() > getMinLevel();
	}

}
