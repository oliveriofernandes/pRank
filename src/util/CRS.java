package util;

/**
 * This class comprises the Row Storage Storage format for sparse matrix. This
 * data structure was created only for storing the features attributes of the
 * examples. In order to store information in respect request id's, score
 * relevance and so on, there is Example class. This CRS class is a composition
 * peace of the Example class.
 * 
 * @author Oliv�rio
 */

public class CRS {
	// The values of nonzero elements of the sparse matrix
	public double[] values;

	// The column indices of the elements in the 'value' vector
	public int[] colIndexes;

	/*
	 * Localizations, in the 'values' vector, in which it starts each row. Its
	 * size is decided by the size of the matrix
	 */
	public int[] rowPtr;

	public int numOfRows;
	public int numOfCol;
	
	boolean strartWithZero = false;

	/*
	 * This is the simples constructor. It takes the values, colIndexes and
	 * rowPtr arrays and assign its values to the correspondents attributes in
	 * this class
	 */

	public CRS(double[] values, int[] colIndexes, int[] rowPtr) {
		this.values = values;
		this.colIndexes = colIndexes;
		this.rowPtr = rowPtr;

		this.numOfRows = rowPtr.length - 1;

		this.numOfCol = colIndexes[0];
		
		for (int i = 0; i < colIndexes.length; i++) {
			if (this.numOfCol < colIndexes[i])
				this.numOfCol = colIndexes[i];
		
			if (colIndexes[i] == 0){
				this.strartWithZero = true;
			}
		}

		if (strartWithZero)
			this.numOfCol++;
	}

	/** This constructor takes a effective sparse matrix and convert it to a CRS
	 * object */
	public CRS (double[][] matrix) {
		int totalNonZeros = 0; // total of nonzero numbers presented in the matrix
		int index = 0; // index used for filling the vectors (values, colIndex and rowPtr)
		this.strartWithZero = true;
		// Get number of rows in the matrix
		numOfRows = matrix.length;

		// Get number of columns in the matrix
		numOfCol = matrix[0].length;

		// Find the number of non zeros in the matrix
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfCol; j++) {
				if (matrix[i][j] != 0)
					totalNonZeros++;
			}
		}

		// Initialize the 'values' vector
		values = new double[totalNonZeros];

		// Initialize the 'colIndex' vector
		colIndexes = new int[totalNonZeros];

		// Initialize the 'rowPtr' vector
		rowPtr = new int[numOfRows + 1];
		// index of rowPtr vector
		int k = 0;

		// Indicates if the current non zero value is the first in the line
		boolean firstValue = true;

		// Fill the vectors 'values' and 'colIndex' according its respective
		// values and its positions of the sparse matrix
		for (int i = 0; i < numOfRows; i++) {
			firstValue = true;
			for (int j = 0; j < numOfCol; j++) {
				if (matrix[i][j] != 0) {
					values[index] = matrix[i][j];
					colIndexes[index] = j;
					if (firstValue) {
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

	/** takes a vector x and returns the product of the matrix stored in the CRS
	 * object with x.
	 */
	//TESTED
	public double dotProduct(CRS weight, int line) throws IndexOutOfBoundsException {
		double dotProduct = 0;

		// Find where the row starts
		int rowStart = rowPtr[line];

		// Find where the next row starts
		int nextRowStarts = rowPtr[line + 1];

		try {

			for (int i = rowStart; i < nextRowStarts; i++)
				dotProduct += values[i] * weight.getElement(0, colIndexes[i]);
			
		} catch (IndexOutOfBoundsException exception) {
			exception.printStackTrace();
		}
		return dotProduct;
	}

	//TESTED
	public double dotProduct(double[] weight, int line) {
		
		if (weight.length != this.numOfCol)
			throw new ArrayIndexOutOfBoundsException("Incompatible lengths! The weight.length = " 
					+ weight.length + " and numOfCol = " + this.numOfCol);
		
		
		double dotProduct = 0;

		// Find where the row starts
		int rowStart = rowPtr[line];

		// Find where the next row starts
		int nextRowStarts = rowPtr[line + 1];

			for (int i = rowStart; i < nextRowStarts; i++)
				dotProduct += values[i] * weight[colIndexes[i]];
			
		return dotProduct;
	}

	public double[] getLine(int line) { //TODO make an OutOfBoundsException
		double[] lineVector = new double[this.numOfCol];
		int count = 0;
		
		// Find where the row starts
		int rowStart = rowPtr[line];

		// Find where the next row starts
		int nextRowStarts = rowPtr[line + 1];

		for (int i = rowStart; i < nextRowStarts; i++) {
			lineVector[count] = this.getElement(line, colIndexes[i]);
		}
		return lineVector;
	}

	// get the element in row i and column j in the matrix
	//TESTED
	public double getElement(int row, int col) throws IndexOutOfBoundsException{

		if (strartWithZero) {

			if ((row > this.numOfRows) || (col >= this.numOfCol)) {
				throw new IndexOutOfBoundsException();

			}
		} else {
			if ((row > this.numOfRows) || (col > this.numOfCol)) {
				throw new IndexOutOfBoundsException();

			}
		}		
		
		// Find where the row starts
		int startRow = rowPtr[row];

		// Find where the next row starts
		int nextStartRow = rowPtr[row + 1];

		// Scan the column indices of entries in row i
		for (int k = startRow; k < nextStartRow; k++) {
			// if j is the column index of a non-zero, then return it
			if (colIndexes[k] == col)
				return values[k];

			// if it has passed j, then entry (i,j) must be a zero
			if (colIndexes[k] > col)
				return 0;
		}

		// if we have reached the end of the non-zeroes without
		// find j, it must be the index of a trailing 0
		return 0;

	}

	public void printCRSMatrix() {

		System.out.println("Values vector:");
		for (int i = 0; i < values.length; i++) {
			System.out.print(values[i] + ":");
		}
		System.out.println();

		System.out.println("Index column vector:");
		for (int i = 0; i < colIndexes.length; i++) {
			System.out.print(colIndexes[i] + ":");
		}
		System.out.println();
		// Fill the rowPtr vector
		System.out.println("rowPtr vector:");
		for (int i = 0; i < rowPtr.length; i++) {
			System.out.print(rowPtr[i] + ":");
		}
		System.out.println();
		System.out.println("----");
	}
}