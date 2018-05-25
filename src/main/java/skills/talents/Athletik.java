package skills.talents;

import aventurian.LevelCostCalculator.Column;
import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;

public class Athletik extends Talent {

	static final String NAME = "Athletik";

	public Athletik() {
		super(NAME, "", 0, 25, Column.D, PRIMARY_ATTRIBUTE.AGILITY, PRIMARY_ATTRIBUTE.CONSTITUTION,
				PRIMARY_ATTRIBUTE.STRENGTH);
	}

}
