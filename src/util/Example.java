package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

/** 
 * This class corresponds an example instance which will be formed the train data set.
 * The train set is represented by the set S = {(Xi,Yi)} where the Xi represents the features
 * in each example (can be document attributes, item attributes and so on.), the Yi corresponds 
 * the labels for each offering (document, item and so on). The rId is the identifier of each 
 * example (a set of documents is associated with a query id, for example) and the Map of labels 
 * corresponds of an association of each offering identifier and its respective label (score) 
 * given in the data set.  
 * 
 * @author Olivério
 *
 **/

public class Example {

	/* Matrix in a CRS format representing a set of feature vectors. Each line of the matrix ('offering')
	 * corresponds only the attributes*/
	public CRS offerings;
	//The request identifier of the example
	public int rId;
	
	/*The relationship between each 'offering' (can be a document, item and so on)
	 * and the corresponding label. This is because, in a general case, an example can contain lots
	 * of document (a query can retrieves a set of documents, each one with a label representing its relevance) */ 
	
	public Map<Integer,Integer> labels;
	
	public Example(CRS documents,int rId) {
		this.offerings = documents;
		this.rId = rId;
		this.labels = new HashMap<Integer,Integer>(100);
	}
	
	public Example(CRS documents, int rId, Map<Integer,Integer> labels) {
		this.offerings = documents;
		this.rId = rId;
		this.labels = labels;
	}
	
	public TreeSet<Integer> getLabelValues(){
		
		TreeSet<Integer> labels = new TreeSet<Integer>();
		for (Entry<Integer, Integer> entry : this.labels.entrySet()) {
			labels.add(entry.getValue());
		}
		return labels;
	}
	
	public TreeSet<Integer> getLabelValues(int rId){
		if (rId == this.rId)
			return getLabelValues();
		return null;
	}
	
	public CRS getOfferings(int rId){
		if (rId == this.rId)
			return this.offerings;
		return null;
	}

}
