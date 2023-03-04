package org.jfree.data;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.*; import org.jmock.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;

public class DataUtilitiesTest extends DataUtilities {
//	
	//DataUtilities calculateColumnTotal tests
	
	@Test
    public void columnTotalCheck1Vals() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(1));
    			one(values).getValue(0, 0);
    			will(returnValue(3));
    			
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0), 3, .000000001d);
    }
	
	@Test
    public void columnTotalCheck2Vals() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(2));
    			one(values).getValue(0, 0);
    			will(returnValue(3));
    			one(values).getValue(1, 0);
    			will(returnValue(2));
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0), 5, .000000001d);
    }
    
    @Test
    public void columnTotalCheck4Vals() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(4));
    			one(values).getValue(0, 0);
    			will(returnValue(3));
    			one(values).getValue(1, 0);
    			will(returnValue(2));
    			one(values).getValue(2, 0);
    			will(returnValue(3));
    			one(values).getValue(3, 0);
    			will(returnValue(2));
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0), 10, .000000001d);
    }
    
    @Test
    public void columnTotalCheckNoVals() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(0));
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0), 0, .000000001d);
    }
	 
	private static Values2D values;

    @BeforeClass
    public static void setUp3x3Matrix() throws Exception { 
    	Mockery mockingContext = new Mockery();
	    values = mockingContext.mock(Values2D.class);
	    mockingContext.checking(new Expectations() {
	        {
//		        	Creating a 3*3 matrix
	        	one(values).getRowCount();
	        	will(returnValue(3));
	        	one(values).getColumnCount();
	            will(returnValue(3));
	        	
	            one(values).getValue(0, 0);
	            will(returnValue(1.0));   
	            one(values).getValue(0, 1);
	            will(returnValue(2.0));
	            one(values).getValue(0,  2);
	            will(returnValue(4.0));
	            
	            one(values).getValue(1, 0);
	            will(returnValue(6.0));   
	            one(values).getValue(1, 1);
	            will(returnValue(2.0));
	            one(values).getValue(1,  2);
	            will(returnValue(3.0));
	            
	            one(values).getValue(2, 0);
	            will(returnValue(7.0));   
	            one(values).getValue(2, 1);
	            will(returnValue(3.0));
	            one(values).getValue(2,  2);
	            will(returnValue(1.0));
	        }
	    });
    }

//  Test for a row that is within the range of rows in our matrix
	@Test
    public void calculateRowTotal_RowWithinRange() {
        double result = DataUtilities.calculateRowTotal(values, 1);
        // verify
        assertEquals("Row total at index 1 is", 11.0, result, .000000001d);
    }
	
//  Test for a boundary row in our matrix
	@Test
    public void calculateRowTotal_RowBoundary() {
        double result = DataUtilities.calculateRowTotal(values, 2);
        // verify
        assertEquals("Row total at index 2 is", 11.0, result, .000000001d);
    }
	
//  Test for a row that is out of the range of rows in our matrix
	@Test
    public void calculateRowTotal_RowOutOfRange() {
        double result = DataUtilities.calculateRowTotal(values, 5);
        // verify
        assertEquals("Row total at index out of bound is", 0.0, result, .000000001d);
    }
	
//  Test for a negative row
	@Test
    public void calculateRowTotal_RowNegative() {
        double result = DataUtilities.calculateRowTotal(values, -1);
        // verify
        assertEquals("Row total at a negative index is", 0.0, result, .000000001d);
    }
	
