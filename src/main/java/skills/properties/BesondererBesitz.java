package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class BesondererBesitz extends Property {

	private static final String DESCRIPTION = "Der Charakter erh�lt nach Absprache mit dem Meister einen mittelm�chtigen Gegenstand.";
	static final String NAME = "Besonderer Besitz";
	
	public BesondererBesitz() {
		super(NAME, DESCRIPTION, 350);
	}
}
