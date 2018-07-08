package ui;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import skills.attributes.primary.PrimaryAttribute;
import skills.attributes.secondary.SecondaryAttribute;

public class AttributesPaneController extends PaneController {

	@FXML
	public ListView<PrimaryAttribute> lvPrimaryAttributes;

	@FXML
	public ListView<SecondaryAttribute> lvSecondaryAttributes;

	@Override
	void update(Aventurian updatedAventurian) {
		lvPrimaryAttributes.setItems(null); // forces listview to re-render all cells
		lvSecondaryAttributes.setItems(null); // forces listview to re-render all cells
		final List<PrimaryAttribute> assignedLanguages = updatedAventurian.getPrimaryAttributes();
		lvPrimaryAttributes.setItems(FXCollections.observableArrayList(assignedLanguages));
		final List<SecondaryAttribute> unassignedLanguages = updatedAventurian.getSecondaryAttributes();
		lvSecondaryAttributes.setItems(FXCollections.observableArrayList(unassignedLanguages));
	}

	@Override
	void initControllerSpecificStuff() {
		lvPrimaryAttributes.getStyleClass().add("attributes-view");
		lvSecondaryAttributes.getStyleClass().add("attributes-view");
		lvPrimaryAttributes.setCellFactory((ListView<PrimaryAttribute> list) -> new PrimaryAttributeCell());
		lvSecondaryAttributes.setCellFactory((ListView<SecondaryAttribute> list) -> new SecondaryAttributeCell());

	}

	private class PrimaryAttributeCell extends ListCell<PrimaryAttribute> {
		HBox hbox = new HBox();
		Label nameLabel = new Label("(empty)");
		Label levelLabel = new Label("1");
		Pane pane = new Pane();
		Button increaseButton = new Button("+");
		Button decreaseButton = new Button("-");

		public PrimaryAttributeCell() {
			hbox.setSpacing(5);
			hbox.setAlignment(Pos.CENTER);
			HBox.setHgrow(pane, Priority.ALWAYS);
			decreaseButton.setPrefWidth(25);
			increaseButton.setPrefWidth(25);
			levelLabel.setPrefWidth(25);
			levelLabel.setAlignment(Pos.BOTTOM_CENTER);
			nameLabel.setId("attributeNameLabel");
			levelLabel.setId("attributeLevelLabel");
			decreaseButton.setOnAction(e -> askForConfirmation(getItem(), m.getDependingSkillsForDecrease(getItem()),
					() -> m.decrease(getItem())));

			increaseButton.setOnAction(e -> askForConfirmation(getItem(), m.getDependingSkillsForIncrease(getItem()),
					() -> m.increase(getItem())));
		}

		@Override
		protected void updateItem(PrimaryAttribute item, boolean empty) {
			super.updateItem(item, empty);
			setText(null); // No text in label of super class
			hbox.getChildren().clear();
			if (empty || item == null) {
				setGraphic(null);
				setTooltip(null);
			} else {
				nameLabel.setText(item.getName());
				levelLabel.setText(String.valueOf(item.getLevel()));
				increaseButton.setDisable(!AttributesPaneController.this.m.canIncrease(item));
				decreaseButton.setDisable(!AttributesPaneController.this.m.canDecrease(item));
				hbox.getChildren().addAll(nameLabel, pane, decreaseButton, levelLabel, increaseButton);
				setGraphic(hbox);
				setTooltip(new Tooltip(item.getDescription()));
			}
		}
	}

	private class SecondaryAttributeCell extends ListCell<SecondaryAttribute> {
		HBox hbox = new HBox();
		Label nameLabel = new Label("(empty)");
		Label levelLabel = new Label("1");
		Pane pane = new Pane();
		Button increaseButton = new Button("+");
		Button decreaseButton = new Button("-");

		public SecondaryAttributeCell() {
			hbox.setSpacing(5);
			hbox.setAlignment(Pos.CENTER);
			HBox.setHgrow(pane, Priority.ALWAYS);
			decreaseButton.setPrefWidth(25);
			increaseButton.setPrefWidth(25);
			levelLabel.setPrefWidth(25);
			levelLabel.setAlignment(Pos.BOTTOM_CENTER);
			nameLabel.setId("attributeNameLabel");
			levelLabel.setId("attributeLevelLabel");
			decreaseButton.setOnAction(e -> askForConfirmation(getItem(), m.getDependingSkillsForDecrease(getItem()),
					() -> m.decrease(getItem())));

			increaseButton.setOnAction(e -> askForConfirmation(getItem(), m.getDependingSkillsForIncrease(getItem()),
					() -> m.increase(getItem())));
		}

		@Override
		protected void updateItem(SecondaryAttribute item, boolean empty) {
			super.updateItem(item, empty);
			setText(null); // No text in label of super class
			hbox.getChildren().clear();
			if (empty || item == null) {
				setGraphic(null);
				setTooltip(null);
			} else {
				nameLabel.setText(item.getName());
				levelLabel.setText(String.valueOf(item.getLevel()));
				increaseButton.setDisable(!AttributesPaneController.this.m.canIncrease(item));
				decreaseButton.setDisable(!AttributesPaneController.this.m.canDecrease(item));
				increaseButton.setVisible(isIncreasableSecondaryAttribute(item));
				decreaseButton.setVisible(isIncreasableSecondaryAttribute(item));
				hbox.getChildren().addAll(nameLabel, pane, decreaseButton, levelLabel, increaseButton);
				setGraphic(hbox);
				setTooltip(new Tooltip(item.getDescription()));
			}
		}

		boolean isIncreasableSecondaryAttribute(SecondaryAttribute item) {
			return AttributesPaneController.this.m.canIncrease(item)
					|| AttributesPaneController.this.m.canDecrease(item);
		}
	}
}
