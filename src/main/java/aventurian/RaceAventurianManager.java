package aventurian;

import java.util.Optional;

import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import database.Database;

public class RaceAventurianManager extends BaseAventurianManager {
	
	private final PropertyAventurianManager propertyManager;

	public RaceAventurianManager(Optional<Aventurian> a, Database db, PropertyAventurianManager propertyManager) {
		super(a, db);
		this.propertyManager = propertyManager;
	}

	void buyRaceMods(Race race) {
		final int hitPointsMod = database.getHitPointsModFor(race);
		if (hitPointsMod > 0)
			increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, hitPointsMod);
		else
			decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.HITPOINTS, Math.abs(hitPointsMod));

		final int magicResistanceMod = database.getMagicResistanceModFor(race);
		if (magicResistanceMod > 0)
			increaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, magicResistanceMod);
		else
			decreaseSecondaryAttribute(SECONDARY_ATTRIBUTE.MAGICRESISTANCE, Math.abs(magicResistanceMod));

		database.getSkillsFor(race).forEach(propertyManager::addProperty);
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
