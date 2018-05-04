package ui;

import static java.util.stream.Collectors.toList;

import java.util.List;

import aventurian.Aventurian;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import skills.languages.Language;

public class LanguagePaneController extends PaneController {

	@FXML
	public ListView<Language> lvUnAssignedLanguages;

	@FXML
	public Button btnAssignLanguage;
	@FXML
	public Button btnUnAssignLanguage;
	@FXML
	public ListView<Language> lvAssignedLanguages;

	private boolean hasNativeTongue;

	public void assignLanguage() {
		final Language language = lvUnAssignedLanguages.getSelectionModel().getSelectedItem();
		m.addLanguage(language);
	}

	public void unassignLanguage() {
		final Language language = lvAssignedLanguages.getSelectionModel().getSelectedItem();
		m.removeLanguage(language);
	}

	@Override
	void update(Aventurian updatedAventurian) {
		hasNativeTongue = updatedAventurian.hasNativeTongue();
		lvAssignedLanguages.setItems(null); // forces listview to re-render all cells
		lvUnAssignedLanguages.setItems(null); // forces listview to re-render all cells
		final List<Language> assignedLanguages = updatedAventurian.getLanguages();
		lvAssignedLanguages.setItems(FXCollections.observableArrayList(assignedLanguages));
		final List<Language> unassignedLanguages = db.getLanguages().stream()
				.filter(l -> !assignedLanguages.contains(l)).collect(toList());
		lvUnAssignedLanguages.setItems(FXCollections.observableArrayList(unassignedLanguages));
	}

	@Override
	void initControllerSpecificStuff() {
		hasNativeTongue = false;
		prepareUnAssignedListView();
		prepareAssignedListView();

	}

	private void prepareUnAssignedListView() {
		lvUnAssignedLanguages.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					btnAssignLanguage.setDisable(newValue == null);
				});

		lvUnAssignedLanguages.setOnMouseClicked((MouseEvent click) -> {
			if (click.getClickCount() == 2 && !lvUnAssignedLanguages.getSelectionModel().isEmpty()) {
				final Language language = lvUnAssignedLanguages.getSelectionModel().getSelectedItem();
				m.addLanguage(language);
			}
		});
		lvUnAssignedLanguages.setCellFactory((ListView<Language> list) -> new UnassignedLanguageCell());
	}

	private void prepareAssignedListView() {
		lvAssignedLanguages.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			btnUnAssignLanguage.setDisable(newValue == null);
		});
		lvAssignedLanguages.setOnMouseClicked((MouseEvent click) -> {
			if (click.getClickCount() == 2 && !lvAssignedLanguages.getSelectionModel().isEmpty()) {
				final Language language = lvAssignedLanguages.getSelectionModel().getSelectedItem();
				m.removeLanguage(language);
			}
		});
		lvAssignedLanguages.setCellFactory((ListView<Language> list) -> new AssignedLanguageCell());
	}

	private class UnassignedLanguageCell extends ListCell<Language> {
		HBox hbox = new HBox();
		Label nameLabel = new Label("(empty)");
		Pane pane = new Pane();
		Button nativeTongueButton = new Button("Muttersprache");

		public UnassignedLanguageCell() {
			hbox.getChildren().addAll(nameLabel, pane, nativeTongueButton);
			hbox.setSpacing(5);
			hbox.setAlignment(Pos.CENTER);
			HBox.setHgrow(pane, Priority.ALWAYS);
			nativeTongueButton.setId("btnNativeTongue");
			nativeTongueButton.setOnAction((ActionEvent e) -> m.addLanguageAsNativeTongue(getItem()));
		}

		@Override
		protected void updateItem(Language item, boolean empty) {
			super.updateItem(item, empty);
			setText(null); // No text in label of super class
			if (empty || item == null) {
				setGraphic(null);
				setTooltip(null);
			} else {
				nameLabel.setText(item.toString());
				nativeTongueButton.setDisable(hasNativeTongue);
				setTooltip(new Tooltip(item.getDescription()));
				setGraphic(hbox);
			}
		}
	}

	private class AssignedLanguageCell extends ListCell<Language> {
		HBox hbox = new HBox();
		Label nameLabel = new Label("(empty)");
		Label levelLabel = new Label("1");
		Pane pane = new Pane();
		Button increaseButton = new Button("+");
		Button decreaseButton = new Button("-");

		public AssignedLanguageCell() {
			hbox.setSpacing(5);
			hbox.setAlignment(Pos.CENTER);
			HBox.setHgrow(pane, Priority.ALWAYS);
			decreaseButton.setPrefWidth(25);
			increaseButton.setPrefWidth(25);
			decreaseButton.setOnAction((ActionEvent e) -> m.decreaseLanguage(getItem()));
			increaseButton.setOnAction((ActionEvent e) -> m.increaseLanguage(getItem()));
		}

		@Override
		protected void updateItem(Language item, boolean empty) {
			super.updateItem(item, empty);
			setText(null); // No text in label of super class
			hbox.getChildren().clear();
			if (empty || item == null) {
				setGraphic(null);
				setTooltip(null);
			} else {
				nameLabel.setText(item.getName());
				levelLabel.setText(String.valueOf(item.getLevel()));
				increaseButton.setDisable(!item.isIncreasable());
				decreaseButton.setDisable(!item.isDecreasable());
				nameLabel.setTextFill(getItem().isNativeTongue() ? Color.BLUE : Color.BLACK);
				hbox.getChildren().addAll(nameLabel, pane, decreaseButton, levelLabel, increaseButton);
				setGraphic(hbox);
				setTooltip(new Tooltip(item.getDescription()));
			}
		}
	}
}
