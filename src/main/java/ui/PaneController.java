package ui;

import aventurian.Aventurian;
import aventurian.AventurianManager;
import database.Database;

public abstract class PaneController {

	protected AventurianManager m;
	protected Database db;

	public PaneController() {
		// TODO Auto-generated constructor stub
	}

	void init(AventurianManager manager, Database database) {
		m = manager;
		db = database;
		initControllerSpecificStuff();
	}

	abstract void initControllerSpecificStuff();

	abstract void update(Aventurian updatedAventurian);

}
