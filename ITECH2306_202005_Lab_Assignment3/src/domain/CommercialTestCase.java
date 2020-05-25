package domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Anush
 * @author Zac
 *
 */

public class CommercialTestCase {
	
	private Property commercial;
	
	// Create a Commercial object before running each test
	@Before
	public void setUp() {
		commercial = new Commercial();
	}
	
	// Test setting up description with NULL
	@Test(expected=NullPointerException.class)
	public void testNullDescription() {
		commercial.setDescription(null);
	}
	
	// Test setting up description with EMPTY STRING
	@Test(expected=NullPointerException.class)
	public void testEmptyDescription() {
		commercial.setDescription("");
	}
		
	// Test setting up a VALID description 
	@Test
	public void testSetDescription() {
		commercial.setDescription("Lot 5 PS445631");
		assertEquals("Lot 5 PS445631", commercial.getDescription());
	}
	
	// Test setting up location with NULL
	@Test(expected=NullPointerException.class)
	public void testNullLocation() {
		commercial.setLocation(null);
	}
	
	// Test setting up location with EMPTY STRING
	@Test(expected=NullPointerException.class)
	public void testEmptyLocation() {
		commercial.setLocation("");
	}
		
	// Test setting up a VALID location 
	@Test
	public void testSetLocation() {
		commercial.setLocation("1 Residence Dr Mount Helen VIC");
		assertEquals("1 Residence Dr Mount Helen VIC", commercial.getLocation());
	}
	
	// Test Area with a negative value
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeArea() {
		commercial.setArea(-1.0);
	}
	
	// Test Area with a zero value
	@Test(expected=IllegalArgumentException.class)
	public void testZeroArea() {
		commercial.setArea(0);
	}
	
