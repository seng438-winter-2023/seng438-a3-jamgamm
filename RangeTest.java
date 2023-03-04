package org.jfree.data;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.jfree.data.KeyedValues;
import org.jfree.data.Range;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.*;

public class RangeTest {
    private Range exampleRange;
    
    private Range secondexRange;
    private Range disjointRange;
    
    
	@Before
    public void setUp() throws Exception { 
    	secondexRange = new Range(1,2);
    	disjointRange = new Range(2,3);
    }


	//max() tests
	@Test
	public void testMaxSecondNan(){
		Range result = Range.combineIgnoringNaN(new Range(1, 3), new Range(0.0/0.0,0.0/0.0));
		assertEquals(1, result.getLowerBound(), .000000001d);
		assertEquals(3, result.getUpperBound(), .000000001d);
		
	}
	
	//Range combine() tests
	@Test
    public void testRangeNextToEachOther() {
    	Range expected = new Range(-1,2);
    	exampleRange = new Range(-1, 1);
    	Range combined = Range.combine(exampleRange, secondexRange);
        assertEquals("Range should start from -1",
        expected.getLowerBound(), combined.getLowerBound(), .000000001d);
        assertEquals("Range should end at 2",
        expected.getUpperBound(), combined.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testTwoNull() {
    	Range combined = Range.combine(null, null);
        assertNull("If two null ranges are combined, null should be returned"
        		, combined);
    }
    
    @Test
    public void testOneNull() {
    	Range expected = new Range(-1,1);
    	exampleRange = new Range(-1, 1);
    	Range combined = Range.combine(exampleRange, null);
        assertEquals("Range should start from -1",
        expected.getLowerBound(), combined.getLowerBound(), .000000001d);
        assertEquals("Range should end at 2",
        expected.getUpperBound(), combined.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testRangeNotNextToEachOther() {
    	Range expected = new Range(-1,3);
    	exampleRange = new Range(-1, 1);
    	Range combined = Range.combine(exampleRange, disjointRange);
        assertEquals("Range should start from -1",
        expected.getLowerBound(), combined.getLowerBound(), .000000001d);
        assertEquals("Range should end at 3",
        expected.getUpperBound(), combined.getUpperBound(), .000000001d);
    }
    

// RANGE getCentralValue() Tests
    @Test
    public void getCentralValueBothPositive() {
    	exampleRange = new Range(10, 20);
        assertEquals("Testing getLowerBound with both positive bounds",
        15, exampleRange.getCentralValue(), .000000001d);
    }
    
    @Test
    public void getCentralValueBothNegative() {
    	exampleRange = new Range(-20, -10);
        assertEquals("Testing getLowerBound with both negative bounds",
        -15, exampleRange.getCentralValue(), .000000001d);
    }
    
 // RANGE hashCode() Tests
    @Test
    public void hashCodeReturnsInteger() {
    	exampleRange = new Range(10, 20);
    	
    	
    	
        assertTrue("Testing hashCode() returns an integer",
        		(Integer.class.isInstance(exampleRange.hashCode())));
    }
    
    @Test
    public void hashCodeReturnsUniqueHashCode() {
    	exampleRange = new Range(10, 20);
    	int hashcode1 = exampleRange.hashCode();
    	exampleRange = new Range(-50, 203);
    	
    	
    	assertNotSame("Testing hashcode actually produces unique hashcode for different range values",
    			hashcode1, exampleRange.hashCode());
    }
    
// RANGE intersects(double, double) Tests
    @Test
    public void intersectWithOverlappingRange() {
    	exampleRange = new Range(10, 20);
    	
    	
    	
        assertTrue("Testing intersect(double, double) with range values that would intersect)",
        		exampleRange.intersects(-5, 15));
    }
    
    @Test
    public void intersectWithNonOverlappingRange() {
    	exampleRange = new Range(10, 20);
    	
    	
        assertFalse("Testing intersect(double, double) with range values that would not intersect)",
        		exampleRange.intersects(-10, -5));
    }
    
    @Test
    public void intersectWithLowerInputGreaterThanLowerBound() {
    	exampleRange = new Range(10, 20);
    	
        assertTrue("Testing intersect(double, double) with lower range value larger than lowerbound of exampleRange)",
        		exampleRange.intersects(12, 30));
    }
    
    @Test
    public void intersectWithLowerInputGreaterThanLowerBoundFullyInsideRange() {
    	exampleRange = new Range(10, 20);
    	
        assertTrue("Testing intersect(double, double) with lower range value larger than lowerbound of exampleRange and higher range value within upper bound)",
        		exampleRange.intersects(12, 18));
    }
    
    @Test
    public void intersectWithLowerInputGreaterThanLowerBoundInputAndHigherInputLower() {
    	exampleRange = new Range(10, 20);
    	
        assertTrue("Testing intersect(double, double) with lower range value larger than lowerbound of exampleRange and higher input same)",
        		exampleRange.intersects(13, 12));
    }
    
    @Test
    public void intersectWithNonOverlappingRangeAboveUpperBound() {
    	exampleRange = new Range(10, 20);
    	
        assertFalse("Testing intersect(double, double) with lower range value larger than lowerbound of exampleRange and both inputs greater than upperbound",
        		exampleRange.intersects(21, 30));
    }
    
 // RANGE intersects(Range) Tests
    @Test
    public void intersectWithOverlappingRangeObject() {
    	exampleRange = new Range(10, 20);
    	
    	Range testIntersectingRange = new Range(4, 20);
    	
        assertTrue("Testing intersect(Range) with overlapping Range)",
        		exampleRange.intersects(testIntersectingRange));
    }
    
    @Test
    public void intersectWithNonOverlappingRangeObject() {
    	exampleRange = new Range(10, 20);
    	
    	Range testIntersectingRange = new Range(4, 9);
    	
        assertFalse("Testing intersect(Range) with overlapping Range)",
        		exampleRange.intersects(testIntersectingRange));
    }

    
// RANGE combineIgnoringNaN(Range, Range) Tests
    @Test
    public void combineIgnoringNaNBothRangeNull() {
    	
    	
        assertNull("Testing combineIgnoringNaN(Range1, Range2) with both range inputs as null)",
        		Range.combineIgnoringNaN(null, null));
    }
    
    @Test
    public void combineIgnoringNaNBothValidRange() {
    	Range Range1 = new Range(10.2, 20.4);
    	Range Range2 = new Range(30.2, 49.8);
    	
        assertNotNull("Testing combineIgnoringNaN(Range1, Range2) with both Range inputs as valide Range objects)",
        		Range.combineIgnoringNaN(Range1, Range2));
    }
    
    @Test
    public void combineIgnoringNaNRange1NullRange2NotNull() {
    	Range Range2 = new Range(10.2, 20.4);
    	
        assertNull("Testing combineIgnoringNaN(Range, Range) with Range1 as null and Range2 as valid Range)",
        		Range.combineIgnoringNaN(null, Range2));
    }
    
    @Test
    public void combineIgnoringNaNRange1NullRange2NaN() {
    	Range Range2 = new Range(Double.NaN, Double.NaN);
    	
        assertNull("Testing combineIgnoringNaN(Range, Range) with Range1 as null and Range2 as Range with NaN values)",
        		Range.combineIgnoringNaN(null, Range2));
    }
    

    @Test
    public void combineIgnoringNaNRange1NotNullRange2Null() {
    	Range Range1 = new Range(10.2, 20.4);
    	
        assertNull("Testing combineIgnoringNaN(Range1, Range2) with Range2 as null and Range1 as valid Range)",
        		Range.combineIgnoringNaN(Range1, null));
    }
    
    @Test
    public void combineIgnoringNaNRange1NaNRange2Null() {
    	Range Range1 = new Range(Double.NaN, Double.NaN);
    	
        assertNull("Testing combineIgnoringNaN(Range, Range) with Range2 as null and Range1 as Range with NaN values)",
        		Range.combineIgnoringNaN(Range1, null));
    }
    
    
    @Test
    public void combineIgnoringNaNBothRangeNaN() {
    	Range Range1 = new Range(Double.NaN, Double.NaN);
    	Range Range2 = new Range(Double.NaN, Double.NaN);
    	
        assertNull("Testing combineIgnoringNaN(Range, Range) with both Range inputs being Range objects with NaN values)",
        		Range.combineIgnoringNaN(Range1, Range2));
    }
    
    
    
// RANGE isNaNRange() Tests
    @Test
    public void isNanRangeWithBothValidDoubleNumbers() {
    	exampleRange = new Range(10.2, 20.4);
    	// PERHAPS USE MOCK
        assertFalse("Testing isNaNRange() with both values as valid double numbers)",
        		exampleRange.isNaNRange());
    }
    
    
    
    @Test
    public void isNanRangeWithUpperInvalidNumber() {
    	exampleRange = new Range(10.2, Double.NaN);
    	
        assertFalse("Testing isNaNRange() with valid lower bound, but invalid upper bound)",
        		exampleRange.isNaNRange());
    }
    
    
    @Test
    public void isNanRangeWithLowerInvalidNumber() {
    	exampleRange = new Range(Double.NaN, 3);
    	
        assertFalse("Testing isNaNRange() with valid upper bound, but invalid lower bound)",
        		exampleRange.isNaNRange());
    }
    
    @Test
    public void isNanRangeWithBothInvalidNumber() {
    	exampleRange = new Range(Double.NaN, Double.NaN);
    	
        assertTrue("Testing isNaNRange() with both invalid bounds)",
        		exampleRange.isNaNRange());
    }
    
    
 // RANGE getLength() Test
    @Test
    public void getLengthTestMixedPositiveNegative() {
    	exampleRange = new Range(-1, 1);
        assertEquals("Testing getLength using a range with negative lower and positive upper bound",
        2, exampleRange.getLength(), .000000001d);
    }
    
    @Test
    public void getLengthTestPositiveRange() {
    	exampleRange = new Range(1, 4);
        assertEquals("Testing getLength using a range with both positive upper and lower bounds",
        3, exampleRange.getLength(), .000000001d);
    }
    
    @Test
    public void getLengthTestNegativeRange() {
    	exampleRange = new Range(-5, -1);
        assertEquals("Testing getLength using a range with both negative upper and lower bounds",
        4, exampleRange.getLength(), .000000001d);
    }
    
    @Test
    public void getLengthTestZeroLength() {
    	exampleRange = new Range(0, 0);
        assertEquals("Testing getLength using a range with same lower and upper bound",
        0, exampleRange.getLength(), .000000001d);
    }
    
    @Test(expected = Exception.class)
    public void getLengthTestWithEmptyRange() {
    	
        //Testing getLength using an empty range object
        
        exampleRange.getLength();
    }
    
    
    // RANGE getLowerBound() Test
    @Test
    public void getLowerBoundWithNegativeInteger() {
    	exampleRange = new Range(-1, 1);
        assertEquals("Testing getLowerBound using a negative integer",
        -1, exampleRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getLowerBoundWithPositiveInteger() {
    	exampleRange = new Range(3, 5);
        assertEquals("Testing getLowerBound using a positive integer",
        3, exampleRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getLowerBoundWithSameRange() {
    	exampleRange = new Range(1, 1);
        assertEquals("Testing getLowerBound where lower and upper bound are the same",
        1, exampleRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getLowerBoundWithZero() {
    	exampleRange = new Range(0, 5);
        assertEquals("Testing getLowerBound using 0",
        0, exampleRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getLowerBoundWithDecimalNegative() {
    	exampleRange = new Range(-38.7, 58);
        assertEquals("Testing getLowerBound using a negative decimal number",
        -38.7, exampleRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getLowerBoundWithDecimalPositive() {
    	exampleRange = new Range(18, 58);
        assertEquals("Testing getLowerBound using a positive decimal number",
        18, exampleRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void getLowerBoundWithBoth0Bounds() {
    	exampleRange = new Range(0, 0);
        assertEquals("Testing getLowerBound when upper and lower bound is the same",
        0, exampleRange.getLowerBound(), .000000001d);
    }
    
    
    
    
    @Test (expected = IllegalArgumentException.class)
    public void getLowerBoundWithLowerBoundLargerThanUpperBound() {
    	
    	//setup
    	exampleRange = new Range(15, 0);
    	
    	
    	// Testing getLowerBound where the Lowerbound value is larger than the upper bound
    	double actualDouble = exampleRange.getLowerBound();
    }
    
    @Test (expected = Exception.class)
    public void getLowerBoundWithEmptyRange() {
    	// Testing getLowerBound when range is empty
        double actualDouble = exampleRange.getLowerBound();
    }
    
    
    
    
    
 // RANGE getUpperBound() Test
    @Test
    public void getUpperBoundWithNegativeInteger() {
    	exampleRange = new Range(-16, -4);
        assertEquals("Testing getUpperBound using a negative integer number",
        -4, exampleRange.getUpperBound(), .000000001d);
    }
    
    @Test
    public void getUpperBoundWithPositiveInteger() {
    	exampleRange = new Range(3, 5);
        assertEquals("Testing getUpperBound using a positive integer number",
        5, exampleRange.getUpperBound(), .000000001d);
    }
    
    @Test
    public void getUpperBoundWithZero() {
    	exampleRange = new Range(-10, 0);
        assertEquals("Testing getUpperBound using 0",
        0, exampleRange.getUpperBound(), .000000001d);
    }
    
    
    @Test
    public void getUpperBoundWithSameRange() {
    	exampleRange = new Range(1, 1);
        assertEquals("Testing getUpperBound where lower and upper bound are the same",
        1, exampleRange.getUpperBound(), .000000001d);
    }
    
    @Test
    public void getUpperBoundWithDecimalNegative() {
    	exampleRange = new Range(-38.7, -5.9);
        assertEquals("Testing getUpperBound using a negative decimal number",
        -5.9, exampleRange.getUpperBound(), .000000001d);
    }
    
    @Test
    public void getUpperBoundWithDecimalPositive() {
    	exampleRange = new Range(18, 58);
        assertEquals("Testing getUpperBound using a positive decimal number",
        58, exampleRange.getUpperBound(), .000000001d);
    }
    
    @Test(expected = Exception.class)
    public void getUpperBoundWithEmptyRange() {
    	//Testing getUpperBound when range is empty
    	double actualDouble = exampleRange.getUpperBound();
    }
    
//Range contains() tests
    
//  Range (+, +)
    
//  Test for a number that is within the range that spans from a positive to another positive number
    @Test
    public void containsWithinRange_PosPos() {
    	exampleRange = new Range(5, 10);
        assertEquals("Testing that range contains 7.0", true, exampleRange.contains(7.0));
    }
    
//  Test for the lower boundary value for a range that spans from a positive to another positive number
    @Test
    public void containsLowerBoundaryValue_PosPos() {
    	exampleRange = new Range(5, 10);
    	assertEquals("Testing that range contains 5.0", true, exampleRange.contains(5.0));
    }
    
//  Test for a value just above the lower boundary value for a range that spans from a positive to another positive number
    @Test
    public void containsAboveLowerBoundaryValue_PosPos() {
    	exampleRange = new Range(5, 10);
    	assertEquals("Testing that range contains 6.0", true, exampleRange.contains(6.0));
    }
    
//  Test for the upper boundary value for a range that spans from a positive to another positive number
    @Test
    public void containsUpperBoundaryValue_PosPos() {
    	exampleRange = new Range(5, 10);
    	assertEquals("Testing that range contains 10.0", true, exampleRange.contains(10.0));
    }
   
//  Test for a value just below the upper boundary value for a range that spans from a positive to another positive number
    @Test
    public void containsBelowUpperBoundaryValue_PosPos() {
    	exampleRange = new Range(5, 10);
    	assertEquals("Testing that range contains 9.0", true, exampleRange.contains(9.0));
    }
    
//  Test for a value outside the lower boundary value for a range that spans from a positive to another positive number
    @Test
    public void containsOutOfLowerRange_PosPos() {
    	exampleRange = new Range(5, 10);
    	assertEquals("Testing that range does not contain 4.0", false, exampleRange.contains(4.0));
    }
    
//  Test for a value outside the upper boundary value for a range that spans from a positive to another positive number
    @Test
    public void containsOutOfUpperRange_PosPos() {
    	exampleRange = new Range(5, 10);
    	assertEquals("Testing that range does not contain 11.0", false, exampleRange.contains(11.0));
    }
    
//    Range (-, +)
    
//  Test for a number that is within the range that spans from a negative to a positive number
    @Test
    public void containsWithinRange_NegPos() {
    	exampleRange = new Range(-5, 10);
        assertEquals("Testing that range contains -3.0", true, exampleRange.contains(-3.0));
    }
    
//  Test for the lower boundary value for a range that spans from a negative to a positive number
    @Test
    public void containsLowerBoundaryValue_NegPos() {
    	exampleRange = new Range(-5, 10);
    	assertEquals("Testing that range contains -5.0", true, exampleRange.contains(-5.0));
    }
    
//  Test for a value just above the lower boundary value for a range that spans from a negative to a positive number
    @Test
    public void containsAboveLowerBoundaryValue_NegPos() {
    	exampleRange = new Range(-5, 10);
    	assertEquals("Testing that range contains -4.0", true, exampleRange.contains(-4.0));
    }
    
//  Test for the upper boundary value for a range that spans from a negative to a positive number
    @Test
    public void containsUpperBoundaryValue_NegPos() {
    	exampleRange = new Range(-5, 10);
    	assertEquals("Testing that range contains 10.0", true, exampleRange.contains(10.0));
    }
    
//  Test for a value just below the upper boundary value for a range that spans from a negative to a positive number
    @Test
    public void containsBelowUpperBoundaryValue_NegPos() {
    	exampleRange = new Range(-5, 10);
    	assertEquals("Testing that range contains 9.0", true, exampleRange.contains(9.0));
    }
    
//  Test for a value outside the lower boundary value for a range that spans from a negative to a positive number
    @Test
    public void containsOutOfLowerRange_NegPos() {
    	exampleRange = new Range(-5, 10);
    	assertEquals("Testing that range does not contain -6.0", false, exampleRange.contains(-6.0));
    }
    
//  Test for a value outside the upper boundary value for a range that spans from a negative to a positive number
    @Test
    public void containsOutOfUpperRange_NegPos() {
    	exampleRange = new Range(-5, 10);
    	assertEquals("Testing that range does not contain 11.0", false, exampleRange.contains(11.0));
    }
    
    
//  Range (-, -)
    
//  Test for a number that is within the range that spans from a negative to another negative number
	@Test
	public void containsWithinRange_NegNeg() {
		exampleRange = new Range(-10, -5);
	    assertEquals("Testing that range contains -7.0", true, exampleRange.contains(-7.0));
	  }
	 
//  Test for the lower boundary value for a range that spans from a negative to another negative number
	@Test
	public void containsLowerBoundaryValue_NegNeg() {
		exampleRange = new Range(-10, -5);
	  	assertEquals("Testing that range contains -10.0", true, exampleRange.contains(-10.0));
	}
	
//  Test for a value just above the lower boundary value for a range that spans from a negative to another negative number
	@Test
    public void containsAboveLowerBoundaryValue_NegNeg() {
    	exampleRange = new Range(-10, -5);
    	assertEquals("Testing that range contains -9.0", true, exampleRange.contains(-9.0));
    }
	 
//  Test for the upper boundary value for a range that spans from a negative to another negative number
	@Test
    public void containsUpperBoundaryValue_NegNeg() {
    	exampleRange = new Range(-10, -5);
    	assertEquals("Testing that range contains -5.0", true, exampleRange.contains(-5.0));
    }
    
//  Test for a value just below the upper boundary value for a range that spans from a negative to another negative number
    @Test
    public void containsBelowUpperBoundaryValue_NegNeg() {
    	exampleRange = new Range(-10, -5);
    	assertEquals("Testing that range contains -6.0", true, exampleRange.contains(-6.0));
    }
    
//  Test for a value outside the lower boundary value for a range that spans from a negative to another negative number
    @Test
    public void containsOutOfLowerRange_NegNeg() {
    	exampleRange = new Range(-10, -5);
    	assertEquals("Testing that range does not contain -11.0", false, exampleRange.contains(-11.0));
    }
    
//  Test for a value outside the upper boundary value for a range that spans from a negative to another negative number
    @Test
    public void containsOutOfUpperRange_NegNeg() {
    	exampleRange = new Range(-10, -5);
    	assertEquals("Testing that range does not contain -4.0", false, exampleRange.contains(-4.0));
    }
    
    
    
    //expandToIncludeTest
    
    @Test
    public void expandBelowLower() {
    	exampleRange = new Range(-1,1);
    	Range expected = new Range (-3,1);
    	Range actual = Range.expandToInclude(exampleRange, -3);
    	assertEquals(expected, actual);
    }
    
    @Test
    public void expandAboveUpper() {
    	exampleRange = new Range(-1,1);
    	Range expected = new Range (-1,3);
    	Range actual = Range.expandToInclude(exampleRange, 3);
    	assertEquals(expected, actual);
    }
    
    @Test
    public void expandWithNullRange() {
    	exampleRange = new Range(-1,1);
    	Range expected = new Range (1,1);
    	Range actual = Range.expandToInclude(null, 1);
    	assertEquals(expected, actual);
    }
    
    @Test
    public void expandAlreadyIncludedValue() {
    	exampleRange = new Range(-3,1);
    	Range expected = new Range (-3,1);
    	Range actual = Range.expandToInclude(exampleRange, -2);
    	assertEquals(expected, actual);
    }
    
    //expandTest
    @Test
    public void expandTest() {
    	exampleRange = new Range(-1,1);
    	Range expected = new Range(-1.4,1.4);
    	Range actual = Range.expand(exampleRange, 0.2, 0.2);
    	assertEquals(expected, actual);
    }
    
    //equalsTest
    @Test
    public void equalFirst() {
    	exampleRange = new Range (-1,1);
    	boolean actual = exampleRange.equals(new Range (-1,1));
    	boolean expected = true;
    	assertEquals(expected,actual);
    }
    
    @Test
    public void equalNotLower() {
    	exampleRange = new Range (-1,1);
    	boolean actual = exampleRange.equals(new Range (-3,1));
    	boolean expected = false;
    	assertEquals(expected,actual);
    }
    
    @Test
    public void equalNotUpper() {
    	exampleRange = new Range (-1,1);
    	boolean actual = exampleRange.equals(new Range (-1,3));
    	boolean expected = false;
    	assertEquals(expected,actual);
    }
    
    @Test
    public void equalSecond() {
    	exampleRange = new Range (-1,1);
    	double [] notRange = new double[] {1,2,3};
    	boolean actual = exampleRange.equals(notRange);
    	boolean expected = false;
    	assertEquals(expected,actual);
    }
    
    //shiftTest
    @Test
    public void shift1() {
    	exampleRange = new Range(-2,1);
    	Range expected = new Range(-1,2);
    	Range actual = Range.shift(exampleRange, 1);
    	assertEquals(expected, actual);
    }
    
    @Test
    public void shift2() {
    	exampleRange = new Range(-1,1);
    	Range expected = new Range(1,3);
    	Range actual = Range.shift(exampleRange, 2,true);
    	assertEquals(expected, actual);
    }
    
    @Test
    public void shiftNoZero() {
    	exampleRange = new Range(-1,1);
    	Range expected = new Range(0,3);
    	Range actual = Range.shift(exampleRange, 2);
    	assertEquals(expected, actual);
    }
    
    @Test
    public void shiftAtZero() {
    	exampleRange = new Range(0,1);
    	Range expected = new Range(2,3);
    	Range actual = Range.shift(exampleRange, 2);
    	assertEquals(expected, actual);
    }
    
 // Test for constrain()
    @Test
    public void constrain_containsVal() {
        exampleRange = new Range (-5, 10);
        assertEquals("Testing the constrained value returned when the value is within range", -3.0, exampleRange.constrain(-3.0), .000000001d);
    }

    @Test
    public void constrain_ContainsValUB() {
        exampleRange = new Range (-5, 10);
        assertEquals("Testing the constrained value returned when the value is the upper boundary", 10.0, exampleRange.constrain(10.0), .000000001d);
    }

    @Test
    public void constrain_ContainsValLB() {
        exampleRange = new Range (-5, 10);
        assertEquals("Testing the constrained value returned when the value is the lower boundary", -5.0, exampleRange.constrain(-5.0), .000000001d);
    }

    @Test
    public void constrain_notContainValLower() {
        exampleRange = new Range (-5, 10);
        assertEquals("Testing the constrained value returned when the value is outside the lower boundary", -5.0, exampleRange.constrain(-6.0), .000000001d);
    }

    @Test
    public void constrain_notContainValUpper() {
        exampleRange = new Range (-5, 10);
        assertEquals("Testing the constrained value returned when the value is outside the upper boundary", 10.0, exampleRange.constrain(11.0), .000000001d);
    }
    
    
//  Test for scale(Range base, double factor)
    @Test
    public void scale_PosFac(){
        exampleRange = new Range (-5.0, 10.0);
        Range expRange = new Range (-10.0, 20.0);
        assertEquals(expRange, Range.scale(exampleRange, 2.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void scale_NegFac(){
        exampleRange = new Range (-5.0, 10.0);
        Range.scale(exampleRange, -2.0);
    }

//    Test for toString()
    @Test
    public void toStringValid() {
        exampleRange = new Range (-5.0, 10.0);
        assertEquals("Range[-5.0,10.0]", exampleRange.toString());
    }
    
    
    
    

}