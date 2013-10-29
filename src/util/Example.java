package util;

import java.util.HashMap;

public class Example {

	private HashMap<Integer, Integer> docNumber;
	public CSR documents;
	private HashMap<Integer, Integer> rank;
	
	public Example(CSR documents) {
		this.documents = documents;
	}

	public int getRank(int line) {
		return rank.get(line);
	}

	public void setRank(HashMap<Integer, Integer> rank) {
		this.rank = rank;
	}
	
	public int getDocNumber(int line) {
		return docNumber.get(line);
	}
	

}
