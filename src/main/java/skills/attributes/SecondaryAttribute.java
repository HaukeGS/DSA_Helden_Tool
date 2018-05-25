package skills.attributes;

import java.util.List;

import skills.IncreasableSkill;

public abstract class SecondaryAttribute extends IncreasableSkill {
	private int mod;
	private int modBuy;
	private int maxBuy;

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
		return level + mod + modBuy;
	}

	public abstract void calculateBasis(List<PrimaryAttribute> a);

	public void setMax(int max) {
		if (max < 0)
			throw new IllegalArgumentException("Maximum cannot be less than zero!");
		this.maxBuy = max;
	}

	public void increaseMod(int mod) {
		if (mod < 0)
			throw new IllegalArgumentException("Input must not be less than zero!");
		this.mod += mod;
	}

	public void decreaseMod(int mod) {
		if (mod < 0)
			throw new IllegalArgumentException("Input must not be less than zero!");
		this.mod -= mod;
	}

	public void increaseModBuy() {
		this.modBuy++;
	}

	public void decreaseModBuy() {
		this.modBuy--;
	}

	protected static PrimaryAttribute get(List<PrimaryAttribute> a, String name) {
		return a.stream().filter(s -> name.equals(s.getName())).findFirst()
				.orElseThrow(() -> new IllegalStateException("cannot happen"));
	}

	protected static int round(double d) {
		return (int) Math.round(d);
	}

}
