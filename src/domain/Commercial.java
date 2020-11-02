package domain;

import utility.Validator;

/**
 * @author Anush
 * 
 * @version 6.26. Concrete class of abstract class Property. 
 * We validate all attributes of the property and will throw an exception for the invalid inputs. 
 * Waste Management and Fire Services Levy are charged together with CIV value multiplied by a CIV rate. 
 */
public class Commercial extends Property {
	private String businessName;
	private String abn;
	private static final double CIV_RATE = 0.0059;
	private static final int WASTE_MANAGEMENT_UNITS = 2;
	private static final double WASTE_MANAGEMENT_FEES = 350.00;
	private static final double FIRE_SERVICES_BASE = 200;
	private static final double FIRE_SERVICES_PERCENT = 0.00006;
	private ServiceType wasteManagement;
	private ServiceType fireServicesLevy;

	/**
	 * Construct a new Commercial Property which inherits all attributes of Property and Commercial class. 
	 * Instantiate the Property class with its nine-parameter constructor by passing in the Property class attributes. 
	 * Set the Commercial class attributes based on the values passed in from the parameters. 
	 * It will throw an exception if any attribute passed in is null or empty.
	 * 
	 * @param description A string to define the description of property
	 * @param location A string to define the location of property
	 * @param area A double to specify the area of property in square meters
	 * @param siteValue A double to specify the site value of property
	 * @param capitalImprovedValue A double to specify the Capital Improved Value of property, it should be greater than the site value
	 * @param capitalImprovedRate A double to specify the Capital Improved Rate of property
	 * @param netAnnualValue A double to specify the net annual value of property
	 * @param valuationDate A string to define the valuation date of property
	 * @param owner A RatePayer object to specify the owner of property
	 * @param businessName A string to define the name of the Commercial property
	 * @param abn A string to define the Australian Business Number(ABN) of the Commercial property
	 * @throws NullPointerException if any parameter passed in is null or empty
	 * @throws IllegalArgumentException if any parameter passed in is invalid
	 */
	public Commercial(String description, String location, double area, double siteValue, double capitalImprovedValue, double capitalImprovedRate, 
			   double netAnnualValue, String valuationDate, RatePayer owner, String businessName, String abn) throws NullPointerException, IllegalArgumentException {
		super(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate, netAnnualValue, valuationDate, owner);
		this.setBusinessName(businessName);
		this.setAbn(abn);
	}
	
	/**
	 * Construct a new Commercial property with zero parameter.
	 * Instantiate the Property class with its zero-parameter constructor. 
	 * Assign default values for Commercial class attributes. 
	 * Set the Capital Improved Rate on the Property class for this property type.
	 */
	public Commercial() {
		super();
		// Explicitly assign defaults for String
		this.setBusinessName("Zac and Anush Pty. Ltd");
		this.setAbn("12367876543");
		setCapitalImprovedRate(CIV_RATE);
	}

	/**
	 * @return the business name of the property
	 */
	public String getBusinessName() {
		return businessName;
	}
	
	/**
	 * @param businessName assigns the passed in string as the businessName of this Commercial property  
	 * @throws NullPointerException if the businessName is null or empty
	 */
	public void setBusinessName(String businessName) throws NullPointerException {
		if (Validator.validateString("Business Name", businessName)) {
			this.businessName = businessName;
		}
		else {
			throw new NullPointerException("Business name for Commercial property is null or empty. Rejecting this record...");
		}
	}
	
	/**
	 * @return the ABN of the property
	 */
	public String getAbn() {
		return abn;
	}
	
	/**
	 * @param abn assigns the passed in string as the abn of this Commercial property  
	 * @throws NullPointerException if the abn is null or empty
	 * @throws IllegalArgumentException if the abn is not 11 digits
	 */
	public void setAbn(String abn) throws NullPointerException, IllegalArgumentException {
		if(Validator.validateString("ABN", abn)) {
			String[] s = abn.split(" ");
			String m = "";
			for(int i  = 0; i < s.length; i++) {
				m += s[i];
			}
			if(Validator.validateStringToLong(m)) {
				if(m.length() == 11) {
					this.abn = m;
				}
				else {
					throw new IllegalArgumentException("ABN must consist of 11 digits. Rejecting this record...");
				}
			}
			else {
				throw new IllegalArgumentException("ABN must contain numbers only. Rejecting this record...");
			}		
		}
		else {
			throw new NullPointerException("ABN of commercial property is null or empty. Rejecting this record...");
		}
	}
	
	/**
	 * Overridden method to setup the extra services on the Commercial property. For Commercial property, Waste Management and Fire Services Levy fees are added.
	 */
	@Override
	public void setUpExtraServices() {
		setHasExtraServices(true);
		wasteManagement = new UnitAndRateService("Waste Management",
												  WASTE_MANAGEMENT_UNITS,
												  WASTE_MANAGEMENT_FEES);
		fireServicesLevy = new BaseAndPercentageOfValueService("Fire Levy",
																FIRE_SERVICES_BASE,
																FIRE_SERVICES_PERCENT,
																getCapitalImprovedValue());		
	}

	/**
	 * Overridden method to calculate the extra services fees by adding them together.
	 * @return the extra services fees of this commercial property
	 */
	@Override
	public double calculateExtraServices() {
		return wasteManagement.calculateChargeForServiceType() +
				   fireServicesLevy.calculateChargeForServiceType();
	}
	
	/**
	 * @return a string representing the toString() of the extra services for Waste Management and Fire Services Levy if extra services are already set up
	 */
	@Override
	public String getExtraServicesDetails() {
		if (getHasExtraServices()) {
			return  "\n" + "Property extra services: [\n" + wasteManagement.toString() + "\n" + 
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
		return  super.toString() + "Property type: Commercial [" + 
									"businessName=" + businessName + ", ABN=" + abn + "]" + 
									getExtraServicesDetails();
	}

}
