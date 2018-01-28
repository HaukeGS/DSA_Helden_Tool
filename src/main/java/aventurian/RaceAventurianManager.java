package aventurian;

import java.util.Optional;

import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import database.Database;

public class RaceAventurianManager extends BaseAventurianManager {
	
	PropertyAventurianManager propertyManager;

	public RaceAventurianManager(Optional<Aventurian> a, Database db, PropertyAventurianManager propertyManager) {
		super(a, db);
		this.propertyManager = propertyManager;
	}

	void buyRaceMods(Race race) {
		final int hitPointsMod = database.getRaceConfiguration(race).getHitPointsMod();
		if (hitPointsMod > 0)
			increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, hitPointsMod);
		else
			decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, Math.abs(hitPointsMod));

		final int magicResistanceMod = database.getRaceConfiguration(race).getMagicResistanceMod();
		if (magicResistanceMod > 0)
			increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, magicResistanceMod);
		else
			decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, Math.abs(magicResistanceMod));

		database.getRaceSkills(race).forEach(propertyManager::addProperty);
	}

	private void increaseSecondaryAttribute(SECONDARY_ATTRIBUTE a, int mod) {
		aventurian.ifPresent(av -> {
			final int cost = av.getSecondaryAttributeCost(a);
			if (av.canPay(cost)) {
				av.increaseSecondaryAttribute(a, mod);
				pay(cost * mod);
			}
		});
	}

	private void decreaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a, int mod) {
		aventurian.ifPresent(av -> {
			final int cost = av.getSecondaryAttributeCost(a);
			av.decreaseSecondaryAttribute(a, mod);
			refund(cost * mod);
		});
	}

}
