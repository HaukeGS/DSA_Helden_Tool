package aventurian;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import aventurian.LevelCostCalculator.COLUMN;
import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import skills.BadProperty;
import skills.Language;
import skills.Property;
import skills.Skill;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Aventurian extends Observable {

	private final LevelCostCalculator calculator;

	@XmlAttribute
	private String nameOfAventurian;
	@XmlAttribute
	private int adventurePoints;
	private final PrimaryAttributes primaryAttributes;
	private final SecondaryAttributes secondaryAttributes;

	@XmlElementWrapper(name = "languages")

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
		if (canPay(cost) && cost >= 0) {
			adventurePoints -= cost;
			setChangedAndNotifyObservers();
		} else
			throw new IllegalArgumentException("Cannot pay: " + cost);
	}

	void refund(int refund) {
		if (refund >= 0) {
			adventurePoints += refund;
			setChangedAndNotifyObservers();
		} else
			throw new IllegalArgumentException("Cannot refund negative amound: " + refund);
	}

	boolean canPay(int cost) {
		return cost <= adventurePoints;
	}

	public void setName(String name) {
		this.nameOfAventurian = name;
		setChangedAndNotifyObservers();
	}

	void add(Skill s) {
		allSkills.add(s);
		s.gain(this);
		setChangedAndNotifyObservers();
	}

	void remove(Skill s) {
		allSkills.remove(s);
		s.lose(this);
		setChangedAndNotifyObservers();
	}

	int getBadPropertySum() {
		return getStreamOfBadProperties().mapToInt(BadProperty::getLevel).sum();
	}

	int getPointsInAdvantages() {
		return getStreamOfProperties().filter((p) -> p.isAdvantage()).mapToInt(Property::getLearningCosts).sum();
	}

	int getPointsOutDisadvantages() {
		return getStreamOfProperties().filter((p) -> p.isDisadvantage()).mapToInt(Property::getLearningCosts).sum()
				+ getStreamOfBadProperties().mapToInt((p) -> p.getLearningCosts() * p.getLevel()).sum();
	}

	boolean hasSkill(Skill skill) {
		return allSkills.stream().anyMatch((s) -> s.equals(skill));
	}
	
	public boolean hasSkill(String skillName) {
		return allSkills.stream().anyMatch((s) -> s.getName().equals(skillName));
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
		setChangedAndNotifyObservers();
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

	public void decrasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE attribute) {
		primaryAttributes.decrease(attribute);
		secondaryAttributes.updateValues(primaryAttributes);
		setChangedAndNotifyObservers();
	}

	void increaseMaximumOfPrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE attribute) {
		primaryAttributes.increaseMaximum(attribute);
		setChangedAndNotifyObservers();
	}

	void decreaseMaximumOfPrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE attribute) {
		primaryAttributes.decreaseMaximum(attribute);
		setChangedAndNotifyObservers();
	}

	public String getName() {
		return nameOfAventurian;
	}

	public List<Language> getLanguages() {
		return getStreamOfLanguages().collect(toList());
	}

	private Stream<Language> getStreamOfLanguages() {
		return allSkills.stream().filter(Language.class::isInstance).map(Language.class::cast);
	}

	private Stream<Property> getStreamOfProperties() {
		return allSkills.stream().filter(p -> Property.class.isInstance(p))
				.map(Property.class::cast);
	}

	private Stream<BadProperty> getStreamOfBadProperties() {
		return allSkills.stream().filter(BadProperty.class::isInstance).map(BadProperty.class::cast);
	}

	public boolean hasNativeTongue() {
		return getStreamOfLanguages().anyMatch((Language l) -> l.isNativeTongue());
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
		setChangedAndNotifyObservers();
	}
}
