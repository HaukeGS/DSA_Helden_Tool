package aventurian;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.stream.Stream;

import aventurian.LevelCostCalculator.COLUMN;
import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import skills.LinearIncreasableSkill;
import skills.Skill;
import skills.languages.Language;
import skills.properties.BadProperty;
import skills.properties.Property;

public class Aventurian extends Observable {

	private final LevelCostCalculator calculator;

	private String nameOfAventurian;
	private int adventurePoints;
	private final PrimaryAttributes primaryAttributes;
	private final SecondaryAttributes secondaryAttributes;

	private final List<Skill> allSkills;

	private boolean isMage;
	private boolean isConsecrated;

	private final Race race;

	public static final int MAX_ATTRIBUTES_SUM = 101;

	public Aventurian(String name, int ap, Race r) {
		this(name, ap, new PrimaryAttributes(), new SecondaryAttributes(), r);
	}

	Aventurian(String name, int ap, PrimaryAttributes primary, SecondaryAttributes secondary, Race r) {
		this.nameOfAventurian = name;
		this.primaryAttributes = primary;
		this.secondaryAttributes = secondary;
		this.adventurePoints = ap;
		this.race = r;
		this.allSkills = new ArrayList<>();
		if (secondary != null)
			secondaryAttributes.updateValues(primaryAttributes);
		this.calculator = new LevelCostCalculator();
	}

	public int getAdventurePoints() {
		return adventurePoints;
	}

	void pay(int cost) {
		if (cost >= 0) {
			adventurePoints -= cost;
			setChangedAndNotifyObservers();
		} else
			throw new IllegalArgumentException("Cannot pay negative costs: " + cost);
	}

	void refund(int refund) {
		if (refund >= 0) {
			adventurePoints += refund;
			setChangedAndNotifyObservers();
		} else
			throw new IllegalArgumentException("Cannot refund negative amound: " + refund);
	}

	void setName(String name) {
		this.nameOfAventurian = name;
		setChangedAndNotifyObservers(getSkillToRemove());
	}

	void add(Skill s) {
		allSkills.add(s);
		s.atGain(this);
		setChangedAndNotifyObservers(getSkillToRemove());
	}

	public int getMaximumOf(PRIMARY_ATTRIBUTE... a) {
		return Stream.of(a).mapToInt(pA -> primaryAttributes.getPrimaryAttribute(pA)).max()
				.orElseThrow(() -> new IllegalArgumentException());

	}

	void remove(Skill s) {
		allSkills.remove(s);
		s.atLose(this);
		// when a skill is removed, it might have been a requirement for other skills ->
		// notify observers about first skill which must be removed, too
		setChangedAndNotifyObservers(getSkillToRemove());
	}

	int getBadPropertySum() {
		return getStreamOfBadProperties().mapToInt(BadProperty::getLevel).sum();
	}

	int getPointsInAdvantages() {
		return getStreamOfProperties().filter(Property::isAdvantage).mapToInt(Property::getLearningCosts).sum();
	}

	int getPointsOutDisadvantages() {
		return getStreamOfProperties().filter(Property::isDisadvantage).mapToInt(Property::getLearningCosts).sum()
				+ getStreamOfBadProperties().mapToInt((p) -> p.getLearningCosts() * p.getLevel()).sum();
	}

	boolean hasSkill(Skill skill) {
		return hasSkill(skill.getName());
	}

	public boolean hasSkill(String skillName) {
		return getStreamOfSkills().anyMatch((s) -> s.getName().equals(skillName));
	}

	public int getSumOfPrimaryAttributes() {
		return primaryAttributes.getSum();
	}

	public boolean isPrimaryAttributeIncreasable(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		return primaryAttributes.isIncreasable(a);
	}

	public boolean isPrimaryAttributeDecreasable(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		return primaryAttributes.isDecreasable(a);
	}

	public Race getRace() {
		return race;
	}