	// Lower boundary testing for Area with a value should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedArea() {
		commercial.setArea(99.99);
	}
	
	// Lower boundary testing for Area with the smallest value allowed within the range
	@Test
	public void testMinAllowedArea() {
		commercial.setArea(100.0);
		assertEquals(100.0, commercial.getArea(), 0.001);
	}
	
	// Upper boundary testing for Area with the largest value allowed within the range
	@Test
	public void testMaxAllowedArea() {
		commercial.setArea(1000000000.0);
		assertEquals(1000000000.0, commercial.getArea(), 0.001);
	}

	// Upper boundary testing for Area with a value should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedArea() {
		commercial.setArea(1000000001.0);
	}
	
	// Test Site value with a negative value
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeSiteValue() {
		commercial.setSiteValue(-1.0);
	}
	
	// Test Site value with a zero value
	@Test(expected=IllegalArgumentException.class)
	public void testZeroSiteValue() {
		commercial.setSiteValue(0);
	}
	
	// Lower boundary testing for Site Value with a value should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedSiteValue() {
		commercial.setSiteValue(99.98);
	}
	
	// Lower boundary testing for Site value with the smallest value allowed within the range
	@Test
	public void testMinAllowedSiteValue() {
		commercial.setSiteValue(99.99);
		assertEquals(99.99, commercial.getSiteValue(), 0.001);
	}
	
	// Upper boundary testing for Site value with the largest value allowed within the range
	@Test
	public void testMaxAllowedSiteValue() {
		commercial.setSiteValue(49999999.99);
		assertEquals(49999999.99, commercial.getSiteValue(), 0.001);
	}
	
	// Upper boundary testing for Site Value with a value should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedSiteValue() {
		commercial.setSiteValue(50000000.0);
	}
	
	// Test CIV with a negative value
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeCIV() {
		commercial.setCapitalImprovedValue(-1.0);
	}
	
	// Test CIV with a zero value
	@Test(expected=IllegalArgumentException.class)
	public void testZeroCIV() {
		commercial.setCapitalImprovedValue(0);
	}
	
	// Lower boundary testing for CIV with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedCIV() {
		commercial.setCapitalImprovedValue(99.99);
	}
	
	// Lower boundary testing for CIV with the smallest value allowed within the range
	@Test
	public void testMinAllowedCIV() {
		commercial.setCapitalImprovedValue(100.0);
		assertEquals(100.0, commercial.getCapitalImprovedValue(), 0.001);
	}
	
	// Upper boundary testing for CIV with the largest value allowed within the range
	@Test
	public void testMaxAllowedCIV() {
		commercial.setCapitalImprovedValue(50000000.0);
		assertEquals(50000000.0, commercial.getCapitalImprovedValue(), 0.001);
	}

	// Upper boundary testing for CIV with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedCIV() {
		commercial.setCapitalImprovedValue(50000000.01);
	}

	// Test setting a CIV which is smaller than Site Value
	@Test(expected=IllegalArgumentException.class)
	public void testCIVSmallerThanSiteValue() {
		commercial.setSiteValue(10000.00);
		commercial.setCapitalImprovedValue(9999.99);
	}
	
	// Test setting a CIV which is greater than Site Value
	@Test
	public void testCIVGreaterThanSiteValue() {
		commercial.setSiteValue(10000.00);
		commercial.setCapitalImprovedValue(10000.01);
		assertEquals(10000.01, commercial.getCapitalImprovedValue(), 0.001);
	}
	
	// Test CIV Rate with a negative value
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeCIVRate() {
		commercial.setCapitalImprovedRate(-1.0);
	}
	
	// Test CIV Rate with a zero value
	@Test(expected=IllegalArgumentException.class)
	public void testZeroCIVRate() {
		commercial.setCapitalImprovedRate(0);
	}
	
	// Lower boundary testing for CIV rate with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedCIVRate() {
		commercial.setCapitalImprovedRate(0.00009);
	}
	
	// Lower boundary testing for CIV rate with the smallest value allowed within the range
	@Test
	public void testMinAllowedCIVRate() {
		commercial.setCapitalImprovedRate(0.0001);
		assertEquals(0.0001, commercial.getCapitalImprovedRate(), 0.001);
	}
	
	// Upper boundary testing for CIV rate with the largest value allowed within the range
	@Test
	public void testMaxAllowedCIVRate() {
		commercial.setCapitalImprovedRate(1.0);
		assertEquals(1.0, commercial.getCapitalImprovedRate(), 0.001);
	}

	// Upper boundary testing for CIV rate with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedCIVRate() {
		commercial.setCapitalImprovedRate(1.1);
	}
	
	// Test Net annual value with a negative value
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeNetAnnualValue() {
		commercial.setNetAnnualValue(-1.0);
	}
	
	// Test Net annual value with a zero value
	@Test(expected=IllegalArgumentException.class)
	public void testZeroNetAnnualValue() {
		commercial.setNetAnnualValue(0);
	}
	
	// Lower boundary testing for Net annual value with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMinRejectedNetAnnualValue() {
		commercial.setNetAnnualValue(99.99);
	}
	
	// Lower boundary testing for Net annual value with the smallest value allowed within the range
	@Test
	public void testMinAllowedNetAnnualValue() {
		commercial.setNetAnnualValue(100.0);
		assertEquals(100.0, commercial.getNetAnnualValue(), 0.001);
	}
	
	// Upper boundary testing for Net annual value with the largest value allowed within the range
	@Test
	public void testMaxAllowedNetAnnualValue() {
		commercial.setNetAnnualValue(50000000.0);
		assertEquals(50000000.0, commercial.getNetAnnualValue(), 0.001);
	}

	// Upper boundary testing for Net annual value with a value that should be rejected
	@Test(expected=IllegalArgumentException.class)
	public void testMaxRejectedNetAnnualValue() {
		commercial.setNetAnnualValue(50000000.01);
	}
	
	// Test setting up Valuation date with NULL
	@Test(expected=NullPointerException.class)
	public void testNullValuationDate() {
		commercial.setValuationDate(null);
	}
	
	// Test setting up Valuation date with EMPTY STRING
	@Test(expected=NullPointerException.class)
	public void testEmptyValuationDate() {
		commercial.setValuationDate("");
	}
	
	// Test setting up Valuation date with INVALID FORMAT of date in string
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidFormatValuationDate() {
		commercial.setValuationDate("02/05/2020");
	}
	
	// Test setting up a VALID Valuation date 
	@Test
	public void testSetValuationDate() {
		commercial.setValuationDate("02 May 2020");
		assertEquals("02 May 2020", commercial.getValuationDate());
	}
	
	// Test setting up a NULL Owner
	@Test(expected=NullPointerException.class)
	public void testNullRatePayer() {
		commercial.setOwner(null);
	}

	// Test setting up a VALID Owner
	@Test
	public void testValidRatePayer() {
		commercial.setOwner(new RatePayer());
		assertEquals(new RatePayer(), commercial.getOwner());
	}
	
	// Test setting up businessName with NULL
	@Test(expected=NullPointerException.class)
	public void testNullBusinessName() {
		Commercial commercial = new Commercial();
		commercial.setBusinessName(null);
	}
	
	// Test setting up businessName with EMPTY STRING
	@Test(expected=NullPointerException.class)
	public void testEmptyBusinessName() {
		Commercial commercial = new Commercial();
		commercial.setBusinessName("");
	}
		
	// Test setting up a VALID businessName
	@Test
	public void testSetBusinessName() {
		Commercial commercial = new Commercial();
		commercial.setBusinessName("Zac and Anush");
		assertEquals("Zac and Anush", commercial.getBusinessName());
	}
	
	// Test setting up ABN with NULL
	@Test(expected=NullPointerException.class)
	public void testNullAbn() {
		Commercial commercial = new Commercial();
		commercial.setAbn(null);
	}
	
	// Test setting up ABN with EMPTY STRING
	@Test(expected=NullPointerException.class)
	public void testEmptyAbn() {
		Commercial commercial = new Commercial();
		commercial.setAbn("");
	}
	
	// Test setting up ABN with invalid input which is not 11 digits
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidAbnSize() {
		Commercial commercial = new Commercial();
		commercial.setAbn("123 456");
	}
	
	// Test setting up ABN with invalid input which doesn't contain all numbers
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidAbnData() {
		Commercial commercial = new Commercial();
		commercial.setAbn("123 456 78A");
	}
		
	// Test setting up a VALID ABN
	@Test
	public void testSetAbn() {
		Commercial commercial = new Commercial();
		commercial.setAbn("2234 567 6678");
		assertEquals("22345676678", commercial.getAbn());
	}

	
	// Test discount percentage
	@Test
	public void testDiscountPercentage() {
		Commercial commercial = new Commercial();
		commercial.getOwner().setCharity(true);
		assertEquals(0.8,(commercial.getOwner().isCharity() ? 1 - commercial.getOwner().getCharityDiscountPercentage() : 1),0.001);
	}
	
	// Test non-discount percentage
	@Test
	public void testNonDiscountPercentage() {
		Commercial commercial = new Commercial();
		commercial.getOwner().setCharity(false);
		assertEquals(1,(commercial.getOwner().isCharity() ? 1 - commercial.getOwner().getCharityDiscountPercentage() : 1), 0.001);
	}
		
	// Test multiplying CIV with CIV rate, CIV = $45,000
	@Test
	public void testCIVRateCalculation() {
		Commercial commercial = new Commercial();
		commercial.setCapitalImprovedValue(45000.00);
		assertEquals(265.50, (commercial.getCapitalImprovedValue() * commercial.getCapitalImprovedRate()), 0.001);
	}
	 
	// Tests the extra services charge, CIV = $45,000
	@Test
	public void testCalculateExtraServices() {
		Commercial commercial = new Commercial();
		commercial.setCapitalImprovedValue(45000.00);
		commercial.setUpExtraServices();
		double output = commercial.calculateExtraServices();
		assertEquals(902.7, output, 0.001);
	}
	
	// Test total yearly rate with NO DISCOUNT, CIV = $45,000
	@Test
	public void testNoDiscountTotalRate() {
		Commercial commercial = new Commercial();
		commercial.setCapitalImprovedValue(45000.00);
		commercial.getOwner().setCharity(false);
		commercial.setUpExtraServices();
		assertEquals(1168.20, commercial.calculateRates(), 0.001);
	}
	
	// Test total yearly rate with DISCOUNT, CIV = $45,000
	@Test
	public void testDiscountTotalRate() {
		Commercial commercial = new Commercial();
		commercial.setCapitalImprovedValue(45000.00);
		commercial.getOwner().setCharity(true);
		commercial.setUpExtraServices();
		assertEquals(934.56, commercial.calculateRates(), 0.001);
	}

}
