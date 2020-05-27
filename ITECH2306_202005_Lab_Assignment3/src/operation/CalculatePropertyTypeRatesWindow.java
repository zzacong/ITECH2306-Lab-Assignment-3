/**
 * 
 */
package operation;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

/**
 * @author Zac
 *
 */
@SuppressWarnings("serial")
public class CalculatePropertyTypeRatesWindow extends JFrame {

	private static final String RESIDENTIAL = "Residential",
							 	COMMERCIAL = "Commercial",
							 	VACANT_LAND = "VacantLand",
							 	HOSPITAL = "Hospital",
							 	INDUSTRIAL = "Industrial",
							 	SCHOOL_COMMUNITY = "School/Community",
							 	OTHER = "Other";
	
	private static final String SMALL = "Small",
								MEDIUM = "Medium",
								LARGE = "Large";

	// The JFrame consists of a main panel that has a few child panels on it
	// The mainPanel uses a GridBagLayout wheareas all the child panels use BorderLayout
	private JPanel mainPanel = new JPanel(new GridBagLayout());
	private JPanel titlePanel = new JPanel(new BorderLayout());
	private JPanel propertyPanel = new JPanel(new BorderLayout());
	private JPanel categoryPanel = new JPanel(new BorderLayout());
	private JPanel valuePanel = new JPanel(new BorderLayout());
	private JPanel charityPanel = new JPanel(new BorderLayout());
	private JPanel respondPanel = new JPanel();
	private JPanel errorPanel = new JPanel(new BorderLayout());
	private JPanel buttonPanel = new JPanel(new BorderLayout());

	// These are all the components needed, components of respondPanel are set up in SubmitButtonListener
	private JLabel propertyLabel;
	private JComboBox<String> propertyCombo;
	private JLabel categoryLabel;
	private ButtonGroup categoryGroup;
	private JLabel valueLabel;
	private JTextField valueField;
	private JLabel charityLabel;
	private JCheckBox charityBox;
	private JButton submitButton;
	private JButton retryButton;
	private JButton exitButton;
	
	// The frame/window itself uses the default BorderLayout
	public CalculatePropertyTypeRatesWindow() {
		this.setUpPanels();
		this.addPanelsToMain(this.mainPanel);
		this.addMainPanelToFrame(this.getContentPane());
	}
	
	// Set up all child panels required to display information and obtain inputs, except the respondPanel,
	// respondPanel are set up after Submit button is triggered
	public void setUpPanels() {
		this.setTitlePanel(this.titlePanel);
		this.setPropertyPanel(this.propertyPanel);
		this.setCategoryPanel(this.categoryPanel);
		this.setValuePanel(this.valuePanel);
		this.setCharityPanel(this.charityPanel);
		this.setErrorPanel(this.errorPanel);
		this.setButtonPanel(this.buttonPanel);
	}
	
	// Add all child panels to the main panel
	// The main panel uses a GridBagLayout, each child panel belongs to a specified row and column
	public void addPanelsToMain(JPanel panel) {
		GridBagConstraints c = new GridBagConstraints();
		
		// Resize the child panels on both vertical and horizontal axis
		c.fill = GridBagConstraints.BOTH;
		// Leave some space/gap between child panels
		c.insets = new Insets(10,0,20,0);

		c.gridx = 0;
		c.gridy = 0;
		panel.add(this.titlePanel, c);
		
		c.insets = new Insets(10,0,10,0);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(this.propertyPanel, c);
		
		c.gridx = 0;
		c.gridy = 2;
		panel.add(this.categoryPanel, c);
		categoryPanel.setVisible(false);
		
		c.gridx = 0;
		c.gridy = 3;
		panel.add(this.valuePanel, c);
		
		c.gridx = 0;
		c.gridy = 4;
		panel.add(this.charityPanel, c);
		
		c.gridx = 0;
		c.gridy = 6;
		panel.add(this.errorPanel, c);
		errorPanel.setVisible(false);
		
		// Don't resize the buttonPanel
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 8;
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 7;
		panel.add(this.buttonPanel, c);	
	}
	
	// Add the main panel to the frame/window
	public void addMainPanelToFrame(Container pane) {
		pane.add(BorderLayout.CENTER, this.mainPanel);
	}
	
