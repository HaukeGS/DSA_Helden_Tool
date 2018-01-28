package aventurian;

import java.util.List;

public class RaceConfiguration {
	
	private final int hitPointsMod;
	private final int magicResistanceMod;
	private final List<String> skillNames;
	
	

	public RaceConfiguration(int hitPointsMod, int magicResistanceMod, List<String> skillNames) {
		this.hitPointsMod = hitPointsMod;
		this.magicResistanceMod = magicResistanceMod;
		this.skillNames = skillNames;
	}

	public int getHitPointsMod() {
		return hitPointsMod;
	}

	public int getMagicResistanceMod() {
		return magicResistanceMod;
	}

	public List<String> getSkillNames() {
		return skillNames;
	}
	
	

}
