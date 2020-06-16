package operation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import domain.*;
import utility.Validator;

/**
 * @author Zac
 *
 */
public class SubmitButtonListener implements ActionListener {
	
	private static final int RESIDENTIAL = 0,
			 				 COMMERCIAL = 1,
							 VACANT_LAND = 2,
							 HOSPITAL = 3,
							 INDUSTRIAL = 4,
							 SCHOOL_COMMUNITY = 5,
							 OTHER = 6;
	private static final String SMALL = "Small",
		 						MEDIUM = "Medium",
		 						LARGE = "Large";
	private static final NumberFormat MYFORMAT = NumberFormat.getNumberInstance();
	private static final boolean NON_EDITABLE = false;
	
	
	private CalculatePropertyTypeRatesWindow target;
	private JLabel categoryLabel;
	private ButtonGroup categoryGroup;
	private JLabel valueLabel;
	private JTextField valueField;
	private JCheckBox charityBox;
	private JPanel respondPanel;
	private JPanel errorPanel;
	
	private String labelText;
	private int propertyType;
	private String category;
	private double capitalImprovedValue;
	
	// Receives passed in JFrame, in this case, the CalculatePropertyTypeRatesWindow
	// Use setters to retrieve the relevent components from the CalculatePropertyTypeRatesWindow object
	public SubmitButtonListener(JFrame frame) {
		this.setTarget(frame);
		this.setCategoryLabel(target.getCategoryLabel());
		this.setCategoryGroup(target.getCategoryGroup());
		this.setValueLabel(target.getValueLabel());
		this.setValueField(target.getValueField());
		this.setCharityBox(target.getCharityBox());
		this.setRespondPanel(target.getRespondPanel());
		this.setErrorPanel(target.getErrorPanel());
	}
	
