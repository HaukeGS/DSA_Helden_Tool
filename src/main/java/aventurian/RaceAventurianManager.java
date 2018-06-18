package aventurian;

import database.Database;
import logging.Logger;
import skills.attributes.secondary.Lebenspunkte;
import skills.attributes.secondary.Magieresistenz;
import skills.attributes.secondary.SecondaryAttribute;

public class RaceAventurianManager extends BaseAventurianManager {

	public RaceAventurianManager(AventurianManagerFacade aventurianManagerFacade, Database db, Logger logger) {
		super(aventurianManagerFacade, db, logger);
	}

	void buyRaceMods(Race race) {
		applyMagicResistenceMod(race);

		applyHitPointsMod(race);

		database.getSkillsFor(race).forEach(aventurianManagerFacade::add);
	}

	private void applyHitPointsMod(Race race) {
		final int hitPointsMod = database.getHitPointsModFor(race);

		final SecondaryAttribute hitpoints = database.getSecondaryAttribute(Lebenspunkte.NAME);
		if (hitPointsMod > 0)
			for (int i = 0; i < hitPointsMod; i++)
				aventurianManagerFacade.increase(hitpoints);
		else
			for (int i = hitPointsMod; i < 0; i++)
				aventurianManagerFacade.decrease(hitpoints);
	}

	private void applyMagicResistenceMod(Race race) {
		final int magicResistanceMod = database.getMagicResistanceModFor(race);

		final SecondaryAttribute magicresistance = database.getSecondaryAttribute(Magieresistenz.NAME);
		if (magicResistanceMod > 0)
			for (int i = 0; i < magicResistanceMod; i++)
				aventurianManagerFacade.increase(magicresistance);
		else
			for (int i = magicResistanceMod; i < 0; i++)
				aventurianManagerFacade.decrease(magicresistance);
	}

}
