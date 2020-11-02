package domain;

import utility.Validator;

/**
 * @author Takeogh
 * @author Zac
 * @author Anush
 *
 */
public class OtherProperty extends Property {

	private String specialDescription;
	private static final double CIV_RATE = 0.0030;
	private static final double FIRE_SERVICES_BASE = 150;
	private static final double FIRE_SERVICES_PERCENT = 0.00006;
	private ServiceType fireServicesLevy;
	
	public OtherProperty(String description, String location, double area, double siteValue, double capitalImprovedValue, double capitalImprovedRate, 
							double netAnnualValue, String valuationDate, RatePayer owner, String specialDescription) throws NullPointerException {
		super(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate, netAnnualValue, valuationDate, owner);
		this.setSpecialDescription(specialDescription);
	}
	
	public OtherProperty() {
		super();
		// Explicitly assign defaults for String
		this.setSpecialDescription("None");
		setCapitalImprovedRate(CIV_RATE);
	}

	public String getSpecialDescription() {
		return specialDescription;
	}

	public void setSpecialDescription(String specialDescription) throws NullPointerException {
		if (Validator.validateString("Description", specialDescription)) {
			this.specialDescription = specialDescription;
		}
		else {
			throw new NullPointerException("Description for OtherProperty is null or empty. Rejecting this record...");
		}
		
	}

	// Set up the extra services of OtherProperty property type
	@Override
	public void setUpExtraServices() {
		setHasExtraServices(true);
		fireServicesLevy = new BaseAndPercentageOfValueService("Fire Levy",
								FIRE_SERVICES_BASE,
								FIRE_SERVICES_PERCENT,
								getCapitalImprovedValue());
	}

	// Add up all the extra services charges
	@Override
	public double calculateExtraServices() {
		return fireServicesLevy.calculateChargeForServiceType();
	}

	@Override
	public String getExtraServicesDetails() {
		if (getHasExtraServices()) {
			return  "\n" + "Property extra services: [\n" + fireServicesLevy.toString() + " ]\n";
		}
		else {
			return "";
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "Property Type: OtherProperty [specialDescription=" + specialDescription + "]" + 
				getExtraServicesDetails();
	}

}