//  Test to calculate row total given a null object
	@Test (expected = NullPointerException.class)
    public void calculateRowTotal_NullArg() {
        DataUtilities.calculateRowTotal(null, 1);
    }
	    
	
   private double[] exampleDoubleArray;
    private double[][] exampleDoubleArray2D;

    
    //TESTING createNumberArray()
    @Test
    public void numberArrayShouldBeCreated() {
    	exampleDoubleArray = new double[] {1.0, -2.5, -3.7, -4.1, 5.6};
    	Number[] actualNumberArray = DataUtilities.createNumberArray(exampleDoubleArray);
        assertNotNull("Testing createNumberArray using a "
        		+ "double array and ensuring the object is not null", actualNumberArray);
    }
    
    @Test
    public void numberArrayWithPositiveNumbers() {
    	exampleDoubleArray = new double[] {1.2, 2.4, 3.6, 4.3, 5.7};
    	Number[] expectedNumberArray = {1.2, 2.4, 3.6, 4.3, 5.7};
    	Number[] actualNumberArray = DataUtilities.createNumberArray(exampleDoubleArray);
        assertArrayEquals("Testing createNumberArray using a double array with only positive numbers",
        		expectedNumberArray, actualNumberArray);
    }
    
    @Test
    public void numberArrayWithNegativeNumbers() {
    	exampleDoubleArray = new double[] {-1.2, -22.5, -312.41, -4.123, -5.124};
    	Number[] expectedNumberArray = {-1.2, -22.5, -312.41, -4.123, -5.124};
    	Number[] actualNumberArray = DataUtilities.createNumberArray(exampleDoubleArray);
        assertArrayEquals("Testing createNumberArray using a double array with only negative numbers",
        		expectedNumberArray, actualNumberArray);
    }
    
    @Test
    public void numberArrayWithMixedPositiveNegativeNumbers() {
    	exampleDoubleArray = new double[] {-1.12, 2.4, 3.87, -4.912, 5.345};
    	Number[] expectedNumberArray = {-1.12, 2.4, 3.87, -4.912, 5.345};
    	Number[] actualNumberArray = DataUtilities.createNumberArray(exampleDoubleArray);
        assertArrayEquals("Testing createNumberArray using a double array with both positive and negative numbers",
        		expectedNumberArray, actualNumberArray);
    }
    

    @Test
    public void numberArrayWithZeros() {
    	exampleDoubleArray = new double[] {0.0,0.0,0.0,0.0};
    	Number[] expectedNumberArray = {0.0,0.0,0.0,0.0};
    	Number[] actualNumberArray = DataUtilities.createNumberArray(exampleDoubleArray);
        assertArrayEquals("Testing createNumberArray using a double array with only zeros",
        		expectedNumberArray, actualNumberArray);
    }
    
    @Test (expected = InvalidParameterException.class)
    public void createNumberArrayWithNull() throws InvalidParameterException {
    	
    	DataUtilities.createNumberArray(null);

    }
    
    
    @Test 
    public void createNumberArrayWithEmptyArray() {
    	exampleDoubleArray = new double[] {};
    	Number[] expectedNumberArray = {};
    	Number[] actualNumberArray = DataUtilities.createNumberArray(exampleDoubleArray);
    	assertArrayEquals("Testing createNumberArray using an empty double array", expectedNumberArray, actualNumberArray);
    }
	    
	    
	    
	  //TESTING createNumberArray2D()
	    
	    
    @Test
    public void NumberArray2DShouldBeCreated() {
    	exampleDoubleArray2D = new double[][] {{1.0, -2.5, -3.7, -4.1, 5.6}, {3.1, 1.3}};
    	Number[][] actualNumberArray2D = DataUtilities.createNumberArray2D(exampleDoubleArray2D);
        assertNotNull("Testing createNumberArray2D using a "
        		+ "2D double array and ensuring the object is not null", actualNumberArray2D);
    }
    
    @Test
    public void numberArray2DWithPositiveNumbers() {
    	exampleDoubleArray2D = new double[][] {{1.2, 2.4, 3.6, 4.3, 5.7},{1.4, 3.2, 9.1}};
    	Number[][] expectedNumberArray2D = {{1.2, 2.4, 3.6, 4.3, 5.7},{1.4, 3.2, 9.1}};
    	Number[][] actualNumberArray2D = DataUtilities.createNumberArray2D(exampleDoubleArray2D);
        assertArrayEquals("Testing createNumberArray2D using a 2D double array with only positive numbers",
        		expectedNumberArray2D, actualNumberArray2D);
    }
    
    @Test
    public void numberArray2DWithNegativeNumbers() {
    	exampleDoubleArray2D = new double[][] {{-1.2, -22.5, -312.41, -4.123, -5.124},{-3.12, -3.4, -9.13, -8.1}};
    	Number[][] expectedNumberArray2D = {{-1.2, -22.5, -312.41, -4.123, -5.124},{-3.12, -3.4, -9.13, -8.1}};
    	Number[][] actualNumberArray2D = DataUtilities.createNumberArray2D(exampleDoubleArray2D);
        assertArrayEquals("Testing createNumberArray2D using a 2D double array with only negative numbers",
        		expectedNumberArray2D, actualNumberArray2D);
    }
    
    @Test
    public void numberArray2DWithMixedPositiveNegativeNumbers() {
    	exampleDoubleArray2D = new double[][] {{-1.12, 2.4, 3.87, -4.912, 5.345}, {1.1, -2.9, -3.2, 6.4}};
    	Number[][] expectedNumberArray2D = {{-1.12, 2.4, 3.87, -4.912, 5.345}, {1.1, -2.9, -3.2, 6.4}};
    	Number[][] actualNumberArray2D = DataUtilities.createNumberArray2D(exampleDoubleArray2D);
        assertArrayEquals("Testing createNumberArray2D using a 2D double array with both positive and negative numbers",
        		expectedNumberArray2D, actualNumberArray2D);
    }
    

    @Test
    public void numberArray2DWithZeros() {
    	exampleDoubleArray2D = new double[][] {{0.0,0.0,0.0,0.0}, {0.0, 0.0}};
    	Number[][] expectedNumberArray2D = {{0.0,0.0,0.0,0.0}, {0.0, 0.0}};
    	Number[][] actualNumberArray2D = DataUtilities.createNumberArray2D(exampleDoubleArray2D);
        assertArrayEquals("Testing createNumberArray2D using a 2D double array with only zeros",
        		expectedNumberArray2D, actualNumberArray2D);
    }
    
    @Test (expected = InvalidParameterException.class)
    public void createNumberArray2DWithNull() throws InvalidParameterException {
    	
    	DataUtilities.createNumberArray2D(null);

    }
    
    
    @Test 
    public void createNumberArray2DWithEmptyArray() {
    	exampleDoubleArray2D = new double[][] {{},{}};
    	Number[][] expectedNumberArray2D = {{},{}};
    	Number[][] actualNumberArray2D = DataUtilities.createNumberArray2D(exampleDoubleArray2D);
    	assertArrayEquals("Testing createNumberArray2D using an empty 2D double array",
    			expectedNumberArray2D, actualNumberArray2D);
    }
	
	
