package ui;

import javafx.scene.control.Button;

import aventurian.Aventurian;
import aventurian.PrimaryAttributes.PRIMARY_ATTRIBUTE;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AttributesPaneController extends PaneController {

	@FXML
	Button btnIncreaseCourage;
	@FXML
	Label labelCourage;
	@FXML
	Button btnDecreaseCourage;
	
	@FXML
	Button btnIncreaseIntelligence;
	@FXML
	Label labelIntelligence;
	@FXML
	Button btnDecreaseIntelligence;
	
	@FXML
	Button btnIncreaseIntuition;
	@FXML
	Label labelIntuition;
	@FXML
	Button btnDecreaseIntuition;
	
	@FXML
	Button btnIncreaseCharisma;
	@FXML
	Label labelCharisma;
	@FXML
	Button btnDecreaseCharisma;
	
	@FXML
	Button btnIncreaseDexterity;
	@FXML
	Label labelDexterity;
	@FXML
	Button btnDecreaseDexterity;
	
	@FXML
	Button btnIncreaseAgility;
	@FXML
	Label labelAgility;
	@FXML
	Button btnDecreaseAgility;
	
	@FXML
	Button btnIncreaseConstitution;
	@FXML
	Label labelConstitution;
	@FXML
	Button btnDecreaseConstitution;
	
	@FXML
	Button btnIncreaseStrength;
	@FXML
	Label labelStrength;
	@FXML
	Button btnDecreaseStrength;
	

	public void increaseCourage() {
		m.increasePrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE);
	}

	public void decreaseCourage() {
		m.decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE);
	}
	
	public void increaseIntelligence() {
		m.increasePrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE);
	}
	
	public void decreaseIntelligence() {
		m.decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE);
	}
	
	public void increaseIntuition() {
		m.increasePrimaryAttribute(PRIMARY_ATTRIBUTE.INTUITION);
	}
	
	public void decreaseIntuition() {
		m.decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.INTUITION);
	}
	
	public void increaseCharisma() {
		m.increasePrimaryAttribute(PRIMARY_ATTRIBUTE.CHARISMA);
	}
	
	public void decreaseCharisma() {
		m.decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.CHARISMA);
	}
	
	public void increaseDexterity() {
		m.increasePrimaryAttribute(PRIMARY_ATTRIBUTE.DEXTERITY);
	}
	
	public void decreaseDexterity() {
		m.decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.DEXTERITY);
	}
	
	public void increaseAgility() {
		m.increasePrimaryAttribute(PRIMARY_ATTRIBUTE.AGILITY);
	}
	
	public void decreaseAgility() {
		m.decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.AGILITY);
	}
	
	public void increaseConstitution() {
		m.increasePrimaryAttribute(PRIMARY_ATTRIBUTE.CONSTITUTION);
	}
	
	public void decreaseConstitution() {
		m.decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.CONSTITUTION);
	}
	
	public void increaseStrength() {
		m.increasePrimaryAttribute(PRIMARY_ATTRIBUTE.STRENGTH);
	}
	
	public void decreaseStrength() {
		m.decreasePrimaryAttribute(PRIMARY_ATTRIBUTE.STRENGTH);
	}

	@Override
	public void update(Aventurian updatedAventurian) {
		labelCourage.setText(String.valueOf(updatedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.COURAGE)));
		labelIntelligence.setText(String.valueOf(updatedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTELLIGENCE)));
		labelIntuition.setText(String.valueOf(updatedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.INTUITION)));
		labelCharisma.setText(String.valueOf(updatedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.CHARISMA)));
		labelDexterity.setText(String.valueOf(updatedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.DEXTERITY)));
		labelAgility.setText(String.valueOf(updatedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.AGILITY)));
		labelConstitution.setText(String.valueOf(updatedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.CONSTITUTION)));
		labelStrength.setText(String.valueOf(updatedAventurian.getPrimaryAttribute(PRIMARY_ATTRIBUTE.STRENGTH)));
		btnIncreaseCourage.setDisable(!(updatedAventurian.isPrimaryAttributeIncreasable(PRIMARY_ATTRIBUTE.COURAGE) && updatedAventurian.isPrimaryAttributesLowerThanThreshhold()));
		btnIncreaseIntelligence.setDisable(!(updatedAventurian.isPrimaryAttributeIncreasable(PRIMARY_ATTRIBUTE.INTELLIGENCE) && updatedAventurian.isPrimaryAttributesLowerThanThreshhold()));
		btnIncreaseIntuition.setDisable(!(updatedAventurian.isPrimaryAttributeIncreasable(PRIMARY_ATTRIBUTE.INTUITION) && updatedAventurian.isPrimaryAttributesLowerThanThreshhold()));
		btnIncreaseCharisma.setDisable(!(updatedAventurian.isPrimaryAttributeIncreasable(PRIMARY_ATTRIBUTE.CHARISMA) && updatedAventurian.isPrimaryAttributesLowerThanThreshhold()));
		btnIncreaseDexterity.setDisable(!(updatedAventurian.isPrimaryAttributeIncreasable(PRIMARY_ATTRIBUTE.DEXTERITY) && updatedAventurian.isPrimaryAttributesLowerThanThreshhold()));
		btnIncreaseAgility.setDisable(!(updatedAventurian.isPrimaryAttributeIncreasable(PRIMARY_ATTRIBUTE.AGILITY) && updatedAventurian.isPrimaryAttributesLowerThanThreshhold()));
		btnIncreaseConstitution.setDisable(!(updatedAventurian.isPrimaryAttributeIncreasable(PRIMARY_ATTRIBUTE.CONSTITUTION) && updatedAventurian.isPrimaryAttributesLowerThanThreshhold()));
		btnIncreaseStrength.setDisable(!(updatedAventurian.isPrimaryAttributeIncreasable(PRIMARY_ATTRIBUTE.STRENGTH) && updatedAventurian.isPrimaryAttributesLowerThanThreshhold()));
		btnDecreaseCourage.setDisable(!updatedAventurian.isPrimaryAttributeDecreasable(PRIMARY_ATTRIBUTE.COURAGE));
		btnDecreaseIntelligence.setDisable(!updatedAventurian.isPrimaryAttributeDecreasable(PRIMARY_ATTRIBUTE.INTELLIGENCE));
		btnDecreaseIntuition.setDisable(!updatedAventurian.isPrimaryAttributeDecreasable(PRIMARY_ATTRIBUTE.INTUITION));
		btnDecreaseCharisma.setDisable(!updatedAventurian.isPrimaryAttributeDecreasable(PRIMARY_ATTRIBUTE.CHARISMA));
		btnDecreaseDexterity.setDisable(!updatedAventurian.isPrimaryAttributeDecreasable(PRIMARY_ATTRIBUTE.DEXTERITY));
		btnDecreaseAgility.setDisable(!updatedAventurian.isPrimaryAttributeDecreasable(PRIMARY_ATTRIBUTE.AGILITY));
		btnDecreaseConstitution.setDisable(!updatedAventurian.isPrimaryAttributeDecreasable(PRIMARY_ATTRIBUTE.CONSTITUTION));
		btnDecreaseStrength.setDisable(!updatedAventurian.isPrimaryAttributeDecreasable(PRIMARY_ATTRIBUTE.STRENGTH));
	}

	@Override
	void initControllerSpecificStuff() {
		// TODO nothing to do here!?
	}
}
