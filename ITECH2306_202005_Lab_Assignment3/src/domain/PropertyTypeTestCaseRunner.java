/**
 * 
 */
package domain;

import org.junit.runner.JUnitCore;
import java.util.ArrayList;
import org.junit.runner.Result;

/**
 * @author Zac
 * @author Anush
 *
 */
public class PropertyTypeTestCaseRunner {

	public static void main(String[] args) {
		
		Result result = JUnitCore.runClasses(SchoolCommunityTestCase.class, CommercialTestCase.class);
		ArrayList<String> emptyList = new ArrayList<String>();
		
		if (result.getFailures().equals(emptyList)) {
			System.out.println("\nAll tests ran successfully.");			
		}
		else {
			System.out.println(result.getFailures());
		}
	}

}
