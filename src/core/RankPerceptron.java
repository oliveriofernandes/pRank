package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import util.comparators.ActivationComparator;
import util.comparators.PositionComparator;

/** @author Olivério */

/** This class aims execute the ranking perceptron algorithm. It
 * receives a collection of documents and try to classify according by
 * activation. **/
public class RankPerceptron extends PerceptronRule {

	// Document list contending the examples
	public List<double[][]> examples;
	public double[] weights;

	public RankPerceptron(double[][] matrix, int maxCount, List<double[][]> examples) {
		super(matrix, maxCount);
		this.examples = examples;

	}

	public RankPerceptron(List<double[][]> examples, int maxCount) {
		super(examples.get(0), maxCount);
		this.examples = examples;
		this.weights = new double[examples.get(0)[0].length - 1];
	}

	public void training() {

		boolean hasError;
		int count = 0;
		
		TreeMap<Integer, Double> sortedScoreItems;
		TreeMap<Integer, Double> trainClassification;
		
		do {
            hasError = false;
            count++;
            
            for (double[][] example : examples) {
    			//Build a TreeMap (sorted map), by activations, for each example - set of documents
            	sortedScoreItems = rankByActivation(example);
            	//Build a TreeMap (sorted map) considering the real order of the example 
            	trainClassification = makeOrderedMap(example);
    			
            	if (hasError(sortedScoreItems,trainClassification)){
            
            		hasError = true;
            		adjustWeights(sortedScoreItems, example);	
            	}
            	
    		}
            
		}while( (hasError == true) && (count < this.maxCount) );    
		
	}
	
	public boolean hasError(TreeMap<Integer, Double> sortedScoreItems,	TreeMap<Integer, Double> trainClassification) {

		double index = 0;
		int numberEquals = 0;
		
		//Put the sorted documents in a list by its activations 
		for (Map.Entry<Integer, Double> entry : sortedScoreItems.entrySet()){
			entry.setValue(++index);
		}
		
		for (Map.Entry<Integer, Double> entry1 : sortedScoreItems.entrySet()){
			
			for (Map.Entry<Integer, Double> entry2 : trainClassification.entrySet()){
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

	public TreeMap<Integer, Double> makeOrderedMap(double[][]example){
		
		// This map is formed by the documents and its classification
		// accordingly by the given the train positions
		Map<Integer, Double> posItems = new HashMap<Integer, Double>();
				
		for (int i = 0; i < example.length; i++) {
			double pos = example[i][example[i].length - 1]; // pos : position of the document
			System.out.println(pos);
			posItems.put(i, pos); // train examples put in a map
		}

		// This auxiliar class introduces the way the examples will be sorted -
		// by CLASSIFICATION
		PositionComparator posComparator = new PositionComparator(posItems);

		// Sorted map contains the correct classifications of the documents
		TreeMap<Integer, Double> orderedMap = new TreeMap<Integer, Double>(posComparator);
		orderedMap.putAll(posItems);
		
		return orderedMap;

	}

	/** It receives a set of documents, computes the activation in each example and
	 * it returns the documents sorted by its activation in a decreasing order 
	 * 
	 * @param example
	 * @return sortedScoreItems
	 */
	public TreeMap<Integer, Double> rankByActivation(double[][] example) {

		// Map containing the score items by activation
		Map<Integer, Double> scoreItems = new HashMap<Integer, Double>();

		// This auxiliar class introduces how to the example(s) will be sorted - by activation
		ActivationComparator actComparator = new ActivationComparator(scoreItems);

		// Sorted Map containing the score items by activation
		TreeMap<Integer, Double> sortedScoreItems = new TreeMap<Integer, Double>(actComparator);
		double activation;

		for (int i = 0; i < example.length; i++) {
			activation = Perceptron.activation(weights,getAtributes(example[i]));
			scoreItems.put(i, activation);
		}

		sortedScoreItems.putAll(scoreItems);
		
		System.out.println(sortedScoreItems);

		return sortedScoreItems;

	}

	/**
	 * return the feature attributes of a document
	 * 
	 * @param example
	 * @return
	 */
	private double[] getAtributes(double[] example) {
		double[] atributes = new double[example.length - 1];
		for (int i = 0; i < atributes.length; i++)
			atributes[i] = example[i];

		return atributes;
	}

	private void adjustWeights(TreeMap<Integer, Double> sortedScoreItems, double[][] example) {

		 TreeMap<Integer, Double> correctClassification = makeOrderedMap(example);
		
		//List which contains the correct classification (train examples)
		List<Entry<Integer, Double>> correctPositions = new ArrayList<>();

		//List contains the trained classification (classification by its computed activations)
		List<Entry<Integer, Double>> trainedPositions = new ArrayList<>();

		//Put the correct sorted documents in a list 
		for (Map.Entry<Integer, Double> entry : correctClassification.entrySet()){
			correctPositions.add(entry);
		}
		double index = 0;
		//Put the sorted documents in a list by its activations 
		for (Map.Entry<Integer, Double> entry : sortedScoreItems.entrySet()){
			entry.setValue(++index);
			trainedPositions.add(entry);
		}
		
		Entry<Integer, Double> Xi;
		Entry<Integer, Double> Xj;
		
		
		for (int i = 0; i < correctPositions.size(); i++) {
			for (int j = i+1; j < correctPositions.size(); j++) {

				Xi = correctPositions.get(i);
				Xj = correctPositions.get(j);

				if (hasInvertion(Xi, Xj, trainedPositions)) {
					
					int lineI = Xi.getKey();
					int lineJ = Xj.getKey();
					for (int k = 0; k < weights.length; k++) {
						weights[k] = weights[k] + example[lineI][k] - example[lineJ][k];
					}
				}
			}
		}
	}

	public boolean hasInvertion(Entry<Integer, Double> Xi, Entry<Integer, Double> Xj, 
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