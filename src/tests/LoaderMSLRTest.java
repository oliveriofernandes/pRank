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

	double[] valuesVectorQId_16 = { 1.0, 7.0, 3.0, 7.0, 1.0, 0.428571, 1.0, 638.0, 7.0, 11.0, 
			656.0, 18.542189, 50.99229, 45.079776, 48.199288, 18.527701, 64.0, 3.0, 67.0, 1.0, 
			1.0, 20.0, 1.0, 21.0, 9.142857, 0.428571, 9.571429, 59.265306, 0.244898, 59.959184, 
			0.100313, 0.428571, 0.102134, 0.001567, 0.001524, 0.031348, 0.142857, 0.032012, 
			0.01433, 0.061224, 0.014591, 0.000146, 0.004998, 0.000139, 198.042434, 17.481966, 
			204.672487, 2.009636, 2.007058, 84.996794, 7.467603, 89.220907, 28.291776, 2.497424, 
			29.238927, 924.295187, 8.915806, 979.169994, 1.0, 1.0, 0.799677, 0.596964, 0.800638, 
			44.386652, 18.113545, 44.735393, -36.170863, -58.536245, -46.069334, -60.615603, 
			-35.855909, -42.190665, -56.968459, -55.737542, -62.040556, -41.896792, -34.310152, 
			-64.94727, -50.588715, -71.199957, -34.054718, 4.0, 67.0, 100.0, 3.0, 40064.0, 51991.0, 
			3.0, 1.0, 7.0, 24.2666666666667, 1.0, 7.0, 2, 1, 7, 1, 0.285714, 0.142857, 1, 458, 4, 12, 
			474, 18.542189, 50.99229, 45.079776, 48.199288, 18.527701, 23, 2, 2, 27, 1, 1, 7, 1, 2, 7,
			3.285714, 0.285714, 0.285714, 3.857143, 3.918367, 0.204082, 0.489796, 5.265306, 0.050218, 
			0.50000, 0.166667, 0.056962, 0.002183, 0.00211, 0.015284, 0.25000, 0.166667, 0.014768, 0.007174,
			0.071429, 0.02381, 0.008137, 0.000019, 0.012755, 0.003401, 0.000023, 59.136478, 10.014364, 
			16.208702, 70.053203, 2.009636, 2.007058, 19.10080, 5.291515, 16.208702, 25.491688, 8.448068,
			1.430623, 2.315529, 10.00760, 40.521935, 5.139807, 32.170043, 67.247544, 1, 1, 0.857476, 0.411387, 
			0.43859, 0.851678, 39.24778, 13.621693, 10.711787, 40.607153, -39.271452, -58.536245, -51.333272,
			-58.903865, -38.519583, -46.179467, -56.968459, -57.478089, -59.726693, -45.508249, -36.775187,
			-64.94727, -58.882774, -68.527975, -36.166918, 4, 71, 37443, 51991, 1, 1 };

	@Test
	public void getDataSetTest() throws Exception {

		String path = System.getProperty("user.dir").concat(File.separator + "datasets" + File.separator + 
				"MSLR-WEB10K" + File.separator + "Fold1" + File.separator + "sampleTrain.txt");

		List<Example> examples = LoaderMSLR.getDataset(path);

		// There are 3 examples which rIds are 1, 16 and 31 respectively
		assertEquals(examples.size(), 3);
		for (Example example : examples) {
			assertEquals(137, example.offerings.numOfCol);
			testRelatedElemnts(example);

		}

	}

	private void testRelatedElemnts(Example example) throws Exception {

		switch (example.rId) {
		case 1:

			break;

		case 16:

			assertEquals(41,example.offerings.numOfRows);
			
			compareValues(example, valuesVectorQId_16, 2);

			// Assert.assertArrayEquals(valuesVectorQId_16,
			// example.offerings.values, 0.0);
			break;

		case 31:

			break;

		default:
			break;
		}

	}

	private void compareValues(Example example, double[] testVector, int qtdLines) {
		
		//the index column CRS matrix 
		int col = 0;
		int limit = testVector.length;
		int colIndex = 0;
		try {
			//Scan each line in the matrix
			for (int lin = 0; lin < qtdLines; lin++) {
				//The test vector corresponds the value vector in the test routine
				col = 0;
				for (; ((colIndex < limit) && (col < example.offerings.numOfCol)); colIndex++) {
					
					//If the value of test vector corresponds the real value 
					//of this corresponding CRS matrix on the data set
					if ((testVector[colIndex]) == (example.offerings.getElement(lin, col))) {
						col++;

					} else if (example.offerings.getElement(lin, col) != 0) {
						System.out.println("error: example.offerings.getElement(" + lin + ", " + col + 
								") is different to zero");
						System.out.println("error: testVector[" + colIndex + "] is different to " +
								"(example.offerings.getElement(" + lin +"," + col + ")");
						System.out.println("The value of testVector[" + colIndex + "] is " 
								+ testVector[colIndex]);
						System.out.println("The value of example.offerings.getElement(" + lin +"" +
								", " + col + ") is " + example.offerings.getElement(lin,col));
					} else {
						while (example.offerings.getElement(lin, col) == 0) 
							col++;
						colIndex--;
						continue;
					}
				}
			}
			System.out.println("matrix scan compteted");
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
}