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
import skills.Property;

public class PropertyPaneController extends PaneController {

	@FXML
	ListView<Property> lvUnassignedAdvantages;
	@FXML
	ListView<Property> lvAssignedAdvantages;
	@FXML
	Button btnAssignAdvantage;
	@FXML
	Button btnUnassignAdvantage;

	@FXML
	ListView<Property> lvUnassignedDisadvantages;
	@FXML
	ListView<Property> lvAssignedDisadvantages;
	@FXML
	Button btnAssignDisadvantage;
	@FXML
	Button btnUnassignDisadvantage;

	public void assignAdvantage() {
		final Property p = lvUnassignedAdvantages.getSelectionModel().getSelectedItem();
		m.addProperty(p);
	}

	public void unassignAdvantage() {
		final Property p = lvAssignedAdvantages.getSelectionModel().getSelectedItem();
		m.removeProperty(p);
	}

	public void assignDisadvantage() {
		final Property p = lvUnassignedDisadvantages.getSelectionModel().getSelectedItem();
		m.addProperty(p);
	}

	public void unassignDisadvantage() {
		final Property p = lvAssignedDisadvantages.getSelectionModel().getSelectedItem();
		m.removeProperty(p);
	}

	@Override
	void initControllerSpecificStuff() {
		prepareUnassignedAdvantages();
		prepareAssignedAdvantages();

		prepareUnassignedDisadvantages();
		prepareAssignedDisadvantages();

	}

	private void prepareAssignedAdvantages() {
		lvAssignedAdvantages.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					btnUnassignAdvantage.setDisable(newValue == null);
				});
		lvAssignedAdvantages.setOnMouseClicked((MouseEvent click) -> {
			if (click.getClickCount() == 2 && !lvAssignedAdvantages.getSelectionModel().isEmpty()) {
				final Property p = lvAssignedAdvantages.getSelectionModel().getSelectedItem();
				m.removeProperty(p);
			}
		});
		lvAssignedAdvantages.setCellFactory((ListView<Property> list) -> new PropertyCell());
	}

	private void prepareUnassignedAdvantages() {
		lvUnassignedAdvantages.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					btnAssignAdvantage.setDisable(newValue == null);
				});

		lvUnassignedAdvantages.setOnMouseClicked((MouseEvent click) -> {
			if (click.getClickCount() == 2 && !lvUnassignedAdvantages.getSelectionModel().isEmpty()) {
				final Property p = lvUnassignedAdvantages.getSelectionModel().getSelectedItem();
				m.addProperty(p);
			}
		});
	}

	private void prepareAssignedDisadvantages() {
		lvAssignedDisadvantages.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					btnUnassignDisadvantage.setDisable(newValue == null);
				});
		lvAssignedDisadvantages.setOnMouseClicked((MouseEvent click) -> {
			if (click.getClickCount() == 2 && !lvAssignedDisadvantages.getSelectionModel().isEmpty()) {
				final Property p = lvAssignedDisadvantages.getSelectionModel().getSelectedItem();
				m.removeProperty(p);
			}
		});
		lvAssignedDisadvantages.setCellFactory((ListView<Property> list) -> new PropertyCell());
	}

	private void prepareUnassignedDisadvantages() {
		lvUnassignedDisadvantages.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					btnAssignDisadvantage.setDisable(newValue == null);
				});

		lvUnassignedDisadvantages.setOnMouseClicked((MouseEvent click) -> {
			if (click.getClickCount() == 2 && !lvUnassignedDisadvantages.getSelectionModel().isEmpty()) {
				final Property p = lvUnassignedDisadvantages.getSelectionModel().getSelectedItem();
				m.addProperty(p);
			}
		});
	}

	@Override
	void update(Aventurian updatedAventurian) {
		lvUnassignedAdvantages.setItems(null);
		lvAssignedAdvantages.setItems(null);
		lvUnassignedDisadvantages.setItems(null);
		lvAssignedDisadvantages.setItems(null);

		final List<Property> assignedAdvantages = updatedAventurian.getAdvantages();
		lvAssignedAdvantages.setItems(FXCollections.observableArrayList(assignedAdvantages));
		final List<Property> unassignedAdvantages = db.getAdvantages().stream()
				.filter(p -> !assignedAdvantages.contains(p)).collect(toList());
		lvUnassignedAdvantages.setItems(FXCollections.observableArrayList(unassignedAdvantages));

		final List<Property> assignedDisadvantages = updatedAventurian.getDisadvantages();
		lvAssignedDisadvantages.setItems(FXCollections.observableArrayList(assignedDisadvantages));
		final List<Property> unassignedDisadvantages = db.getDisadvantages().stream()
				.filter(p -> !assignedDisadvantages.contains(p)).collect(toList());
		lvUnassignedDisadvantages.setItems(FXCollections.observableArrayList(unassignedDisadvantages));
	}

	private class PropertyCell extends ListCell<Property> {
		HBox hbox = new HBox();
		Label nameLabel = new Label("(empty)");
		Label levelLabel = new Label("1");
		Pane pane = new Pane();
		Button increaseButton = new Button("+");
		Button decreaseButton = new Button("-");

		public PropertyCell() {
			hbox.getChildren().addAll(nameLabel, pane, decreaseButton, levelLabel, increaseButton);
			hbox.setSpacing(5);
			hbox.setAlignment(Pos.CENTER);
			HBox.setHgrow(pane, Priority.ALWAYS);
			decreaseButton.setPrefWidth(25);
			increaseButton.setPrefWidth(25);
			decreaseButton.setOnAction((ActionEvent e) -> m.decreaseProperty(getItem()));
			increaseButton.setOnAction((ActionEvent e) -> m.increaseProperty(getItem()));
		}

		@Override
		protected void updateItem(Property item, boolean empty) {
			super.updateItem(item, empty);
			setText(null); // No text in label of super class
			if (empty || item == null) {
				setGraphic(null);
				setTooltip(null);
			} else if (item.getMinLevel() == item.getMaxLevel()) {
				nameLabel.setText(item.getName());
				setTooltip(new Tooltip(item.getDescription()));
				setGraphic(nameLabel);
			} else {
				nameLabel.setText(item.getName());
				levelLabel.setText(String.valueOf(item.getLevel()));
				increaseButton.setDisable(!item.isIncreasable());
				decreaseButton.setDisable(!item.isDecreasable());
				setTooltip(new Tooltip(item.getDescription()));
				setGraphic(hbox);
			}
		}
	}

}
