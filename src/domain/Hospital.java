package domain;

import utility.Validator;

/**
 * @author Zac
 * @author Anush
 * 
 * @version 6.26. Concrete class of abstract Property. 
 * We validate all attributes of the property and will throw an exception if any invalid input is given. 
 * Industrial Waste Management and Fire Services Levy are charged together with CIV value multiplied by a CIV rate. 
 */
public class Hospital extends Property {

	private boolean isPublic;
	private String facilities;
	private int numberOfFloors; 
	private static final double CIV_RATE = 0.0035;
	private static final int INDUSTRIAL_WASTE_DISPOSAL_UNITS = 4;
	private static final double INDUSTRIAL_WASTE_DISPOSAL_FEES = 500.00;
	private static final double FIRE_SERVICES_BASE = 200;
	private static final double FIRE_SERVICES_PERCENT = 0.00006;
	private ServiceType industrialWasteDisposal;
	private ServiceType fireServicesLevy;
	
	/**
	 * Construct a new Hospital which takes in all attributes of a property and a hospital. 
	 * Instantiate the Property class with its nine-parameter constructor by passing in the Property class attributes. 
	 * Set the Hospital class attributes based on the values passed in from the parameters. 
	 * It will throw an exception if any attribute passed in is null or invalid.
	 * 
	 * @param description A string to define the description of property
	 * @param location A string to define the location of property
	 * @param area A double to specify the area of property in square metres
	 * @param siteValue A double to specify the site value of property
	 * @param capitalImprovedValue A double to specify the Capital Improved Value of property, it should be greater than the site value
	 * @param capitalImprovedRate A double to specify the Capital Improved Rate of property
	 * @param netAnnualValue A double to specify the net annual value of property
	 * @param valuationDate A string to define the valuation date of property
	 * @param owner A RatePayer object to specify the owner of property
	 * @param isPublic A boolean value to specify the public/private status of Hospital
	 * @param facilities A string to define the facilities of Hospital
	 * @param numberOfFloors An integer to specify the number of floors of Hospital
	 * @throws NullPointerException if any parameter passed in is null
	 * @throws IllegalArgumentException if any parameter passed in invalid
	 */
	public Hospital(String description, String location, double area, double siteValue, double capitalImprovedValue, double capitalImprovedRate, 
			   double netAnnualValue, String valuationDate, RatePayer owner, String isPublic, String facilities, int numberOfFloors) throws NullPointerException, IllegalArgumentException {
		super(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate, netAnnualValue, valuationDate, owner);
		
		this.setIsPublic(stringToBoolean("isPublic value", "Hospital",isPublic));
		this.setFacilities(facilities);
		this.setNumberOfFloors(numberOfFloors);
	}
	
	
	/**
	 * Construct a new Hospital with zero parameter.
	 * Instantiate the Property class with its zero-parameter constructor. 
	 * Assign default values for Hospital class attributes. 
	 * Set the Capital Improved Rate on the Property class for this property type.
	 */
	public Hospital() {
		super();
		// Explicitly assign defaults for String
		this.setIsPublic(true);
		this.setFacilities("Clinics");
		this.setNumberOfFloors(1);
		setCapitalImprovedRate(CIV_RATE);
	}

	/**
	 * @return the public/private status of this Hospital property, true if this Hospital is public. 
	 */
	public boolean getIsPublic() {
		return isPublic;
	}

	/**
	 * @param isPublic assign the passed in boolean to the isPublic of this Hospital property 
	 */
	public void setIsPublic(boolean isPublic) {	
		this.isPublic = isPublic;
	}

	/**
	 * @return the facilities of this Hospital property
	 */
	public String getFacilities() {
		return facilities;
	}

	/**
	 * @param facilities assign the passed in string to the facilities of this Hospital property  
	 * @throws NullPointerException if the facilities is null or empty
	 */
	public void setFacilities(String facilities) throws NullPointerException {
		if (Validator.validateString("Facilities", facilities)) {
			this.facilities = facilities;
		}
		else {
			throw new NullPointerException("Facilities of Hospital is null or empty. Rejecting this record...");
		}		
		
	}

	/**
	 * @return representing the number of floors of this Hospital property
	 */
	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	/**
	 * @param numberOfFloors Assign the passed in int to the numberOfFloors of this Hospital property
	 * @throws IllegalArgumentException if the number of floors is zero, negative or out of the valid range
	 */
	public void setNumberOfFloors(int numberOfFloors) throws IllegalArgumentException {
		if(Validator.checkIntWithinRange("Number of floors", numberOfFloors, 1, 100)) {
			this.numberOfFloors = numberOfFloors;
		}
		else {
			throw new IllegalArgumentException("Invalid number of floors of Hospital. Rejecting this record...");
		}
	}

	/**
	 * Overriden method to setup the extra services on the Hospital property. For Hospital property, Industrial Waste Disposal and Fire Services Levy fees are added.
	 */
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

	/**
	 * Overriden method to calculate the extra services fees by adding them altogether.
	 * @return the extra services fees of this property
	 */
	@Override
	public double calculateExtraServices() {
		return industrialWasteDisposal.calculateChargeForServiceType() + 
				fireServicesLevy.calculateChargeForServiceType();
	}

	/**
	 * @return a string representing the toString() of the extra services for Industrial Waste Disposal and Fire Services Levy if extra services are already set up
	 */
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
	
	/**
	 * @return a string showing all the attributes on the property; and the details of extra services if there's any  
	 */
	@Override
	public String toString() {
		return super.toString() + "Property type: Hospital [isPublic=" + isPublic + ", facilities=" + facilities + 
									", numberOfFloors=" + numberOfFloors + "]" + 
									getExtraServicesDetails();
	}
}
