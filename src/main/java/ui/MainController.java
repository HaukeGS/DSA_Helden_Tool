package ui;

import static ui.NavigationPaneController.PAGES.ATTRIBUTES;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import aventurian.Aventurian;
import aventurian.AventurianManager;
import database.Database;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import logging.Logger;
import ui.NavigationPaneController.PAGES;

public class MainController extends PaneController implements Observer {

	private final Map<PAGES, PaneController> centerControllers;
	private final Map<PAGES, Parent> centerPages;

	public MainController() {
		centerControllers = new HashMap<>();
		centerPages = new HashMap<>();
	}

	@FXML
	TopController topController;

	@FXML
	MenuController menuController;
	@FXML
	NavigationPaneController navigationPaneController;

	@FXML
	OverviewPaneController overviewPaneController;

	@FXML
	LogPaneController logPaneController;

	@FXML
	Pane centerPane;

	public void init(AventurianManager manager, Database db, Logger logger) {
		this.m = manager;
		navigationPaneController.init(this);
		logPaneController.init(logger);
		topController.init(manager, db);
		menuController.init(manager, db);
		overviewPaneController.init(manager, db);
		centerControllers.values().forEach(c -> c.init(manager, db));
		m.registerObserver(this);

		changeTo(ATTRIBUTES);
	}

	void addLoadedPage(PAGES p, PaneController c, Parent page) {
		centerPages.put(p, page);
		centerControllers.put(p, c);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Aventurian) {
			update((Aventurian) o);
		}
	}

	void changeTo(PAGES page) {
		centerPane.getChildren().clear();
		centerPane.getChildren().add(centerPages.get(page));

	}

	PaneController getControllerOfPage(PAGES p) {
		return centerControllers.get(p);
	}

	@Override
	void update(Aventurian updatedAventurian) {
		navigationPaneController.update(updatedAventurian);
		topController.update(updatedAventurian);
		overviewPaneController.update(updatedAventurian);
		menuController.update(updatedAventurian);
		logPaneController.update(updatedAventurian);
		centerControllers.values().forEach(c -> c.update(updatedAventurian));

	}

	@Override
	void initControllerSpecificStuff() {
		// TODO Auto-generated method stub

	}

}
