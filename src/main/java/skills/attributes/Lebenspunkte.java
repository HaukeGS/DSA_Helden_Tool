package skills.attributes;

import java.util.List;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.SECONDARY_ATTRIBUTE)
public class Lebenspunkte extends SecondaryAttribute {
	static final String NAME = "Lebenspunkte";

	public Lebenspunkte() {
		super(NAME, "");
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> a) {
		final int constitution = getLevelOf(a, Konstitution.NAME);
		final int strength = getLevelOf(a, Koerperkraft.NAME);
		basisLevel = round((constitution * 2 + strength) / 2.0);
		maxLevel = round(constitution / 2.0);

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

}
