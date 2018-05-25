package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Altersresistenz extends Property {

	private static final String DESCRIPTION = "Normalerweise nur für Elfen und Zwerge.\r\n" + 
			"Der Charakter altert merklich langsamer. Sein Aussehen und seine Werte verändern sich dementsprechend anders.";
	final static String NAME = "Altersresistenz";
	
	public Altersresistenz() {
		super(NAME, DESCRIPTION, 50);
	}
}
