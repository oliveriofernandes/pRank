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
		//the Yi value
		int label;
		
		//query Id variable
		int qId = 0;
		//index of the feature vector
		int index;
		//value of each index on the feature vector
		double value;
		
		//Contains the id query as the key and a list with lots of maps which represents the documents 
		//associated (key as index of the feature vector)   
		Map<Integer, List<Map<Integer, Double>>> mapExamples = new HashMap<Integer, List<Map<Integer, Double>>>();
		Scanner scanner = new Scanner(new File(path));

		String[] strTokens;

		///each map is a document - feature vector
		//Map<Integer, Double> map = new HashMap<Integer, Double>();

		while (scanner.hasNextLine()) {

			//map.clear();
			Map<Integer, Double> map = new HashMap<Integer, Double>();
			// Get the label value in the example
			label = scanner.nextInt();

			// Get the qId in the line
			qId = Integer.parseInt(scanner.next().split(":")[1]);

			// Catches the relationship between a query id and feature vector
			// (qId and Xi).
			strTokens = scanner.nextLine().replace(" ", ":").split(":");

			for (int i = 1; i < strTokens.length - 1; i++) {
				index = Integer.parseInt(strTokens[i]);
				value = Double.parseDouble(strTokens[i + 1]);
				map.put(index, value);
				i++;
			}
			map.put(-1, new Double(label));
			//if exists a query identifier in the map example, add a map (feature vector - document) in the 
			//list of documents(associated ) 
			if ((mapExamples.get(qId)) != null) {
				mapExamples.get(qId).add(map);
			} else {
				mapExamples.put(qId, new ArrayList<Map<Integer, Double>>());
				mapExamples.get(qId).add(map);
			}

		}
		scanner.close();
		return extractAttributes(mapExamples);
	}

	/**
	 * Get a map with all examples in a data set which contains, for each request id, there is a list with lots of maps
	 * with the corresponding documents.
	 * 
	 * @param mapExample
	 * @return
	 */
	private static List<Example> extractAttributes(Map<Integer, List<Map<Integer, Double>>> mapExample) {

		int labelValue;
		List<Example> examples = new ArrayList<Example>();
		for (Entry<Integer, List<Map<Integer, Double>>> entry : mapExample.entrySet()) {
			
			// This array list will receive the indexes for filling the colIndexes
			// vector for the CRS attribute of each example
			List<Integer> indx = new ArrayList<Integer>();
			
			// This array list will receive the values for filling the values vector
			// for the CRS attribute of each example
			List<Double> valuesList = new ArrayList<Double>();
			
			// This array list will receive the rowPtrValue for filling the
			// rowPtrValue vector for the CRS attribute of each example
			List<Integer> rowPtrValue = new ArrayList<Integer>();
			
			// This Map associates each example with its label- the Key is the line
			// number and value is the corresponding label
			Map<Integer, Integer> labels = new HashMap<Integer, Integer>();
			
			// Stores labels of each row
			boolean timeArrays;
			int countLine = 0;

			// Identifies the query identifier on each document
			int qId = entry.getKey();
			
			// Performing on each line of data set
			for (Map<Integer, Double> dsLine : entry.getValue()) {
				// Flag which indicates if a value corresponds the first non-zero
				// of this line
				timeArrays = true;
			
				labelValue = dsLine.remove(-1).intValue();
				
				labels.put(countLine++,labelValue );

				// Fills the fields of indx list, values list and rowPtw list on each line
				// on the data set
				for (Entry<Integer, Double> attributes : dsLine.entrySet()) {
					// Catch the length size column of the matrix.
						
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