package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class BesondererBesitz extends Property {

	private static final String DESCRIPTION = "Der Charakter erhält nach Absprache mit dem Meister einen mittelmächtigen Gegenstand.";
	static final String NAME = "Besonderer Besitz";
	
	public BesondererBesitz() {
		super(NAME, DESCRIPTION, 350);
	}
}
