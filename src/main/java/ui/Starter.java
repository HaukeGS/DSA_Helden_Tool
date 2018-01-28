package ui;

import static ui.NavigationPaneController.PAGES.ATTRIBUTES;
import static ui.NavigationPaneController.PAGES.LANGUAGES;

import java.io.IOException;

import aventurian.AventurianManager;
import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.NavigationPaneController.PAGES;

public class Starter extends Application {
	private MainController mainController;

	private Database db;
	private AventurianManager aventurianManager;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void init() {
		db = new Database();
		aventurianManager = new AventurianManager(db);
	}

	@Override
	public void start(Stage stage) throws Exception {
		final Parent root = loadMainPane();

		loadPage(LANGUAGES, "/languages.fxml");
		loadPage(ATTRIBUTES, "/attributes.fxml");

		mainController.init(aventurianManager, db);

		final Scene scene = new Scene(root);
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
		final FXMLLoader l = new FXMLLoader(ui.MainController.class.getResource(fxml));
		final Parent pane = l.load();
		final PaneController controller = l.getController();
		mainController.addLoadedPage(p, controller, pane);
	}

}
