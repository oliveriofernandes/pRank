package coreCSR;

import java.util.TreeSet;

import util.Example;
import core.PerceptronRule;

/** This class is an implementation of the Perceptron Ranking Learning Algorithm,
 * an online algorithm for ordinal classification. A pointwise method of
 * learning to rank, which is based on Perceptron Learning Algorithm.
 * 
 * For this class, the structure of the ranking problem is ignored. Each example
 * corresponds a feature vector (offerings attribute) and the corresponding
 * label (value)
 * 
 * @author Olivério
 * 
 */
public class PerceptronRank {

	// The example attribute corresponds a set of feature vectors (CRS object)
	// and its corresponding labels
	public Example example;
	
	//The weights vector.
	public double [] weights;
	
	//Label vector
	public int [] labels;
	
	// Intervals which define the labels
	//each example will be labeled according to their threshold
	public double thresholds[];

	// Maximun number of iterations in the main training loop
	public int maxCount;
	
	/** The main constructor receives an example and maxCount
	 * 
	 * @param example
	 * @param maxCount
	 */
	public PerceptronRank(Example example, int maxCount) {
		this.example = example;
		this.weights = new double[example.offerings.numOfCol];
		
		// Initialize labels vector
		fillLabeslVector();
		
		// Initialize thresholds vector
		this.thresholds = new double[this.labels.length];
		
		this.maxCount = maxCount;
	}

	protected void fillLabeslVector() {
		int count = 0;

		//Ordered set of the thresholds 
		TreeSet<Integer> treeSet = this.example.getLabelValues();
		
		this.labels = new int [treeSet.size()];
		
		for (int label : treeSet) {
			this.labels[count] = label;
			count++;
		}
	}

	/** Computes the minimum value r = min{r / wi*xi - br < 0} (r in 1 .. k)
	 * 
	 * @param line
	 * @return
	 */
	//TESTED
	private int minValue(int line) {

		// Result of the aggregation function of the
		// perceptron (dot product between weight vector and feature vector)
		double dotProduct;

		// Temporary minimum value of the doc product
		double minValue = this.thresholds[0];

		// Temporary index of the minimum doc product
		int indexMinValue = 0;

		dotProduct = this.example.offerings.dotProduct(this.weights, line);

		// Catch the min value among the values less or equal then the doc
		// product
		// The minimum threshold value greater than dot product!
		for (int i = 0; i < this.thresholds.length; i++) {

			if (dotProduct < this.thresholds[i]) {

				if (this.thresholds[i] < minValue) {

					minValue = this.thresholds[i];
					indexMinValue = i;
				}
			}
		}

		// label which contains the threshold interval.
		return this.labels[indexMinValue];

	}

	public int sumTau(int t[]) {
		int sum = 0;
		for (int i = 0; i < t.length; i++) {
			sum += t[i];
		}

		return sum;
	}

	public double[] training() {

		// The weights, thresholds and labels vectors are already initialized on the constructor.
		int count = 0;
		
		//Initially, this postulate is assumed to be true 
		boolean hasError = true;
		
		//The number of examples is the total number of Xi,Yi pars in the present algorithm
		int qtdExamples = this.example.offerings.numOfRows;

		// Predicted value 
		double predicted;

		// According to the algorithm, the last value is "infinitely" positive
		this.thresholds[this.thresholds.length - 1] = Integer.MAX_VALUE;

		double dotProduct;

		// observed value in the training set (label: Yi)
		int currentLabel;

		int auxLabels[] = new int[this.thresholds.length];

		int tau[] = new int[this.thresholds.length];
		
		/* While there is misclassification and the quantity of attempts is less
		 * than the maximum value initially set (maxCount attribute) */
		while ((hasError == true) && (count < this.maxCount)) {
			hasError = false;
			count++;

			// For each example in the training data set do
			for (int i = 0; i < qtdExamples; i++) {

				predicted = minValue(i);
				currentLabel = this.example.labels.get(i);

				// Updtade W and thresholds
				if (predicted != currentLabel) {

					for (int j = 0; j < this.labels.length - 1; j++) {
						if (currentLabel <= this.labels[j])
							auxLabels[j] = -1;
						else
							auxLabels[j] = 1;
					}

					for (int j = 0; j < this.labels.length - 1; j++) {
						dotProduct = example.offerings.dotProduct(weights, i);
						if ((dotProduct - this.labels[j]) * auxLabels[j] <= 0)
							tau[j] = auxLabels[j];
						else
							tau[j] = 0;

					}

					//update weight vector W 
					for (int j = 0; j < this.weights.length; j++) {
						this.weights[j] = this.weights[j] + sumTau(tau) * this.example.offerings.getLine(i)[j];
					}
					
					//update threshold vector
					for (int j = 0; j < this.thresholds.length-1; j++) {
						this.thresholds[j] = this.thresholds[j] - tau[j]; 
						
					}

				}

			}

		}

		return this.weights;
	}
}