	public int getPrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		return primaryAttributes.getPrimaryAttribute(a);
	}

	public int getMaxOfPrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		return primaryAttributes.getMaximumOfPrimaryAttribute(a);
	}

	public void increasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		primaryAttributes.increase(a);
		secondaryAttributes.updateValues(primaryAttributes);
		setChangedAndNotifyObservers(getSkillToRemove());
	}

	public boolean isSecondaryAttributeIncreasableByBuy(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		return secondaryAttributes.isIncreasableByBuy(a);
	}

	public boolean isSecondaryAttributeDecreasableByBuy(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		return secondaryAttributes.isDecreasableByBuy(a);
	}

	public void increaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a, int mod) {
		secondaryAttributes.increaseMod(a, mod);
	}

	public void decreaseSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a, int mod) {
		secondaryAttributes.decreaseMod(a, mod);
	}

	public void increaseSecondaryAttributeByBuy(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		secondaryAttributes.increaseModBuy(a);
	}

	public void decreaseSecondaryAttributeByBuy(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		secondaryAttributes.decreaseModBuy(a);
	}

	int getSecondaryAttributeCost(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		return secondaryAttributes.getCost(a);
	}

	public int getSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		return secondaryAttributes.getValueOf(a);
	}

	private void setChangedAndNotifyObservers() {
		setChanged();
		notifyObservers();
	}

	private void setChangedAndNotifyObservers(Optional<Skill> s) {
		setChanged();
		notifyObservers(s.orElse(null));
	}

	public void decrasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE attribute) {
		primaryAttributes.decrease(attribute);
		secondaryAttributes.updateValues(primaryAttributes);
		
		setChangedAndNotifyObservers(getSkillToRemove());
	}

	void increaseMaximumOfPrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE attribute) {
		primaryAttributes.increaseMaximum(attribute);
		setChangedAndNotifyObservers(getSkillToRemove());
	}

	void decreaseMaximumOfPrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE attribute) {
		primaryAttributes.decreaseMaximum(attribute);
		setChangedAndNotifyObservers(getSkillToRemove());
	}

	public String getName() {
		return nameOfAventurian;
	}

	public List<Language> getLanguages() {
		return getStreamOfLanguages().collect(toList());
	}

	private Stream<Skill> getStreamOfSkills() {
		return allSkills.stream().sorted();
	}

	private Stream<Language> getStreamOfLanguages() {
		return getStreamOfSkills().filter(Language.class::isInstance).map(Language.class::cast);
	}

	private Stream<Property> getStreamOfProperties() {
		return getStreamOfSkills().filter(Property.class::isInstance).map(Property.class::cast);
	}

	private Stream<BadProperty> getStreamOfBadProperties() {
		return getStreamOfSkills().filter(BadProperty.class::isInstance).map(BadProperty.class::cast);
	}

	public boolean hasNativeTongue() {
		return getStreamOfLanguages().anyMatch((Language l) -> l.isNativeTongue());
	}

	public int getLevelSumOfLanguages() {
		return getLanguages().stream().mapToInt(Language::getLevel).sum();
	}

	public boolean isMage() {
		return isMage;
	}

	public boolean isConsecrated() {
		return isConsecrated;
	}

	public boolean isPrimaryAttributesLowerThanThreshhold() {
		return getSumOfPrimaryAttributes() < MAX_ATTRIBUTES_SUM;
	}

	public int getAPinAttributes() {
		int sum = 0;
		for (final PRIMARY_ATTRIBUTE a : PRIMARY_ATTRIBUTE.values()) {
			sum += calculator.getCost(8, primaryAttributes.getPrimaryAttribute(a), COLUMN.H);
		}
		return sum;
	}

	public List<Property> getAdvantages() {
		return getStreamOfProperties().filter((p) -> p.isAdvantage()).collect(toList());
	}

	public List<Property> getDisadvantages() {
		return getStreamOfProperties().filter((p) -> p.isDisadvantage()).collect(toList());

	}

	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
		setChangedAndNotifyObservers(getSkillToRemove());
	}

	void increaseIncreasableSkill(LinearIncreasableSkill s) {
		s.increase();
		setChangedAndNotifyObservers(getSkillToRemove());
	}

	void decreaseIncreasableSkill(LinearIncreasableSkill s) {
		s.decrease();
		setChangedAndNotifyObservers(getSkillToRemove());
	}

	private Optional<Skill> getSkillToRemove() {
		Collections.reverse(allSkills);
		final Optional<Skill> toRemove = allSkills.stream().filter(skill -> !skill.isAllowedToHave(this)).findFirst();
		Collections.reverse(allSkills);
		return toRemove;
	}
}
