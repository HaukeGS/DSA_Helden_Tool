package skills.attributes.secondary;

import java.util.List;

import aventurian.Aventurian;
import skills.IncreasableSkill;
import skills.attributes.primary.PrimaryAttribute;

public abstract class SecondaryAttribute extends IncreasableSkill {

	protected int levelModifier;
	protected int basisLevel;

	public SecondaryAttribute(String name, String description) {
		super(name, description, 0, 0);
	}

	@Override
	public int getUpgradeCosts() {
		throw new IllegalStateException("does not support increasing: " + getName());
	}

	@Override
	public int getDowngradeRefund() {
		throw new IllegalStateException("does not support decreasing: " + getName());
	}

	@Override
	public int getTotalCosts() {
		return 0;
	}

	@Override
	public int getLevel() {
		return level + levelModifier + basisLevel;
	}

	public void increaseMod(int mod) {
		if (mod < 0)
			throw new IllegalArgumentException("Input must not be less than zero!");
		this.levelModifier += mod;
	}

	public void decreaseMod(int mod) {
		if (mod < 0)
			throw new IllegalArgumentException("Input must not be less than zero!");
		this.levelModifier -= mod;
	}

	public abstract void calculateBasis(List<PrimaryAttribute> a);

	@Override
	protected boolean isAbleToIncrease(Aventurian a) {
		return false;
	}

	@Override
	public boolean isAllowedToDecrease() {
		return false;
	}

	protected static PrimaryAttribute get(List<PrimaryAttribute> a, String name) {
		return a.stream().filter(s -> name.equals(s.getName())).findFirst()
				.orElseThrow(() -> new IllegalStateException("could not find primary attribute: " + name));
	}

	protected static int getLevelOf(List<PrimaryAttribute> a, String name) {
		return get(a, name).getLevel();
	}

	protected static int round(double d) {
		return (int) Math.round(d);
	}

}