//DataUtilities getCumulativePercentage test
	
	private static KeyedValues valuesAllPos;
	private static KeyedValues resultAllPos;
	private static KeyedValues valuesOneNeg;
	private static KeyedValues resultOneNeg;
	private static KeyedValues valuesZero;
	private static KeyedValues resultDivZero;
	//setup of mocked KeyedValues class
	@BeforeClass
	public static void setUpKeyedValues() {
		Mockery mockingContextAllPos = new Mockery();
		valuesAllPos = mockingContextAllPos.mock(KeyedValues.class);
		//creating expected key and value pairs
		//values should look like this when all are positive
		/*
		* Key  Value
		0        5
		1        9
		2        2
		*/
		mockingContextAllPos.checking(new Expectations() {
			{
				//use allowing so that the invocation can happen any number of times
				//but does not have to happen
				//we don't know exactly how the function works because we cannot see code
				//and therefore don't know how many times the invocation happens
				//or if it even happens at all
				
				//return 3 items in object
				allowing(valuesAllPos).getItemCount();
				will(returnValue(3));
				
				//return keys 0,1,2 for indexes 0,1,2
				allowing(valuesAllPos).getKey(0);
				will(returnValue(0));
				allowing(valuesAllPos).getKey(1);
				will(returnValue(1));
				allowing(valuesAllPos).getKey(2);
				will(returnValue(2));
				
				//return values 5,9,2 for keys 0,1,2
				allowing(valuesAllPos).getValue(0);
				will(returnValue(5));
				allowing(valuesAllPos).getValue(1);
				will(returnValue(9));
				allowing(valuesAllPos).getValue(2);
				will(returnValue(2));
			}
		});
		//storing actual result of calculated percentages from method
		resultAllPos = DataUtilities.getCumulativePercentages(valuesAllPos);
		
		
		Mockery mockingContextOneNeg = new Mockery();
		valuesOneNeg = mockingContextOneNeg.mock(KeyedValues.class);
		//creating expected key and value pairs
		//values should look like this when one value is negative
		/*
		* Key  Value
		0        -5
		1        9
		*/
		mockingContextOneNeg.checking(new Expectations() {
			{ 
				//return 2 items in object
				allowing(valuesOneNeg).getItemCount();
				will(returnValue(2));
				
				//return keys 0,1 for indexes 0,1
				allowing(valuesOneNeg).getKey(0);
				will(returnValue(0));
				allowing(valuesOneNeg).getKey(1);
				will(returnValue(1));
				
				//return values -5,9 for keys 0,1
				allowing(valuesOneNeg).getValue(0);
				will(returnValue(-5));
				allowing(valuesOneNeg).getValue(1);
				will(returnValue(9));
			}
		});
		//storing actual result of calculated percentages from method
		resultOneNeg = DataUtilities.getCumulativePercentages(valuesOneNeg);
		
		Mockery mockingContextZero = new Mockery();
		valuesZero = mockingContextZero.mock(KeyedValues.class);
		//creating expected key and value pairs
		//values should look like this when all values are zero
		/*
		* Key  Value
		0        0
		1        0
		*/
		mockingContextZero.checking(new Expectations() {
			{ 
				//return 2 items in object
				allowing(valuesZero).getItemCount();
				will(returnValue(2));
				
				//return keys 0,1 for indexes 0,1
				allowing(valuesZero).getKey(0);
				will(returnValue(0));
				allowing(valuesZero).getKey(1);
				will(returnValue(1));
				
				//return values 0,0 for keys 0,1
				allowing(valuesZero).getValue(0);
				will(returnValue(0));
				allowing(valuesZero).getValue(1);
				will(returnValue(0));
			}
		});
		//storing actual result of calculated percentages from method
		resultDivZero = DataUtilities.getCumulativePercentages(valuesZero);
	}

	//this section of testing covers the equivalence class of Keys and Values considered to be normal
	
	//testing for calculated percentage in key 0 when all values are positive
	@Test
	public void getCumulativePercentagesTestAllPosKey0() {
		Number expect = 0.3125;
		assertEquals(expect,resultAllPos.getValue(0));
	}
	
	//testing for calculated percentage in key 1 when all values are positive
	@Test
	public void getCumulativePercentagesTestAllPosKey1() {
		Number expect = 0.875;
		assertEquals(expect,resultAllPos.getValue(1));
	}
	
	//testing for calculated percentage in key 2 when all values are positive
	@Test
	public void getCumulativePercentagesTestAllPosKey2() {
		Number expect = 1.0;
		assertEquals(expect,resultAllPos.getValue(2));
	}
	
	//this section of testing covers the equivalence class of Keys and Values considered to create invalid results

   //testing for calculated percentage in key 0 when values are -5 and 9
	//calculated percentage would be -1.25, which is not possible to have negative percentage
	//therefore an exception or error should occur
	@Test (expected = Exception.class)
	public void getCumulativePercentagesTestOneNegKey0() {
		resultOneNeg.getValue(0);
	}
	
	//testing for calculated percentage in key 1 when values are -5 and 9
	@Test
	public void getCumulativePercentagesTestOneNegKey1N() {
		Number expect = 1.0;
		assertEquals(expect,resultOneNeg.getValue(1));
	}
	
	//testing for calculated percentage in key 0 when values are 0 and 0
	//should return NaN because 0/0 not possible
	@Test 
	public void getCumulativePercentagesTestDivZeroKey0() {
		Number expect = Double.NaN;
		assertEquals(expect,resultDivZero.getValue(0));
	}
	
	//testing for calculated percentage in key 1 when values are 0 and 0
	//should return NaN because 0/0 not possible
	@Test
	public void getCumulativePercentagesTestDivZeroKey1Zero() {
		Number expect = Double.NaN;
		assertEquals(expect,resultDivZero.getValue(1));
	}
	
	
	//testing for null values not permitted is correctly throwing exceptions
	@Test (expected = InvalidParameterException.class)
	public void getCumulativePercentagesTestNull() throws InvalidParameterException {
		DataUtilities.getCumulativePercentages(null);
	}
	
	@Test
	public void getCumulativePercentagesNullValue() {
	    Mockery mockingContext = new Mockery();
	    KeyedValues values = mockingContext.mock(KeyedValues.class);
	    //creating expected key and value pairs
	    mockingContext.checking(new Expectations() {
	        {
	            allowing(values).getItemCount();
	            will(returnValue(2));
	            
	            allowing(values).getKey(0);
	            will(returnValue(0));
	            allowing(values).getKey(1);
	            will(returnValue(1));
	            
	            allowing(values).getValue(0);
	            will(returnValue(1));
	            allowing(values).getValue(1);
	            will(returnValue(null));
	            
	        }
	    });
	    KeyedValues result = DataUtilities.getCumulativePercentages(values);
	    Number expect = 1.0;
	    assertEquals(expect,result.getValue(1));
	}

	@Test
	public void getCumulativePercentagesNegCount() {
	    Mockery mockingContext = new Mockery();
	    KeyedValues values = mockingContext.mock(KeyedValues.class);
	    //creating expected key and value pairs
	    mockingContext.checking(new Expectations() {
	        {
	            allowing(values).getItemCount();
	            will(returnValue(-1));
	            
	            allowing(values).getValue(0);
	            will(returnValue(1));
	            allowing(values).getValue(1);
	            will(returnValue(1));
	            
	        }
	    });
	    KeyedValues result = DataUtilities.getCumulativePercentages(values);
	    Number expect = 1.0;
	    assertEquals(expect,result.getValue(1));
	}

	@Test
	public void getCumulativePercentagesNegCountNullValue() {
	    Mockery mockingContext = new Mockery();
	    KeyedValues values = mockingContext.mock(KeyedValues.class);
	    //creating expected key and value pairs
	    mockingContext.checking(new Expectations() {
	        {
	            allowing(values).getItemCount();
	            will(returnValue(-1));
	            
	            allowing(values).getValue(0);
	            will(returnValue(1));
	            allowing(values).getValue(1);
	            will(returnValue(null));
	            
	        }
	    });
	    KeyedValues result = DataUtilities.getCumulativePercentages(values);
	    Number expect = 1.0;
	    assertEquals(expect,result.getValue(1));
	}
	

