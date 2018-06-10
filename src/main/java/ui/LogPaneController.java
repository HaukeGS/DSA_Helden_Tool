package ui;

import aventurian.Aventurian;
import aventurian.AventurianManager;
import database.Database;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import logging.LogRecord;
import logging.Logger;

public class LogPaneController extends PaneController {
	private static final int MAX_ENTRIES = 10_000;
	@FXML
	ListView<LogRecord> logListView;
	@FXML
	VBox logPane;
	private final ObservableList<LogRecord> logItems = FXCollections.observableArrayList();
	private Logger logger;
	private final static PseudoClass debug = PseudoClass.getPseudoClass("debug");
	private final static PseudoClass info = PseudoClass.getPseudoClass("info");
	private final static PseudoClass warn = PseudoClass.getPseudoClass("warn");
	private final static PseudoClass error = PseudoClass.getPseudoClass("error");

	public LogPaneController() {
	}

	void init(AventurianManager manager, Database database, Logger log) {
		m = manager;
		db = database;
		this.logger = log;
		initControllerSpecificStuff();
	}

	@Override
	void initControllerSpecificStuff() {
		logListView.getStyleClass().add("log-view");
		logListView.setItems(logItems);

		final Timeline logTransfer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			logger.drainTo(logItems);

			if (logItems.size() > MAX_ENTRIES) {
				logItems.remove(0, logItems.size() - MAX_ENTRIES);
			}

			// if (tail.get()) {
			logListView.scrollTo(logItems.size());
			// }
		}));
		logTransfer.setCycleCount(Timeline.INDEFINITE);
		logTransfer.setRate(10);
		logTransfer.play();
		logListView.setCellFactory(list -> new LogItemListCell());
		logger.debug("hallo");
	}

	@Override
	void update(Aventurian updatedAventurian) {
		// TODO Auto-generated method stub

	}

	private class LogItemListCell extends ListCell<LogRecord> {
		// {
		// showTimestamp.addListener(observable -> updateItem(this.getItem(),
		// this.isEmpty()));
		// }

		@Override
		protected void updateItem(LogRecord item, boolean empty) {
			super.updateItem(item, empty);

			pseudoClassStateChanged(debug, false);
			pseudoClassStateChanged(info, false);
			pseudoClassStateChanged(warn, false);
			pseudoClassStateChanged(error, false);

			if (item == null || empty) {
				setText(null);
				return;
			}

			final String context = "";// (item.getContext() == null) ? "" : item.getContext() + " ";

			// if (showTimestamp.get()) {
			// final String timestamp = (item.getTimestamp() == null) ? ""
			// : timestampFormatter.format(item.getTimestamp()) + " ";
			// setText(timestamp + context + item.getMessage());
			// } else {
			setText(context + item.getMessage());
			// }

			switch (item.getLevel()) {
			case DEBUG:
				pseudoClassStateChanged(debug, true);
				break;

			case INFO:
				pseudoClassStateChanged(info, true);
				break;

			case WARN:
				pseudoClassStateChanged(warn, true);
				break;

			case ERROR:
				pseudoClassStateChanged(error, true);
				break;
			}
		}
	}
}
