/**
 * 
 */
package operation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import domain.Property;
import domain.RatePayer;

/**
 * @author Zac
 * @author Anush
 *
 */
public class QueryRatePayer extends FunctionalDialog {
	
	private static final int END = 0;
	private static final int MIN_RATE_PAYERS = 0;
	private int maxRatePayers;
	private static final String RATE_PAYER_PROMPT = "Which rate payer would you like to query? \n";
	private static final String NO_RATE_PAYER_PROMPT = "There are no Rate Payers to query. Press 0 to exit. \n";
	private String propertyPrompt;
	private String prompt;
	private ArrayList<RatePayer> listOfRatePayers = new ArrayList<RatePayer>();
	private int ratePayer;
	private ArrayList<Property> listOfProperties = new ArrayList<Property>();
	private ArrayList<Property> ownedProperties;
	private static final int MAX_NO_USER_INPUTS = 1;
	private static final DecimalFormat FORMAT_2DP = new DecimalFormat("0.00");

	RatePayerManager rManager = new RatePayerManager();
	PropertyManager pManager = new PropertyManager();
	
	public QueryRatePayer(Scanner console) {
		super(MAX_NO_USER_INPUTS, console);
		this.setListOfRatePayersAndProperties();
	}

	@Override
	protected void obtainInput(int i) {
		switch(i) {
		case 0:
			maxRatePayers = listOfRatePayers.size();
			if(listOfRatePayers.isEmpty()) {
				setPrompt(NO_RATE_PAYER_PROMPT, 0);
			}
			else {
				setPrompt(RATE_PAYER_PROMPT, 0);
			}
			ratePayer = obtainIntInput(MIN_RATE_PAYERS, maxRatePayers, prompt);
			if(ratePayer == END) {
				setStillRunning(false);
			}
			break;
		}
	}
	
	@Override
	protected void respondToInput() {
		RatePayer payer = rManager.getRatePayer(ratePayer-1);
		ownedProperties = rManager.getOwnedProperties(ratePayer-1, listOfProperties);
		if(ownedProperties.isEmpty()) {
			propertyPrompt = ("You have selected : " + payer + "\n" +
					payer.getName() + " does not have any property. \n");
		}
		else {
			propertyPrompt = ("You have selected : " + payer + "\n\n" +
		 			"The properties owned by " + payer.getName() + " are: \n\n");
		}
		setPrompt(propertyPrompt, 1);
		System.out.println(prompt);
	}
	
	public void setPrompt(String description, int index) {
		String list = "";
		int i = 1;
		switch(index) {
		case 0:
			for(RatePayer rp : listOfRatePayers) {
				list += i + ". " + rp.getName() + "\n";
				i++;
			}
			list += END + ". To exit";
			break;
		case 1:
			for(Property p : ownedProperties) {
				list += i + ". " + p.toString() + "Total Rate Costs: " + FORMAT_2DP.format(p.calculateRates()) + "\n\n";
				i++;
			}
			break;
		}
		this.prompt = description + list;
	}

	private void setListOfRatePayersAndProperties() {
		rManager.loadListOfRatePayers();
		this.listOfRatePayers = rManager.getListOfRatePayers();
		
		pManager.loadListOfProperties();
		this.listOfProperties = pManager.getListOfProperties();
	}
	
	private int obtainIntInput(int min, int max, String prompt) {
		System.out.println(prompt);
		return validateInt(min, max);
	}
	
	private int validateInt(int min, int max) {
		int userInput;
		do {
			System.out.print("Enter a selection ("+min + "-" + max +"):");
			if(!getScanner().hasNextInt())
				userInput = max+1;
			else
				userInput = getScanner().nextInt();	// obtain the input
			getScanner().nextLine();					// gets rid of the newline after the number we just read
			if(userInput < min || userInput > max)
				System.out.println("Invalid choice.");
		} while(userInput < min || userInput > max);
		System.out.println();		// put a space before the next output	
		return userInput;
	}

}
