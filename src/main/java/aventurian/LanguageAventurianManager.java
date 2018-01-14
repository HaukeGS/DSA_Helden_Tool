package aventurian;

import skills.Language;

class LanguageAventurianManager extends BaseAventurianManager {

	public LanguageAventurianManager(Aventurian a) {
		super(a);
	}

	void addLanguageAsNativeTongue(Language l) {
		if (aventurian.hasSkill(l))
			throw new IllegalStateException("has already skill " + l.getName());
		if (l.isNativeTongue())
			throw new IllegalStateException("language is already native tongue" + l.getName());
		if (l.isAllowed(aventurian)) {
			while (l.isIncreasable() && l.getLevel() < Language.NATIVE_TONGUE_LEVEL)
				l.increase();
			l.setNativeTongue(true);
			aventurian.add(l);
		}
	}

	void removeLanguage(Language l) {
		if (!aventurian.hasSkill(l))
			throw new IllegalStateException("cannot remove skill " + l.getName());
		if (l.isNativeTongue()) {
			l.setNativeTongue(false);
			decreaseLanguageWithoutRefund(l);
		} else {
			decreaseLanguageWithRefund(l);
		}
		aventurian.remove(l);
	}

	private void decreaseLanguageWithRefund(Language l) {
		while (l.isDecreasable()) {
			decreaseLanguage(l);
		}
		refund(l.getLearningCost());
	}

	private void decreaseLanguageWithoutRefund(Language l) {
		while (l.getLevel() > Language.NATIVE_TONGUE_LEVEL)
			decreaseLanguage(l);
		while (l.isDecreasable())
			l.decrease();
	}

	void decreaseLanguage(Language l) {
		if (!l.isDecreasable())
			throw new IllegalStateException("cannot further decrease level of " + l.getName());
		if (!aventurian.hasSkill(l))
			throw new IllegalStateException("cannot decrease skill which is not owned: " + l.getName());
		final int refund = l.getDowngradeRefund();
		l.decrease();
		refund(refund);
	}

	void addLanguage(Language l) {
		if (aventurian.hasSkill(l))
			throw new IllegalStateException("has already skill " + l.getName());
		final int cost = l.getLearningCost();
		if (canPay(cost) && l.isAllowed(aventurian)) {
			aventurian.add(l);
			pay(cost);
		}
	}

	void increaseLanguage(Language l) {
		if (!l.isIncreasable())
			throw new IllegalStateException("cannot further increase level of " + l.getName());
		if (!aventurian.hasSkill(l))
			throw new IllegalStateException("cannot increase skill " + l.getName());
		final int cost = l.getUpgradeCost();
		if (canPay(cost) && l.isAllowed(aventurian) && l.isIncreasable()) {
			l.increase();
			pay(cost);
		}
	}

}
