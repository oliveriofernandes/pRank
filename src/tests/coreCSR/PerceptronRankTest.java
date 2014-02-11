package tests.coreCSR;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.Example;
import util.LoaderMSLR;
import coreCSR.PerceptronRank;

public class PerceptronRankTest {

	String path;
	
	List<Example> examples;
	
	PerceptronRank pRank;
	
	@Before
	public void initilizeVariables() throws FileNotFoundException{
		path = System.getProperty("user.dir").concat(
				File.separator + "datasets" + File.separator + "MSLR-WEB10K"
					+ File.separator + "Fold1" + File.separator + "smallSampleTrainForPRankTest.txt");

		examples = LoaderMSLR.getDataset(path);
		
		pRank = new PerceptronRank(examples.get(0),100);
	}
	
	@Test
	public void checkInitializingAttributes(){
		Assert.assertNotNull(pRank.example);
		Assert.assertNotNull(pRank.weights);
		Assert.assertNotNull(pRank.labels);
		Assert.assertNotNull(pRank.thresholds);
		Assert.assertNotNull(pRank.maxCount);
	}
	
	
	@Test
	public void testDocproduct(){
		
		for (int i = 0; i < pRank.example.offerings.numOfRows; i++) {
			for (int j = 0; j < pRank.example.offerings.numOfCol; j++) {
			
				System.out.println(pRank.example.offerings.getElement(i, j));
			}
		}
		
	}
	
	@Test
	public void testMinValueMethod(){
		
		
		
	}
	
	@Test
	public void execute() throws FileNotFoundException {

		int [] testValuesLabels = {0,1,2};
		
		Assert.assertArrayEquals(testValuesLabels, pRank.labels);
		
		Assert.assertEquals("Labels and Thresholds array lengths are equal",
				pRank.thresholds.length, pRank.labels.length);
		
		assertEquals(0, 0,0);
		
		
		
		

	}
}
