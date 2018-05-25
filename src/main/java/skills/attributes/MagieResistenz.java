package skills.attributes;

import java.util.List;

public class MagieResistenz extends SecondaryAttribute {

	static final String NAME = "MagieResistenz";

	public MagieResistenz() {
		super(NAME, "", 0, 6);
	}

	@Override
	public void calculateBasis(List<PrimaryAttribute> primaryAttributes) {
		final int intelligence = get(primaryAttributes, Intelligenz.NAME).getLevel();
		final int constitution = get(primaryAttributes, Konstitution.NAME).getLevel();
		final int courage = get(primaryAttributes, Mut.NAME).getLevel();

		level = round((courage + intelligence + constitution) / 5.0);
	}

}
