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
	
	protected final Predicate<IncreasableSkill> IS_NOT_INCREASABLE = (IncreasableSkill s) -> !s.isIncreasable();
	protected final Predicate<Optional<Aventurian>> IS_NOT_PRESENT = (Optional<Aventurian> av) -> !av.isPresent();
	protected final BiPredicate<Aventurian,IncreasableSkill> IS_NOT_ALLOWED = (Aventurian av,IncreasableSkill s) -> !s.isAllowed(av);
	protected final BiPredicate<Aventurian, IncreasableSkill> CANNOT_PAY_TOTAL_COSTS = (Aventurian av, IncreasableSkill s) -> !av.canPay(s.getTotalCosts());
	protected final BiPredicate<Aventurian, IncreasableSkill> CANNOT_PAY_UPGRADE_COSTS = (Aventurian av, IncreasableSkill s) -> !av.canPay(s.getUpgradeCosts());
	protected final BiPredicate<Aventurian, IncreasableSkill> HAS_SKILL = (Aventurian av, IncreasableSkill s) -> av.hasSkill(s);
	protected final BiPredicate<Aventurian, IncreasableSkill> HAS_NOT_SKILL = (Aventurian av, IncreasableSkill s) -> HAS_SKILL.negate().test(av, s);
	

	BaseAventurianManager(Optional<Aventurian> a, Database db) {
		this.calculator = new LevelCostCalculator();
		this.aventurian = a;
		this.database = db;

	}

	protected void changeAventurian(Optional<Aventurian> a) {
		this.aventurian = a;
	}

	protected final boolean canPay(int cost) {
		return aventurian.map(a -> a.canPay(cost)).orElse(false);
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
	
	protected final void checkForBasicDecreasableRequirements(IncreasableSkill s) {
		if (!aventurian.isPresent())
			throw new IllegalStateException("No Aventurian Present");
		if (!aventurian.get().hasSkill(s))
			throw new IllegalStateException("cannot decrease skill which is not owned: " + s.getName());
		if (!s.isDecreasable())
			throw new IllegalStateException("cannot further decrease level of " + s.getName());
	}
	
	protected final void checkForBasicIncreasableRequirements(IncreasableSkill s) {
		checkForBasicRequirements(s);
		if (!aventurian.get().hasSkill(s))
			throw new IllegalStateException("cannot increase skill which is not owned: " + s.getName());
		if (!s.isIncreasable())
			throw new IllegalStateException("cannot further increase level of " + s.getName());
	}
	
	protected final void checkForBasicRequirements(IncreasableSkill s) {
		if (!aventurian.isPresent())
			throw new IllegalStateException("No Aventurian Present");
		if (!s.isAllowed(aventurian.get()))
			throw new IllegalStateException("requirements not met " + s.getName());
		if (!aventurian.get().canPay(s.getTotalCosts()))
			throw new IllegalStateException("cannot pay for " + s.getName());
	}
}
