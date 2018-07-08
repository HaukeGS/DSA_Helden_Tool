package ui;

import static java.util.stream.Collectors.toList;

import java.util.List;

import aventurian.Aventurian;
import javafx.collections.FXCollections;
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
import skills.properties.Property;

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
		m.add(p);
	}

	public void unassignAdvantage() {
		final Property p = lvAssignedAdvantages.getSelectionModel().getSelectedItem();
		m.remove(p);
	}

	public void assignDisadvantage() {
		final Property p = lvUnassignedDisadvantages.getSelectionModel().getSelectedItem();
		m.add(p);
	}

	public void unassignDisadvantage() {
		final Property p = lvAssignedDisadvantages.getSelectionModel().getSelectedItem();
		m.remove(p);
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
			final Property p = lvAssignedAdvantages.getSelectionModel().getSelectedItem();
			if (click.getClickCount() == 2 && p != null && m.canRemove(p)) {
				askForConfirmation(p, m.getDependingSkillsForRemove(p), () -> m.remove(p));
			}
		});
		lvAssignedAdvantages.setCellFactory((ListView<Property> list) -> new AssignedPropertyCell());
	}

	private void prepareUnassignedAdvantages() {
		lvUnassignedAdvantages.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					btnAssignAdvantage.setDisable(newValue == null || !m.canAdd(newValue));
				});

		lvUnassignedAdvantages.setOnMouseClicked((MouseEvent click) -> {
			final Property p = lvUnassignedAdvantages.getSelectionModel().getSelectedItem();
			if (click.getClickCount() == 2 && p != null && m.canAdd(p)) {
				askForConfirmation(p, m.getDependingSkillsForAdd(p), () -> m.add(p));
			}
		});

		lvUnassignedAdvantages.setCellFactory((ListView<Property> list) -> new UnAssignedPropertyCell());
	}

	private void prepareAssignedDisadvantages() {
		lvAssignedDisadvantages.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					btnUnassignDisadvantage.setDisable(newValue == null || m.canRemove(newValue));
				});
		lvAssignedDisadvantages.setOnMouseClicked((MouseEvent click) -> {
			final Property p = lvAssignedDisadvantages.getSelectionModel().getSelectedItem();
			if (click.getClickCount() == 2 && p != null && m.canRemove(p)) {
				askForConfirmation(p, m.getDependingSkillsForRemove(p), () -> m.remove(p));
			}
		});
		lvAssignedDisadvantages.setCellFactory((ListView<Property> list) -> new AssignedPropertyCell());
	}

	private void prepareUnassignedDisadvantages() {
		lvUnassignedDisadvantages.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					btnAssignDisadvantage.setDisable(newValue == null || !m.canAdd(newValue));
				});

		lvUnassignedDisadvantages.setOnMouseClicked((MouseEvent click) -> {
			final Property p = lvUnassignedDisadvantages.getSelectionModel().getSelectedItem();
			if (click.getClickCount() == 2 && p != null && m.canAdd(p)) {
				askForConfirmation(p, m.getDependingSkillsForAdd(p), () -> m.add(p));
			}
		});
		lvUnassignedDisadvantages.setCellFactory((ListView<Property> list) -> new UnAssignedPropertyCell());
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

	private class AssignedPropertyCell extends ListCell<Property> {
		HBox hbox = new HBox();
		Label nameLabel = new Label("(empty)");
		Label levelLabel = new Label("1");
		Pane pane = new Pane();
		Button increaseButton = new Button("+");
		Button decreaseButton = new Button("-");

		public AssignedPropertyCell() {
			hbox.setSpacing(5);
			hbox.setAlignment(Pos.CENTER);
			HBox.setHgrow(pane, Priority.ALWAYS);
			decreaseButton.setPrefWidth(25);
			increaseButton.setPrefWidth(25);
			decreaseButton.setOnAction(e -> askForConfirmation(getItem(), m.getDependingSkillsForDecrease(getItem()),
					() -> m.decrease(getItem())));
			increaseButton.setOnAction(e -> askForConfirmation(getItem(), m.getDependingSkillsForIncrease(getItem()),
					() -> m.increase(getItem())));
		}

		@Override
		protected void updateItem(Property item, boolean empty) {
			super.updateItem(item, empty);
			setText(null); // No text in label of super class
			hbox.getChildren().clear();
			if (empty || item == null) {
				setGraphic(null);
				setTooltip(null);
			} else if (item.getMinLevel() == item.getMaxLevel()) {
				nameLabel.setText(item.toString());
				nameLabel.setDisable(!PropertyPaneController.this.m.canRemove(item));
				setTooltip(new Tooltip(item.getDescription()));
				setGraphic(nameLabel);
			} else {
				nameLabel.setText(item.toString());
				nameLabel.setDisable(!PropertyPaneController.this.m.canRemove(item));
				levelLabel.setText(String.valueOf(item.getLevel()));
				increaseButton.setDisable(!PropertyPaneController.this.m.canIncrease(item));
				decreaseButton.setDisable(!PropertyPaneController.this.m.canDecreaseProperty(item));
				hbox.getChildren().addAll(nameLabel, pane, decreaseButton, levelLabel, increaseButton);
				setGraphic(hbox);
				setTooltip(new Tooltip(item.getDescription()));
			}
		}
	}

	private class UnAssignedPropertyCell extends ListCell<Property> {
		Label nameLabel = new Label("");

		@Override
		protected void updateItem(Property item, boolean empty) {
			super.updateItem(item, empty);
			setText(null);
			if (empty || item == null) {
				setGraphic(null);
				setTooltip(null);
			} else {
				nameLabel.setText(item.toString());
				nameLabel.setDisable(!PropertyPaneController.this.m.canAdd(item));
				setGraphic(nameLabel);
				setTooltip(new Tooltip(item.getDescription()));
			}
		}
	}

}
