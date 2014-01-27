package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import util.Example;
import util.LoaderMSLR;

public class LoaderMSLRTest {

	
	double [] valuesVectorQId_16 = {7.0, 3.0, 7.0, 1.0, 0.428571, 1.0, 638.0, 7.0, 11.0, 656.0, 18.542189, 50.99229, 
			45.079776, 48.199288, 18.527701, 64.0, 3.0, 67.0, 1.0, 1.0, 20.0, 1.0, 21.0, 9.142857, 
			0.428571, 9.571429, 59.265306, 0.244898, 59.959184, 0.100313, 0.428571, 0.102134, 0.001567,
			0.001524, 0.031348, 0.142857, 0.032012, 0.01433, 0.061224, 0.014591, 0.000146, 0.004998, 
			0.000139, 198.042434, 17.481966, 204.672487, 2.009636, 2.007058, 84.996794, 7.467603, 
			89.220907, 28.291776, 2.497424, 29.238927, 924.295187, 8.915806, 979.169994, 1.0, 1.0, 
			0.799677, 0.596964, 0.800638, 44.386652, 18.113545, 44.735393, -36.170863, -58.536245, 
			-46.069334, -60.615603, -35.855909, -42.190665, -56.968459, -55.737542, -62.040556, 
			-41.896792, -34.310152, -64.94727, -50.588715, -71.199957, -34.054718, 4.0, 67.0, 100.0, 3.0, 40064.0, 51991.0, 3.0, 1.0, 7.0, 24.2666666666667};
	
	
	@Test
	public void getDataSetTest() throws FileNotFoundException{
	
	String path = System.getProperty("user.dir").concat(File.separator+"datasets" + File.separator+
			"MSLR-WEB10K" + File.separator+"Fold1" + File.separator + "sampleTrain.txt");
	
	List<Example> examples = LoaderMSLR.getDataset(path);
	
	//There are 3 examples which rIds are 1, 16 and 31 respectively
	assertEquals(examples.size(), 3);
	for (Example example : examples){
		assertEquals(136, example.offerings.numOfCol);
		testRelatedElemnts(example);
		
	}

	for (Entry<Integer, Integer> entry : examples.get(0).labels.entrySet() ) {
		
		
		
	}
	
	
	}

	private void testRelatedElemnts(Example example) {

		
		switch (example.rId) {
		case 1:
			
			break;
		
		case 16:
			
			assertEquals(example.offerings.numOfRows,41);
			
			//Assert.assertArrayEquals(valuesVectorQId_16,example.offerings.values,0.0);
			
			for (int i = 0; i < valuesVectorQId_16.length; i++) {
				if( (valuesVectorQId_16[i]) == (example.offerings.getElement(0, i)) ){
					System.out.println("OK: " + valuesVectorQId_16[i]);
					
				}else{
					System.out.println("valuesVectorQId_16[" + i + "] = "+ valuesVectorQId_16[i]);
					System.out.println("example.offerings.getElement(0,"+i + ") = "+ example.offerings.getElement(0, i));
				}
			}
			
			
			break;

		case 31:
			
			break;
		
		
		default:
			break;
		}
		
		
	}
}