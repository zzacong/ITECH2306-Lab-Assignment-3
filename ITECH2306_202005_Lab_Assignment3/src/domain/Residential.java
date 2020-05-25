package domain;

import utility.Validator;

/**
 * @author Takeogh
 * @author Zac
 *
 */
public class Residential extends Property {

	private String propertyType;
	private String architecturalStyle;
	private static final double CIV_RATE = 0.0039;
	private static final int WASTE_MANAGEMENT_UNITS = 1;
	private static final double WASTE_MANAGEMENT_FEES = 350.00;
	private static final int GREEN_WASTE_MANAGEMENT_UNITS = 1;
	private static final double GREEN_WASTE_MANAGEMENT_FEES = 75.00;
	private static final double FIRE_SERVICES_BASE = 110;
	private static final double FIRE_SERVICES_PERCENT = 0.00006;
	//These would be better in a multi-element variable e.g. array but we haven't got there yet in the course
	private ServiceType wasteManagement;
	private ServiceType greenWasteManagement;
	private ServiceType fireServicesLevy;
	
	public Residential(String description, String location, double area, double siteValue, double capitalImprovedValue, double capitalImprovedRate,
					   double netAnnualValue, String valuationDate, RatePayer owner, String propertyType, String architecturalStyle) throws NullPointerException {
		super(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate, netAnnualValue, valuationDate, owner);
		this.setPropertyType(propertyType);
		this.setArchitecturalStyle(architecturalStyle);
	}
	
	public Residential() {
		super();
		// Explicitly assign defaults for Strings
		this.setPropertyType("House");
		this.setArchitecturalStyle("Modern");
		setCapitalImprovedRate(CIV_RATE);
	}
	
	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) throws NullPointerException {
		if (Validator.validateString("Type", propertyType)) {
			this.propertyType = propertyType;
		}
		else {
			throw new NullPointerException("Type of Residential is null. Rejecting this record...");
		}
	}

	public String getArchitecturalStyle() {
		return architecturalStyle;
	}

	public void setArchitecturalStyle(String architecturalStyle) throws NullPointerException {
		if (Validator.validateString("Architectural style", architecturalStyle)) {
			this.architecturalStyle = architecturalStyle;
		}
		else {
			throw new NullPointerException("Architectural style of Residential is null. Rejecting this record...");
		}
	}
	
	// Set up the extra services of Residential property type
	@Override
	public void setUpExtraServices() {
		// At this stage, this is perhaps more understandable but there may be better alternatives
		setHasExtraServices(true);
		wasteManagement = new UnitAndRateService("Waste Management",
				  WASTE_MANAGEMENT_UNITS,
				  WASTE_MANAGEMENT_FEES);
		greenWasteManagement = new UnitAndRateService("Green Waste Management",
				  GREEN_WASTE_MANAGEMENT_UNITS,
				  GREEN_WASTE_MANAGEMENT_FEES);
		fireServicesLevy = new BaseAndPercentageOfValueService("Fire Levy",
																FIRE_SERVICES_BASE,
																FIRE_SERVICES_PERCENT,
																getCapitalImprovedValue());
	}
	
	// Add up all the extra services charges
	@Override
	public double calculateExtraServices() {
		
		return wasteManagement.calculateChargeForServiceType() +
			   greenWasteManagement.calculateChargeForServiceType() +
			   fireServicesLevy.calculateChargeForServiceType();
	}

	@Override
	public String getExtraServicesDetails() {
		if (getHasExtraServices()) {
			return  "\n" + "Property extra services: [\n" + wasteManagement.toString() + "\n" + 
				greenWasteManagement.toString() + "\n" + fireServicesLevy.toString() + " ]\n";
		}
		else {
			return "";
		}
	}
	
	@Override 
	public String toString() {
		return  super.toString() + "Property type: Residential [" + 
				"Type=" + propertyType + ", architecturalStyle=" + 
				architecturalStyle + "]" + getExtraServicesDetails();
	}

}