//  Test for DataUtilities clone(double[][] source)
	  @Test 
	  public void doubleArr() {
	      double[][] arr = {{1, 2, 3},{2, 5, 6}};
	      assertArrayEquals(arr, DataUtilities.clone(arr));
	  }

	  @Test 
	    public void doubleArrNull() {
	        double[][] arr = {{},{}};
	        assertArrayEquals(arr, DataUtilities.clone(arr));
	    }
	  
	//Test for equals
      @Test
      public void testEquals() {
          double[][] first = {{1,2,3}, {4,5,6}};
          double[][] second = {{1,2,3}, {4,5,6}};
          boolean actual = DataUtilities.equal(first, second);
          assertEquals(true,actual);
      }

      @Test
      public void EqualsFirstNull() {
          double[][] first = null;
          double[][] second = {{1,2,3}, {4,5,6}};
          boolean actual = DataUtilities.equal(first, second);
          assertEquals(false,actual);
      }

      @Test
      public void EqualsSecondNull() {
          double[][] first = {{1,2,3}, {4,5,6}};
          double[][] second = null;
          boolean actual = DataUtilities.equal(first, second);
          assertEquals(false,actual);
      }

      @Test
      public void EqualsBothNull() {
          double[][] first = null;
          double[][] second = null;
          boolean actual = DataUtilities.equal(first, second);
          assertEquals(true,actual);
      }

      @Test
      public void NotEqual() {
          double[][] first = {{1,2,3}, {4,5,6}};
          double[][] second = {{1,2,3}, {4,5}};
          boolean actual = DataUtilities.equal(first, second);
          assertEquals(false,actual);
      }

      @Test
      public void EqualDifferentLength() {
          double[][] first = {{1,2,3}};
          double[][] second = {{1,2,3}, {4,5}};
          boolean actual = DataUtilities.equal(first, second);
          assertEquals(false,actual);
      }

      
  	@Test
    public void columnTotalCheckNoRows() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(0));
    			
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0), 0, .000000001d);
    }
	
	@Test
    public void columnTotalCheckWithNull() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(1));
    			one(values).getValue(0, 0);
    			will(returnValue(null));
    			
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0), 0, .000000001d);
    }
	
	@Test
	public void columnTotalCheckWithNegativeRows() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(-1));
    			one(values).getValue(0, 0);
    			will(returnValue(1));
    			
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0), 0, .000000001d);
    }
	
	@Test
	public void columnTotalCheckWithNegativeRowsAndNull() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(-1));
    			one(values).getValue(0, 0);
    			will(returnValue(null));
    			
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0), 0, .000000001d);
    }
	
  //DataUtilities calculateColumnTotal(int[]) tests
	
  	@Test
      public void columnTotalCheckShortArrayRows() {
      	//setup
      	Mockery mocked = new Mockery();
      	final Values2D values = mocked.mock(Values2D.class);
      	mocked.checking(new Expectations() {
      		{
      			one(values).getRowCount();
      			will(returnValue(0));
      			
      		}
      	}
      			);
      	
      	//attempt
      	int[] i = {};
      	assertEquals(DataUtilities.calculateColumnTotal(values, 0, i), 0, .000000001d);
      }
  	
  	@Test
    public void columnTotalCheckOOB() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(1));
    			
    		}
    	}
    			);
    	
    	//attempt
    	int[] i = {1};
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0, i), 0, .000000001d);
    }
  	
  	@Test
    public void columnTotalCheckValidRowsLessRowCount() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(4));
    			one(values).getValue(0, 0);
	            will(returnValue(1.0)); 
	            one(values).getValue(0, 0);
	            will(returnValue(1.0)); 
	            one(values).getValue(0, 0);
	            will(returnValue(1.0)); 
	            one(values).getValue(0, 0);
	            will(returnValue(1.0)); 
	            one(values).getValue(0, 1);
	            will(returnValue(1.0));
	            one(values).getValue(0, 2);
	            will(returnValue(1.0)); 
	            one(values).getValue(0, 3);
	            will(returnValue(1.0)); 
    			
    			
    		}
    	}
    			);
    	
    	//attempt
    	int[] i = {0,0,0};
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0, i), 3, .000000001d);
    }
  	
  	@Test
    public void columnTotalCheckValidRowsIntWithNull() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getRowCount();
    			will(returnValue(3));
    			one(values).getValue(0, 0);
	            will(returnValue(null)); 
	            one(values).getValue(1, 0);
	            will(returnValue(1.0));
	            one(values).getValue(2, 0);
	            will(returnValue(1.0)); 
    			
    			
    		}
    	}
    			);
    	
    	//attempt
    	int[] i = {0,1};
    	assertEquals(DataUtilities.calculateColumnTotal(values, 0, i), 1, .000000001d);
    }
  	
	
	@Test
    public void rowTotalCheckNoRows() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getColumnCount();
    			will(returnValue(0));
    			
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateRowTotal(values, 0), 0, .000000001d);
    }
	
	@Test
    public void rowTotalCheckWithNull() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getColumnCount();
    			will(returnValue(1));
    			one(values).getValue(0, 0);
    			will(returnValue(null));
    			
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateRowTotal(values, 0), 0, .000000001d);
    }
	
	@Test
	public void rowTotalCheckWithNegativeRows() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getColumnCount();
    			will(returnValue(-1));
    			one(values).getValue(0, 0);
    			will(returnValue(1));
    			
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateRowTotal(values, 0), 0, .000000001d);
    }
	
	@Test
	public void rowTotalCheckWithNegativeRowsAndNull() {
    	//setup
    	Mockery mocked = new Mockery();
    	final Values2D values = mocked.mock(Values2D.class);
    	mocked.checking(new Expectations() {
    		{
    			one(values).getColumnCount();
    			will(returnValue(-1));
    			one(values).getValue(0, 0);
    			will(returnValue(null));
    			
    		}
    	}
    			);
    	//attempt
    	assertEquals(DataUtilities.calculateRowTotal(values, 0), 0, .000000001d);
    }
	
	
	//Test Rows with int
		@Test
	    public void rowTotalCheckShortArrayRows() {
	    	//setup
	    	Mockery mocked = new Mockery();
	    	final Values2D values = mocked.mock(Values2D.class);
	    	mocked.checking(new Expectations() {
	    		{
	    			one(values).getColumnCount();
	    			will(returnValue(0));
	    			
	    		}
	    	}
	    			);
	    	
	    	//attempt
	    	int[] i = {};
	    	assertEquals(DataUtilities.calculateRowTotal(values, 0, i), 0, .000000001d);
	    }
		
		@Test
	    public void rowTotalCheckNegative() {
	    	//setup
	    	Mockery mocked = new Mockery();
	    	final Values2D values = mocked.mock(Values2D.class);
	    	mocked.checking(new Expectations() {
	    		{
	    			one(values).getColumnCount();
	    			will(returnValue(-1));
	    			
	    		}
	    	}
	    			);
	    	
	    	//attempt
	    	int[] i = {};
	    	assertEquals(DataUtilities.calculateRowTotal(values, 0, i), 0, .000000001d);
	    }
		
		@Test
	  public void rowTotalCheckOOB() {
	  	//setup
	  	Mockery mocked = new Mockery();
	  	final Values2D values = mocked.mock(Values2D.class);
	  	mocked.checking(new Expectations() {
	  		{
	  			one(values).getColumnCount();
	  			will(returnValue(1));
	  			
	  		}
	  	}
	  			);
	  	
	  	//attempt
	  	int[] i = {1};
	  	assertEquals(DataUtilities.calculateRowTotal(values, 0, i), 0, .000000001d);
	  }
		
		@Test
	  public void RowTotalCheckValidRowsLessRowCount() {
	  	//setup
	  	Mockery mocked = new Mockery();
	  	final Values2D values = mocked.mock(Values2D.class);
	  	mocked.checking(new Expectations() {
	  		{
	  			one(values).getColumnCount();
	  			will(returnValue(4));
	  			one(values).getValue(0, 0);
		            will(returnValue(1.0)); 
		            one(values).getValue(0, 0);
		            will(returnValue(1.0)); 
		            one(values).getValue(0, 0);
		            will(returnValue(1.0)); 
		            one(values).getValue(0, 0);
		            will(returnValue(1.0)); 
		            one(values).getValue(0, 1);
		            will(returnValue(1.0));
		            one(values).getValue(0, 2);
		            will(returnValue(1.0)); 
		            one(values).getValue(0, 3);
		            will(returnValue(1.0)); 
	  			
	  			
	  		}
	  	}
	  			);
	  	
	  	//attempt
	  	int[] i = {0,0,0};
	  	assertEquals(DataUtilities.calculateRowTotal(values, 0, i), 3, .000000001d);
	  }
		
		@Test
	  public void rowTotalCheckValidRowsIntWithNull() {
	  	//setup
	  	Mockery mocked = new Mockery();
	  	final Values2D values = mocked.mock(Values2D.class);
	  	mocked.checking(new Expectations() {
	  		{
	  			one(values).getColumnCount();
	  			will(returnValue(3));
	  			one(values).getValue(0, 0);
		            will(returnValue(null)); 
		            one(values).getValue(0, 1);
		            will(returnValue(1.0));
		            one(values).getValue(0, 2);
		            will(returnValue(1.0)); 
	  			
	  			
	  		}
	  	}
	  			);
	  	
	  	//attempt
	  	int[] i = {0,1};
	  	assertEquals(DataUtilities.calculateRowTotal(values, 0, i), 1, .000000001d);
	  }
		    
}


