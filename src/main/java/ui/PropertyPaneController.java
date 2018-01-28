package ui;

import static java.util.stream.Collectors.toList;

import java.util.List;

import aventurian.Aventurian;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	void update(Aventurian updatedAventurian) {
		lvUnassignedAdvantages.setItems(null);
		lvAssignedAdvantages.setItems(null);
		lvUnassignedDisadvantages.setItems(null);
		lvAssignedDisadvantages.setItems(null);
		
		final List<Property> assignedAdvantages = updatedAventurian.getAdvantages();
		lvAssignedAdvantages.setItems(FXCollections.observableArrayList(assignedAdvantages));
		final List<Property> unassignedAdvantages = db.getAdvantages().stream().filter(p -> !assignedAdvantages.contains(p)).collect(toList());
		lvUnassignedAdvantages.setItems(FXCollections.observableArrayList(unassignedAdvantages));
		
		final List<Property> assignedDisadvantages = updatedAventurian.getDisadvantages();
		lvAssignedDisadvantages.setItems(FXCollections.observableArrayList(assignedDisadvantages));
		final List<Property> unassignedDisadvantages = db.getDisadvantages().stream().filter(p -> !assignedDisadvantages.contains(p)).collect(toList());
		lvUnassignedDisadvantages.setItems(FXCollections.observableArrayList(unassignedDisadvantages));
	}

	
}
