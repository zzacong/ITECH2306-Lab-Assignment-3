package domain;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import utility.Validator;

/**
 * @author Takeogh
 * @author Zac
 * @author Anush
 *
 */
public abstract class Property implements Serializable {
	private String description;
	private String location;
	private double area;
	private double siteValue;
	private double capitalImprovedValue;
	private double capitalImprovedRate;
	private double netAnnualValue;
	private String valuationDate;
	private RatePayer owner;
	
	private boolean hasExtraServices = false;
	private static final double AREA_MIN = 100.0;
	private static final double AREA_MAX = 1000000000.0;
	private static final double SITE_VALUE_MIN = 99.99;
	private static final double SITE_VALUE_MAX = 49999999.99;
	private static final double CIV_MIN = 100.0;
	private static final double CIV_MAX = 50000000.0;
	private static final double CIV_RATE_MIN = 0.0001;
	private static final double CIV_RATE_MAX = 1.0;
	private static final double NET_ANNUAL_VALUE_MIN = 100.0;
	private static final double NET_ANNUAL_VALUE_MAX = 50000000.0;
	private static final DecimalFormat FORMAT_2DP = new DecimalFormat("0.00");
	private static final NumberFormat MYFORMAT = NumberFormat.getNumberInstance();
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
	protected static final String NOT_AVAILABLE = "Not Available";
	
	public Property() {
		// Explicitly assign defaults for String and date but leave the numbers to be filled with Java default values
		// Provide a default owner
		this(NOT_AVAILABLE, NOT_AVAILABLE, dateToString(LocalDate.now()), new RatePayer());
	}
	
	public Property (String description, String location, String valuationDate, RatePayer owner) {
		this.setDescription(description);
		this.setLocation(location);
		this.setValuationDate(valuationDate);
		this.setOwner(owner);
	}
	
