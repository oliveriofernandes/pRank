package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.CSR;

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

	CSR csr ;
	
	@Before
	public void initialize(){
		csr = new CSR(sparseMatrix);
	}
	
	@Test
	public void testCSRAttributes(){
		//Length of the values vector = 19
		assertEquals(19, csr.values.length);
		
		double[] val = {10, -2, 3, 9, 3, 7, 8, 7, 3, 8, 7, 5, 8, 9, 9, 13, 4, 2, -1};
				
		Assert.assertArrayEquals("failure - byte arrays not same", val, csr.values, 0);
		
	}
	
	
	@Test
	public void testGetElemtMethod(){
		/* BEGIN: Testing the return of all elements of the matrix */
		//First line
		assertEquals(csr.getElement(0, 0), 10, 0); // i=0; j=0; aij = 10
		assertEquals(csr.getElement(0, 1), 0,  0); // i=0; j=1; aij = 0
		assertEquals(csr.getElement(0, 2), 0,  0); // i=0; j=2; aij = 0
		assertEquals(csr.getElement(0, 3), 0,  0); // i=0; j=3; aij = 0
		assertEquals(csr.getElement(0, 4), -2, 0); // i=0; j=4; aij = -2
		assertEquals(csr.getElement(0, 5), 0,  0); // i=0; j=5; aij = 0
		//Second line
		assertEquals(csr.getElement(1, 0), 3,  0); // i=1; j=0; aij = 3
		assertEquals(csr.getElement(1, 1), 9,  0); // i=1; j=1; aij = 9
		assertEquals(csr.getElement(1, 2), 0,  0); // i=1; j=2; aij = 0
		assertEquals(csr.getElement(1, 3), 0,  0); // i=1; j=3; aij = 0
		assertEquals(csr.getElement(1, 4), 0,  0); // i=1; j=4; aij = 0
		assertEquals(csr.getElement(1, 5), 3,  0); // i=1; j=5; aij = 3
		//Third line
		assertEquals(csr.getElement(2, 0), 0,  0); // i=2; j=0; aij = 0
		assertEquals(csr.getElement(2, 1), 7,  0); // i=2; j=1; aij = 7
		assertEquals(csr.getElement(2, 2), 8,  0); // i=2; j=2; aij = 8
		assertEquals(csr.getElement(2, 3), 7,  0); // i=2; j=3; aij = 7
		assertEquals(csr.getElement(2, 4), 0,  0); // i=2; j=4; aij = 0
		assertEquals(csr.getElement(2, 5), 0,  0); // i=2; j=5; aij = 0
		//Fourth line
		assertEquals(csr.getElement(3, 0), 3,  0); // i=3; j=0; aij = 3
		assertEquals(csr.getElement(3, 1), 0,  0); // i=3; j=1; aij = 0
		assertEquals(csr.getElement(3, 2), 8,  0); // i=3; j=2; aij = 8
		assertEquals(csr.getElement(3, 3), 7,  0); // i=3; j=3; aij = 7
		assertEquals(csr.getElement(3, 4), 5,  0); // i=3; j=4; aij = 5
		assertEquals(csr.getElement(3, 5), 0,  0); // i=3; j=5; aij = 0
		//Fiveth line
		assertEquals(csr.getElement(4, 0), 0,  0); // i=4; j=0; aij = 0
		assertEquals(csr.getElement(4, 1), 8,  0); // i=4; j=1; aij = 8
		assertEquals(csr.getElement(4, 2), 0,  0); // i=4; j=2; aij = 0
		assertEquals(csr.getElement(4, 3), 9,  0); // i=4; j=3; aij = 9
		assertEquals(csr.getElement(4, 4), 9,  0); // i=4; j=4; aij = 9
		assertEquals(csr.getElement(4, 5), 13, 0); // i=4; j=5; aij = 13
		//Sixth line
		assertEquals(csr.getElement(5, 0), 0,  0); // i=5; j=0; aij = 0
		assertEquals(csr.getElement(5, 1), 4,  0); // i=5; j=1; aij = 4
		assertEquals(csr.getElement(5, 2), 0,  0); // i=5; j=2; aij = 0
		assertEquals(csr.getElement(5, 3), 0,  0); // i=5; j=3; aij = 0
		assertEquals(csr.getElement(5, 4), 2,  0); // i=5; j=4; aij = 2
		assertEquals(csr.getElement(5, 5), -1, 0); // i=5; j=5; aij = -1
	/* END: Testing the return value of all elements of the matrix */	
	}
}