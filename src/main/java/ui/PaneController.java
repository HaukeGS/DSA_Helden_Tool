package ui;

import aventurian.Aventurian;
import aventurian.AventurianManagerFacade;
import database.Database;

public abstract class PaneController {

	protected AventurianManagerFacade m;
	protected Database db;


	void init(AventurianManagerFacade manager, Database database) {
		m = manager;
		db = database;
		initControllerSpecificStuff();
	}

	abstract void initControllerSpecificStuff();

	abstract void update(Aventurian updatedAventurian);

}
