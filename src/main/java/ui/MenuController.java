package ui;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.Optional;
import java.util.prefs.Preferences;
import java.util.stream.Stream;

import javax.xml.bind.JAXBException;

import aventurian.Aventurian;
import aventurian.Race;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public class MenuController extends PaneController {
	@FXML
	Parent menu;

	String nameOfAventurian = "";

	public MenuController() {
		// TODO Auto-generated constructor stub
	}

	
	public void open() {
		final FileChooser fileChooser = new FileChooser();

		final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		final File file = fileChooser.showOpenDialog(menu.getScene().getWindow());

		if (file != null) {
			//m.loadAventurian(file);
		}
	}

	public void save() {
		if (getFilePath().isPresent())
			try {
				m.saveAventurian(getFilePath().map(File::new).get());
			} catch (final JAXBException e) {
				e.printStackTrace();
			}
		else
			saveAs();
	}

	public void saveAs() {
		try {
			final FileChooser fileChooser = new FileChooser();

			final FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
			fileChooser.getExtensionFilters().add(extFilter);
			fileChooser.setInitialFileName(nameOfAventurian);

			File file = fileChooser.showSaveDialog(menu.getScene().getWindow());

			if (file != null) {
				if (!file.getPath().endsWith(".xml")) {
					file = new File(file.getPath() + ".xml");
				}
				m.saveAventurian(file);
				saveFilePath(file);
			}
		} catch (final JAXBException e) {
			e.printStackTrace();
		}
	}

	private Optional<String> getFilePath() {
		final Preferences prefs = Preferences.userNodeForPackage(MainController.class);
		return Optional.ofNullable(prefs.get("filePath", null));
	}

	private void saveFilePath(File file) {
		final Preferences prefs = Preferences.userNodeForPackage(MainController.class);
		prefs.put("filePath", file.getPath());

	}

	public void newAventurian() {

		final Optional<Triple<String, Integer, Race>> result = createNewAventurianDialog().showAndWait();

		result.ifPresent(config -> {
			m.createNewAventurian(config.value1, config.value2, config.value3);
		});
	}

	private Dialog<Triple<String, Integer, Race>> createNewAventurianDialog() {
		// Create the custom dialog.
		final Dialog<Triple<String, Integer, Race>> dialog = new Dialog<>();

		dialog.setTitle("Erstelle neuen Aventurier");
		// dialog.setHeaderText("Lege folgende Einstellungen fest, um einen neuen
		// Aventurian zu erstellen:");

		// Set the button types.
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialog.getDialogPane().setId("newAventurianDialog");

		// Create custom content
		final GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 75, 10, 10));

		final TextField name = new TextField();
		name.setId("txtFieldName");
		name.setPromptText("Name");
		final TextField ap = new TextField("16500");
		ap.setId("txtFieldAp");
		ap.setPromptText("16500");
		final ComboBox<Race> race = new ComboBox<>();
		race.setMaxWidth(Double.MAX_VALUE);
		race.setItems(FXCollections.observableList(Stream.of(Race.values()).collect(toList())));
		race.getSelectionModel().select(0);

		grid.add(new Label("Name:"), 0, 0);
		grid.add(name, 1, 0);
		grid.add(new Label("Anfangs-AP:"), 0, 1);
		grid.add(ap, 1, 1);
		grid.add(new Label("Rasse:"), 0, 2);
		grid.add(race, 1, 2);

		// OK button is only enabled when input is valid
		final Node cancelButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		cancelButton.setId("btnCancel");
		final Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setDisable(true);
		okButton.setId("btnOk");

		name.textProperty().addListener((observable, oldValue, newValue) -> {
			final boolean isNumeric = ap.getText().chars().allMatch(Character::isDigit);
			okButton.setDisable(newValue.isEmpty() || ap.getText().isEmpty() || !isNumeric);
		});
		ap.textProperty().addListener((observable, oldValue, newValue) -> {
			final boolean isNumeric = newValue.chars().allMatch(Character::isDigit);
			okButton.setDisable(newValue.isEmpty() || name.getText().isEmpty() || !isNumeric);
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the name field by default.
		Platform.runLater(() -> name.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				return new Triple<>(name.getText(), Integer.parseInt(ap.getText()),
						race.getSelectionModel().getSelectedItem());
			}
			return null;
		});
		return dialog;
	}

	private static class Triple<A, B, C> {
		A value1;
		B value2;
		C value3;

		Triple(A value1, B value2, C value3) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
		}
	}

	@Override
	void initControllerSpecificStuff() {
		// nothing to do here
	}

	@Override
	void update(Aventurian updatedAventurian) {
		nameOfAventurian = updatedAventurian.getName();

	}

}
