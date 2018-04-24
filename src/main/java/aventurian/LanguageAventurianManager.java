package aventurian;

import java.util.Optional;

import database.Database;
import skills.Language;

class LanguageAventurianManager extends BaseAventurianManager {

	public LanguageAventurianManager(Optional<Aventurian> a, Database db) {
		super(a, db);
	}

	void addLanguageAsNativeTongue(Language l) {
		aventurian.ifPresent(av -> {
			checkForBasicRequirements(l);
			if (av.hasSkill(l))
				throw new IllegalStateException("has already skill " + l.getName());
			if (l.isNativeTongue())
				throw new IllegalStateException("language is already native tongue" + l.getName());
			while (l.isIncreasable() && l.getLevel() < Language.NATIVE_TONGUE_LEVEL)
					l.increase();
				l.setNativeTongue(true);
				av.add(l);
		});
	}

	void removeLanguage(Language l) {
		aventurian.ifPresent(av -> {
			if (!av.hasSkill(l))
				throw new IllegalStateException("cannot remove skill " + l.getName());
			if (l.isNativeTongue()) {
				l.setNativeTongue(false);
				decreaseLanguageWithoutRefund(l);
			} else {
				decreaseLanguageWithRefund(l);
			}
			av.remove(l);			
		});
	}

	private void decreaseLanguageWithRefund(Language l) {
		while (l.isDecreasable()) {
			decreaseLanguage(l);
		}
		refund(l.getLearningCosts());
	}

	private void decreaseLanguageWithoutRefund(Language l) {
		while (l.getLevel() > Language.NATIVE_TONGUE_LEVEL)
			decreaseLanguage(l);
		while (l.isDecreasable())
			l.decrease();
	}

	void decreaseLanguage(Language l) {
		aventurian.ifPresent(av -> {
			if (!l.isDecreasable())
				throw new IllegalStateException("cannot further decrease level of " + l.getName());
			if (!av.hasSkill(l))
				throw new IllegalStateException("cannot decrease skill which is not owned: " + l.getName());
			final int refund = l.getDowngradeRefund();
			l.decrease();
			refund(refund);			
		});
	}

	void addLanguage(Language l) {
		aventurian.ifPresent(av -> {
			if (av.hasSkill(l))
				throw new IllegalStateException("has already skill " + l.getName());
			final int cost = l.getLearningCosts();
			if (canPay(cost) && l.isAllowed(av)) {
				av.add(l);
				pay(cost);
			}			
		});
	}

	void increaseLanguage(Language l) {
		aventurian.ifPresent(av -> {
			if (!l.isIncreasable())
				throw new IllegalStateException("cannot further increase level of " + l.getName());
			if (!av.hasSkill(l))
				throw new IllegalStateException("cannot increase skill " + l.getName());
			final int cost = l.getUpgradeCosts();
			if (canPay(cost) && l.isAllowed(av) && l.isIncreasable()) {
				l.increase();
				pay(cost);
			}			
		});
	}

}
