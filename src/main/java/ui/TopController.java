package ui;

import aventurian.Aventurian;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TopController extends PaneController {

	@FXML
	public TextField name;

	@Override
	public void update(Aventurian updatedAventurian) {
		name.setText(updatedAventurian.getName());

	}

	public void setName() {
		m.setName(name.getText());
	}

	@Override
	void initControllerSpecificStuff() {
		// TODO Auto-generated method stub
	}

}