	// Add relevent components to the titlePanel
	// Here I display the title as it would in the console
	public void setTitlePanel(JPanel panel) {
		JLabel titleLabel = new JLabel(" Council Rate Payer System", SwingConstants.CENTER);
		JLabel lineLabel = new JLabel("==================", SwingConstants.CENTER);
		JLabel subtitleLabel = new JLabel("Calculate Property Type Rates", SwingConstants.CENTER);
		Font titleFont = new Font("SansSerif", Font.PLAIN, 14);
		titleLabel.setFont(titleFont);
		lineLabel.setFont(titleFont);
		subtitleLabel.setFont(titleFont);

		panel.add(BorderLayout.NORTH, titleLabel);
		panel.add(BorderLayout.CENTER, lineLabel);
		panel.add(BorderLayout.SOUTH, subtitleLabel);
	}
	
	// Add relevent components to the propertyPanel
	// Here I use a JComboBox to display the list of property types, by default, Residential will be chosen
	// The JComboBox has a ItemListener attached:
	// Everytime a user changes the combo box selection, it checks if School/Community is selected
	// If School/Community is selected, it displays the categoryPanel
	public void setPropertyPanel(JPanel panel) {
		propertyLabel = new JLabel("Select a property type to calculate rate: ");
		String[] propertyTypeList = {RESIDENTIAL, COMMERCIAL, VACANT_LAND, HOSPITAL, INDUSTRIAL, SCHOOL_COMMUNITY, OTHER};
		propertyCombo = new JComboBox<String>(propertyTypeList);
		propertyCombo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				showHideCategoryPanel(propertyCombo.getSelectedIndex() == 5);
			}
			
		});
		
		panel.add(BorderLayout.NORTH, propertyLabel);
		panel.add(BorderLayout.CENTER, propertyCombo);
	}
	
	// Add relevent components to the categoryPanel
	// By default, this panel is not visible
	// Will be displayed when user select School/Community from the property JComboBox   
	// The categories uses JRadioButton and are grouped under a ButtonGroup
	public void setCategoryPanel(JPanel panel) {
		categoryLabel = new JLabel("Select a category for your School/Community:");
		JRadioButton smallButton = new JRadioButton("Small category");
		smallButton.setActionCommand(SMALL);
		JRadioButton mediumButton = new JRadioButton("Medium category");
		mediumButton.setActionCommand(MEDIUM);
		JRadioButton largeButton = new JRadioButton("Large category");
		largeButton.setActionCommand(LARGE);
		
		categoryGroup = new ButtonGroup();
		categoryGroup.add(smallButton);
		categoryGroup.add(mediumButton);
		categoryGroup.add(largeButton);
		
		JPanel categoryButtons = new JPanel(new GridLayout(0,1));
		categoryButtons.add(smallButton);
		categoryButtons.add(mediumButton);
		categoryButtons.add(largeButton);
		
		panel.add(BorderLayout.NORTH, categoryLabel);
		panel.add(BorderLayout.CENTER, categoryButtons);
	}
	
	// This method is used to control the visibility of categoryPanel
	public void showHideCategoryPanel(boolean show) {
		categoryGroup.clearSelection();
		categoryPanel.setVisible(show);
		resizeWindow();
	}
	
	// Add relevent components to the valuePanel
	// Here a JTextField is used as an input field for the user to key in the CIV of the property
	public void setValuePanel(JPanel panel) {
		valueLabel = new JLabel("Enter the value of property (100.00-50 000 000.00): ");
		valueField = new JTextField();
		valueField.setEditable(true);

		panel.add(BorderLayout.NORTH, valueLabel);
		panel.add(BorderLayout.CENTER, valueField);
	}
	
	// Add relevent components to the charityPanel
	// Here a JCheckBox is used to select charity status 
	public void setCharityPanel(JPanel panel) {
		charityLabel = new JLabel("Tick if Rate Payer has a charitable status:");
		charityBox = new JCheckBox("Charitable status");
		
		panel.add(BorderLayout.NORTH, charityLabel);
		panel.add(BorderLayout.CENTER, charityBox);
	}
	
	// By default, this panel is not visible 
	// Will be displayed when catches invalid inputs after Submit button is triggered 
	public void setErrorPanel(JPanel panel) {
		JLabel errorField = new JLabel("Invalid input. Unable to calculate property type rates.");
		errorField.setForeground(Color.red);
		panel.add(BorderLayout.CENTER, errorField);
	}
	
	// Add relevent buttons to the buttonPanel
	// There are three buttons: exitButton, retryButton, submitButton
	// exitButton disposes the window when triggered, is attached with ExitButtonListener
	// retryButton allow user to calculate rate for another property, it reset the window back to initial state
			// is attached with an anonymous ActionListener
			// by default, it is not visible
			// will be displayed after a succesful rate calculation is performed
	// submitButton validate all inputs, then calculate the property rate or display error message based on the validation result
			// is attached with SubmitButtonListener
			// will be disabled after a succesful rate calculation is performed
	public void setButtonPanel(JPanel panel) {
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitButtonListener(this));
		retryButton = new JButton("Do it again");
		retryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Component component = (Component) e.getSource();
				CalculatePropertyTypeRatesWindow frame = (CalculatePropertyTypeRatesWindow) SwingUtilities.getRoot(component);
		        frame.remove(frame.mainPanel);
		        frame.removeComponentsOnAllPanels();
		        frame.setUpPanels();
		        frame.addPanelsToMain(frame.mainPanel);
		        frame.addMainPanelToFrame(frame.getContentPane());
		        frame.resizeWindow();
			}
			
		});
		
		retryButton.setVisible(false);
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ExitButtonListener(this));
		BorderLayout borderLayout = (BorderLayout) panel.getLayout(); 
		borderLayout.setHgap(10);
		panel.add(BorderLayout.WEST, exitButton);
		panel.add(BorderLayout.CENTER, retryButton);
		panel.add(BorderLayout.EAST, submitButton);
	}
	
	// This method is used to remove all components on all panels
	// So that we can set up the panels again
	// Used when a user trigger the retryButton
	public void removeComponentsOnAllPanels() {
		this.mainPanel.removeAll();
		this.titlePanel.removeAll();
		this.propertyPanel.removeAll();
		this.categoryPanel.removeAll();
		this.valuePanel.removeAll();
		this.charityPanel.removeAll();
		this.respondPanel.removeAll();
		this.errorPanel.removeAll();
		this.buttonPanel.removeAll();
	}
	
	// Provides a list of getters for SubmitButtonListener to retrieve relevent components from this object
	// So we don't have to pass in too many arguments when calling the ActionListener, i.e. SubmitButtonListener
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public JPanel getCharityPanel() {
		return charityPanel;
	}

	public JPanel getRespondPanel() {
		return respondPanel;
	}
	
	public JPanel getErrorPanel() {
		return errorPanel;
	}

	public JLabel getPropertyLabel() {
		return propertyLabel;
	}
	
	public JComboBox<String> getPropertyCombo() {
		return propertyCombo;
	}

	public int getPropertyComboIndex() {
		return propertyCombo.getSelectedIndex();
	}
	
	public JLabel getCategoryLabel() {
		return categoryLabel;
	}

	public ButtonGroup getCategoryGroup() {
		return categoryGroup;
	}
	
	public JLabel getValueLabel() {
		return valueLabel;
	}

	public JTextField getValueField() {
		return valueField;
	}

	public JCheckBox getCharityBox() {
		return charityBox;
	}

	public JButton getSubmitButton() {
		return submitButton;
	}
	
	public JButton getRetryButton() {
		return retryButton;
	}
	
	// This method is used to resize the window to wrap all components
	// Then enlarge the window by a bit to have some space surrounding its contents 
	public void resizeWindow() {
		this.pack();
		this.setSize(new Dimension(this.getWidth() + 60, this.getHeight() + 60));
	}
	
	// Display the window 
	public void createAndShowWindow() {
		this.setTitle("Calculate Property Type Rates - Zhi Zao Ong 30360914");
		this.resizeWindow();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	// The main method is provided for standalone testing of the GUI, 
	// without having to run through CouncilSystem class from the console, it is now disabled
//	public static void main(String[] args) {
//		CalculatePropertyTypeRatesWindow window = new CalculatePropertyTypeRatesWindow();
//		window.createAndShowWindow();	
//	}

}
