/**
 * 
 */
package operation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Zac
 *
 */
public class CalculatePropertyTypeRatesWindow extends JFrame {

//	private JPanel borderLayout;
	private JPanel titlePanel = new JPanel(new BorderLayout());
	private JPanel propertyPanel = new JPanel(new BorderLayout());
	private JPanel valuePanel = new JPanel(new BorderLayout());
	private JPanel charityPanel = new JPanel(new BorderLayout());
	private JPanel respondPanel = new JPanel(new BorderLayout());
	private JPanel buttonPanel = new JPanel(new BorderLayout());
	
	private JLabel propertyLabel;
	private ButtonGroup propertyGroup;
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
		this.setValuePanel(this.valuePanel);
		this.setCharityPanel(this.charityPanel);
		this.setRespondPanel(this.respondPanel);
		this.setButtonPanel(this.buttonPanel);
	}
	
	public void addPanelsToFrame(Container pane) {
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10,0,0,0);

		c.gridx = 0;
		c.gridy = 0;
		pane.add(this.titlePanel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		pane.add(this.propertyPanel, c);
		
		c.gridx = 0;
		c.gridy = 3;
		pane.add(this.valuePanel, c);
		
		c.gridx = 0;
		c.gridy = 4;
		pane.add(this.charityPanel, c);
		
		c.gridx = 0;
		c.gridy = 5;
		pane.add(this.respondPanel, c);
		
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
		propertyLabel = new JLabel("Choose a property type: ");
		JRadioButton residentialButton = new JRadioButton("Residential");
		residentialButton.setActionCommand("1");
		JRadioButton commercialButton = new JRadioButton("Commercial");
		commercialButton.setActionCommand("2");
		JRadioButton vacantLandButton = new JRadioButton("Vacant Land");
		vacantLandButton.setActionCommand("3");
		JRadioButton hospitalButton = new JRadioButton("Hospital");
		hospitalButton.setActionCommand("4");
		JRadioButton industrialButton = new JRadioButton("Industrial");
		industrialButton.setActionCommand("5");
		JRadioButton schoolCommunityButton = new JRadioButton("School/Community");
		schoolCommunityButton.setActionCommand("6");
		JRadioButton otherButton = new JRadioButton("Other");
		otherButton.setActionCommand("7");
		
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
		
//		borderLayout = new JPanel(new BorderLayout());
//		borderLayout.add(BorderLayout.NORTH, textLabel);
//		borderLayout.add(BorderLayout.CENTER, propertyButtons);
//		panel.add(borderLayout);
		panel.add(BorderLayout.NORTH, propertyLabel);
		panel.add(BorderLayout.CENTER, propertyButtons);
	}
	
	public void setValuePanel(JPanel panel) {
		valueLabel = new JLabel("Enter the value of property (100.00-50 000 000.00): ");
		valueField = new JTextField();
		valueField.setEditable(true);

//		borderLayout = new JPanel(new BorderLayout());
//		borderLayout.add(BorderLayout.NORTH, textLabel);
//		borderLayout.add(BorderLayout.CENTER, valueField);
//		panel.add(borderLayout);
		panel.add(BorderLayout.NORTH, valueLabel);
		panel.add(BorderLayout.CENTER, valueField);
	}
	
	public void setCharityPanel(JPanel panel) {
		charityLabel = new JLabel("Enter the value of property (100.00-50 000 000.00): ");
		charityBox = new JCheckBox("Charitable status");
		
//		borderLayout = new JPanel(new BorderLayout());
//		borderLayout.add(BorderLayout.NORTH, textLabel);
//		borderLayout.add(BorderLayout.CENTER, charityBox);
//		panel.add(borderLayout);
		panel.add(BorderLayout.NORTH, charityLabel);
		panel.add(BorderLayout.CENTER, charityBox);
	}

	public void setRespondPanel(JPanel panel) {
		JTextArea textArea = new JTextArea("Property details here...", 5, 0);
		panel.add(BorderLayout.CENTER,textArea);
	}
	
	public void setButtonPanel(JPanel panel) {
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitButtonListener(propertyLabel, propertyGroup, valueField, charityBox));
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ExitButtonListener(this));
		
//		borderLayout = new JPanel(new BorderLayout());
//		borderLayout.add(BorderLayout.WEST, exitButton);
//		borderLayout.add(BorderLayout.EAST, submitButton);
//		panel.add(borderLayout);
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
