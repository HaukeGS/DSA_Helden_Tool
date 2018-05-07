package skills.languages;

import aventurian.Aventurian;
import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.LANGUAGE)
public class Asdharia extends Language {
	static final String NAME = "Asdharia";
	static final String DESCRIPTION = "Das Asdharia ist die Sprache der alten Hochelfen. Auch heute noch kennen viele Elfen diese Sprache und singen Lieder oder wirken Zauber in ihr, doch aktiv gesprochen wird die Sprache kaum noch. \\nUm es über die zweite Stufe hinaus zu steigern ist die Sonderfertigkeit Sprachenkunde erforderlich. Dennoch können nur die Elfen mit ihrem Zweistimmigen Gesang die Sprache über die dritte Stufe hinaus steigern. \nDie Sprache wird in den 27 elfischen Schriftzeichen geschrieben, doch stark verschnörkelt, sodass das Lesen manchmal schwierig ist.";

	public Asdharia() {
		super(NAME, DESCRIPTION, 5, 75);
	}
	
	@Override
	public boolean isAllowed(Aventurian a) {
		if (getLevel() < 2)
			return true;
		if (getLevel() < 3 && a.hasSkill("Sprachenkunde"))
			return true;
		if (a.hasSkill("Zweistimmiger Gesang"))
			return true;
		return false;
	}

}