package utility;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * @author Zac
 * @author Anush
 *
 */
public class Validator {

	private static final NumberFormat MYFORMAT = NumberFormat.getNumberInstance();

	public static boolean checkIntWithinRange(String description, int inputInt, int min, int max) {
		if(min >= max) {
			System.out.println("Min must be smaller than Max.");
			return false;
		}
		else {
			if(inputInt <= 0) {
				System.out.println(description + " cannot be zero or negative.");
				return false;
			}
			if(inputInt < min || inputInt > max) {
				System.out.println(description + ": " + inputInt + " is out of the valid range: (" + min + "-" + max + ").");
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	public static boolean checkDoubleWithinRange(String description, double inputDouble, double min, double max) {
		MYFORMAT.setMinimumFractionDigits(2);
		MYFORMAT.setMaximumFractionDigits(4);
		if(min >= max) {
			System.out.println("Min must be smaller than Max.");
			return false;
		}
		else {
			if(inputDouble <= 0) {
				System.out.println(description + " cannot be zero or negative.");
				return false;
			}
			if(inputDouble < min || inputDouble > max) {
				System.out.println(description + ": " + MYFORMAT.format(inputDouble) + 
									" is out of the valid range (" + MYFORMAT.format(min) + "-" + MYFORMAT.format(max) + ").");
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	public static boolean validateString(String description, String inputString) {
		if(inputString == null || inputString.isEmpty()) {
			System.out.println("Input string: " + description + " cannot be null or empty.");
			return false;
		}
		else {
			return true;
		}
	}
	
	public static boolean validateStringToDate(String description, String dateInString, DateTimeFormatter formatter) {
		try {
			LocalDate.parse(dateInString, formatter);
			return true;	
		}
		catch (DateTimeParseException dtpExc) {
			System.out.println("Unable to convert string to date: " + dtpExc.getMessage());
			return false;
		}
	}
	
	public static boolean validateStringToInt(String intInString) {
		try {
		    Integer.parseInt(intInString);
		    return true;
		} catch (NumberFormatException nfExc) {
			System.out.println("Unable to convert string to integer: " + nfExc.getMessage());
		    return false;
		}
	}
	
	public static boolean validateStringToLong(String longInString) {
		try {
		    Long.parseLong(longInString);
		    return true;
		} catch (NumberFormatException nfExc) {
			System.out.println("Unable to convert string to long: " + nfExc.getMessage());
		    return false;
		}
	}
	
	// There exists no method to test where a String encodes a boolean, hence the value of the string is tested.
	public static boolean validateStringToBoolean(String description, String input) {
		if(input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
			return true;
		}
		else {
			System.out.println("Input boolean: " + description + " is not boolean.");
			return false;
		}				
	}
	
}
