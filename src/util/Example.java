package util;

/** @author Olivério */

public class Example {

	public CRS documents;
	
	public Example(CRS documents) {
		this.documents = documents;
	}

	/** @param line
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
