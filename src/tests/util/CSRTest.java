package tests.util;

import static org.junit.Assert.assertEquals;
import junit.framework.AssertionFailedError;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.CRS;

public class CSRTest {

//	double [][] sparseMatrix = {
//	{0,0,11,0,0,0,0,0,0,0,0,0},
//	{0,0,0,0,0,0,0,0,0,0,0,0},
//	{0,0,0,0,0,0,4,0,0,0,0,0},
//	{0,0,0,9,0,0,0,0,0,5,0,0},
//	{0,0,0,0,0,0,0,0,0,0,0,0},
//	{0,0,0,3,0,14,0,0,0,0,0,7}
//	};
	
	double [][] sparseMatrix = {
	
			{ 10, 0,  0,  0, -2,  0  },
			{ 3,  9,  0,  0,  0,  3  },
			{ 0,  7,  8,  7,  0,  0  },
			{ 3,  0,  8,  7,  5,  0  },
			{ 0,  8,  0,  9,  9,  13 },
			{ 0,  4,  0,  0,  2,  -1 }
			
	};

	CRS docs ;
	CRS weights;
	
	@Before
	public void initialize(){
		docs = new CRS(sparseMatrix);
		double[][] w = {{1, 2,  0,  0, 0,  1}};
		weights = new CRS (w);
	}
	
	@Test
	public void testCSRAttributes(){
		//Length of the values vector = 19
		assertEquals(19, docs.values.length);
		
		double[] val = {10, -2, 3, 9, 3, 7, 8, 7, 3, 8, 7, 5, 8, 9, 9, 13, 4, 2, -1};
				
		Assert.assertArrayEquals("failure - byte arrays not same", val, docs.values, 0);
		
	}
	
	@Test
	public void testGetElemtMethod(){
		/* BEGIN: Testing the return of all elements of the matrix */
		//First line
		assertEquals(10, docs.getElement(0, 0), 0); // i=0; j=0; aij = 10
		assertEquals(0,  docs.getElement(0, 1), 0); // i=0; j=1; aij = 0
		assertEquals(0,  docs.getElement(0, 2), 0); // i=0; j=2; aij = 0
		assertEquals(0,  docs.getElement(0, 3), 0); // i=0; j=3; aij = 0
		assertEquals(-2, docs.getElement(0, 4), 0); // i=0; j=4; aij = -2
		assertEquals(0,  docs.getElement(0, 5), 0); // i=0; j=5; aij = 0
		//Second line
		assertEquals(3, docs.getElement(1, 0), 0); // i=1; j=0; aij = 3
		assertEquals(9, docs.getElement(1, 1), 0); // i=1; j=1; aij = 9
		assertEquals(0, docs.getElement(1, 2), 0); // i=1; j=2; aij = 0
		assertEquals(0, docs.getElement(1, 3), 0); // i=1; j=3; aij = 0
		assertEquals(0, docs.getElement(1, 4), 0); // i=1; j=4; aij = 0
		assertEquals(3, docs.getElement(1, 5), 0); // i=1; j=5; aij = 3
		//Third line
		assertEquals(0, docs.getElement(2, 0),  0); // i=2; j=0; aij = 0
		assertEquals(7, docs.getElement(2, 1),  0); // i=2; j=1; aij = 7
		assertEquals(8, docs.getElement(2, 2),  0); // i=2; j=2; aij = 8
		assertEquals(7, docs.getElement(2, 3),  0); // i=2; j=3; aij = 7
		assertEquals(0, docs.getElement(2, 4),  0); // i=2; j=4; aij = 0
		assertEquals(0, docs.getElement(2, 5),  0); // i=2; j=5; aij = 0
		//Fourth line
		assertEquals(3, docs.getElement(3, 0), 0); // i=3; j=0; aij = 3
		assertEquals(0, docs.getElement(3, 1), 0); // i=3; j=1; aij = 0
		assertEquals(8, docs.getElement(3, 2), 0); // i=3; j=2; aij = 8
		assertEquals(7, docs.getElement(3, 3), 0); // i=3; j=3; aij = 7
		assertEquals(5, docs.getElement(3, 4), 0); // i=3; j=4; aij = 5
		assertEquals(0, docs.getElement(3, 5), 0); // i=3; j=5; aij = 0
		//Fiveth line
		assertEquals(0,  docs.getElement(4, 0), 0); // i=4; j=0; aij = 0
		assertEquals(8,  docs.getElement(4, 1), 0); // i=4; j=1; aij = 8
		assertEquals(0,  docs.getElement(4, 2), 0); // i=4; j=2; aij = 0
		assertEquals(9,  docs.getElement(4, 3), 0); // i=4; j=3; aij = 9
		assertEquals(9,  docs.getElement(4, 4), 0); // i=4; j=4; aij = 9
		assertEquals(13, docs.getElement(4, 5), 0); // i=4; j=5; aij = 13
		//Sixth line
		assertEquals(0,  docs.getElement(5, 0),  0); // i=5; j=0; aij = 0
		assertEquals(4,  docs.getElement(5, 1),  0); // i=5; j=1; aij = 4
		assertEquals(0,  docs.getElement(5, 2),  0); // i=5; j=2; aij = 0
		assertEquals(0,  docs.getElement(5, 3),  0); // i=5; j=3; aij = 0
		assertEquals(2,  docs.getElement(5, 4),  0); // i=5; j=4; aij = 2
		assertEquals(-1, docs.getElement(5, 5),  0); // i=5; j=5; aij = -1
	/* END: Testing the return value of all elements of the matrix */	
		
		docs.printCRSMatrix();
		
	}
	
