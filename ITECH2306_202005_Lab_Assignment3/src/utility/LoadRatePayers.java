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
import java.util.Scanner;

import domain.RatePayer;

/**
 * @author Zac
 * @author Anush
 *
 */
public class LoadRatePayers {

	static final String SEPERATOR = File.separator;
	static final String LOAD_RATEPAYERS_CSV = "files" + SEPERATOR + "ITECH2306_2020_Load_RatePayers.csv";
	static final String LOAD_RATEPAYERS_DAT = "files" + SEPERATOR + "Load_RatePayers.dat";
	static int recordNo = 0;

	public static void main(String[] args) {
		
		ArrayList<RatePayer> listOfRatePayers = new ArrayList<RatePayer>();
		
		
		System.out.println("Setting up list of Rate Payers...");

		try (Scanner fileScanner = new Scanner(new File(LOAD_RATEPAYERS_CSV));
			FileOutputStream fos = new FileOutputStream(new File(LOAD_RATEPAYERS_DAT));
			ObjectOutputStream oos = new ObjectOutputStream(fos);)
		{	
			System.out.println("\"ITECH2306_2020_Load_RatePayers.csv\" is located \n");
			
			while(fileScanner.hasNextLine()) {
				System.out.println("Record " + ++recordNo + ": ");
				
				try {
					Scanner rowScanner = new Scanner(fileScanner.nextLine());
					rowScanner.useDelimiter(",");
					
					String name = null;
					String address = null;
					String postcode = null;
					String phone = null;
					String type = null;
					String charity = null;
					
					int column = 0;
					while(rowScanner.hasNext()) {
						String stringData = null;
						
						stringData = rowScanner.next();
						stringData = stringData.trim();
						
						switch(column) {
						case 0:
							name = stringData;
							break;
						case 1:
							address = stringData;
							break;
						case 2:
							postcode = stringData;
							break;
						case 3:
							phone = stringData;
							break;
						case 4:
							type = stringData;
							break;
						case 5:
							charity = stringData;
							break;
						}
						
						column = (rowScanner.hasNext())? ++column : 0;
					}
					RatePayer payer = new RatePayer(name, address, postcode, phone, type, charity);

					listOfRatePayers.add(payer);
					System.out.println(payer);
					printRecordCreationMsg(true);
					
					rowScanner.close();
				}
				catch(NullPointerException npExc) {
					System.out.println(npExc.getMessage());
					printRecordCreationMsg(false);
				}
				catch(IllegalArgumentException iaExc) {
					System.out.println(iaExc.getMessage());
					printRecordCreationMsg(false);
				}
			}
			oos.writeObject(listOfRatePayers);
			oos.flush();
			System.out.println("Number of Rate Payers created: " + listOfRatePayers.size() + "\n");
			System.out.println("Serializable file \"Load_RatePayers.dat\" is created");
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
	
	private static void printRecordCreationMsg(boolean result) {
		System.out.println((result? "Record creation successful. " : "Record creation failed. ") + "\n");
	}

}
