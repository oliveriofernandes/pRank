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
	
		String path = System.getProperty("user.dir").concat(
				File.separator+"datasets"+File.separator+"MSLR-WEB10K"+
						File.separator+"Fold1"+File.separator+"sampleTrain.txt");

		LoaderMSLR.getDataset(path);
		
	}
	
	public static void getDataset(String path ) throws FileNotFoundException{
		
		int label;
		int qId = 0;
		int qIdTemp;
		Map<Integer,List<Map<Integer,Double>>> example = new HashMap<Integer, List<Map<Integer,Double>>>(); 
		Scanner scanner = new Scanner(new File(path));
		
		String[] strTokens;
		qIdTemp = qId; 
		while (scanner.hasNextLine()) {
				
			// Get the label value in the token
			label = scanner.nextInt();
				
			// Get the  qId in the line
			qIdTemp = Integer.parseInt(scanner.next().split(":")[1]);
				
			//Catches the relationship (qId and Xi) presented in the document.
			if (qIdTemp == qId){
				strTokens = scanner.nextLine().replace(" ", ":").split(":");
				Map<Integer,Double> map = new HashMap<Integer,Double>();
				for (int i = 1; i < strTokens.length-1; i++) {
					int indx = Integer.parseInt(strTokens[i]);
					double value = Double.parseDouble(strTokens[i+1]);
					map.put(indx,value );
					i++;
				}
				map.put(-1, new Double(label));
				example.get(qId).add(map);
			}
				
			else{
				qId = qIdTemp; 
				strTokens = scanner.nextLine().replace(" ", ":").split(":");
				Map<Integer,Double> map = new HashMap<Integer,Double>();
				for (int i = 1; i < strTokens.length-1; i++) {
					int indx = Integer.parseInt(strTokens[i]);
					double value = Double.parseDouble(strTokens[i+1]);
					map.put(indx,value );
					i++;
				}
				map.put(-1, new Double(label));
				List<Map<Integer,Double>> listMaps = new ArrayList<Map<Integer,Double>>();
				listMaps.add(map);
				example.put(qId,listMaps);
			}
		}
		scanner.close();
		extractAttributes(example);
		}
			
	private static void extractAttributes(Map<Integer,List<Map<Integer,Double>>> example) {
	
		for (Entry<Integer, List<Map<Integer,Double>>> entry : example.entrySet()) {
		
			List<Integer> indx = new ArrayList<Integer>();
			List<Double> valuesList = new ArrayList<Double>();
			List<Integer> rowPtrValue  = new ArrayList<Integer>();
			//Stores labels of each row 
			List<Integer> labels  = new ArrayList<Integer>();
			boolean timeArrays = true;
			
			for (Map<Integer, Double> entity : entry.getValue()) {
				if (timeArrays){
					
					for (Entry<Integer, Double> subEntryint > entity.g) {
						indx.add(Integer.parseInt(vals[i]));
						valuesList.add(Double.parseDouble(vals[i+1]));	
					}
					
					rowPtrValue.add(valuesList.size() - vals.length);
				}
				else
				{
					labels.add((Integer)obj);
				}
			}
			
			//The values of nonzero elements of the sparse matrix
			double [] values = new double[valuesList.size()];
			
			//The column indices of the elements in the 'value' vector
			int [] colIndexes = new int [valuesList.size()];
			
			int rowPtr[] = new int [rowPtrValue.size()];
					
			for (int i = 0; i < valuesList.size(); i++) {
				values[i] = valuesList.get(i);
				colIndexes[i] = indx.get(i);		
			}
			
			for (int i = 0; i < rowPtr.length; i++) {
				rowPtr[i] = rowPtrValue.get(i);
			}
		}
	}
}