package aventurian;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import database.Database;
import skills.LinearIncreasableSkill;
import skills.Skill;

abstract class BaseAventurianManager {

	protected final Database database;
	protected Optional<Aventurian> aventurian;
	protected final LevelCostCalculator calculator;

	protected final BiPredicate<Aventurian, LinearIncreasableSkill> IS_NOT_INCREASABLE = (Aventurian a,
			LinearIncreasableSkill s) -> !s.isAllowedToIncrease(a);
	protected final Predicate<LinearIncreasableSkill> IS_NOT_DECREASABLE = (LinearIncreasableSkill s) -> !s.isAllowedToDecrease();
	protected final Predicate<Optional<Aventurian>> IS_NOT_PRESENT = (Optional<Aventurian> av) -> !av.isPresent();
	protected final BiPredicate<Aventurian, LinearIncreasableSkill> IS_NOT_ALLOWED = (Aventurian av,
			LinearIncreasableSkill s) -> !s.isAllowedToHave(av);

	protected final BiPredicate<Aventurian, LinearIncreasableSkill> HAS_SKILL = (Aventurian av, LinearIncreasableSkill s) -> av
			.hasSkill(s);
	protected final BiPredicate<Aventurian, LinearIncreasableSkill> HAS_NOT_SKILL = (Aventurian av,
			LinearIncreasableSkill s) -> HAS_SKILL.negate().test(av, s);

	BaseAventurianManager(Optional<Aventurian> a, Database db) {
		this.calculator = new LevelCostCalculator();
		this.aventurian = a;
		this.database = db;

	}

	protected void changeAventurian(Optional<Aventurian> a) {
		this.aventurian = a;
	}

	protected final void pay(int cost) {
		aventurian.ifPresent(a -> a.pay(cost));
	}

	protected final void refund(int refund) {
		aventurian.ifPresent(a -> a.refund(refund));
	}

	protected final void remove(Skill s) {
		aventurian.ifPresent(a -> a.remove(s));
	}

	protected final void add(Skill s) {
		aventurian.ifPresent(a -> a.add(s));
	}

}
