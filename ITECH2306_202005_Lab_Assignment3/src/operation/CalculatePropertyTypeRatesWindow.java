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

	private JPanel titlePanel = new JPanel(new BorderLayout());
	private JPanel propertyPanel = new JPanel(new BorderLayout());
	private JPanel categoryPanel = new JPanel(new BorderLayout());
	private JPanel valuePanel = new JPanel(new BorderLayout());
	private JPanel charityPanel = new JPanel(new BorderLayout());
	private JPanel respondPanel = new JPanel();
	private JPanel errorPanel = new JPanel(new BorderLayout());
//	private JPanel buttonPanel = new JPanel(new GridBagLayout());
	private JPanel buttonPanel = new JPanel(new BorderLayout());
	
	private JLabel propertyLabel;
	private JComboBox<String> propertyCombo;
	JLabel categoryLabel;
	private ButtonGroup categoryGroup;
	private JLabel valueLabel;
	private JTextField valueField;
	private JLabel charityLabel;
	private JCheckBox charityBox;
	private JButton submitButton;
	private JButton retryButton;
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
		this.setErrorPanel(this.errorPanel);
		this.setButtonPanel(this.buttonPanel);
	}
	
	public void addPanelsToFrame(Container pane) {
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
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
		
//		c.gridx = 0;
//		c.gridy = 5;
//		pane.add(this.respondPanel, c);
//		respondPanel.setVisible(false);
		
		c.gridx = 0;
		c.gridy = 6;
		pane.add(this.errorPanel, c);
		errorPanel.setVisible(false);
		
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 8;
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 7;
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
		String[] propertyTypeList = {RESIDENTIAL, COMMERCIAL, VACANT_LAND, HOSPITAL, INDUSTRIAL, SCHOOL_COMMUNITY, OTHER};
		propertyCombo = new JComboBox<String>(propertyTypeList);
//		propertyCombo.addItem(RESIDENTIAL);
//		propertyCombo.addItem(COMMERCIAL);
//		propertyCombo.addItem(VACANT_LAND);
//		propertyCombo.addItem(HOSPITAL);
//		propertyCombo.addItem(INDUSTRIAL);
//		propertyCombo.addItem(SCHOOL_COMMUNITY);
//		propertyCombo.addItem(OTHER);
//		propertyCombo.setEditable(false);
		propertyCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				showHideCategoryPanel(propertyCombo.getSelectedIndex() == 5);
			}
		});
		
		panel.add(BorderLayout.NORTH, propertyLabel);
		panel.add(BorderLayout.CENTER, propertyCombo);
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
		resizeWindow();
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
//		
//	}
	
	public void setErrorPanel(JPanel panel) {
		JLabel errorField = new JLabel("Invalid input. Unable to calculate property type rates.");
		errorField.setForeground(Color.red);
		panel.add(BorderLayout.CENTER, errorField);
	}
	
	public void setButtonPanel(JPanel panel) {
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitButtonListener(this));
		retryButton = new JButton("Do it again");
		retryButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Component component = (Component) e.getSource();
		        JFrame frame = (JFrame) SwingUtilities.getRoot(component);
		        frame.dispose();
				createAndShowWindow();
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

	public int getPropertyComboIndex() {
		return propertyCombo.getSelectedIndex();
	}
	
	public JLabel getCategoryLabel() {
		return categoryLabel;
	}

	public ButtonGroup getCategoryGroup() {
		return categoryGroup;
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

//	public JButton getExitButton() {
//		return exitButton;
//	}
	
	public void resizeWindow() {
		this.pack();
		this.setSize(new Dimension(this.getWidth() + 60, this.getHeight() + 60));
	}

	public static void createAndShowWindow() {
		CalculatePropertyTypeRatesWindow window = new CalculatePropertyTypeRatesWindow();
		window.setTitle("Calculate Property Type Rates");
		window.pack();
		window.setSize(new Dimension(window.getWidth() + 60, window.getHeight() + 60));
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		createAndShowWindow();	
	}

}
