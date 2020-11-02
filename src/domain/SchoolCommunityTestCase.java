package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Zac
 * @author Anush
 *
 */

public class SchoolCommunityTestCase {
	
	private Property schoolCommunity;

	// Create a SchoolCommunity object before running each test
	@Before
	public void setUp() {
		schoolCommunity = new SchoolCommunity();
	}
	
	// Test setting up description with NULL
	@Test(expected=NullPointerException.class)
	public void testNullDescription() {
		schoolCommunity.setDescription(null);
	}
	
	// Test setting up description with EMPTY STRING
	@Test(expected=NullPointerException.class)
	public void testEmptyDescription() {
		schoolCommunity.setDescription("");
	}
		
	// Test setting up a VALID description 
	@Test
	public void testSetDescription() {
		schoolCommunity.setDescription("Lot 5 PS445631");
		assertEquals("Lot 5 PS445631", schoolCommunity.getDescription());
	}
	
	// Test setting up location with NULL
	@Test(expected=NullPointerException.class)
	public void testNullLocation() {
		schoolCommunity.setLocation(null);
	}
	
	// Test setting up location with EMPTY STRING
	@Test(expected=NullPointerException.class)
	public void testEmptyLocation() {
		schoolCommunity.setLocation("");
	}
		
	// Test setting up a VALID location 
	@Test
	public void testSetLocation() {
		schoolCommunity.setLocation("1 Residence Dr Mount Helen VIC");
		assertEquals("1 Residence Dr Mount Helen VIC", schoolCommunity.getLocation());
	}
	
	// Test Area with a negative value
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeArea() {
		schoolCommunity.setArea(-1.0);
	}
	
	// Test Area with a zero value
	@Test(expected=IllegalArgumentException.class)
	public void testZeroArea() {
		schoolCommunity.setArea(0);
	}
	
