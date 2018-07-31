package skills.properties;

import skills.InstantiableSkill;
import skills.InstantiableSkill.SkillType;

@InstantiableSkill(SkillType.PROPERTY)
public class Ausruestungsvorteil extends Property {

	private static final String DESCRIPTION = "Mit diesem Vorteil kann sich ein Charakter von Anfang an bessere Ausr�stung ausw�hlen. Dabei zahlt er f�r insgesamt 15 Dukaten an Geldwert 50 AP.\r\n" + 
			"Dieser Vorteil kann auch von mehreren Spielern kombiniert werden. M�chten zwei Spieler zum Beispiel eine besondere Kutsche im Wert von 300 Dukaten haben, so k�nnen beide 500 AP (500 / 50 = 10. 10 * 15 Dukaten = 150 Dukaten) f�r Ausr�stungsvorteil zahlen und besitzen die Kutsche dann gemeinsam.";
	static final String NAME = "Ausr�stungsvorteil";
	
	public Ausruestungsvorteil() {
		super(NAME, DESCRIPTION, 50, 1, 100);
	}
	
	@Override
	public int getUpgradeCosts() {
		return getLearningCosts();
	}
	
	@Override
	public int getDowngradeRefund() {
		return getLearningCosts();
	}
	
	@Override
	public int getTotalCosts() {
		return getLevel() * getLearningCosts();
	}
}
