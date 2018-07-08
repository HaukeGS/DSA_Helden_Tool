package aventurian;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.stream.Stream;

import skills.IncreasableSkill;
import skills.Skill;
import skills.attributes.primary.PrimaryAttribute;
import skills.attributes.secondary.SecondaryAttribute;
import skills.languages.Language;
import skills.properties.BadProperty;
import skills.properties.Property;

public class Aventurian extends Observable {

	private String nameOfAventurian;
	private int adventurePoints;

	private List<Skill> allSkills;

	private boolean isMage;
	private boolean isConsecrated;

	private final Race race;

	Aventurian(String name, int ap, Race r) {
		this.nameOfAventurian = name;
		this.adventurePoints = ap;
		this.race = r;
		this.allSkills = new ArrayList<>();
	}

	public int getAdventurePoints() {
		return adventurePoints;
	}

	public int getPrimaryAttribute(String attributeName) {
		return getStreamOfPrimaryAttributes().filter(pa -> pa.getName().equals(attributeName)).findFirst()
				.map(PrimaryAttribute::getLevel)
				.orElseThrow(() -> new IllegalStateException("could not find primary attribute " + attributeName));
	}

	public Optional<Property> getProperty(String name) {
		return getStreamOfProperties().filter(prop -> prop.getName().equals(name)).findFirst();
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
			throw new IllegalArgumentException("Cannot refund negative amount: " + refund);
	}

	void setName(String name) {
		this.nameOfAventurian = name;
		setChangedAndNotifyObservers(getDependingSkill(allSkills));
	}

	void add(Skill s) {
		allSkills.add(s);
		setChangedAndNotifyObservers(getDependingSkill(allSkills));
	}

	void remove(Skill s) {
		allSkills.remove(s);
		setChangedAndNotifyObservers(getDependingSkill(allSkills));
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
		return getStreamOfSkills().anyMatch(s -> s.getName().equals(skillName));
	}

	int getSumOfPrimaryAttributes() {
		return getStreamOfPrimaryAttributes().mapToInt(PrimaryAttribute::getLevel).sum();
	}

	public Race getRace() {
		return race;
	}

	private void setChangedAndNotifyObservers() {
		setChanged();
		notifyObservers();
	}

	private void setChangedAndNotifyObservers(Optional<Skill> s) {
		setChanged();
		notifyObservers(s.orElse(null));
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

	private Stream<PrimaryAttribute> getStreamOfPrimaryAttributes() {
		return getStreamOfSkills().filter(PrimaryAttribute.class::isInstance).map(PrimaryAttribute.class::cast);
	}

	private Stream<SecondaryAttribute> getStreamOfSecondaryAttributes() {
		return getStreamOfSkills().filter(SecondaryAttribute.class::isInstance).map(SecondaryAttribute.class::cast);
	}

	public List<PrimaryAttribute> getPrimaryAttributes() {
		return getStreamOfPrimaryAttributes().collect(toList());
	}

	public List<SecondaryAttribute> getSecondaryAttributes() {
		return getStreamOfSecondaryAttributes().collect(toList());
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
		return getStreamOfLanguages().mapToInt(Language::getLevel).sum();
	}

	public boolean isMage() {
		return isMage;
	}

	public boolean isConsecrated() {
		return isConsecrated;
	}

	public int getAPInAttributes() {
		return getStreamOfPrimaryAttributes().mapToInt(PrimaryAttribute::getTotalCosts).sum();
	}

	public List<Property> getAdvantages() {
		return getStreamOfProperties().filter(Property::isAdvantage).collect(toList());
	}

	public List<Property> getDisadvantages() {
		return getStreamOfProperties().filter(Property::isDisadvantage).collect(toList());
	}

	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
		setChangedAndNotifyObservers(getDependingSkill(allSkills));
	}

	void increaseSkill(IncreasableSkill s) {
		s.increase();
		updateSecondaryAttributes();
		setChangedAndNotifyObservers(getDependingSkill(allSkills));
	}

	public void increaseSkill(String name) {
		findIncreasableSkill(name).ifPresent(this::increaseSkill);
	}

	public void decreaseSkill(String name) {
		findIncreasableSkill(name).ifPresent(this::decreaseSkill);
	}

	private Optional<IncreasableSkill> findIncreasableSkill(String name) {
		return getStreamOfIncreasableSkills().filter(s -> s.getName().equals(name)).findFirst();
	}

	private Stream<IncreasableSkill> getStreamOfIncreasableSkills() {
		return getStreamOfSkills().filter(IncreasableSkill.class::isInstance).map(IncreasableSkill.class::cast);
	}

	void updateSecondaryAttributes() {
		getStreamOfSecondaryAttributes().forEach(ss -> ss.calculateBasis(getPrimaryAttributes()));
	}

	void decreaseSkill(IncreasableSkill s) {
		s.decrease();
		updateSecondaryAttributes();
		setChangedAndNotifyObservers(getDependingSkill(allSkills));
	}

	private Optional<Skill> getDependingSkill(List<Skill> listToSearch) {
		Collections.reverse(listToSearch);
		final List<Skill> temp = allSkills;
		allSkills = listToSearch;
		final Optional<Skill> dependingSkill = listToSearch.stream().filter(skill -> !skill.isAllowedToHave(this))
				.findFirst();
		allSkills = temp;
		Collections.reverse(listToSearch);
		return dependingSkill;
	}

	public int getMaximumOfPrimaryAttributes(String name1, String name2, String name3) {
		return Math.max(getPrimaryAttribute(name1), Math.max(getPrimaryAttribute(name2), getPrimaryAttribute(name3)));
	}

	List<Skill> getDependingSkillsForRemove(Skill toRemove) {
		final List<Skill> skillsToCheck = new ArrayList<>(allSkills);
		skillsToCheck.remove(toRemove);
		return getDependingSkills(new ArrayList<>(), skillsToCheck);
	}

	List<Skill> getDependingSkillsForAdd(Skill toAdd) {
		final List<Skill> skillsToCheck = new ArrayList<>(allSkills);
		skillsToCheck.add(toAdd);
		return getDependingSkills(new ArrayList<>(), skillsToCheck);
	}

	List<Skill> getDependingSkillsForDecrease(IncreasableSkill toDecrease) {
		final List<Skill> skillsToCheck = new ArrayList<>(allSkills);
		toDecrease.decrease();
		final List<Skill> dependingSkills = getDependingSkills(new ArrayList<>(), skillsToCheck);
		toDecrease.increase();
		return dependingSkills;
	}

	List<Skill> getDependingSkillsForIncrease(IncreasableSkill toIncrease) {
		final List<Skill> skillsToCheck = new ArrayList<>(allSkills);
		toIncrease.increase();
		final List<Skill> dependingSkills = getDependingSkills(new ArrayList<>(), skillsToCheck);
		toIncrease.decrease();
		return dependingSkills;
	}

	private List<Skill> getDependingSkills(List<Skill> dependingSkills, List<Skill> skillsToCheck) {
		final Optional<Skill> dependingSkill = getDependingSkill(skillsToCheck);
		if (!dependingSkill.isPresent())
			return dependingSkills;
		dependingSkills.add(dependingSkill.get());
		skillsToCheck.remove(dependingSkill.get());
		return getDependingSkills(dependingSkills, skillsToCheck);

	}
}
