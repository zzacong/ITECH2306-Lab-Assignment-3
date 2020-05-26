package operation;

import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import utility.Validator;

/**
 * @author Zac
 *
 */
public class SubmitButtonListener implements ActionListener{
	
	private JLabel propertyLabel;
	private ButtonGroup propertyGroup;
	private JTextField valueField;
	private JCheckBox charityBox;
	
	private String labelText;
	private String propertyType;
	private double civ;
//	private boolean passValidation = false;
	
	
	public SubmitButtonListener(JLabel propertyLabel, ButtonGroup propertyGroup, JTextField valueField, JCheckBox charityBox) {
		this.setPropertyLabel(propertyLabel);
		this.setPropertyGroup(propertyGroup);
		this.setValueField(valueField);
		this.setCharityBox(charityBox);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(isValidProperty(propertyGroup)) {
			labelText = "Choose one property type:";
			propertyLabel.setText(labelText);
			propertyLabel.setForeground(Color.black);
		}
		else {
			labelText = "You must select one property type";
			propertyLabel.setText(labelText);
			propertyLabel.setForeground(Color.red);
		}
		
		if(isValidCIV(valueField.getText())) {
			valueField.setBackground(Color.white);
		}
		else {
			valueField.setBackground(Color.red);
		}
		
		
		
	}
	
	public boolean isValidProperty(ButtonGroup propertyGroup) {
		Object object = propertyGroup.getSelection(); 
		if(object != null) {
			propertyType  = propertyGroup.getSelection().getActionCommand();
			System.out.println(propertyType);
			return true;
		}
		else {
			System.out.println(object);
			return false;
		}
	}
	
	public boolean isValidCIV(String civInString) {
		if(Validator.validateStringToDouble(civInString)) {
			civ = Double.parseDouble(civInString);
			if(Validator.checkDoubleWithinRange("Capital Improved Rate", civ, 100.00, 50000000.00)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public void setPropertyLabel(JLabel propertyLabel) {
		this.propertyLabel = propertyLabel;
	}
	
	public void setPropertyGroup(ButtonGroup propertyGroup) {
		this.propertyGroup = propertyGroup;
	}

	public void setValueField(JTextField valueField) {
		this.valueField = valueField;
	}

	public void setCharityBox(JCheckBox charityBox) {
		this.charityBox = charityBox;
	}
}