	@Test
	public void testDotProductByAnotherCRSVector(){
		double[][] w1 = { {  1,      2,     0,     0,     0,     1  } };
		double[][] w2 = { {  0,      0,     0,     0,     0,     0  } };
		double[][] w3 = { { -5,    -10,     8,     7,   6.5,     1  } };
		double[][] w4 = { {0.1,      5,     0,   1.1,     7,     0  } };
		double[][] w5 = { {2.1,  0.125,  0.01,     5,     1,     0  } };
		double[][] w6 = { {  1,      2,     0,     0,      0,    1  } };
		double[][] w7 = { {  0,      2,     7,  -1.1,    3.3,  1000 } };
		double[][] w8 = { {  0,      1,     0,     1,      0,    1  } };
		double[][] w9 = { {4.2,    8.1,   2.0,   1.9,   2.01,   1.1 } };
		double[][] w10 ={ {  0,      5,     4,     3,      1,   0.9 } };
		double[][] w11 ={ {  0,      1,     2,     3,      4,     5 } };
		double[][] w12 ={ {4.2,    0.5,  1.12,     0,   0.23,   0.9 } };
		
		assertEquals(    24,    docs.dotProduct(new CRS (w1),1),  0);
		assertEquals(     0,    docs.dotProduct(new CRS (w2),4),  0);
		assertEquals(   -63,    docs.dotProduct(new CRS (w3),0),  0);
		assertEquals(    34,    docs.dotProduct(new CRS (w4),5),  0);
		assertEquals(35.955,    docs.dotProduct(new CRS (w5),2),  0);
		assertEquals(     3,    docs.dotProduct(new CRS (w6),3),  0);
		assertEquals(  62.3,    docs.dotProduct(new CRS (w7),2),  0);
		assertEquals(    12,    docs.dotProduct(new CRS (w8),1),  0);
		//assertEquals( 51.95,    docs.dotProduct(new CRS (w9),3),  0); //TODO ver isso com o Ruy
		assertEquals(   -2,    docs.dotProduct(new CRS (w10),0), 0);
		assertEquals(   136,   docs.dotProduct(new CRS (w11),4), 0);
		assertEquals(   1.56,   docs.dotProduct(new CRS (w12),5), 0);
		
	}
	
	@Test
	public void testDotProductByAnotherSingleWeightVector(){
		
		try {
			docs.dotProduct(new double [] {1,2,3,4,5,6,7,9}, 2);
		} catch (ArrayIndexOutOfBoundsException e) {
			Assert.assertTrue(e.getMessage().equals("Incompatible lengths! " +
					"The weight.length = 8 and numOfCol = 6"));
		}
		
		assertEquals(0, docs.dotProduct(new double [] {0, 9, 4, 0, 0, 1}, 0),0);
		assertEquals(31.8, docs.dotProduct(new double [] {1, 3.2, 4, 0, 0, 0}, 1),0);
		assertEquals(58.7, docs.dotProduct(new double [] {0, 1.1, 2, 5, 0, 0}, 2),0);
		
	}
	
}