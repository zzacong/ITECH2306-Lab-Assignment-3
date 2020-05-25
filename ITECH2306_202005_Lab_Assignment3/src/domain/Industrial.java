package domain;

import utility.Validator;

/**
 * @author Anush
 * @author Zac
 * 
 */
public class Industrial extends Property {

	private String hazardStatus;
	private boolean heavyVehicleStatus;
	private static final double CIV_RATE = 0.0059;
	private static final int INDUSTRIAL_WASTE_DISPOSAL_UNITS = 4;
	private static final double INDUSTRIAL_WASTE_DISPOSAL_FEES = 500.00;
	private static final double FIRE_SERVICES_BASE = 200;
	private static final double FIRE_SERVICES_PERCENT = 0.00006;
	private ServiceType industrialWasteDisposal;
	private ServiceType fireServicesLevy;
	
	public Industrial(String description, String location, double area, double siteValue, double capitalImprovedValue, double capitalImprovedRate, 
			   double netAnnualValue, String valuationDate, RatePayer owner, String hazardStatus, String heavyVehicleStatus) throws IllegalArgumentException, NullPointerException {
		super(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate, netAnnualValue, valuationDate, owner);
		this.setHazardStatus(hazardStatus);
		this.setHeavyVehicleStatus(stringToBoolean("heavyVehicleStatus", "Industrial", heavyVehicleStatus));
	}
	
	public Industrial() {
		super();
		// Explicitly assign defaults for String
		this.setHazardStatus("Chemicals");
		this.setHeavyVehicleStatus(true);
		setCapitalImprovedRate(CIV_RATE);
	}
	
	public String getHazardStatus() {
		return hazardStatus;
	}

	public void setHazardStatus(String hazardStatus) throws NullPointerException{
		if (Validator.validateString("Hazard status", hazardStatus)) {
			this.hazardStatus = hazardStatus;
		}
		else {
			throw new NullPointerException("Hazard status of Industrial is null or empty. Rejecting this record...");
		}
		
	}

	public boolean getHeavyVehicleStatus() {
		return heavyVehicleStatus;
	}

	public void setHeavyVehicleStatus(boolean heavyVehicleStatus) {
		this.heavyVehicleStatus = heavyVehicleStatus;
	}

	// Set up the extra services of Industrial property type
	@Override
	public void setUpExtraServices() {
		setHasExtraServices(true);
		industrialWasteDisposal = new UnitAndRateService("Industrial Waste Disposal", 
														  INDUSTRIAL_WASTE_DISPOSAL_UNITS, 
														  INDUSTRIAL_WASTE_DISPOSAL_FEES);
		fireServicesLevy = new BaseAndPercentageOfValueService("Fire Levy", FIRE_SERVICES_BASE, 
					   											FIRE_SERVICES_PERCENT, 
					   											getCapitalImprovedValue());				
	}

	// Add up all the extra services charges
	@Override
	public double calculateExtraServices() {
		return industrialWasteDisposal.calculateChargeForServiceType() + 
				fireServicesLevy.calculateChargeForServiceType();
	}

	@Override
	public String getExtraServicesDetails() {
		if (getHasExtraServices()) {
			return  "\n" + "Property extra services: [\n" + industrialWasteDisposal.toString() + "\n" + 
					fireServicesLevy.toString() + " ]\n";
		}
		else {
			return "";
		}
	}
	
	@Override 
	public String toString() {
		return  super.toString() + "Property type: Industrial [" + "hazardStatus=" + hazardStatus + 
									", heavyVehicleStatus=" + heavyVehicleStatus + "]" + 
									getExtraServicesDetails();
	}
}
