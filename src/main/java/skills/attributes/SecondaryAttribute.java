package skills.attributes;

import java.util.List;

import aventurian.Aventurian;
import skills.IncreasableSkill;

public abstract class SecondaryAttribute extends IncreasableSkill {

	static final int maxLevel = 100;
	static final int minLevel = 0;
	private int levelModifier;
	private int boughtLevelModifier;
	protected int maxBoughtLevelModifier;

	public SecondaryAttribute(String name, String description, int minLevel, int maxLevel) {
		super(name, description, minLevel, maxLevel);
	}

	@Override
	public int getUpgradeCosts() {
		return 0;
	}

	@Override
	public int getDowngradeRefund() {
		return 0;
	}

	@Override
	public int getTotalCosts() {
		return 0;
	}

	@Override
	public int getLevel() {
		return level + levelModifier + boughtLevelModifier;
	}

	public abstract void calculateBasis(List<PrimaryAttribute> a);

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

	public void increaseModBuy() {
		this.boughtLevelModifier++;
	}

	public void decreaseModBuy() {
		this.boughtLevelModifier--;
	}

	@Override
	protected boolean isAbleToIncrease(Aventurian a) {
		return true;
	}

	boolean isIncreasableByBuy() {
		return boughtLevelModifier < maxBoughtLevelModifier;
	}

	protected boolean isDecreasableByBuy() {
		return false;
	}

	protected static PrimaryAttribute get(List<PrimaryAttribute> a, String name) {
		return a.stream().filter(s -> name.equals(s.getName())).findFirst()
				.orElseThrow(() -> new IllegalStateException("cannot happen"));
	}

	protected static int getLevelOf(List<PrimaryAttribute> a, String name) {
		return get(a, name).getLevel();
	}

	protected static int round(double d) {
		return (int) Math.round(d);
	}

}
