package util;


public class Example {

	public CSR documents;
	
	public Example(CSR documents) {
		this.documents = documents;
	}

	/**
	 * 
	 * @param line
	 * @return
	 */
	public double getRank(int line) {
		
		//Index where starts next line
		int rowPtr = documents.rowPtr[line+1];
		
		//Index where the line above ends (column target)
		int j = documents.colIndexes[rowPtr]-1;
		
		
		return documents.getElement(line,j);
		
	}


}
