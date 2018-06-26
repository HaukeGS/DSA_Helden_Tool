package aventurian;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import database.Database;
import logging.Logger;
import skills.IncreasableSkill;
import skills.Skill;

public class MiscelleanousAventurianManager extends BaseAventurianManager {
	private final List<Observer> observers;

	MiscelleanousAventurianManager(AventurianManagerFacade aventurianManagerFacade, Database db, Logger logger) {
		super(aventurianManagerFacade, db, logger);
		this.observers = new ArrayList<>();
	}

	void setName(String name) {
		aventurian.ifPresent(a -> a.setName(name));
	}

	void registerObserver(Observer o) {
		this.observers.add(o);
		addObserversToAventurian();
	}

	private void addObserversToAventurian() {
		aventurian.ifPresent(a -> a.deleteObservers());
		aventurian.ifPresent(a -> observers.forEach(a::addObserver));
	}

	@Override
	protected void changeAventurian(Aventurian a) {
		super.changeAventurian(a);
		addObserversToAventurian();
	}
	
	List<Skill> getDependingSkillsForRemove(Skill toRemove) {
		return aventurian.map(av -> av.getDependingSkillsForRemove(toRemove)).orElse(new ArrayList<>());
	}
	
	List<Skill> getDependingSkillsForAdd(Skill toAdd) {
		return aventurian.map(av -> av.getDependingSkillsForAdd(toAdd)).orElse(new ArrayList<>());
	}
	
	List<Skill> getDependingSkillsForDecrease(IncreasableSkill toDecrease) {
		return aventurian.map(av -> av.getDependingSkillsForDecrease(toDecrease)).orElse(new ArrayList<>());
	}
	
	List<Skill> getDependingSkillsForIncrease(IncreasableSkill toIncrease) {
		return aventurian.map(av -> av.getDependingSkillsForDecrease(toIncrease)).orElse(new ArrayList<>());
	}

}
