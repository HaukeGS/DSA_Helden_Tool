package ui;

import static ui.NavigationPaneController.PAGES.ATTRIBUTES;
import static ui.NavigationPaneController.PAGES.ATTRIBUTES_2;
import static ui.NavigationPaneController.PAGES.LANGUAGES;
import static ui.NavigationPaneController.PAGES.PROPERTIES;

import java.io.IOException;

import aventurian.AventurianManager;
import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logging.Logger;
import ui.NavigationPaneController.PAGES;

public class Starter extends Application {
	private MainController mainController;

	private Database db;
	private AventurianManager aventurianManager;

	private Logger logger;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void init() {
		db = new Database();
		logger = new Logger();
		aventurianManager = new AventurianManager(db, logger);
	}

	@Override
	public void start(Stage stage) throws Exception {
		final Parent root = loadMainPane();

		loadPage(LANGUAGES, "/languages.fxml");
		loadPage(ATTRIBUTES, "/attributes.fxml");
		loadPage(PROPERTIES, "/propertyPane.fxml");
		loadPage(ATTRIBUTES_2, "/attributes2.fxml");

		mainController.init(aventurianManager, db, logger);

		final Scene scene = new Scene(root);
		scene.getStylesheets().add(ui.Starter.class.getResource("/log-view.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}

	private Parent loadMainPane() throws IOException {
		final FXMLLoader loader = new FXMLLoader(ui.Starter.class.getResource("/main.fxml"));
		final Parent root = loader.load();
		mainController = loader.getController();
		return root;
	}

	private void loadPage(PAGES p, String fxml) throws IOException {
		final FXMLLoader l = new FXMLLoader(ui.Starter.class.getResource(fxml));
		final Parent pane = l.load();
		final PaneController controller = l.getController();
		mainController.addLoadedPage(p, controller, pane);
	}

}
