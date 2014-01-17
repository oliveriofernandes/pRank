package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/** 
 * This class corresponds an example instance which will be formed the train data set.
 * The train set is represented by the set S = {(Xi,Yi)} where the Xi represents the features
 * in each example (here is documents attribute), the Yi corresponds the labels for each
 * offering (document, item and so on). The rId is the identifier of each example (a set of documents
 * is associated with a query id, for example) and the Map of labels corresponds of an association
 * of each offering identifier and its respective label (score) given in the data set  
 * 
 * @author Olivério
 *
 **/

public class Example {

	public CRS offerings;
	public int rId;
	public Map<Integer,Double> labels;
	
	public Example(CRS documents,int rId) {
		this.offerings = documents;
		this.rId = rId;
		this.labels = new HashMap<Integer,Double>(100);
	}
	
	public Example(CRS documents,int rId, Map<Integer,Double> labels) {
		this.offerings = documents;
		this.rId = rId;
		this.labels = labels;
	}
	
	public TreeSet<Double> getLabelValues(){
		
		TreeSet<Double> labels = new TreeSet<Double>();
		for (Entry<Integer, Double> entry : this.labels.entrySet()) {
			labels.add(entry.getValue());
		}
		return labels;
	}
	

//	/** @param line
//	 * @return the last column in an specific line - It can be the score, rank, etc.: depends on the dataset. */
//	public double getRank(int line) {
//		
//		//Index where starts next line
//		int rowPtr = documents.rowPtr[line+1];
//		
//		//Index where the line above ends (column target)
//		int j = documents.colIndexes[rowPtr]-1;
//		
//		return documents.getElement(line,j);
//	}
}
