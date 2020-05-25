package domain;

import java.io.Serializable;

import utility.Validator;

/**
 * @author Takeogh
 * @author Zac
 * @author Anush
 *
 */
public class RatePayer implements Serializable {

	private String name;
	private String address;
	private String postcode;
	private String phone;
	private String type;
	private boolean charity;
	//Discount might not necessarily be on RatePayer but for convenience at the moment we place it here.
	private double charityDiscountPercentage = 0.20;
	private static final String DUMMY_VALUE = "Dummy Value";
	
	public RatePayer(String name, String address, String postcode, String phone, String type, String charity) throws IllegalArgumentException, NullPointerException {
		this.setName(name);
		this.setAddress(address);
		this.setPostcode(postcode);
		this.setPhone(phone);
		this.setType(type);
		this.setCharity(stringToBoolean("Charity status", charity));
	}
	
	public RatePayer() {
		this(DUMMY_VALUE, DUMMY_VALUE, DUMMY_VALUE, "1234567890", DUMMY_VALUE, "FALSE");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws NullPointerException {
		if (Validator.validateString("Name", name)) {
			this.name = name;
		}
		else {
			throw new NullPointerException("Name of Rate Payer is null or empty. Rejecting this record...");
		}	
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) throws NullPointerException {
		if (Validator.validateString("Address", address)) {
			this.address = address;
		}
		else {
			throw new NullPointerException("Address of Rate Payer is null or empty. Rejecting this record...");
		}	
		
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) throws NullPointerException {
		if (Validator.validateString("Postcode", postcode)) {
			this.postcode = postcode;
		}
		else {
			throw new NullPointerException("Postcode of Rate Payer is null or empty. Rejecting this record...");
		}	
		
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) throws NullPointerException, IllegalArgumentException {
		if(Validator.validateString("Phone", phone)) {
			String[] s = phone.split(" ");
			String m = "";
			for(int i  = 0; i < s.length; i++) {
				m += s[i];
			}
			if(Validator.validateStringToInt(m)) {
				if(m.length() == 10) {
					this.phone = m;
				}
				else {
					throw new IllegalArgumentException("Phone number of Rate Payer must consist of 10 digits. Rejecting this record...");
				}
			}
			else {
				throw new IllegalArgumentException("Phone number must contain numbers only. Rejecting this record...");
			}
		}
		else {
			throw new NullPointerException("Phone number of Rate Payer is null or empty. Rejecting this record...");
		}
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) throws NullPointerException {
		if (Validator.validateString("Type", postcode)) {
			this.type = type;
		}
		else {
			throw new NullPointerException("Type of Rate Payer is null or empty. Rejecting this record...");
		}	
	}

	public boolean isCharity() {
		return charity;
	}

	public void setCharity(boolean charity) {
		this.charity = charity;
	}

	public boolean stringToBoolean(String description, String booleanInString) {
		if (Validator.validateString(description, booleanInString)) {
			if (Validator.validateStringToBoolean(description, booleanInString)) {
				return Boolean.parseBoolean(booleanInString);
			}
			else {
				throw new IllegalArgumentException(description + " of Rate Payer is not a boolean. Rejecting this record...");
			}
		}
		else {
			throw new NullPointerException(description + " of Rate Payer is null or empty. Rejecting this record...");
		}
	}
	
	public double getCharityDiscountPercentage() {
		return charityDiscountPercentage;
	}

	public void setCharityDiscountPercentage(double charityDiscountPercentage) throws IllegalArgumentException {
		if (Validator.checkDoubleWithinRange("Discount Percentage", charityDiscountPercentage, 0.0, 1.0) == true) {
			this.charityDiscountPercentage = charityDiscountPercentage;
		}
		else {
			throw new IllegalArgumentException("Invalid Discount Percentage value...");
		}
		
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj.getClass().equals(this.getClass()))) {
			return false;
		}
		RatePayer other = (RatePayer) obj;		
		if (this.getName().equals(other.getName()) &&
			this.getAddress().equals(other.getAddress()) &&
			this.getPostcode().equals(other.getPostcode()) &&
			this.getPhone().equals(other.getPhone()) &&
			this.getType().equals(other.getType()) &&
			this.isCharity() == other.isCharity() &&
			this.getCharityDiscountPercentage() == other.getCharityDiscountPercentage()) {
			return true;
		}
		else {
			return false;	
		}
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode() + (int)getCharityDiscountPercentage();
	}
	
	@Override
	public String toString() {
		return "RatePayer [name=" + name + ", address=" + address + ", postcode=" + postcode + ", phone=" + phone
				+ ", type=" + type + ", charity=" + charity + ", charityDiscountPercentage=" + charityDiscountPercentage
				+ "]";
	}
}
