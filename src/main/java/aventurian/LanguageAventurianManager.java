package aventurian;

import java.util.Optional;
import java.util.function.Predicate;

import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import database.Database;
import skills.languages.Language;

class LanguageAventurianManager extends BaseAventurianManager {
	
	private final Predicate<Language> IS_NATIVE_TONGUE = (Language l) -> l.isNativeTongue();
	private final Predicate<Aventurian> EXCEEDS_MAX_SUM_IN_LANGUAGES = (Aventurian a) -> a.getLanguages().stream().mapToInt(Language::getLevel).sum() >= a.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE);
	private final Predicate<Aventurian> HAS_ALREADY_NATIVE_TONGUE = (Aventurian av) -> av.hasNativeTongue();
	
	public LanguageAventurianManager(Optional<Aventurian> a, Database db) {
		super(a, db);
	}

	void addLanguageAsNativeTongue(Language l) {
		if (!canAddAsNativeTongue(l))
			throw new IllegalStateException("requirements not met for adding " + l.getName() + " as native tongue");
		while (l.isIncreasable() && l.getLevel() < Language.NATIVE_TONGUE_LEVEL)
			l.increase();
		l.setNativeTongue(true);
		add(l);
	}

	boolean canAddAsNativeTongue(Language l) {
		return !aventurian.map(av -> HAS_SKILL.test(av, l)//
				|| IS_NOT_ALLOWED.test(av, l)//
				|| IS_NATIVE_TONGUE.test(l)//
				|| HAS_ALREADY_NATIVE_TONGUE.test(av)).orElse(true);
	}

	void removeLanguage(Language l) {
		if (!canRemoveLanguage(l))
			throw new IllegalStateException("requirements not met for removing " + l.getName());
		if (l.isNativeTongue()) {
			l.setNativeTongue(false);
			decreaseLanguageWithoutRefund(l);
		} else {
			decreaseLanguageWithRefund(l);
		}
		remove(l);
	}

	boolean canRemoveLanguage(Language l) {
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, l)).orElse(true);
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
		if (!canDecrease(l))
			throw new IllegalStateException("requirements not met for decreasing " + l.getName());
		final int refund = l.getDowngradeRefund();
		l.decrease();
		refund(refund);
	}

	boolean canDecrease(Language l) {
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, l)//
				|| IS_NOT_DECREASABLE.test(l)).orElse(true);
	}

	void addLanguage(Language l) {
		if (!canAdd(l))
			throw new IllegalStateException("requirements not met for adding " + l.getName());
		add(l);
		pay(l.getTotalCosts());

	}

	boolean canAdd(Language l) {
		return !aventurian.map(av -> HAS_SKILL.test(av, l)//
				|| IS_NOT_ALLOWED.test(av, l)//
				|| CANNOT_PAY_TOTAL_COSTS.test(av, l)).orElse(true);
	}

	void increaseLanguage(Language l) {
		if (!canIncrease(l))
			throw new IllegalStateException("requirements not met for increasing " + l.getName());
		l.increase();
		pay(l.getUpgradeCosts());
	}

	boolean canIncrease(Language l) {
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, l)//
				|| IS_NOT_ALLOWED.test(av, l)//
				|| IS_NOT_INCREASABLE.test(l)//
				|| EXCEEDS_MAX_SUM_IN_LANGUAGES.test(av)//
				|| CANNOT_PAY_UPGRADE_COSTS.test(av, l)).orElse(true);
	}

}
