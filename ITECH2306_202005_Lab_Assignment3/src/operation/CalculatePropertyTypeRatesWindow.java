/**
 * 
 */
package operation;

import javax.swing.*;
import java.awt.*;

/**
 * @author Zac
 *
 */
public class CalculatePropertyTypeRatesWindow extends JFrame {

	private static final FlowLayout FLOW_LAYOUT = new FlowLayout(FlowLayout.CENTER);
//	private static final BorderLayout BORDER_LAYOUT = new BorderLayout();
	private JPanel borderLayout;
	private JPanel titlePanel = new JPanel(FLOW_LAYOUT);
	private JPanel propertyPanel = new JPanel(FLOW_LAYOUT);
	private JPanel valuePanel = new JPanel(FLOW_LAYOUT);
	private JPanel charityPanel = new JPanel(FLOW_LAYOUT);
	
	public CalculatePropertyTypeRatesWindow() {
		setLayout(new GridLayout(5,1));
		this.setPanels();
	}
	
	public void setPanels() {
		this.setTitlePanel();
		this.setPropertyPanel();
		this.setValuePanel();
		this.setCharityPanel();
		JPanel a = new JPanel(new BorderLayout());
		a.add(BorderLayout.WEST, valuePanel);
		a.add(BorderLayout.EAST, charityPanel);
		this.add(titlePanel);
		this.add(propertyPanel);
		this.add(valuePanel);
		this.add(charityPanel);
//		this.add(a);
	}
	
	public void setTitlePanel() {
		JLabel titleLabel = new JLabel("  Council Rate Payer System");
		JLabel lineLabel = new JLabel("==================");
		JLabel subTitleLabel = new JLabel("Calculate Property Type Rates");
		
		borderLayout = new JPanel(new BorderLayout());
		borderLayout.add(BorderLayout.NORTH, titleLabel);
		borderLayout.add(BorderLayout.CENTER, lineLabel);
		borderLayout.add(BorderLayout.SOUTH, subTitleLabel);
		this.titlePanel.add(borderLayout);
	}
	
	public void setPropertyPanel() {
		JLabel textLabel = new JLabel("Choose a property type: ");
		JRadioButton residentialButton = new JRadioButton("Residential");
		JRadioButton commercialButton = new JRadioButton("Commercial");
		JRadioButton vacantLandButton = new JRadioButton("Vacant Land");
		JRadioButton hospitalButton = new JRadioButton("Hospital");
		JRadioButton industrialButton = new JRadioButton("Industrial");
		JRadioButton schoolCommunityButton = new JRadioButton("School/Community");
		JRadioButton otherButton = new JRadioButton("Other");
		ButtonGroup propertyGroup = new ButtonGroup();
		propertyGroup.add(residentialButton);
		propertyGroup.add(commercialButton);
		propertyGroup.add(vacantLandButton);
		propertyGroup.add(hospitalButton);
		propertyGroup.add(industrialButton);
		propertyGroup.add(schoolCommunityButton);
		propertyGroup.add(otherButton);
		
		JPanel propertyButtons = new JPanel(new GridLayout(4,2));
		propertyButtons.add(residentialButton);
		propertyButtons.add(commercialButton);
		propertyButtons.add(vacantLandButton);
		propertyButtons.add(hospitalButton);
		propertyButtons.add(industrialButton);
		propertyButtons.add(schoolCommunityButton);
		propertyButtons.add(otherButton);
		
		borderLayout = new JPanel(new BorderLayout());
		borderLayout.add(BorderLayout.NORTH, textLabel);
		borderLayout.add(BorderLayout.CENTER, propertyButtons);
		this.propertyPanel.add(borderLayout);
	}
	
	public void setValuePanel() {
		JLabel textLabel = new JLabel("Enter the value of property (100.00-50 000 000.00): ");
		JTextField valueField = new JTextField();
		valueField.setEditable(true);

		JPanel borderLayout = new JPanel(new BorderLayout());
		borderLayout.add(BorderLayout.NORTH, textLabel);
		borderLayout.add(BorderLayout.CENTER, valueField);
		this.valuePanel.add(borderLayout);
	}
	
	public void setCharityPanel() {
		JLabel textLabel = new JLabel("Enter the value of property (100.00-50 000 000.00): ");
		JCheckBox charityBox = new JCheckBox("Charitable status");
		JPanel borderLayout = new JPanel(new BorderLayout());
		borderLayout.add(BorderLayout.NORTH, textLabel);
		borderLayout.add(BorderLayout.CENTER, charityBox);
		this.charityPanel.add(borderLayout);
	}

	public static void createAndShowWindow() {
		CalculatePropertyTypeRatesWindow window = new CalculatePropertyTypeRatesWindow();
		window.setTitle("Border Layout Window");
		window.pack();
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		createAndShowWindow();	
	}

}
