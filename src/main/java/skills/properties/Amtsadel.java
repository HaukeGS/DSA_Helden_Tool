package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Amtsadel extends Property {

	private static final String DESCRIPTION = "Der Charakter ist nicht von adliger Geburt, hat aber durch seinen Rang oder seinen Beruf einen ähnlichen, wenn auch nicht so ausgeprägten, Titel und damit vergleichbare Vorteile.";
	static final String NAME = "Amtsadel";

	public Amtsadel() {
		super(NAME, DESCRIPTION, 250, 1, 1);
	}
}
