package coreCSR;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.print.attribute.HashAttributeSet;

import core.Perceptron;
import core.PerceptronRule;

import tests.CSRTest;
import util.CRS;
import util.Example;

/**
 * This class is an implementation of the Perceptron Ranking Learning Algorithm, an online algorithm 
 * for ordinal classification. A pointwise method of learning to rank, which is based 
 * on Perceptron Learning Algorithm.
 * 
 * For this class, the structure of the ranking problem is ignored. Each
 * example corresponds only a number of feature vector (offerings attribute).
 * 
 * @author Olivério
 * 
 */
public class PerceptronRank {

	//The example attribute corresponds a set of feature vectors (CRS object) and its corresponding labels 
	public Example example;
	public double[] weights;
	// Maximun number of iterations in the main training loop
	public int maxCount;
	public int labels[];
	//Intervals which define the labels on the W arrow...
	public double thresholds[];

	/**
	 * The main constructor catch a example and maxCount
	 * 
	 * @param example
	 * @param maxCount
	 */
	public PerceptronRank(Example example, int maxCount) {
		this.example  = example;
		this.weights  = new double[example.offerings.numOfCol];
		this.maxCount = maxCount;

		TreeSet<Integer> treeSet = example.getLabelValues();
		//Initialize labels vector
		this.labels = new int[treeSet.size()];
		//Initialize thresholds vector
		this.thresholds = new double [treeSet.size()]; 
		
		fillLabeslVector(treeSet);

	}

	protected void fillLabeslVector(TreeSet<Integer> treeSet) {
		int count = 0;

		for (int label : treeSet) {
			this.labels[count] = label;
			count++;
		}

	}

	private int maxValue(int line) {
		
		//Result of the aggregation function of the 
		//perceptron (dot product between weight vector and feature vector)
		double dotProduct;
		
		//Temporary maximun value of the doc product 
		double maxValue = this.thresholds[0];
		
		//Temporary index of the maximun doc product
		int indexMaxValue = 0;
		
		dotProduct = PerceptronRule.dotProduct(this.weights ,this.example.offerings.getLine(line));

		//Catch the max value threshold among the values less or equal then the doc product
		for (int i = 0; i < this.thresholds.length; i++) {
			
			if (this.thresholds[i] <= dotProduct){
				
				if ( this.thresholds[i]> maxValue ){
				
					maxValue = this.thresholds[i];
					indexMaxValue = i;
				}
			}
		}

		return indexMaxValue;

	}

	public double[] training() {

		//The weights, thresholds and labels vector are already initialized
		int count = 0;
		boolean hasError = true;
		int qtdExamples = this.example.offerings.numOfRows;
		double predicted;

		// first one is infinitely negative
		this.thresholds[0] = -1000;

		/*
		 * while there is misclassification and the quantity of attempts is
		 * less than the maximum permitted (maxCount attribute)
		 */
		while ((hasError == true) && (count < this.maxCount)) {
			hasError = false;
			count++;
			// For each example in the training data set do
			for (int i = 0; i < qtdExamples; i++) {
				
				predicted = maxValue(i);
				
				if (predicted != this.example.labels.get(i)){
				
					for (int j = 0; j < this.weights.length; j++) {
						for (int k = 0; k < bounds.length; k++) {
						
							this.weights[j] = this.weights[j] + ( y - predicted)*this.example.offerings;
							
						}
						 
					}
					
				}
				
			}

		}

		return this.weights;
	}
}