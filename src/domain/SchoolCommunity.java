package domain;

import java.util.ArrayList;
import java.util.Arrays;

import utility.Validator;

/**
 * @author Zac
 * @author Anush
 *
 */
public class SchoolCommunity extends Property {

	private String classification;
	private String category;
	private static final String SMALL = "Small", 
								MEDIUM = "Medium",
								LARGE = "Large";
	private static final ArrayList<String> CATEGORY_LIST = new ArrayList<String>(Arrays.asList(SMALL, MEDIUM, LARGE));
	private static final double CIV_RATE = 0.0025;
	private static final int INDUSTRIAL_WASTE_DISPOSAL_UNITS = 2;
	private static final double INDUSTRIAL_WASTE_DISPOSAL_FEES = 500.00;
	private static final double FIRE_SERVICES_BASE = 200;
	private static final double FIRE_SERVICES_PERCENT = 0.00006;
	private static final double TRAFFIC_MANAGEMENT_BASE = 200.00;
	private static final double TRAFFIC_MANAGEMENT_EXTRA_SMALL = 60.00;
	private static final double TRAFFIC_MANAGEMENT_EXTRA_MEDIUM = 80.00;
	private static final double TRAFFIC_MANAGEMENT_EXTRA_LARGE = 100.00;
	private double trafficManagementExtra;
	private ServiceType industrialWasteDisposal;
	private ServiceType fireServicesLevy;
	private ServiceType trafficManagementLevy;
	
	public SchoolCommunity(String description, String location, double area, double siteValue, double capitalImprovedValue, double capitalImprovedRate,
							double netAnnualValue, String valuationDate, RatePayer owner, String classification, String category) throws NullPointerException, IllegalArgumentException {
		super(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate, netAnnualValue, valuationDate, owner);
		this.setClassification(classification);
		this.setCategory(category);
	}
	
	public SchoolCommunity(int categoryIndex) {
		this();
		this.setCategory(categoryIndex);
	}
	
	public SchoolCommunity() {
		super();
		// Explicitly assign defaults for String
		this.setClassification("Private");
		setCapitalImprovedRate(CIV_RATE);
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) throws NullPointerException {
		if (Validator.validateString("Classification", classification)) {
			this.classification = classification;
		}
		else {
			throw new NullPointerException("Classification of School/Community is null or empty. Rejecting this record...");
		}
	}

	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) throws NullPointerException, IllegalArgumentException {
		boolean exist = false;
		if (Validator.validateString("Category", category)) {
			for (int i = 0; i < CATEGORY_LIST.size(); i++) {
				if (CATEGORY_LIST.get(i).equalsIgnoreCase(category)) {
					this.setCategory(i + 1);
					exist = true;
					break;
				}
			}
			if (!exist) {
				throw new IllegalArgumentException("\"School/Community category: " + category + " is not among " + 
													"Small, Medium or Large. Rejecting this record...");
			}
		}
		else {
			throw new NullPointerException("Category of School/Community is null. Rejecting this record...");
		}
	}

	public void setCategory(int categoryIndex) {
		if (Validator.checkIntWithinRange("CategoryIndex", categoryIndex, 1, 3)) { // Make sure the categoryIndex is within range
			switch (categoryIndex)
			{
			case 1:
				this.category = SMALL;
				trafficManagementExtra = TRAFFIC_MANAGEMENT_EXTRA_SMALL;
				break;
			case 2:
				this.category = MEDIUM;
				trafficManagementExtra = TRAFFIC_MANAGEMENT_EXTRA_MEDIUM;
				break;
			case 3:
				this.category = LARGE;
				trafficManagementExtra = TRAFFIC_MANAGEMENT_EXTRA_LARGE;
				break;
			}
		}
		else {
			throw new IllegalArgumentException("Invalid School/Community category.");
		}
	}
	
	public double getTrafficManagementExtra() {
		return trafficManagementExtra;
	}

	// Set up the extra services of School Community property type
	@Override
	public void setUpExtraServices() {
		setHasExtraServices(true);
		industrialWasteDisposal = new UnitAndRateService("Industrial Waste Disposal", 
														  INDUSTRIAL_WASTE_DISPOSAL_UNITS, 
														  INDUSTRIAL_WASTE_DISPOSAL_FEES);
		fireServicesLevy = new BaseAndPercentageOfValueService("Fire Levy", FIRE_SERVICES_BASE, 
																FIRE_SERVICES_PERCENT, 
																getCapitalImprovedValue());
		trafficManagementLevy = new BaseAndExtraService("Traffic Management Levy", 
														 TRAFFIC_MANAGEMENT_BASE, 
														 trafficManagementExtra);
	}

	// Add up all the extra services charges
	@Override
	public double calculateExtraServices() {
		return industrialWasteDisposal.calculateChargeForServiceType() + 
			   fireServicesLevy.calculateChargeForServiceType() + 
			   trafficManagementLevy.calculateChargeForServiceType();
	}

	@Override
	public String getExtraServicesDetails() {
		if (getHasExtraServices()) {
			return  "\n" + "Property extra services: [\n" + industrialWasteDisposal.toString() + "\n" + 
					fireServicesLevy.toString() + "\n" + trafficManagementLevy.toString() + " ]\n";
		}
		else {
			return "";
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "Property Type: SchoolCommunity [classification=" + classification + 
									", category=" + category + "]" + 
									getExtraServicesDetails();
	}
}
