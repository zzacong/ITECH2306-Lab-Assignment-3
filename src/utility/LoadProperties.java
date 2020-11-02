/**
 * 
 */
package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import domain.Commercial;
import domain.Hospital;
import domain.Industrial;
import domain.OtherProperty;
import domain.Property;
import domain.RatePayer;
import domain.Residential;
import domain.SchoolCommunity;
import domain.VacantLand;
import operation.RatePayerManager;

/**
 * @author Zac
 * @author Anush
 *
 */
public class LoadProperties {

	private static final int RESIDENTIAL = 0;
	private static final int COMMERCIAL = 1;
	private static final int VACANT_LAND = 2;
	private static final int HOSPITAL = 3;
	private static final int INDUSTRIAL = 4;
	private static final int SCHOOL_COMMUNITY = 5;
	private static final int OTHER = 6;
	static final String SEPERATOR = File.separator;
	private static final String LOAD_PROPERTIES_DAT = "files" + SEPERATOR + "Load_Properties.dat";
	private static final String LOAD_PROPERTIES_CSV = "files" + SEPERATOR + "ITECH2306_2020_Load_Properties.csv";
	private static final ArrayList<String> PROPERTY_TYPE_NAMES = new ArrayList<String>(Arrays.asList("Residential", "Commercial", "VacantLand", 
																				"Hospital", "Industrial", "SchoolCommunity", "Other"));
	static int recordNo = 0;
	
	public static void main(String[] args) {
		RatePayerManager rpManager = new RatePayerManager();
		rpManager.loadListOfRatePayers();
		
		ArrayList<RatePayer> listOfRatePayers = rpManager.getListOfRatePayers();
		ArrayList<Property> listOfProperties = new ArrayList<Property>();
		
		if(!listOfRatePayers.isEmpty()) {
			System.out.println("Setting up list of Properties...");
			
			try(Scanner fileScanner = new Scanner(new File(LOAD_PROPERTIES_CSV));
					FileOutputStream fos = new FileOutputStream(new File(LOAD_PROPERTIES_DAT));
					ObjectOutputStream oos = new ObjectOutputStream(fos);) 
			{	
				System.out.println("\"ITECH2306_2020_Load_Properties.csv\" file is located \n");				
							
				while(fileScanner.hasNextLine()) {
					System.out.println("Record " + ++recordNo + ": ");
					
					try {
						Scanner rowScanner = new Scanner(fileScanner.nextLine());
						rowScanner.useDelimiter(",");
						
						String propertyType = null;
						String description = null;
						String location = null;
						double area = 0;
						double siteValue = 0;
						double capitalImprovedValue = 0;
						double capitalImprovedRate = 0;
						double netAnnualValue = 0;
						String valuationDate = null;
						RatePayer owner = null;
						String extraAttr1 = null;
						String extraAttr2= null;
						int extraAttr3 = 0;
						
						int column = 0;
						
						while(rowScanner.hasNext()) {
							String stringData = null;
							double doubleData = 0;
							
							stringData = rowScanner.next();
							stringData = stringData.trim();
							
							if(!stringData.isEmpty()) {
								if((column >= 3 && column <= 7) || column == 12) {
									doubleData = Double.parseDouble(stringData);	
								}
							}
							
							switch(column) {
							case 0:
								propertyType = stringData;
								break;
							case 1:
								description = stringData;
								break;
							case 2:
								location = stringData;
								break;
							case 3:
								area = doubleData;
								break;
							case 4:
								siteValue = doubleData;
								break;
							case 5:
								capitalImprovedValue = doubleData;
								break;
							case 6:
								capitalImprovedRate = (doubleData / 100);
								break;
							case 7:
								netAnnualValue = doubleData;
								break;
							case 8:
								valuationDate = stringData;
								break;
							case 9:
								boolean exist = false;
								for(RatePayer payer : listOfRatePayers) {
									if (payer.getName().equalsIgnoreCase(stringData)) {
										owner = payer;
										exist = true;
										break;
									}
								}
								if (!exist) {
									owner = null;
								}
								break;
							case 10:
								extraAttr1 = stringData;
								break;
							case 11:
								extraAttr2 = stringData;
								break;
							case 12:
								extraAttr3 = (int) doubleData;
								break;
							}

							column = (rowScanner.hasNext())? ++column : 0;
						}
						
						Property property = null;
						if(PROPERTY_TYPE_NAMES.contains(propertyType)) {
							int index = PROPERTY_TYPE_NAMES.indexOf(propertyType);
							switch(index) {
							case(RESIDENTIAL):
								property = new Residential(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate, 
															netAnnualValue, valuationDate, owner, extraAttr1, extraAttr2);
								break;
							case(COMMERCIAL):
								property = new Commercial(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate,
															netAnnualValue, valuationDate, owner, extraAttr1, extraAttr2);
								break;
							case(VACANT_LAND):
								property = new VacantLand();
								break;
							case(HOSPITAL):
								property = new Hospital(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate,
														netAnnualValue, valuationDate, owner, extraAttr1, extraAttr2, extraAttr3);
								break;
							case(INDUSTRIAL):
								property = new Industrial(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate,
														netAnnualValue, valuationDate, owner, extraAttr1, extraAttr2);
								break;
							case(SCHOOL_COMMUNITY):
								property = new SchoolCommunity(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate,
															netAnnualValue, valuationDate, owner, extraAttr1, extraAttr2);
								break;
							case(OTHER):
								property = new OtherProperty(description, location, area, siteValue, capitalImprovedValue, capitalImprovedRate,
															netAnnualValue, valuationDate, owner, extraAttr1);
								break;	
							}
						}
						
						if(property != null) {
							listOfProperties.add(property);				
							System.out.println(property);
							printRecordCreationMsg(true);
						}
						else {
							System.out.println("Property type is null. Rejecting this record...");
							printRecordCreationMsg(false);
						}
						
						rowScanner.close();
					}
					catch(NullPointerException npExc) {
						System.out.println(npExc.getMessage());
						printRecordCreationMsg(false);
					}
					catch(NumberFormatException nfExc) {
						System.out.println("Unable to convert string to double: " + nfExc.getMessage() + ".\nRejecting this record...");
						printRecordCreationMsg(false);
					}	
					catch(IllegalArgumentException iaExc) {
						System.out.println(iaExc.getMessage());
						printRecordCreationMsg(false);
					}
				}
				oos.writeObject(listOfProperties);
				oos.flush();
				System.out.println("Number of Properties: " + listOfProperties.size() + "\n");
				System.out.println("Serializable file \"Load_Properties.dat\" is created");
			}
			catch(FileNotFoundException fnfExc) {
				System.out.println("Unable to locate file for opening: " + fnfExc.getMessage());
				fnfExc.printStackTrace();
			}
			catch(IOException ioExc) {
				System.out.println("Problem with file processing: " + ioExc.getMessage());
				ioExc.printStackTrace();
			}
			catch(Exception otherExc) {
				System.out.println("Something went wrong: " + otherExc.getMessage());
				otherExc.printStackTrace();
			}
		}
		else {
			System.out.println("List of Rate Payers is empty. Cannot load ITECH2306_2020_Load_Properties.csv file. Aborting operation...");
		}
	}
	
	private static void printRecordCreationMsg(boolean result) {
		System.out.println((result? "Record creation successful. " : "Record creation failed. ") + "\n");
	}
}
