package aventurian;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Optional;

import database.Database;
import logging.Logger;

public class MiscelleanousAventurianManager extends BaseAventurianManager {
	private final List<Observer> observers;

	MiscelleanousAventurianManager(Optional<Aventurian> a, Database db, Logger logger) {
		super(a, db, logger);
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
	protected void changeAventurian(Optional<Aventurian> a) {
		super.changeAventurian(a);
		addObserversToAventurian();
	}

}
