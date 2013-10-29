package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {
	
	double[][] matrix = new double[1000][9490];
	int max;
	
	public void parser(File file) throws FileNotFoundException{

			Scanner scanner = new Scanner(file);
			StringBuilder strb = new StringBuilder();
			int count = 0;
			while (scanner.hasNextLine()) {
				strb.append(scanner.nextLine());
				carrega(new Scanner(strb.toString()),count++);
				strb.setLength(0);
			}
			
			scanner.close();
			
	}
	
	private void carrega(Scanner scan, int line) {

		int colLabel = matrix[0].length-1;
		matrix[line][colLabel] = scan.nextInt();

		StringBuilder strBuilder = new StringBuilder();
		
		Pattern patternInt = Pattern.compile("[0-9]+");
		Pattern patternDouble = Pattern.compile("[0-9]+\\.[0-9]+");
		int col = 0;
		double value;
		//int max = 0;
		while (scan.hasNext()){
			strBuilder.append(scan.next());
			Matcher matcherInt = patternInt.matcher(strBuilder);
			Matcher matcherDouble = patternDouble.matcher(strBuilder);
			if (matcherInt.find()){
				
				col = Integer.parseInt(matcherInt.group());
			
			
			}
			if (matcherDouble.find()){
				value = Double.parseDouble(matcherDouble.group()); 
				matrix[line][col] = value;
			
			}
			strBuilder.setLength(0);
		}
		
	}

	
	public static void getExampleDocuments(){
		
	}
	
	public double [][] getMatrix(){
		return this.matrix;
	}
	public static void main(String[] args) {
		
		StringBuilder localPath = new StringBuilder(System.getProperty("user.dir")); 
		localPath.append(File.separator+"datasets"+File.separator+"dataset");
		
		Loader loader = new Loader();
		File file = new File(localPath.toString());
		
		
		System.out.println("aue");
		try {
			loader.parser(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}