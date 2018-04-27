package skills.properties;

import java.util.function.Predicate;

import aventurian.Aventurian;

public class Adlig_III extends Property {

	private static final String DESCRIPTION = "Der Charakter ist adliger Abstammung und hat Anspruch auf einen entsprechenden Titel. In manchen Bereichen unterliegt er nicht der gewöhnlichen Gerichtsbarkeit. Generell wird man ihm mehr Respekt entgegeben bringen und ihm mehr Gehör schenken. \nAllerdings hat er keine Aussicht darauf das Lehen seiner Eltern zu erben, da er ältere Geschwister besitzt oder aus einem Anderen Grund keinen Anspruch auf das Lehen hat. \n Er ist Kind eines Edelmannes oder einer Edelfrau, also eines Ritters oder eiens Junkers. \n Kann nicht mit 'Adlig II' oder 'Adlig II' gewählt werden!";
	static final String NAME = "Adlig I";
	static final Predicate<Aventurian> REQ = (Aventurian a) -> !a.hasSkill(Adlig_I.NAME)
			&& !a.hasSkill(Adlig_II.NAME);

	public Adlig_III() {
		super(NAME, DESCRIPTION, 500, EMPTY, EMPTY, REQ);
	}
}
