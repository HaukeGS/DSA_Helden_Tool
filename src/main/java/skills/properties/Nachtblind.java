package skills.properties;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Nachtblind extends Property {

	private static final String DESCRIPTION = "Der Charakter erhält die doppelten Mali durch fehlendes Licht gemäß der Tabelle. Allerdings trotzdem nur bis zum Maximum entsprechend der Abzüge von Absoluter Dunkelheit.";
	static final String NAME = "Nachtblind";

	public Nachtblind() {
		super(NAME, DESCRIPTION, -500);
	}

	@Override
	public boolean isAllowedToHave(Aventurian a) {
		return !a.hasSkill(Daemmerungssicht.NAME);
	}	
	
}
