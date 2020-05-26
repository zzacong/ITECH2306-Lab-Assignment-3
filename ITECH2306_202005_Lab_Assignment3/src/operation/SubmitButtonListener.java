package operation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import domain.*;
import utility.Validator;

/**
 * @author Zac
 *
 */
public class SubmitButtonListener implements ActionListener{
	
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
	private JTextField valueField;
	private JCheckBox charityBox;
	private JPanel respondPanel;
	private JPanel errorPanel;
	
	private String labelText;
	private int propertyType;
	private String category;
	private double capitalImprovedValue;
	
	public SubmitButtonListener(JFrame frame) {
		this.setTarget(frame);
		this.setCategoryLabel(target.getCategoryLabel());
		this.setCategoryGroup(target.getCategoryGroup());
		this.setValueField(target.getValueField());
		this.setCharityBox(target.getCharityBox());
		this.setRespondPanel(target.getRespondPanel());
		this.setErrorPanel(target.getErrorPanel());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setPropertyType(target.getPropertyComboIndex());
		if(validateInput(categoryGroup, valueField.getText())) {
			showHideErrorMessage(errorPanel, false);
			calculateRate();
			// Disable button after click
			JButton source = (JButton) e.getSource();
	        source.setEnabled(false);
	        target.getRetryButton().setVisible(true);
		}
		else {
			showHideErrorMessage(errorPanel, true);
		}
	}
	
	public boolean validateInput(ButtonGroup categoryGroup, String civInString) {
		boolean pass = true;
		if(propertyType == SCHOOL_COMMUNITY) {
			pass = isValidCategory(categoryGroup);
		}
		return isValidCIV(civInString) && pass;
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
	
	public void addComponentsToRespondPanel(JPanel panel, Property property) {
		MYFORMAT.setMinimumFractionDigits(2);
		MYFORMAT.setMaximumFractionDigits(4);
		JTextField propertyTypeName = new JTextField(property.getClass().getSimpleName());
		propertyTypeName.setEditable(NON_EDITABLE);
		JTextField description = new JTextField(property.getDescription());
		description.setEditable(NON_EDITABLE);
		JTextField capitalImprovedValue = new JTextField(MYFORMAT.format(property.getCapitalImprovedValue()));
		capitalImprovedValue.setEditable(NON_EDITABLE);
		JTextField capitalImprovedRate = new JTextField(MYFORMAT.format(property.getCapitalImprovedRate()));
		capitalImprovedRate.setEditable(NON_EDITABLE);
		JTextArea extraServices = new JTextArea(property.getExtraServicesDetails());
		extraServices.setEditable(NON_EDITABLE);
		int end;
		try {
			end = extraServices.getLineEndOffset(1);
			extraServices.replaceRange("", 1, end);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		MYFORMAT.setMaximumFractionDigits(2);
		JTextField totalRateCosts = new JTextField(MYFORMAT.format(property.calculateRates()));
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
		
		// Add respondPane to JFrame
		c.insets = new Insets(10,0,10,0);
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 5;
		this.target.add(panel, c);
		panel.setVisible(true);
		target.resizeWindow();
	}
	
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
