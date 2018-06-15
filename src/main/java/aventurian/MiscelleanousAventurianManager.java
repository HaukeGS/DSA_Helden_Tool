package aventurian;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import database.Database;
import logging.Logger;

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

}
