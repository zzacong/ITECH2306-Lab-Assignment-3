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
							 	SCHOOL_COMMUNITY = "SchoolCommunity",
							 	OTHER = "Other";
	
	private static final String SMALL = "Small",
								MEDIUM = "Medium",
								LARGE = "Large";

	private JPanel titlePanel = new JPanel(new BorderLayout());
	private JPanel propertyPanel = new JPanel(new BorderLayout());
	private JPanel categoryPanel = new JPanel(new BorderLayout());
	private JPanel valuePanel = new JPanel(new BorderLayout());
	private JPanel charityPanel = new JPanel(new BorderLayout());
	private JPanel respondPanel = new JPanel();
	private JPanel buttonPanel = new JPanel(new BorderLayout());
	
	private JLabel propertyLabel;
	private ButtonGroup propertyGroup;
	JLabel categoryLabel;
	private ButtonGroup categoryGroup;
	private JLabel valueLabel;
	private JTextField valueField;
	private JLabel charityLabel;
	private JCheckBox charityBox;
	private JButton submitButton;
	private JButton exitButton;
	
	public CalculatePropertyTypeRatesWindow() {
		setLayout(new GridBagLayout());
		this.setUpPanels();
		this.addPanelsToFrame(this.getContentPane());
	}
	
	public void setUpPanels() {
		this.setTitlePanel(this.titlePanel);
		this.setPropertyPanel(this.propertyPanel);
		this.setCategoryPanel(this.categoryPanel);
		this.setValuePanel(this.valuePanel);
		this.setCharityPanel(this.charityPanel);
//		this.setRespondPanel(this.respondPanel);
		this.setButtonPanel(this.buttonPanel);
	}
	
	public void addPanelsToFrame(Container pane) {
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10,0,10,0);

		c.gridx = 0;
		c.gridy = 0;
		pane.add(this.titlePanel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		pane.add(this.propertyPanel, c);
		
		c.gridx = 0;
		c.gridy = 2;
		pane.add(this.categoryPanel, c);
		categoryPanel.setVisible(false);
		
		c.gridx = 0;
		c.gridy = 3;
		pane.add(this.valuePanel, c);
		
		c.gridx = 0;
		c.gridy = 4;
		pane.add(this.charityPanel, c);
		
		c.gridx = 0;
		c.gridy = 5;
		pane.add(this.respondPanel, c);
		respondPanel.setVisible(false);
		
		c.gridx = 0;
		c.gridy = 6;
		pane.add(this.buttonPanel, c);	
	}
	
	public void setTitlePanel(JPanel panel) {
		JLabel titleLabel = new JLabel("  Council Rate Payer System", SwingConstants.CENTER);
		JLabel lineLabel = new JLabel("==================", SwingConstants.CENTER);
		JLabel subTitleLabel = new JLabel("Calculate Property Type Rates", SwingConstants.CENTER);

		panel.add(BorderLayout.NORTH, titleLabel);
		panel.add(BorderLayout.CENTER, lineLabel);
		panel.add(BorderLayout.SOUTH, subTitleLabel);
	}
	
	public void setPropertyPanel(JPanel panel) {
		propertyLabel = new JLabel("Select a property type to calculate rate: ");
		JRadioButton residentialButton = new JRadioButton("Residential");
		residentialButton.setActionCommand(RESIDENTIAL);
		JRadioButton commercialButton = new JRadioButton("Commercial");
		commercialButton.setActionCommand(COMMERCIAL);
		JRadioButton vacantLandButton = new JRadioButton("Vacant Land");
		vacantLandButton.setActionCommand(VACANT_LAND);
		JRadioButton hospitalButton = new JRadioButton("Hospital");
		hospitalButton.setActionCommand(HOSPITAL);
		JRadioButton industrialButton = new JRadioButton("Industrial");
		industrialButton.setActionCommand(INDUSTRIAL);
		JRadioButton schoolCommunityButton = new JRadioButton("School/Community");
		schoolCommunityButton.setActionCommand(SCHOOL_COMMUNITY);
		schoolCommunityButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				showHideCategoryPanel(e.getStateChange() == ItemEvent.SELECTED);
			}
		});
		
		JRadioButton otherButton = new JRadioButton("Other");
		otherButton.setActionCommand(OTHER);
		
		propertyGroup = new ButtonGroup();
		propertyGroup.add(residentialButton);
		propertyGroup.add(commercialButton);
		propertyGroup.add(vacantLandButton);
		propertyGroup.add(hospitalButton);
		propertyGroup.add(industrialButton);
		propertyGroup.add(schoolCommunityButton);
		propertyGroup.add(otherButton);
		
		JPanel propertyButtons = new JPanel(new GridLayout(0,2));
		propertyButtons.add(residentialButton);
		propertyButtons.add(commercialButton);
		propertyButtons.add(vacantLandButton);
		propertyButtons.add(hospitalButton);
		propertyButtons.add(industrialButton);
		propertyButtons.add(schoolCommunityButton);
		propertyButtons.add(otherButton);
		
		panel.add(BorderLayout.NORTH, propertyLabel);
		panel.add(BorderLayout.CENTER, propertyButtons);
	}
	
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
	
	public void showHideCategoryPanel(boolean show) {
		categoryGroup.clearSelection();
		categoryPanel.setVisible(show);
		this.pack();
		this.setSize(new Dimension(this.getWidth() + 60, this.getHeight() + 60));
	}
	
	public void setValuePanel(JPanel panel) {
		valueLabel = new JLabel("Enter the value of property (100.00-50 000 000.00): ");
		valueField = new JTextField();
		valueField.setEditable(true);

		panel.add(BorderLayout.NORTH, valueLabel);
		panel.add(BorderLayout.CENTER, valueField);
	}
	
	public void setCharityPanel(JPanel panel) {
		charityLabel = new JLabel("Tick if Rate Payer has a charitable status:");
		charityBox = new JCheckBox("Charitable status");
		
		panel.add(BorderLayout.NORTH, charityLabel);
		panel.add(BorderLayout.CENTER, charityBox);
	}

//	public void setRespondPanel(JPanel panel) {
//		respondTable = new JTable();
//		panel.add(BorderLayout.CENTER,respondTable);
//	}
	
	public void setButtonPanel(JPanel panel) {
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitButtonListener(this, propertyLabel, propertyGroup, categoryLabel, categoryGroup, valueField, charityBox, respondPanel));
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ExitButtonListener(this));
		
		panel.add(BorderLayout.WEST, exitButton);
		panel.add(BorderLayout.EAST, submitButton);
	}
	
	public static void createAndShowWindow() {
		CalculatePropertyTypeRatesWindow window = new CalculatePropertyTypeRatesWindow();
		window.setTitle("Calculate Property Type Rates");
		window.pack();
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		window.setResizable(false);
		window.setSize(new Dimension(window.getWidth() + 60, window.getHeight() + 60));
		System.out.println(window.getSize());
		System.out.println();
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		createAndShowWindow();	
	}

}
