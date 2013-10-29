package tests;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

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
//	{0,0,0,3,0,14,0,0,0,0,0,7}};
	
	double [][] sparseMatrix = {
			{10,0,0,0,-2,0},
			{3,9,0,0,0,3},
			{0,7,8,7,0,0},
			{3,0,8,7,5,0},
			{0,8,0,9,9,13},
			{0,4,0,0,2,-1}};
	@Before
	public void initialize(){
		
	}
	
	@Test
	public void execute(){
	
		CSR csr = new CSR(sparseMatrix);
		
		//assertEquals(7, csr.values.length);
	
	}
}
