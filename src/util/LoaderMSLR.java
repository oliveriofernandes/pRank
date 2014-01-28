package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class LoaderMSLR {

	public static void main(String[] args) throws FileNotFoundException {

		String path = System.getProperty("user.dir").concat(File.separator + "datasets" + File.separator + "MSLR-WEB10K"
						+ File.separator + "Fold1" + File.separator + "sampleTrain.txt");

		LoaderMSLR l = new LoaderMSLR();
		l.getDataset(path);

	}

	/** Receive a path file, parses the dataset and returns a list containing the
	 *  integer data set, preserving its relationship between queries ids, its
	 *  correspondents feature vectors and the labels associated each query and
	 *  feature vector, meaning this relevance.
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
		
		/*The mapExample structure consists of the query id as key and a list with lots of TreeMaps which
		/represents the associated documents (key as index of the feature vector and its corresponding value)
		with the query */   
		Map<Integer, List<TreeMap<Integer, Double>>> mapExamples = new HashMap<Integer, List<TreeMap<Integer, Double>>>();
		Scanner scanner = new Scanner(new File(path));

		String[] strTokens;

		while (scanner.hasNextLine()) {

			///each TreeMap is a document (feature vector)
			TreeMap<Integer, Double> map = new TreeMap<Integer, Double>();
			
			// Get the label value in the example
			label = scanner.nextInt();

			// Get the qId (query identifier) in the line
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
			
			// ON EACH LINE, ADD A DUMMI VALUE EQUALS 1
			//map.put(0, 1.0);
			map.put(-1, new Double(label));
			//if exists a query identifier in the map example, add a TreeMap (feature vector - document) in the 
			//list of the associated documents 
			if ((mapExamples.get(qId)) != null) {
				mapExamples.get(qId).add(map);
			} else {
				mapExamples.put(qId, new ArrayList<TreeMap<Integer, Double>>());
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
	private static List<Example> extractAttributes(Map<Integer, List<TreeMap<Integer, Double>>> mapExample) {

		int labelValue;
		List<Example> examples = new ArrayList<Example>();
		for (Entry<Integer, List<TreeMap<Integer, Double>>> entry : mapExample.entrySet()) {
			
			// This array list will receive the indexes for filling the colIndexes
			// vector for the CRS attribute of each example
			List<Integer> index = new ArrayList<Integer>();
			
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
			
			// Iterate on each line of data set
			for (Map<Integer, Double> dsLine : entry.getValue()) {
				// Flag which indicates if a value corresponds the first non-zero
				// of this line
				timeArrays = true;
			
				labelValue = dsLine.remove(-1).intValue();
				
				labels.put(countLine++,labelValue );

				// Fills the fields of index list, values list and rowPtw list on each line
				// on the data set
				for (Entry<Integer, Double> attributes : dsLine.entrySet()) {
					
					// Catch the length size column of the matrix.
					if (attributes.getValue() != 0) {
						index.add(attributes.getKey());
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
				colIndexes[i] = index.get(i);
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