	// Lower boundary testing for Area with a value should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedArea() {
		schoolCommunity.setArea(99.99);
	}
	
	// Lower boundary testing for Area with the smallest value allowed within the range
	@Test
	public void testMinAllowedArea() {
		schoolCommunity.setArea(100.0);
		assertEquals(100.0, schoolCommunity.getArea(), 0.001);
	}
	
	// Upper boundary testing for Area with the largest value allowed within the range
	@Test
	public void testMaxAllowedArea() {
		schoolCommunity.setArea(1000000000.0);
		assertEquals(1000000000.0, schoolCommunity.getArea(), 0.001);
	}

	// Upper boundary testing for Area with a value should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedArea() {
		schoolCommunity.setArea(1000000001.0);
	}
	
	// Test Site value with a negative value
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeSiteValue() {
		schoolCommunity.setSiteValue(-1.0);
	}
	
	// Test Site value with a zero value
	@Test(expected=IllegalArgumentException.class)
	public void testZeroSiteValue() {
		schoolCommunity.setSiteValue(0);
	}
	
	// Lower boundary testing for Site Value with a value should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedSiteValue() {
		schoolCommunity.setSiteValue(99.98);
	}
	
	// Lower boundary testing for Site value with the smallest value allowed within the range
	@Test
	public void testMinAllowedSiteValue() {
		schoolCommunity.setSiteValue(99.99);
		assertEquals(99.99, schoolCommunity.getSiteValue(), 0.001);
	}
	
	// Upper boundary testing for Site value with the largest value allowed within the range
	@Test
	public void testMaxAllowedSiteValue() {
		schoolCommunity.setSiteValue(49999999.99);
		assertEquals(49999999.99, schoolCommunity.getSiteValue(), 0.001);
	}
	
	// Upper boundary testing for Site Value with a value should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedSiteValue() {
		schoolCommunity.setSiteValue(50000000.0);
	}
	
	// Test CIV with a negative value
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeCIV() {
		schoolCommunity.setCapitalImprovedValue(-1.0);
	}
	
	// Test CIV with a zero value
	@Test(expected=IllegalArgumentException.class)
	public void testZeroCIV() {
		schoolCommunity.setCapitalImprovedValue(0);
	}
	
	// Lower boundary testing for CIV with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedCIV() {
		schoolCommunity.setCapitalImprovedValue(99.99);
	}
	
	// Lower boundary testing for CIV with the smallest value allowed within the range
	@Test
	public void testMinAllowedCIV() {
		schoolCommunity.setCapitalImprovedValue(100.0);
		assertEquals(100.0, schoolCommunity.getCapitalImprovedValue(), 0.001);
	}
	
	// Upper boundary testing for CIV with the largest value allowed within the range
	@Test
	public void testMaxAllowedCIV() {
		schoolCommunity.setCapitalImprovedValue(50000000.0);
		assertEquals(50000000.0, schoolCommunity.getCapitalImprovedValue(), 0.001);
	}

	// Upper boundary testing for CIV with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedCIV() {
		schoolCommunity.setCapitalImprovedValue(50000000.01);
	}

	// Test setting a CIV which is smaller than Site Value
	@Test(expected=IllegalArgumentException.class)
	public void testCIVSmallerThanSiteValue() {
		schoolCommunity.setSiteValue(10000.00);
		schoolCommunity.setCapitalImprovedValue(9999.99);
	}
	
	// Test setting a CIV which is greater than Site Value
	@Test
	public void testCIVGreaterThanSiteValue() {
		schoolCommunity.setSiteValue(10000.00);
		schoolCommunity.setCapitalImprovedValue(10000.01);
		assertEquals(10000.01, schoolCommunity.getCapitalImprovedValue(), 0.001);
	}
	
	// Test CIV Rate with a negative value
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeCIVRate() {
		schoolCommunity.setCapitalImprovedRate(-1.0);
	}
	
	// Test CIV Rate with a zero value
	@Test(expected=IllegalArgumentException.class)
	public void testZeroCIVRate() {
		schoolCommunity.setCapitalImprovedRate(0);
	}
	
	// Lower boundary testing for CIV rate with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedCIVRate() {
		schoolCommunity.setCapitalImprovedRate(0.00009);
	}
	
	// Lower boundary testing for CIV rate with the smallest value allowed within the range
	@Test
	public void testMinAllowedCIVRate() {
		schoolCommunity.setCapitalImprovedRate(0.0001);
		assertEquals(0.0001, schoolCommunity.getCapitalImprovedRate(), 0.001);
	}
	
	// Upper boundary testing for CIV rate with the largest value allowed within the range
	@Test
	public void testMaxAllowedCIVRate() {
		schoolCommunity.setCapitalImprovedRate(1.0);
		assertEquals(1.0, schoolCommunity.getCapitalImprovedRate(), 0.001);
	}

	// Upper boundary testing for CIV rate with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedCIVRate() {
		schoolCommunity.setCapitalImprovedRate(1.1);
	}
	
	// Test Net annual value with a negative value
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeNetAnnualValue() {
		schoolCommunity.setNetAnnualValue(-1.0);
	}
	
	// Test Net annual value with a zero value
	@Test(expected=IllegalArgumentException.class)
	public void testZeroNetAnnualValue() {
		schoolCommunity.setNetAnnualValue(0);
	}
	
	// Lower boundary testing for Net annual value with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedNetAnnualValue() {
		schoolCommunity.setNetAnnualValue(99.99);
	}
	
	// Lower boundary testing for Net annual value with the smallest value allowed within the range
	@Test
	public void testMinAllowedNetAnnualValue() {
		schoolCommunity.setNetAnnualValue(100.0);
		assertEquals(100.0, schoolCommunity.getNetAnnualValue(), 0.001);
	}
	
	// Upper boundary testing for Net annual value with the largest value allowed within the range
	@Test
	public void testMaxAllowedNetAnnualValue() {
		schoolCommunity.setNetAnnualValue(50000000.0);
		assertEquals(50000000.0, schoolCommunity.getNetAnnualValue(), 0.001);
	}

	// Upper boundary testing for Net annual value with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedNetAnnualValue() {
		schoolCommunity.setNetAnnualValue(50000000.01);
	}
	
	// Test setting up Valuation date with NULL
	@Test(expected=NullPointerException.class)
	public void testNullValuationDate() {
		schoolCommunity.setValuationDate(null);
	}
	
	// Test setting up Valuation date with EMPTY STRING
	@Test(expected=NullPointerException.class)
	public void testEmptyValuationDate() {
		schoolCommunity.setValuationDate("");
	}
	
	// Test setting up Valuation date with INVALID FORMAT of date in string
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidFormatValuationDate() {
		schoolCommunity.setValuationDate("02/05/2020");
	}
	
	// Test setting up a VALID Valuation date 
	@Test
	public void testSetValuationDate() {
		schoolCommunity.setValuationDate("02 May 2020");
		assertEquals("02 May 2020", schoolCommunity.getValuationDate());
	}
	
	// Test setting up a NULL Owner
	@Test(expected=NullPointerException.class)
	public void testNullRatePayer() {
		schoolCommunity.setOwner(null);
	}

	// Test setting up a VALID Owner
	@Test
	public void testValidRatePayer() {
		schoolCommunity.setOwner(new RatePayer());
		assertEquals(new RatePayer(), schoolCommunity.getOwner());
	}
	
	// Test setting up Classification with NULL
	@Test(expected=NullPointerException.class)
	public void testNullClassification() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setClassification(null);
	}
	
	// Test setting up Classification with EMPTY STRING
	@Test(expected=NullPointerException.class)
	public void testEmptyClassification() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setClassification("");
	}
		
	// Test setting up a VALID Classification 
	@Test
	public void testSetClassification() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setClassification("Public");
		assertEquals("Public", schoolCommunity.getClassification());
	}
	
	// Test setting up Category with NULL
	@Test(expected=NullPointerException.class)
	public void testNullCategoryName() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory(null);
	}
	
	// Test setting up Category with EMPTY STRING
	@Test(expected=NullPointerException.class)
	public void testEmptyCategoryName() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory("");
	}
	
	// Test setting up SMALL Category
	@Test
	public void testSmallCategoryName() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory("Small");
		assertEquals("Small", schoolCommunity.getCategory());
	}
	
	// Test setting up MEDIUM Category
	@Test
	public void testMediumCategoryName() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory("Medium");
		assertEquals("Medium", schoolCommunity.getCategory());
	}
	
	// Test setting up LARGE Category
	@Test
	public void testLargeCategoryName() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory("Large");
		assertEquals("Large", schoolCommunity.getCategory());
	}
	
	// Lower boundary testing for categoryIndex with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedCategoryIndex() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory(4);
	}
	
	// Lower boundary testing for categoryIndex with the smallest value allowed within the range
	@Test
	public void testMinAllowedCategoryIndex() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory(1);
		assertNotNull(schoolCommunity.getCategory());
	}
	
	// Upper boundary testing for categoryIndex with the largest value allowed within the range
	@Test
	public void testMaxAllowedCategoryIndex() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory(3);
		assertNotNull(schoolCommunity.getCategory());
	}
	
	// Upper boundary testing for categoryIndex with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedCategoryIndex() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory(0);
	}
	
	// Test the Traffic Management Levy value for SMALL Category
	@Test
	public void testSmallTrafficManagementLevy() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory(1);
		assertEquals(60.00, schoolCommunity.getTrafficManagementExtra(), 0.001);
	}
	
	// Test the Traffic Management Levy value for SMALL Category
	@Test
	public void testMediumTrafficManagementLevy() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory(2);
		assertEquals(80.00, schoolCommunity.getTrafficManagementExtra(), 0.001);
	}
	
	// Test the Traffic Management Levy value for SMALL Category
	@Test
	public void testLargeTrafficManagementLevy() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCategory(3);
		assertEquals(100.00, schoolCommunity.getTrafficManagementExtra(), 0.001);
	}
	
	// Test discount percentage
	@Test
	public void testDiscountPercentage() {
		schoolCommunity.getOwner().setCharity(true);
		assertEquals(0.8,(schoolCommunity.getOwner().isCharity() ? 1 - schoolCommunity.getOwner().getCharityDiscountPercentage() : 1),0.001);
	}
	
	// Test non-discount percentage
	@Test
	public void testNonDiscountPercentage() {
		schoolCommunity.getOwner().setCharity(false);
		assertEquals(1,(schoolCommunity.getOwner().isCharity() ? 1 - schoolCommunity.getOwner().getCharityDiscountPercentage() : 1), 0.001);
	}
	
	// Test multiplying CIV with CIV rate, CIV = $350,000
	@Test
	public void testCIVRateCalculation() {
		schoolCommunity.setCapitalImprovedValue(350000.00);
		assertEquals(875, (schoolCommunity.getCapitalImprovedValue() * schoolCommunity.getCapitalImprovedRate()), 0.001);
	}
	
	// Test extra services charge for SMALL Category, CIV = $350,000 
	@Test
	public void testSmallCategoryExtraServices() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCapitalImprovedValue(350000.00);
		schoolCommunity.setCategory(1);
		schoolCommunity.setUpExtraServices();
		assertEquals(1481.00, schoolCommunity.calculateExtraServices(), 0.001);
	}
	
	// Test extra services charge for MEDIUM Category, CIV = $350,000
	@Test
	public void testMediumCategoryExtraServices() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCapitalImprovedValue(350000.00);
		schoolCommunity.setCategory(2);
		schoolCommunity.setUpExtraServices();
		assertEquals(1501.00, schoolCommunity.calculateExtraServices(), 0.001);
	}
	
	// Test extra services charge for LARGE Category, CIV = $350,000
	@Test
	public void testLargeCategoryExtraServices() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCapitalImprovedValue(350000.00);
		schoolCommunity.setCategory(3);
		schoolCommunity.setUpExtraServices();
		assertEquals(1521.00, schoolCommunity.calculateExtraServices(), 0.001);
	}
	
	// Test total yearly rate for SMALL Category with NO DISCOUNT, CIV = $350,000
	@Test
	public void testNoDiscountSmallCategoryTotalRate() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCapitalImprovedValue(350000.00);
		schoolCommunity.getOwner().setCharity(false);
		schoolCommunity.setCategory(1);
		schoolCommunity.setUpExtraServices();
		assertEquals(2356.00, schoolCommunity.calculateRates(), 0.001);
	}
	
	// Test total yearly rate for SMALL Category with DISCOUNT, CIV = $350,000
	@Test
	public void testDiscountSmallCategoryTotalRate() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCapitalImprovedValue(350000.00);
		schoolCommunity.getOwner().setCharity(true);
		schoolCommunity.setCategory(1);
		schoolCommunity.setUpExtraServices();
		assertEquals(1884.80, schoolCommunity.calculateRates(), 0.001);
	}
	
	// Test total yearly rate for MEDIUM Category with NO DISCOUNT, CIV = $350,000
	@Test
	public void testNoDiscountMediumCategoryTotalRate() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCapitalImprovedValue(350000.00);
		schoolCommunity.getOwner().setCharity(false);
		schoolCommunity.setCategory(2);
		schoolCommunity.setUpExtraServices();
		assertEquals(2376.00, schoolCommunity.calculateRates(), 0.001);
	}

	// Test total yearly rate for MEDIUM Category with DISCOUNT, CIV = $350,000
	@Test
	public void testDiscountMediumCategoryTotalRate() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCapitalImprovedValue(350000.00);
		schoolCommunity.getOwner().setCharity(true);
		schoolCommunity.setCategory(2);
		schoolCommunity.setUpExtraServices();
		assertEquals(1900.80, schoolCommunity.calculateRates(), 0.001);
	}
	
	// Test total yearly rate for LARGE Category with NO DISCOUNT, CIV = $350,000
	@Test
	public void testNoDiscountLargeCategoryTotalRate() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCapitalImprovedValue(350000.00);
		schoolCommunity.getOwner().setCharity(false);
		schoolCommunity.setCategory(3);
		schoolCommunity.setUpExtraServices();
		assertEquals(2396.00, schoolCommunity.calculateRates(), 0.001);
	}

	// Test total yearly rate for LARGE Category with DISCOUNT, CIV = $350,000
	@Test
	public void testDiscountLargeCategoryTotalRate() {
		SchoolCommunity schoolCommunity = new SchoolCommunity();
		schoolCommunity.setCapitalImprovedValue(350000.00);
		schoolCommunity.getOwner().setCharity(true);
		schoolCommunity.setCategory(3);
		schoolCommunity.setUpExtraServices();
		assertEquals(1916.80, schoolCommunity.calculateRates(), 0.001);
	}
		
}
