package coreCSR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import util.CSR;
import util.Example;
import util.comparators.ActivationComparator;
import util.comparators.PositionComparator;
import core.Perceptron;

/** @author Olivério */

/** This class aims execute the ranking perceptron algorithm. It
 * receives a collection of documents and try to classify according by
 * activation. **/
public class RankPerceptron {

	// Document list contending the examples
	public HashMap<Integer,Example> examples;
	public double[] weights;
	public int maxCount;

	public RankPerceptron(HashMap<Integer,Example> examples, int maxCount) {
		this.examples = examples;
		this.maxCount = maxCount;
	}

	public void training() {

		boolean hasError;
		int count = 0;
		
		//ordered items by its scores (activations)
		TreeMap<Integer, Double> sortedScoreItems;
		//Original order given by data set (training examples) 
		TreeMap<Integer, Integer> trainClassification;
		
		do {
            hasError = false;
            count++;
            
            for(Entry<Integer, Example> entry : examples.entrySet()){
            	
            	//Build a TreeMap (sorted map), by activations, for each example
            	sortedScoreItems = rankByActivation(entry);
            	//Build a TreeMap (sorted map) considering the real order of the example 
            	trainClassification = makeOrderedMap(entry.getValue());
            	
            	if (hasError(sortedScoreItems,trainClassification)){
                    
            		hasError = true;
            		adjustWeights(sortedScoreItems, entry.getValue());	
            	}
            }
		}while( (hasError == true) && (count < this.maxCount) );    
	}
	
	/** It receives a set of documents, computes the activation in each example and
	 * it returns the documents sorted by its activation in a decreasing order 
	 * 
	 * @param examples
	 * @return sortedScoreItems
	 */
	public TreeMap<Integer, Double> rankByActivation(Entry<Integer, Example> example) {

		// Map containing the scored items by activation
		Map<Integer, Double> scoreItems = new HashMap<Integer, Double>();

		// This auxiliar class introduces how to the example(s) will be sorted - by activation
		ActivationComparator actComparator = new ActivationComparator(scoreItems);

		// Sorted Map containing the score items by activation
		TreeMap<Integer, Double> sortedScoreItems = new TreeMap<Integer, Double>(actComparator);
		double activation;

		//Qtd of documents in this example
		int numOfCol = example.getValue().documents.numOfCol;
		int numOfRows = example.getValue().documents.numOfRows;
		
		CSR doc = example.getValue().documents;
		for (int i = 0; i < numOfRows; i++) {
			activation = 0;
			for (int j = 0; j < numOfCol; j++) {
				activation+= doc.getElement(i, j);
			}
			scoreItems.put(example.getValue().getDocNumber(i), activation);
		}
		
		//For each example, compute the activation of each document and puts in the map.
//		for (int i = 0; i < length; i++) {
//			//Get document
//			CSR doc = examples.get(i).document; 
//			//Compute activation
//			activation = doc.dotProduct(weights); 
//			//Get number of document (document identification)
//			int docNum = examples.get(i).docNumber;
//			//Put the example in the map 
//			scoreItems.put(docNum, activation);
//		}

		sortedScoreItems.putAll(scoreItems);
		System.out.println(sortedScoreItems);

		return sortedScoreItems;
	}

	public TreeMap<Integer, Integer> makeOrderedMap(Example example){
		
		// This map is formed by the documents and its classification
		// accordingly by the given the train positions
		Map<Integer, Integer> posItems = new HashMap<Integer, Integer>();
		
		// This auxiliar class introduces the way the examples will be sorted -
		// by CLASSIFICATION
		PositionComparator posComparator = new PositionComparator(posItems);
		
		int numOfRows = example.documents.numOfRows;
				
		for (int i = 0; i < numOfRows; i++) {
			posItems.put(example.getDocNumber(i),example.getRank(i));
		}
		
//		for (int i = 0; i < length; i++) {
//			Example ex = examples.get(i);
//			posItems.put(ex.docNumber, ex.rank); // train examples put in a map
//		}

		// Sorted map contains the correct classifications of the documents
		TreeMap<Integer, Integer> orderedMap = new TreeMap<Integer, Integer>(posComparator);
		orderedMap.putAll(posItems);
		
		System.out.println(orderedMap);
		return orderedMap;

	}

	public boolean hasError(TreeMap<Integer, Double> sortedScoreItems,	TreeMap<Integer, Integer> trainClassification) {

		int numberEquals = 0;
		
		//Put the sorted documents in a list by its activations 
		for (Map.Entry<Integer, Double> entry1 : sortedScoreItems.entrySet()){
			
			for (Map.Entry<Integer, Integer> entry2 : trainClassification.entrySet()){
				if(entry1.getKey().equals(entry2.getKey())){
					
					if (entry1.getValue().equals(entry2.getValue())){
						numberEquals++;
						break;
					}
				}
			
			}
		}
		if (numberEquals == sortedScoreItems.size())
			return false;
			else return true;
	}

	/**
	 * return the feature attributes of a document
	 * 
	 * @param example
	 * @return
	 */
	private void adjustWeights(TreeMap<Integer, Double> sortedScoreItems, Example examples) {

		 TreeMap<Integer, Integer> correctClassification = makeOrderedMap(examples);
		
		//List which contains the correct classification (train examples)
		List<Entry<Integer, Integer>> correctPositions = new ArrayList<>();

		//List contains the trained classification (classification by its computed activations)
		List<Entry<Integer, Double>> trainedPositions = new ArrayList<>();

		//Put the correct sorted documents in a list 
		for (Map.Entry<Integer, Integer> entry : correctClassification.entrySet()){
			correctPositions.add(entry);
		}
		
		double index = 0;
		
		//Put the sorted documents in a list by its activations 
		for (Map.Entry<Integer, Double> entry : sortedScoreItems.entrySet()){
			entry.setValue(++index);
			trainedPositions.add(entry);
		}
		
		Entry<Integer, Integer> Xi;
		Entry<Integer, Integer> Xj;
		
		for (int i = 0; i < correctPositions.size(); i++) {
			for (int j = i+1; j < correctPositions.size(); j++) {

				Xi = correctPositions.get(i);
				Xj = correctPositions.get(j);

				if (hasInvertion(Xi, Xj, trainedPositions)) {
					
					int lineI = Xi.getKey();
					int lineJ = Xj.getKey();
					CSR docI = examples.documents;
					CSR docJ = examples.documents;
					
					for (int k = 0; k < weights.length; k++) {
						
						weights[k] = weights[k] + docI.getElement(lineI, k) - docJ.getElement(lineJ, k);
					}
				}
			}
		}
	}

	public boolean hasInvertion(Entry<Integer, Integer> Xi, Entry<Integer, Integer> Xj, 
			List<Entry<Integer, Double>> trainedRelativePositions) {

		boolean catchI = false;
		boolean catchJ = false;
		double posYi = 0.0;
		double posYj = 0.0;

		for (int i = 0; i < trainedRelativePositions.size(); i++) {
			if (Xi.getKey() == trainedRelativePositions.get(i).getKey()) {
				posYi = trainedRelativePositions.get(i).getValue();
				catchI = true;
			}
			if (Xj.getKey() == trainedRelativePositions.get(i).getKey()) {
				posYj = trainedRelativePositions.get(i).getValue();
				catchJ = true;
			}
		
			if (catchI && catchJ)
				break;
		}

		if (catchI && catchJ) {

			if (posYi > posYj) {
				return true;
			}
		}
		return false;
	}
}