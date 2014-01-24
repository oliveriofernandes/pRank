package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class LoaderMSLR {

	public static void main(String[] args) throws FileNotFoundException {

		String path = System.getProperty("user.dir").concat(File.separator + "datasets" + File.separator + "MSLR-WEB10K"
						+ File.separator + "Fold1" + File.separator + "sampleTrain.txt");

		LoaderMSLR l = new LoaderMSLR();
		l.getDataset(path);

	}

	/**
	 * Receive a path file, parses the dataset and returns a list containing the
	 * integer data set, preserving its relationship between queries ids, its
	 * correspondents feature vectors and the labels associated each query and
	 * feature vector, meaning this relevance.
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static List<Example> getDataset(String path)	throws FileNotFoundException {
		int label;
		int qId = 0;
		int indx;
		double value;
		Map<Integer, List<Map<Integer, Double>>> mapExample = new HashMap<Integer, List<Map<Integer, Double>>>();
		Scanner scanner = new Scanner(new File(path));

		String[] strTokens;

		List<Map<Integer, Double>> listMaps = new ArrayList<Map<Integer, Double>>();
		Map<Integer, Double> map = new HashMap<Integer, Double>();

		while (scanner.hasNextLine()) {

			map.clear();

			// Get the label value in the example
			label = scanner.nextInt();

			// Get the qId in the line
			qId = Integer.parseInt(scanner.next().split(":")[1]);

			// Catches the relationship between a query id and feature vector
			// (qId and Xi).
			strTokens = scanner.nextLine().replace(" ", ":").split(":");

			for (int i = 1; i < strTokens.length - 1; i++) {
				indx = Integer.parseInt(strTokens[i]);
				value = Double.parseDouble(strTokens[i + 1]);
				map.put(indx, value);
				i++;
			}
			map.put(-1, new Double(label));
			listMaps.add(map);
			if ((mapExample.get(qId)) != null) {
				mapExample.get(qId).add(map);
			} else {
				mapExample.put(qId, listMaps);
				mapExample.get(qId).add(map);
			}

		}
		scanner.close();
		return extractAttributes(mapExample);
	}

	private static List<Example> extractAttributes(
			Map<Integer, List<Map<Integer, Double>>> mapExample) {

		List<Example> examples = new ArrayList<Example>();
		for (Entry<Integer, List<Map<Integer, Double>>> entry : mapExample
				.entrySet()) {
			// array list will receive the indexes for filling the colIndexes
			// vector for the CRS attribute of each example
			List<Integer> indx = new ArrayList<Integer>();
			// array list will receive the values for filling the values vector
			// for the CRS attribute of each example
			List<Double> valuesList = new ArrayList<Double>();
			// array list will receive the rowPtrValue for filling the
			// rowPtrValue vector for the CRS attribute of each example
			List<Integer> rowPtrValue = new ArrayList<Integer>();
			// Map contained the labels of each example - the Key is the line
			// number and value is the corresponding label
			Map<Integer, Double> labels = new HashMap<Integer, Double>();
			// Stores labels of each row
			boolean timeArrays;
			int countLine = 0;

			// Identifies the id of the query document
			int qId = entry.getKey();
			int lengthColumn = 0;
			// Performing at each line of data set
			for (Map<Integer, Double> dsLine : entry.getValue()) {
				// Flag which indicates
				timeArrays = true;
				labels.put(countLine++, dsLine.remove(-1));

				// Fills the fields of indx, values and rowPtw list of each line
				// on the data set
				for (Entry<Integer, Double> attributes : dsLine.entrySet()) {
					// Catch the length size column of the matrix.
					if (lengthColumn < attributes.getKey())
						lengthColumn = attributes.getKey();

					if (attributes.getValue() != 0) {
						indx.add(attributes.getKey());
						valuesList.add(attributes.getValue());
						// Test if the value corresponds the first non-zero
						// value of this line
						if (timeArrays) {
							rowPtrValue.add(valuesList.size() - 1);
							timeArrays = false;
						}
					}
				}
				
			}

			// The values of nonzero elements of the sparse matrix
			double[] values = new double[valuesList.size()];
			// The column indices of the elements in the 'value' vector
			int[] colIndexes = new int[valuesList.size()];
			// The rowPtr of the elements in the 'rowPtr' vector
			int rowPtr[] = new int[rowPtrValue.size() + 1];

			for (int i = 0; i < valuesList.size(); i++) {
				values[i] = valuesList.get(i);
				colIndexes[i] = indx.get(i);
			}

			// Fill rowPtr array
			for (int i = 0; i < rowPtrValue.size(); i++){
				rowPtr[i] = rowPtrValue.get(i);
			}
			rowPtr[rowPtr.length - 1] = values.length;

			CRS csr = new CRS(values, colIndexes, rowPtr);
			Example example = new Example(csr, qId, labels);
			examples.add(example);
		}
		return examples;
	}
}