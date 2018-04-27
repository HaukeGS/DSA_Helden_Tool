package skills.properties;

import java.util.function.Predicate;

import aventurian.Aventurian;

public class Adlig_I extends Property {
	
//	mehrere Vorteile:
//		1. der Name ist eine Konstante und kann in anderen Skills einfach verwendet werden (keine Typos, nur eine Stelle zu ändern), siehe REQ
//		2. Logik ist da, wo sie semantisch hingehört: in die Skill-Definition und nicht irgendwo in der Database
//		3. So müsste es sehr einfach sein, die einzelnen Skills abzutesten (gerade das REQ ist ja abzutestende Business-Logik!!)
//		4. die Database wird sehr sauber aussehen: keine komplexen Konstruktoren der einzelnen Skills mit endlosen Strings (das ist hier besser aber auch immer noch unschön)
//		5. Wenn wir super cool sind, nutzen wir Reflection in der Database, dann müssen wir nicht mal mehr properties.add(new Adlig_I()) machen -> advanced ;)

	private static final String DESCRIPTION = "Der Charakter ist adliger Abstammung und hat Anspruch auf einen entsprechenden Titel. In manchen Bereichen unterliegt er nicht der gewöhnlichen Gerichtsbarkeit. Generell wird man ihm mehr Respekt entgegeben bringen und ihm mehr Gehör schenken. \nAllerdings hat er keine Aussicht darauf das Lehen seiner Eltern zu erben, da er ältere Geschwister besitzt oder aus einem Anderen Grund keinen Anspruch auf das Lehen hat. \n Er ist Kind eines Edelmannes oder einer Edelfrau, also eines Ritters oder eiens Junkers. \n Kann nicht mit 'Adlig II' oder 'Adlig III' gewählt werden!";
	static final String NAME = "Adlig I";
	static final Predicate<Aventurian> REQ = (Aventurian a) -> !a.hasSkill(Adlig_II.NAME) && !a.hasSkill(Adlig_III.NAME);

	public Adlig_I() {
		super(NAME, DESCRIPTION, 250, EMPTY, EMPTY, REQ);
	}
}
