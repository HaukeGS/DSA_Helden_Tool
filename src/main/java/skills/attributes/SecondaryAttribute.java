package skills.attributes;

import skills.IncreasableSkill;

public class SecondaryAttribute extends IncreasableSkill {
	private int mod;
	private int modBuy;
	private int maxBuy;

	public SecondaryAttribute(String name, String description, int minLevel,
			int maxLevel) {
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

	public void setBasis(int basis) {
		this.level = basis;
	}
	
	public void setMax(int max) {
    	if (max < 0) throw new IllegalArgumentException("Maximum cannot be less than zero!");
    	this.maxBuy = max;
    }

    public void increaseMod(int mod) {
        if (mod < 0) throw new IllegalArgumentException("Input must not be less than zero!");
        this.mod += mod;
    }

    public void decreaseMod(int mod) {
        if (mod < 0) throw new IllegalArgumentException("Input must not be less than zero!");
        this.mod -= mod;
    }

    public void increaseModBuy() {
        this.modBuy++;
    }

    public void decreaseModBuy() {
        this.modBuy--;
    }


}
