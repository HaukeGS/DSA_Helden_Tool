package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Entfernungssinn extends Property {

	private static final String DESCRIPTION = "IN-Proben zum Einschätzen der Entfernung sind um bis zu 5 Punkte erleichtert.\n Beim Fernkampf sind die Proben auf Ziele, die mindestens 50 Schritt entfernt sind, nicht erschwert.";
	static final String NAME = "Entfernungssinn";

	public Entfernungssinn() {
		super(NAME, DESCRIPTION, 150);
	}

}
