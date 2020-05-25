package operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import domain.Property;

/**
 * @author Zac
 *
 */
public class PropertyManager {
	
	static final String SEPERATOR = File.separator;
	private static final String LOAD_PROPERTIES_DAT = "files" + SEPERATOR + "Load_Properties.dat";
	private ArrayList<Property> listOfProperties = new ArrayList<Property>();
	
	public ArrayList<Property> getListOfProperties() {
		return listOfProperties;
	}
	
	public void loadListOfProperties() {
		
		System.out.println("Loading list of Properties...");
		
		try(FileInputStream fis = new FileInputStream(LOAD_PROPERTIES_DAT); 
			ObjectInputStream ois = new ObjectInputStream(fis);) 
		{
			System.out.println("\"Load_Properties.dat\" file is located");
			Object firstThing = ois.readObject(); 
			if(firstThing instanceof ArrayList<?>) {
				this.listOfProperties = (ArrayList<Property>) firstThing;
				System.out.println("Number of Properties: " + listOfProperties.size() + "\n");
			}
			else {
				System.out.println("First object is not an ArrayList: " + firstThing + "\n");
			}
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
}
