package ui;

import static ui.NavigationPaneController.PAGES.ATTRIBUTES;
import static ui.NavigationPaneController.PAGES.LANGUAGES;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.mockito.Mock;
import org.testfx.framework.junit.ApplicationTest;

import aventurian.Aventurian;
import aventurian.AventurianManager;
import database.Database;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.NavigationPaneController.PAGES;

/**
 * This is the base class for UI Unit Tests. Every testcase must extend it. This
 * class starts up the actual GUI and provides mocks that can be configured and
 * used by actual ui tests. <br>
 * Have a look at {@link LanguagePaneTest} or {@link AttributePaneTest}.
 * 
 * @author Jonas
 *
 */
public abstract class BaseGuiTest extends ApplicationTest {

	protected MainController mainController;
	@Mock
	protected AventurianManager mockedAventurianManager;

	@Mock
	protected Aventurian mockedAventurian;

	@Mock
	protected Database mockedDatabase;

	@Override
	public void init() {
		setUpMocks();
	}

	@Override
	public void start(Stage stage) throws Exception {
		final Parent root = loadMainPane();

		loadPage(LANGUAGES, "/languages.fxml");
		loadPage(ATTRIBUTES, "/attributes.fxml");

		mainController.init(mockedAventurianManager, mockedDatabase);
		mockedAventurian.addObserver(mainController);
		mainController.update(mockedAventurian, null);

		stage.setScene(new Scene(root));
		stage.show();
		/*
		 * Do not forget to put the GUI in front of windows. Otherwise, the robots may
		 * interact with another window, the one in front of all the windows...
		 */
		stage.toFront();
	}

	private Parent loadMainPane() throws IOException {
		final FXMLLoader loader = new FXMLLoader(ui.Main.class.getResource("/main.fxml"));
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

	public <T extends Node> T find(final String query) {

		// TestFX provides many operations to retrieve elements from the loaded GUI.
		return lookup(query).query();
	}

	public <T extends Node> Set<T> findAll(final String query) {

		// TestFX provides many operations to retrieve elements from the loaded GUI.
		return lookup(query).queryAll();
	}

	private static void waitForRunLater() throws InterruptedException {
		final Semaphore semaphore = new Semaphore(0);
		Platform.runLater(() -> semaphore.release());
		semaphore.acquire();
		Thread.sleep(100);

	}

	/**
	 * Call to update the GUI with {@link #mockedAventurian} whose behaviour you
	 * have changed/specified via Mocktio.when().
	 */
	protected final void updateGui() {
		try {
			Platform.runLater(() -> {
				mainController.update(mockedAventurian);
			});
			waitForRunLater();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Implement to configure the behaviour of the mocks the actual testcase relies
	 * on. Typically, you would setup {@link #mockedDatabase} and
	 * {@link #mockedAventurian} (if needed by all tests) and verify on {@link #mockedAventurianManager}.
	 * <br>
	 * Have a look at {@link LanguagePaneTest}.
	 */
	abstract void setUpMocks();

}
