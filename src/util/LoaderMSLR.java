package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class LoaderMSLR {

	public static void getDataset(String path ) throws FileNotFoundException{
		
		int label;
		int qId;
		int indx;
		double value;
		
		//Creates a buffered reader in order to read each line of the data set
		try (Scanner scanner = new Scanner(new File(path)))	{
 
			//While exists a line in the data set
			while ( scanner.hasNextLine())  {
				
				label = Integer.parseInt(scanner.next());
				String [] vec = scanner.next().split(":");
				qId = Integer.parseInt(vec[1]);
				
				extractAttributes(scanner);
				
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private static void extractAttributes(Scanner scanner) {
		new CRS(null);
		while (scanner.hasNext()) {
			String [] vec = scanner.next().split(":");
			//vec[0] = 
		}
		
	}

	//String [] vec = scanner.next().split(":");
	//int indx = Integer.parseInt(vec[0]);
	//doc[indx-1] = Double.parseDouble(vec[1]);		
		
	
}
