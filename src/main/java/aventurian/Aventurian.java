package aventurian;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import aventurian.SecondaryAttributes.SECONDARY_ATTRIBUTE;
import skills.BadProperty;
import skills.Language;
import skills.Property;
import skills.Skill;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Aventurian extends Observable {

	@XmlAttribute
	private String nameOfAventurian;
	@XmlAttribute
	private int adventurePoints;
	private final PrimaryAttributes primaryAttributes;
	private final SecondaryAttributes secondaryAttributes;

	private final List<Property> properties;
	private final List<BadProperty> badProperties;
	@XmlElementWrapper(name = "languages")
	private final List<Language> languages;
	
	private boolean isMage;
	private boolean isConsecrated;

	static final int MAX_ATTRIBUTES_SUM = 101;

	private Aventurian() {
		// only needed for JAXB
		this(0);
	}

	public Aventurian(String name, int ap) {
		this(name, ap, new PrimaryAttributes(), new SecondaryAttributes());
	}

	Aventurian(String name, int ap, PrimaryAttributes primary, SecondaryAttributes secondary) {
		this.nameOfAventurian = name;
		this.primaryAttributes = primary;
		this.secondaryAttributes = secondary;
		this.adventurePoints = ap;
		this.properties = new ArrayList<>();
		this.badProperties = new ArrayList<>();
		this.languages = new ArrayList<>();
		if (secondary != null)
			secondaryAttributes.updateValues(primaryAttributes);
	}

	Aventurian(int ap) {
		this("", ap, new PrimaryAttributes(), new SecondaryAttributes());
	}

	public int getAdventurePoints() {
		return adventurePoints;
	}

	void pay(int cost) {
		if (canPay(cost) && cost >= 0) {
			adventurePoints -= cost;
			notifyObserversAndSetChanged();
		} else
			throw new IllegalArgumentException("Cannot pay: " + cost);
	}

	void refund(int refund) {
		if (refund >= 0) {
			adventurePoints += refund;
			notifyObserversAndSetChanged();
		} else
			throw new IllegalArgumentException("Cannot refund negative amound: " + refund);
	}

	boolean canPay(int cost) {
		return cost <= adventurePoints;
	}

	public void setName(String name) {
		this.nameOfAventurian = name;
		notifyObserversAndSetChanged();
	}

	void add(Property p) {
		properties.add(p);
		p.gain(this);
		notifyObserversAndSetChanged();
	}

	void remove(Property p) {
		properties.remove(p);
		p.lose(this);
		notifyObserversAndSetChanged();
	}

	void add(BadProperty p) {
		badProperties.add(p);
		p.gain(this);
		notifyObserversAndSetChanged();
	}

	void remove(BadProperty p) {
		badProperties.remove(p);
		p.lose(this);
		notifyObserversAndSetChanged();
	}

	int getBadPropertySum() {
		return badProperties.stream().mapToInt(BadProperty::getLevel).sum();
	}

	int getPointsInAdvantages() {
		return properties.stream().filter((p) -> p.isAdvantage()).mapToInt(Property::getCost).sum();
	}

	int getPointsOutDisadvantages() {
		return properties.stream().filter((p) -> p.isDisadvantage()).mapToInt(Property::getCost).sum()
				+ badProperties.stream().mapToInt((p) -> p.getCost() * p.getLevel()).sum();
	}

	void add(Language l) {
		languages.add(l);
		l.gain(this);
		notifyObserversAndSetChanged();
	}

	void remove(Language l) {
		languages.remove(l);
		l.lose(this);
		notifyObserversAndSetChanged();
	}

	boolean hasSkill(Skill skill) {
		return Stream.of(badProperties, properties, languages).flatMap(Collection::stream)
				.anyMatch((s) -> s.equals(skill));
	}

	boolean hasSkill(String skill) {
		return Stream.of(badProperties, properties, languages).flatMap(Collection::stream)
				.anyMatch((s) -> s.equals(skill));
	}

	int getSumOfPrimaryAttributes() {
		return primaryAttributes.getSum();
	}

	public boolean isPrimaryAttributeIncreasable(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		return primaryAttributes.isIncreasable(a);
	}

	public boolean isPrimaryAttributeDecreasable(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		return primaryAttributes.isDecreasable(a);
	}

	public int getPrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		return primaryAttributes.getPrimaryAttribute(a);
	}

	public int getMaxOfPrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE a) {
		return primaryAttributes.getMaximumOfPrimaryAttribute(a);
	}

	public void increasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE attribute) {
		primaryAttributes.increase(attribute);
		secondaryAttributes.updateValues(primaryAttributes);
		notifyObserversAndSetChanged();
	}

	public int getSecondaryAttribute(SecondaryAttributes.SECONDARY_ATTRIBUTE a) {
		return secondaryAttributes.getValueOf(a);
	}

	void notifyObserversAndSetChanged() {
		setChanged();
		notifyObservers();
	}

	public void decrasePrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE attribute) {
		primaryAttributes.decrease(attribute);
		secondaryAttributes.updateValues(primaryAttributes);
		notifyObserversAndSetChanged();
	}

	void increaseMaximumOfPrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE attribute) {
		primaryAttributes.increaseMaximum(attribute);
		notifyObserversAndSetChanged();
	}

	void decreaseMaximumOfPrimaryAttribute(PrimaryAttributes.PRIMARY_ATTRIBUTE attribute) {
		primaryAttributes.decreaseMaximum(attribute);
		notifyObserversAndSetChanged();
	}

	public String getName() {
		return nameOfAventurian;
	}

	public List<Language> getLanguages() {
		return new ArrayList<>(languages);
	}

	public boolean hasNativeTongue() {
		return languages.stream().anyMatch((Language l) -> l.isNativeTongue());
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
}
