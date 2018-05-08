package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;


@InstantiableSkill(SkillType.PROPERTY)
public class Adlig extends Property {
	
//	mehrere Vorteile:
//		1. der Name ist eine Konstante und kann in anderen Skills einfach verwendet werden (keine Typos, nur eine Stelle zu �ndern), siehe REQ
//		2. Logik ist da, wo sie semantisch hingeh�rt: in die Skill-Definition und nicht irgendwo in der Database
//		3. So m�sste es sehr einfach sein, die einzelnen Skills abzutesten (gerade das REQ ist ja abzutestende Business-Logik!!)
//		4. die Database wird sehr sauber aussehen: keine komplexen Konstruktoren der einzelnen Skills mit endlosen Strings (das ist hier besser aber auch immer noch unsch�n)
//		5. Wenn wir super cool sind, nutzen wir Reflection in der Database, dann m�ssen wir nicht mal mehr properties.add(new Adlig_I()) machen -> advanced ;)

	private static final String DESCRIPTION = "Der Charakter ist adliger Abstammung und hat Anspruch auf einen entsprechenden Titel. In manchen Bereichen unterliegt er nicht der gew�hnlichen Gerichtsbarkeit. Generell wird man ihm mehr Respekt entgegeben bringen und ihm mehr Geh�r schenken. \nAllerdings hat er keine Aussicht darauf das Lehen seiner Eltern zu erben, da er �ltere Geschwister besitzt oder aus einem Anderen Grund keinen Anspruch auf das Lehen hat. \n Auf Stufe 1 ist er Kind eines Edelmannes oder einer Edelfrau, also eines Ritters oder eiens Junkers. \nAuf Stufe 2 ist er Kind eines mittleren Adligen, also eines Barons o.�. \nAuf Stufe 3 ist er Kind eines h�heren Adligen, also eines Grafen o.�.";
	static final String NAME = "Adlig";

	public Adlig() {
		super(NAME, DESCRIPTION, 250, EMPTY, EMPTY, 1, 3);
	}
	
	@Override
	public int getUpgradeCosts() {
		if (getLevel() >= 2)
			return 150;
		return 100;
	}
	
	@Override
	public int getDowngradeRefund() {
		if (getLevel() >= 3) 
			return 150;
		return 100;		
	}
	
	@Override
	public int getTotalCosts() {
		if (getLevel() == 3)
			return 500;
		if (getLevel() == 2)
			return 350;
		return 250;
	}
	
}
