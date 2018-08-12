package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Feenfreund extends Property {

	private static final String DESCRIPTION = "Feen helfen h�ufiger und ohne eine Gegenleistung zu erwarten.";
	static final String NAME = "Feenfreund";

	public Feenfreund() {
		super(NAME, DESCRIPTION, 150);
	}

}
