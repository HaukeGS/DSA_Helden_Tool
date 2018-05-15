package aventurian;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import database.Database;
import skills.languages.Language;

class LanguageAventurianManager extends BaseAventurianManager {

	private final Predicate<Language> IS_NATIVE_TONGUE = (Language l) -> l.isNativeTongue();

	private final BiPredicate<Aventurian, Language> EXCEEDS_MAX_SUM_AS_NATIVE_TONGUE = (Aventurian a,
			Language l) -> a.getLevelSumOfLanguages() + Math.min(l.getMaxLevel(), Language.NATIVE_TONGUE_LEVEL) > a
					.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE);

	private final Predicate<Aventurian> HAS_ALREADY_NATIVE_TONGUE = (Aventurian av) -> av.hasNativeTongue();

	public LanguageAventurianManager(Optional<Aventurian> a, Database db) {
		super(a, db);
	}

	void addLanguageAsNativeTongue(Language l) {
		if (!canAddAsNativeTongue(l))
			throw new IllegalStateException("requirements not met for adding " + l.getName() + " as native tongue");
		l.setNativeTongue(true);
		while (mustIncreaseNativeTongue(l))
			l.increase();
		add(l);
	}

	private boolean mustIncreaseNativeTongue(Language l) {
		return aventurian.map(av -> l.isAllowedToIncrease(av) && l.getLevel() < Language.NATIVE_TONGUE_LEVEL)
				.orElse(false);
	}

	boolean canAddAsNativeTongue(Language l) {
		return !aventurian.map(av -> HAS_SKILL.test(av, l)//
				|| IS_NOT_ALLOWED_TO_ADD.test(av, l)//
				|| EXCEEDS_MAX_SUM_AS_NATIVE_TONGUE.test(av, l)//
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
		while (l.isAllowedToDecrease()) {
			decreaseLanguage(l);
		}
		refund(l.getTotalCosts());
	}

	private void decreaseLanguageWithoutRefund(Language l) {
		while (l.getLevel() > Language.NATIVE_TONGUE_LEVEL)
			decreaseLanguage(l);
		while (l.isAllowedToDecrease())
			aventurian.ifPresent(av -> av.decreaseIncreasableSkill(l));
	}

	void decreaseLanguage(Language l) {
		if (!canDecrease(l))
			throw new IllegalStateException("requirements not met for decreasing " + l.getName());
		refund(l.getDowngradeRefund());
		aventurian.ifPresent(av -> av.decreaseIncreasableSkill(l));
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
				|| IS_NOT_ALLOWED_TO_ADD.test(av, l)).orElse(true);
	}

	void increaseLanguage(Language l) {
		if (!canIncrease(l))
			throw new IllegalStateException("requirements not met for increasing " + l.getName());
		pay(l.getUpgradeCosts());
		aventurian.ifPresent(av -> av.increaseIncreasableSkill(l));
	}

	boolean canIncrease(Language l) {
		return !aventurian.map(av -> HAS_NOT_SKILL.test(av, l)//
				|| IS_NOT_ALLOWED.test(av, l)//
				|| IS_NOT_INCREASABLE.test(av, l)).orElse(true);
	}

}
