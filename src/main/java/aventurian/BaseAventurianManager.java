package aventurian;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import database.Database;
import skills.IncreasableSkill;
import skills.Skill;

abstract class BaseAventurianManager {

	protected final Database database;
	protected Optional<Aventurian> aventurian;
	protected final LevelCostCalculator calculator;

	protected final static BiPredicate<Aventurian, IncreasableSkill> IS_NOT_INCREASABLE = (a,
			s) -> !s.isAllowedToIncrease(a);
	protected final static Predicate<IncreasableSkill> IS_NOT_DECREASABLE = s -> !s.isAllowedToDecrease();
	protected final static BiPredicate<Aventurian, IncreasableSkill> IS_NOT_ALLOWED_TO_HAVE = (av,
			s) -> !s.isAllowedToHave(av);
	protected final static BiPredicate<Aventurian, IncreasableSkill> IS_NOT_ALLOWED_TO_ADD = (av,
			s) -> !s.isAllowedToAdd(av);

	protected final static BiPredicate<Aventurian, IncreasableSkill> HAS_SKILL = (av, s) -> av.hasSkill(s);
	protected final static BiPredicate<Aventurian, IncreasableSkill> HAS_NOT_SKILL = (av, s) -> !av.hasSkill(s);

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

	protected void decrease(IncreasableSkill s) {
		aventurian.ifPresent(av -> av.decreaseSkill(s));
	}

	protected void increase(IncreasableSkill s) {
		aventurian.ifPresent(av -> av.increaseSkill(s));
	}

}
