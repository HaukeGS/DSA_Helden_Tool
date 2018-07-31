package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Flink extends Property {

	private static final String DESCRIPTION = "Der Ausweichen-Wert ist um 1 erhöht. Außerdem sind Athletik-Proben für Geschwindigkeit (Verfolgung, Wettrennen etc.) um 3 Punkte erleichtert. Diese Punkte können auch als TaP* überbehalten werden.";
	static final String NAME = "Flink";

	public Flink() {
		super(NAME, DESCRIPTION, 300);
	}

}
