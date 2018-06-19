package skills.talents;

import aventurian.LevelCostCalculator.Column;
import skills.attributes.primary.Agilitaet;
import skills.attributes.primary.Koerperkraft;
import skills.attributes.primary.Konstitution;

public class Athletik extends Talent {

	static final String NAME = "Athletik";

	public Athletik() {
		super(NAME, "", 0, 25, Column.D, Agilitaet.NAME, Konstitution.NAME, Koerperkraft.NAME);
	}

}