	public Property(String description, String location, double area, double siteValue, double capitalImprovedValue, double capitalImprovedRate, 
					double netAnnualValue, String valuationDate, RatePayer owner) throws NullPointerException, IllegalArgumentException {
		this(description, location, valuationDate, owner);
		this.setArea(area);
		this.setSiteValue(siteValue);
		this.setCapitalImprovedValue(capitalImprovedValue);
		this.setCapitalImprovedRate(capitalImprovedRate);
		this.setNetAnnualValue(netAnnualValue);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws NullPointerException {
		if (Validator.validateString("Description", description)) {
			this.description = description;
		}
		else {
			throw new NullPointerException("Description of Property is null. Rejecting this record...");
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) throws NullPointerException {
		if (Validator.validateString("Location", location)) {
			this.location = location;
		}
		else {
			throw new NullPointerException("Location of Property is null. Rejecting this record...");
		}
	}

	public double getArea() {
		
		return area;
	}

	public void setArea(double area) throws IllegalArgumentException {
		if (Validator.checkDoubleWithinRange("Area", area, AREA_MIN, AREA_MAX)) {
			this.area = area;
		}
		else {
			throw new IllegalArgumentException("Invalid Area. Rejecting this record...");
		}
	}

	public double getSiteValue() {
		return siteValue;
	}

	public void setSiteValue(double siteValue) throws IllegalArgumentException {
		if (Validator.checkDoubleWithinRange("Site value", siteValue, SITE_VALUE_MIN, SITE_VALUE_MAX)) {
			this.siteValue = siteValue;
		}
		else {
			throw new IllegalArgumentException("Invalid Site value. Rejecting this record...");
		}
	}

	public double getCapitalImprovedValue() {
		return capitalImprovedValue;
	}

	public void setCapitalImprovedValue(double capitalImprovedValue) throws IllegalArgumentException {
		if (Validator.checkDoubleWithinRange("CIV", capitalImprovedValue, CIV_MIN, CIV_MAX)) {
			if(capitalImprovedValue > getSiteValue()) {
				this.capitalImprovedValue = capitalImprovedValue;
			}
			else {
				throw new IllegalArgumentException("CIV must be greater than site value. Rejecting this record...");
			}
		}
		else {
			throw new IllegalArgumentException("Invalid Capital Improved Value. Rejecting this record...");
		}
	}

	public double getCapitalImprovedRate() {
		return capitalImprovedRate;
	}

	public void setCapitalImprovedRate(double capitalImprovedRate) throws IllegalArgumentException {
		if (Validator.checkDoubleWithinRange("Capital Improved Rate", capitalImprovedRate, CIV_RATE_MIN, CIV_RATE_MAX)) {
			this.capitalImprovedRate = capitalImprovedRate;
		}
		else {
			throw new IllegalArgumentException("Invalid Capital Improved Rate. Rejecting this record...");
		}
	}
	
	public double getNetAnnualValue() {
		return netAnnualValue;
	}

	public void setNetAnnualValue(double netAnnualValue) throws IllegalArgumentException {
		if (Validator.checkDoubleWithinRange("Net annual value", netAnnualValue, NET_ANNUAL_VALUE_MIN, NET_ANNUAL_VALUE_MAX)) {
			this.netAnnualValue = netAnnualValue;
		}
		else {
			throw new IllegalArgumentException("Invalid Net annual value. Rejecting this record...");
		}
	}

	public String getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(String dateInString) throws NullPointerException, IllegalArgumentException {
		if (Validator.validateString("Valuation date", dateInString)) {
			if (Validator.validateStringToDate("Valuation date", dateInString, FORMATTER)) {
				this.valuationDate = dateInString;
			}
			else {
				throw new IllegalArgumentException("Valuation date must use format \"dd MMM yyyy\". Rejecting this record...");
			}
		}
		else {
			throw new NullPointerException("Valuation date of Property is null. Rejecting this record...");
		}
	}
	
	private static String dateToString(LocalDate date) {
		return date.format(FORMATTER);
	}

	public RatePayer getOwner() {
		return owner;
	}

	public void setOwner(RatePayer owner) throws NullPointerException {
		if(owner != null) {
			this.owner = owner;
		}	
		else {
			throw new NullPointerException("Owner of Property is null. Rejecting this record...");
		}
	}
	
	public boolean stringToBoolean(String description, String propertyType, String booleanInString) {
		if (Validator.validateString(description, booleanInString)) {
			if (Validator.validateStringToBoolean(description, booleanInString) == true) {
				return Boolean.parseBoolean(booleanInString);
			}
			else {
				throw new IllegalArgumentException(description + " of " + propertyType + " is not a boolean. Rejecting this record...");
			}
		}
		else {
			throw new NullPointerException(description + " of " + propertyType + " is null or empty. Rejecting this record...");
		}
	}
	
	public boolean getHasExtraServices() {
		return hasExtraServices;
	}

	public void setHasExtraServices(boolean hasExtraServices) {
		this.hasExtraServices = hasExtraServices;
	}

	public double calculateRates() {
		// So, we return the CIV multiplied by the CIV rate + the total of extra services all multiplied by
		// a charity discount (if applicable)
		return ((getCapitalImprovedValue() * getCapitalImprovedRate()) 
				+ calculateExtraServices()) *
				(getOwner().isCharity() ? 1 - getOwner().getCharityDiscountPercentage() : 1);
	}
	
	public abstract void setUpExtraServices();
	
	public abstract double calculateExtraServices();
	
	public abstract String getExtraServicesDetails();

	@Override
	public String toString() {
		MYFORMAT.setMinimumFractionDigits(2);
		MYFORMAT.setMaximumFractionDigits(4);
		return "Property [description=" + description + ", location=" + location + ", area=" + FORMAT_2DP.format(area) 
				+ ", siteValue=" + FORMAT_2DP.format(siteValue) + ", capitalImprovedValue=" + FORMAT_2DP.format(capitalImprovedValue)
				+ ", capitalImprovedRate=" + MYFORMAT.format(capitalImprovedRate) + ", netAnnualValue=" + FORMAT_2DP.format(netAnnualValue) 
				+ ", valuationDate=" + valuationDate + ", owner=" + owner.getName() + "] \n";
	}
	
}
