
package skills.languages;

import java.util.function.Predicate;

import aventurian.Aventurian;
import skills.IncreasableSkill;

public class Language extends IncreasableSkill {
	public static final int NATIVE_TONGUE_LEVEL = 4;
	private boolean isNativeTongue;

	public Language(String name, String description, Predicate<Aventurian> requirement, int maxLevel, int cost) {
		super(name, description, EMPTY, EMPTY, requirement, cost, 1, maxLevel);
		this.isNativeTongue = false;
	}



	public void setNativeTongue(boolean nativeTongue) {
		isNativeTongue = nativeTongue;
	}

	public boolean isNativeTongue() {
		return isNativeTongue;
	}

	@Override
	public boolean isDecreasable() {
		if (isNativeTongue())
			return level > NATIVE_TONGUE_LEVEL;
		return level > 1;
	}

}
