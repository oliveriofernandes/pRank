package util;


/** @author oliverio
 *
 *Comprises the Row Storage Storage format for sparse matrix */

public class CSR {
	//The values of nonzero elements of the sparse matrix
	public double [] values;
	
	//The column indices of the elements in the value vector
	public int [] colIndexes;
	
	/* Localizations in the 'values' vector that starts each row.
	   Its size is decided by the size of the matrix */
	public int rowPtr[];
	
	int numOfRows;
	int numOfCol;
	/* This constructor takes a effective sparse matrix 
	 * and convert it to a CRS object */

	public CSR (double [][] matrix){
		int totalNonZeros = 0; // total non zero numbers presented in the matrix
		int index = 0;
		
		//Get number of rows in the matrix
		numOfRows = matrix.length;
		
		//Get number of columns in the matrix
		numOfCol = matrix[0].length;
		
		//Find the number of non zeros in the matrix
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfCol; j++) {
				if (matrix[i][j]!=0)
						totalNonZeros++;
			}
		}
		
		//Initialize the 'values' vector  
		values = new double[totalNonZeros];
						
		//Initialize the 'colIndex' vector
		colIndexes = new int[totalNonZeros];
		
		//Initialize the 'rowPtr' vector
		rowPtr = new int[numOfRows+1];
		//index of rowPtr vector
		int k = 0;
		
		//Indicates if the current non zero value is the first in the line 
		boolean firstValue = true;
		
		
		//Fill the vectors 'values' and 'colIndex' according its respective 
		//values and its positions of the sparse matrix
		for (int i = 0; i < numOfRows; i++) {
			firstValue = true;
			for (int j = 0; j < numOfCol; j++) {
				if (matrix[i][j]!=0){
					values[index] = matrix[i][j];
					colIndexes[index] = j;
					if (firstValue){
						rowPtr[k] = index;
						k++;
						firstValue = false;
					}
					index++;
				}
			}
		}
		rowPtr[k] = index;
		
	}
	
	/*takes a vector x and returns the product of the matrix stored in the CRS object with x.*/
	public double dotProduct(double[] w){
		//create vector to save product
		double dotProduct = 0;

		for (int i = 0; i < numOfCol; i++){
			for( int j = rowPtr[i]; j < rowPtr[i+1]; j++){
				dotProduct += values[j] * w[colIndexes[j]];

			}
		}

		return dotProduct;

	}
	
	
	//get the element in row i and column j in the matrix
		public double getElement(int i, int j){

			// Find where the row starts
			int rowStart = rowPtr[i];

			// Find where the next row starts
			int nextRowStarts = rowPtr[i+1];

			// Scan the column indices of entries in row i
			for(int k = rowStart; k < nextRowStarts; k++){
				// if j is the column index of a non-zero, then return it
				if(colIndexes[k] == j)
					return values[k];

				// if we have passed j, then entry (i, j) must be a zero
				if(colIndexes[k] > j)
					return 0;
			}

			// if we have reached the end of the non-zeroes without
			// find j, it must be the index of a trailing 0
			return 0;

		}
	
	public void printCRSMatrix (){
		
		System.out.println("Values vector:");
		for (int i = 0; i < values.length; i++) {
			System.out.print(values[i] +":");
		}
		System.out.println("");
		
		System.out.println("Index column vector:");
		for (int i = 0; i < colIndexes.length; i++) {
			System.out.print(colIndexes[i] + ":");
		}
		System.out.println("");
		//Fill the rowPtr vector
		System.out.println("rowPtr vector:");
		for (int i = 0; i < rowPtr.length; i++) {
			System.out.print(rowPtr[i] + ":");
		}
	}
	
}