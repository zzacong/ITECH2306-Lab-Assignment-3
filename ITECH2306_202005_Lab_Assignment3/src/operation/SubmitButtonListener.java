package operation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;

import domain.Commercial;
import domain.Hospital;
import domain.Industrial;
import domain.OtherProperty;
import domain.Property;
import domain.Residential;
import domain.SchoolCommunity;
import utility.Validator;

/**
 * @author Zac
 *
 */
public class SubmitButtonListener implements ActionListener{
	
	private static final String RESIDENTIAL = "Residential",
							 	COMMERCIAL = "Commercial",
							 	VACANT_LAND = "VacantLand",
							 	HOSPITAL = "Hospital",
							 	INDUSTRIAL = "Industrial",
							 	SCHOOL_COMMUNITY = "SchoolCommunity",
							 	OTHER = "Other";
	private static final String SMALL = "Small",
		 						MEDIUM = "Medium",
		 						LARGE = "Large";
	private static final NumberFormat MYFORMAT = NumberFormat.getNumberInstance();
	private static final boolean NON_EDITABLE = false;

	private JFrame window;
	private JLabel propertyLabel;
	private ButtonGroup propertyGroup;
	private JLabel categoryLabel;
	private ButtonGroup categoryGroup;
	private JTextField valueField;
	private JCheckBox charityBox;
	private JPanel respondPanel;
	
	private String labelText;
	private String propertyType;
	private String category;
	private double capitalImprovedValue;
	
	public SubmitButtonListener(JFrame window, JLabel propertyLabel, ButtonGroup propertyGroup, JLabel categoryLabel, ButtonGroup categoryGroup, JTextField valueField, JCheckBox charityBox, JPanel respondPanel) {
		this.setWindow(window);
		this.setPropertyLabel(propertyLabel);
		this.setPropertyGroup(propertyGroup);
		this.setCategoryLabel(categoryLabel);
		this.setCategoryGroup(categoryGroup);
		this.setValueField(valueField);
		this.setCharityBox(charityBox);
		this.setRespondPanel(respondPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(validateInput(propertyGroup, categoryGroup, valueField.getText())) {
			calculateRate();
		}
		else {
			showErrorMessage(respondPanel);
		}
	}
	
	public boolean validateInput(ButtonGroup propertyGroup, ButtonGroup categoryGroup, String civInString) {
		boolean pass = false;
		if(isValidProperty(propertyGroup)) {
			if(propertyType.equals(SCHOOL_COMMUNITY)) {
				pass = isValidCategory(categoryGroup);
			}
			pass = true;
		}
		if(isValidCIV(civInString) && pass) {
			return true;
		}
		return false;
	}
	
	public boolean isValidProperty(ButtonGroup propertyGroup) {
		System.out.println("Validate Property:");
		Object object = propertyGroup.getSelection(); 
		if(object != null) {
			propertyType = propertyGroup.getSelection().getActionCommand();
			labelText = "Select one property type to calculate rate:";
			propertyLabel.setText(labelText);
			propertyLabel.setForeground(Color.black);
			System.out.println(propertyType);
			return true;
		}
		else {
			labelText = "You must select one property type:";
			propertyLabel.setText(labelText);
			propertyLabel.setForeground(Color.red);
			System.out.println(object);
			return false;
		}
	}
	
	public boolean isValidCIV(String civInString) {
		System.out.println("Validate CIV:");
		if(Validator.validateString("Capital Improved Value", civInString)) {
			if(Validator.validateStringToDouble(civInString)) {
				this.capitalImprovedValue = Double.parseDouble(civInString);
				if(Validator.checkDoubleWithinRange("Capital Improved Rate", capitalImprovedValue, 100.00, 50000000.00)) {
					valueField.setBackground(Color.white);
					System.out.println(capitalImprovedValue);
					return true;
				}
			}
		}
		valueField.setBackground(Color.red);
		return false;
	}

	public boolean isValidCategory(ButtonGroup categoryGroup) {
		System.out.println("Validate Category:");
		Object object = categoryGroup.getSelection();
		if(object != null) {
			category = categoryGroup.getSelection().getActionCommand();
			labelText = "Select a category for your School/Community:";
			categoryLabel.setText(labelText);
			categoryLabel.setForeground(Color.black);
			System.out.println(category);
			return true;
		}
		else {
			labelText = "You must select one category:";
			categoryLabel.setText(labelText);
			categoryLabel.setForeground(Color.red);
			System.out.println(object);
			return false;
		}
	}
	
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
	
	public void showErrorMessage(JPanel panel) {
		panel = new JPanel(new BorderLayout());
		panel.add(BorderLayout.CENTER, new JTextField("Invalid input. Unable to calculate property type rates."));
		panel.setVisible(true);
		this.window.pack();
		this.window.setSize(new Dimension(window.getWidth() + 60, window.getHeight() + 60));
	}
	
	public void addComponentsToRespondPanel(JPanel panel, Property property) {
		MYFORMAT.setMinimumFractionDigits(2);
		MYFORMAT.setMaximumFractionDigits(4);
		
		JTextField description = new JTextField(property.getDescription());
		description.setEditable(NON_EDITABLE);
		JTextField capitalImprovedValue = new JTextField(MYFORMAT.format(property.getCapitalImprovedValue()));
		capitalImprovedValue.setEditable(NON_EDITABLE);
		JTextField capitalImprovedRate = new JTextField(MYFORMAT.format(property.getCapitalImprovedRate()));
		capitalImprovedRate.setEditable(NON_EDITABLE);
		MYFORMAT.setMaximumFractionDigits(2);
		JTextField totalRateCosts = new JTextField(MYFORMAT.format(property.calculateRates()));
		totalRateCosts.setEditable(NON_EDITABLE);
		totalRateCosts.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,0,0,0);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel("Property type:"), c);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(new JLabel("Capital Improved Value:"), c);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(new JLabel("Capital Improved Rate:"), c);
		c.gridx = 0;
		c.gridy = 6;
		panel.add(new JLabel("Total Rate Costs:"), c);
		c.ipadx = 10;
		c.gridx = 1;
		c.gridy = 0;
		panel.add(description, c);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(capitalImprovedValue, c);
		c.gridx = 1;
		c.gridy = 2;
		panel.add(capitalImprovedRate, c);
		c.gridx = 1;
		c.gridy = 6;
		panel.add(totalRateCosts, c);
		
		// Add respondPane to JFrame
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10,0,10,0);
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 5;
//		this.window.add(panel, c);
		panel.setVisible(true);
		this.window.pack();
		this.window.setSize(new Dimension(window.getWidth() + 60, window.getHeight() + 60));
	}

	public void setWindow(JFrame window) {
		this.window = window;
	}
	
	public void setPropertyLabel(JLabel propertyLabel) {
		this.propertyLabel = propertyLabel;
	}
	
	public void setPropertyGroup(ButtonGroup propertyGroup) {
		this.propertyGroup = propertyGroup;
	}

	public void setCategoryLabel(JLabel categoryLabel) {
		this.categoryLabel = categoryLabel;
	}

	public void setCategoryGroup(ButtonGroup categoryGroup) {
		this.categoryGroup = categoryGroup;
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
}