	// This method is performed when the ActionListener is triggered
	// It first get the property type selected
	// Then validate relevent inputs - CIV, category (if SchoolCommunity is selected)
	// Show/Hide the error message panel
	// When passes validation
			// Calculate the property rates based on the property type if passes validation
			// Disable relevent components to prevent further changes
			// Show the retryButton
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setPropertyType(target.getPropertyComboIndex());
		if(validateInput(categoryGroup, valueField.getText())) {
			showHideErrorMessage(errorPanel, false);
			calculateRate();
			disableReleventComponents();
		}
		else {
			showHideErrorMessage(errorPanel, true);
		}
	}
	
	// Used to disable the relevent components to prevent further changes
	// Disable submitButton
	// Show retryButton
	public void disableReleventComponents() {
		target.getPropertyCombo().setEnabled(NON_EDITABLE);
		ArrayList<AbstractButton> allRadioButtons = Collections.list(categoryGroup.getElements());
		for(AbstractButton button : allRadioButtons) {
			button.setEnabled(NON_EDITABLE);
		}
		valueField.setEnabled(NON_EDITABLE);
		charityBox.setEnabled(NON_EDITABLE);
        target.getSubmitButton().setEnabled(NON_EDITABLE);
        target.getRetryButton().setVisible(true);
	}
	
	// Validation method to check valid CIV and category
	public boolean validateInput(ButtonGroup categoryGroup, String civInString) {
		boolean pass = true;
		if(propertyType == SCHOOL_COMMUNITY) {
			pass = isValidCategory(categoryGroup);
		}
		return isValidCIV(civInString) && pass;
	}
	
	// Validate the input CIV
	public boolean isValidCIV(String civInString) {
		if(Validator.validateString("Capital Improved Value", civInString)) {
			if(Validator.validateStringToDouble(civInString)) {
				this.capitalImprovedValue = Double.parseDouble(civInString);
				if(Validator.checkDoubleWithinRange("Capital Improved Rate", capitalImprovedValue, 100.00, 50000000.00)) {
					valueLabel.setForeground(Color.black);
					valueField.setBackground(Color.white);
					return true;
				}
			}
		}
		valueLabel.setForeground(Color.red);
		valueField.setBackground(new Color(255, 150, 150));
		return false;
	}

	// Validate the input category ButtonGroup
	// Check if there is one JRadioButton selected
	public boolean isValidCategory(ButtonGroup categoryGroup) {
		Object object = categoryGroup.getSelection();
		if(object != null) {
			category = categoryGroup.getSelection().getActionCommand();
			labelText = "Select a category for your School/Community:";
			categoryLabel.setText(labelText);
			categoryLabel.setForeground(Color.black);
			return true;
		}
		else {
			labelText = "You must select one category:";
			categoryLabel.setText(labelText);
			categoryLabel.setForeground(Color.red);
			return false;
		}
	}
	
	// Determine the property type selected and call the corresponding constructor
	// Also set the property CIV, extra services, and owner charity status
	public void calculateRate() {
		Property property = null;
		switch (propertyType) {
			case(RESIDENTIAL):
				property = new Residential();
				break;
			case(COMMERCIAL):
				property = new Commercial();
				break;
			case(VACANT_LAND):
				break;
			case(HOSPITAL):
				property = new Hospital();
				break;
			case(INDUSTRIAL):
				property = new Industrial();
				break;
			case(SCHOOL_COMMUNITY):
				switch(category) {
				case(SMALL):
					property = new SchoolCommunity(1);
					break;
				case(MEDIUM):
					property = new SchoolCommunity(2);
					break;
				case(LARGE):
					property = new SchoolCommunity(3);
					break;
				}
				break;
			case(OTHER):
				property = new OtherProperty();
				break;			
		}
		if (property != null) {
			property.setCapitalImprovedValue(this.capitalImprovedValue);
			property.setUpExtraServices();
			property.getOwner().setCharity(this.charityBox.isSelected());
			addComponentsToRespondPanel(respondPanel, property);
		}
	}
	
	// Here the respondPanel is set up
	// Add succifient information of the property to display on the window
	// The panel use a GridBagLayout to display the information in a table view
	// Property type, description, CIV, CIV rate, extra services, total rate costs are displayed 
	public void addComponentsToRespondPanel(JPanel panel, Property property) {
		MYFORMAT.setMinimumFractionDigits(2);
		MYFORMAT.setMaximumFractionDigits(2);
		JTextField propertyTypeName = new JTextField(property.getClass().getSimpleName());
		propertyTypeName.setEditable(NON_EDITABLE);
		JTextField description = new JTextField(property.getDescription());
		description.setEditable(NON_EDITABLE);
		JTextField capitalImprovedValue = new JTextField("$" + MYFORMAT.format(property.getCapitalImprovedValue()));
		capitalImprovedValue.setEditable(NON_EDITABLE);
		JTextField capitalImprovedRate = new JTextField(MYFORMAT.format(property.getCapitalImprovedRate()*100) + "%");
		capitalImprovedRate.setEditable(NON_EDITABLE);
		JTextArea extraServices = new JTextArea(property.getExtraServicesDetails());
		extraServices.setEditable(NON_EDITABLE);
		// Remove the second line from the extraservice toString()
		int end;
		try {
			end = extraServices.getLineEndOffset(1);
			extraServices.replaceRange("", 1, end);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		JTextField totalRateCosts = new JTextField("$" + MYFORMAT.format(property.calculateRates()));
		totalRateCosts.setEditable(NON_EDITABLE);
		totalRateCosts.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,0,0,0);
		c.ipadx = 10;

		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel("Property type:"), c);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(new JLabel("Description:"), c);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(new JLabel("Capital Improved Value:"), c);
		c.gridx = 0;
		c.gridy = 3;
		panel.add(new JLabel("Capital Improved Rate:"), c);
		c.gridx = 0;
		c.gridy = 4;
		panel.add(new JLabel("Extra services:"), c);
		c.gridx = 0;
		c.gridy = 6;
		panel.add(new JLabel("Total Rate Costs:"), c);
		c.gridx = 1;
		c.gridy = 0;
		panel.add(propertyTypeName, c);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(description, c);
		c.gridx = 1;
		c.gridy = 2;
		panel.add(capitalImprovedValue, c);
		c.gridx = 1;
		c.gridy = 3;
		panel.add(capitalImprovedRate, c);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 5;
		panel.add(extraServices, c);
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 6;
		panel.add(totalRateCosts, c);
		
		// Add respondPane to mainPanel
		c.insets = new Insets(10,0,10,0);
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 5;
		this.target.getMainPanel().add(panel, c);
		panel.setVisible(true);
		target.resizeWindow();
	}
	
	// This method is used to control the visibility of errorPanel 
	public void showHideErrorMessage(JPanel panel, boolean show) {
		panel.setVisible(show);
		target.resizeWindow();
	}

	public void setTarget(JFrame target) {
		this.target = (CalculatePropertyTypeRatesWindow) target;
	}
	
	public void setPropertyType(int propertyType) {
		this.propertyType = propertyType;
	}

	public void setCategoryLabel(JLabel categoryLabel) {
		this.categoryLabel = categoryLabel;
	}

	public void setCategoryGroup(ButtonGroup categoryGroup) {
		this.categoryGroup = categoryGroup;
	}
	
	public void setValueLabel(JLabel valueLabel) {
		this.valueLabel = valueLabel;
	}

	public void setValueField(JTextField valueField) {
		this.valueField = valueField;
	}

	public void setCharityBox(JCheckBox charityBox) {
		this.charityBox = charityBox;
	}

	public void setRespondPanel(JPanel respondPanel) {
		this.respondPanel = respondPanel;
	}

	public void setErrorPanel(JPanel errorPanel) {
		this.errorPanel = errorPanel;
	}
}
