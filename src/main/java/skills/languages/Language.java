
package skills.languages;

import aventurian.Aventurian;
import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import skills.LinearIncreasableSkill;

public class Language extends LinearIncreasableSkill {
	public static final int NATIVE_TONGUE_LEVEL = 4;
	private boolean isNativeTongue;

	public Language(String name, String description, int maxLevel, int cost) {
		super(name, description, cost, 1, maxLevel);
		this.isNativeTongue = false;
	}

	public void setNativeTongue(boolean nativeTongue) {
		isNativeTongue = nativeTongue;
	}

	public boolean isNativeTongue() {
		return isNativeTongue;
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return a.getLevelSumOfLanguages() <= a.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE);
	}

	@Override
	public boolean isAllowedToAdd(Aventurian a) {
		return a.getLevelSumOfLanguages() < a.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE);
	}
	
	@Override
	protected boolean isAbleToIncrease(Aventurian a) {
		return a.getLevelSumOfLanguages() < a.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE);
	}

	@Override
	public boolean isAllowedToDecrease() {
		if (isNativeTongue())
			return level > NATIVE_TONGUE_LEVEL;
		return super.isAllowedToDecrease();
	}

}
