package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
		Map<Integer,List<String[]>> example = new HashMap<Integer,List<String[]>>();
		Scanner scanner = new Scanner(new File(path));
		
		String[] strTokens;
		StringBuilder strBuilder = new StringBuilder();
		qIdTemp = qId; 
		while (scanner.hasNextLine()) {
				
			// Get the label value in the token
			label = scanner.nextInt();
				
			// Get the  qId in the line
			qIdTemp = Integer.parseInt(scanner.next().split(":")[1]);
				
			//Catches the relationship (qId and Xi) presented in the document.
			if (qIdTemp == qId){
				String str [] = scanner.nextLine().split(":");
				strBuilder.append(str.toString());
				strBuilder.append(label);
				
			//	example.get(qId).add(strBuilder.toString());
			//	example.get(qId).add(strTokens);
			//	example.get(qId).add(label);
			}
				
			else{
				qId = qIdTemp; 
			//	strTokens = scanner.nextLine().split(":");
				strBuilder.append(scanner.nextLine().split(":"));
				strBuilder.append(label);
				Scanner sc = new Scanner(strBuilder.toString());
				System.out.println(sc.nextLine());
				
				System.out.println("length string builder = " + strBuilder.length());
				//	example.get(qId).add(strTokens);
			//	example.get(qId).add((Object)label);
			}
		}
		scanner.close();
	//	extractAttributes(example);
		}
			
	private static void extractAttributes(Map<Integer, List<Object>> example) {
	
		for (Entry<Integer, List<Object>> entry : example.entrySet()) {
		
			List<Integer> indx = new ArrayList<Integer>();
			List<Double> valuesList = new ArrayList<Double>();
			List<Integer> rowPtrValue  = new ArrayList<Integer>();
			//Stores labels of each row 
			List<Integer> labels  = new ArrayList<Integer>();
			boolean timeArrays = true;
			
			for (Object obj : entry.getValue()) {
				if (timeArrays){
					String [] vals = (String []) obj;
					
					for (int i = 0; i < vals.length -1; i++) {
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