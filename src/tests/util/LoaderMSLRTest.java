package tests.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import util.Example;
import util.LoaderMSLR;

public class LoaderMSLRTest {

	//This vectors consist on vector values extracted from the sampleTrain.txt data set.
	//There is a modification: Each "new line" begins with the value 1 (one) meaning the 
	// "artificial entry" for the W0 weight on the weight vector!
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
			-64.94727, -58.882774, -68.527975, -36.166918, 4, 71, 37443, 51991, 1, 1, 1, 5, 1, 2, 6, 0.714286, 
			0.142857, 0.285714, 0.857143, 1702, 5, 9, 1716, 18.542189, 50.99229, 45.079776, 48.199288, 18.527701, 
			51, 1, 2, 54, 42, 1, 1, 44, 7.285714, 0.142857, 0.285714, 7.714286, 203.346939, 0.122449, 0.204082, 
			221.632653, 0.029965, 0.20000, 0.222222, 0.031469, 0.024677, 0.20000, 0.111111, 0.025641, 0.004281, 
			0.028571, 0.031746, 0.004496, 0.00007, 0.004898, 0.00252, 0.000075, 141.004502, 6.467245, 15.370228, 
			151.046366, 126.97482, 6.467245, 8.135147, 132.981649, 20.14350, 0.923892, 2.195747, 21.578052, 1907.212337, 
			5.12146, 12.111126, 2072.759715, 0.421122, 0.374694, 0.589198, 0.435438, 16.239993, 7.966264, 14.955498,
			18.911348, -56.422364, -58.536245, -55.066035, -51.973749, -53.379023, -54.50521, -56.968459, -57.304641, 
			-58.392014, -51.928294, -56.095791, -64.94727, -65.645859, -59.508556, -51.250372, 3, 49, 7, 6683, 39632, 1, 
			8, 1, 209.8};

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

		switch ( example.rId ) {
		case 1:

			assertEquals(20,example.offerings.numOfRows);
			break;

		case 16:

			assertEquals(41,example.offerings.numOfRows);
			
			compareValues(example, valuesVectorQId_16, 3);

			break;

		case 31:

			assertEquals(6,example.offerings.numOfRows);
			break;

		default:
			System.out.println("Some error with the parser and data training used here!");
			break;
		}

	}

	private void compareValues(Example example, double[] testVector, int qtdLines) {
		
		//the index column counter CRS matrix 
		int countCol = 0;
		int limit = testVector.length;
		int colIndex = 0;
		try {
			//Scan each line in the matrix
			for (int lin = 0; lin < qtdLines; lin++) {
				//The test vector corresponds the value vector in the test routine
				countCol = 0;
				for (; ( (colIndex < limit) && (countCol < example.offerings.numOfCol) ); colIndex++) {
					
					/* If the value of test vector corresponds the real value 
					   of this corresponding CRS matrix on the data set */
					if ((testVector[colIndex]) == (example.offerings.getElement(lin, countCol))) {
						countCol++;

					} else if (example.offerings.getElement(lin, countCol) != 0) {
						System.out.println("error: example.offerings.getElement(" + lin + ", " + countCol + 
								") is different to zero");
						System.out.println("error: testVector[" + colIndex + "] is different to " +
								"(example.offerings.getElement(" + lin +"," + countCol + ")");
						System.out.println("The value of testVector[" + colIndex + "] is " 
								+ testVector[colIndex]);
						System.out.println("The value of example.offerings.getElement(" + lin +"" +
								", " + countCol + ") is " + example.offerings.getElement(lin,countCol));
					} else {
						while ( (countCol < example.offerings.numOfCol) && (example.offerings.getElement(lin, countCol) == 0)) 
							countCol++;
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