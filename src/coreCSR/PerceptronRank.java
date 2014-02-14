package coreCSR;

import java.io.File;
import java.util.List;
import java.util.TreeSet;

import util.Example;
import util.LoaderMSLR;

/** This class is an implementation of the Perceptron Ranking Learning Algorithm,
 * an online algorithm for ordinal classification. A pointwise method of
 * learning to rank, which is based on Perceptron Learning Algorithm.
 * 
 * For this class, the structure of the ranking problem is ignored. Each example
 * corresponds a feature vector (offerings attribute) and the corresponding
 * label (value)
 * 
 * @author Oliv�rio
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
		
		// Initialize thresholds vector. There are k thresholds values but the real values are only
		// the k-1 values because the k value is assumed to be potentially infinite.
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

	
	public static void main(String[] args) {
		String str = "1:3 2:0 3:3 4:0 5:3 6:1 7:0 8:1 9:0 10:1 11:2009 12:2 13:4 14:7 15:2022 16:6.931275 17:22.076928 18:19.673353 19:22.255383 20:6.926551 21:8 22:0 23:3 24:0 25:11 26:2 27:0 28:1 29:0 30:3 31:3 32:0 33:1 34:0 35:4 36:2.666667 37:0 38:1 39:0 40:3.666667 41:0.222222 42:0 43:0 44:0 45:0.222222 46:0.003982 47:0 48:0.75000 49:0 50:0.00544 51:0.000996 52:0 53:0.25000 54:0 55:0.001484 56:0.001493 57:0 58:0.25000 59:0 60:0.001978 61:0.001327 62:0 63:0.25000 64:0 65:0.001813 66:0 67:0 68:0 69:0 70:0 71:17.714906 72:0 73:19.673353 74:0 75:24.627908 76:3.456385 77:0 78:5.373163 79:0 80:4.594393 81:8.100687 82:0 83:7.985784 84:0 85:10.798622 86:5.904969 87:0 88:6.557784 89:0 90:8.209303 91:3.626893 92:0 93:1.167248 94:0 95:6.941327 96:1 97:0 98:1 99:0 100:1 101:0.980551 102:0 103:1 104:0 105:0.989938 106:7.802556 107:0 108:26.759999 109:0 110:9.749707 111:-20.673887 112:-21.242171 113:-7.76183 114:-25.436074 115:-19.469471 116:-21.419394 117:-24.805464 118:-21.45982 119:-27.690319 120:-20.58994 121:-20.168345 122:-24.041386 123:-4.474536 124:-28.119826 125:-19.226044 126:3 127:64 128:5 129:7 130:256 131:49697 132:1 133:13 134:0 135:0 136:0";
		String [] tokens;
		
		tokens = str.replace(" ", ":").split(":");
		for (int i = 0; i < tokens.length; i++) {
			if ((i % 2) != 0)
			System.out.println(tokens[i]);
			//	System.out.println(Math.random());
		}
		System.out.println(tokens.length);
		
		
		
	String	path = System.getProperty("user.dir").concat(
				File.separator + "datasets" + File.separator + "MSLR-WEB10K"
					+ File.separator + "Fold1" + File.separator + "smallSampleTrainForPRankTest.txt");

	List<Example>	examples = LoaderMSLR.getDataset(path);
		
		PerceptronRank pRank = new PerceptronRank(examples.get(0),100);

		pRank.training();
		
		
		
	}
	
	/** Computes the minimum label value which corresponds
	 *  r = min{r / wi*xi - br < 0} (r in 1 .. k)
	 * 
	 * @param line - This parameter corresponds the position of the actual feature vector,
	 * on the data set, which will be calculated join by the weight vector.
	 * @return corresponding label which minimizes the search on the thresholds
	 */
	//TESTED
	public int minThresholdValue(int line) {
		
		// Result of the aggregation function of the
		// perceptron (dot product between weights vector and features vector)
		double dotProduct;

		// Temporary minimum threshold value greater than the doc product
		// At first, it may be assumed to be true.
		double minValue = this.thresholds[this.thresholds.length - 1];

		// Temporary index of the minimum doc product
		int indexMinValue = this.thresholds.length-1;

		dotProduct = this.example.offerings.dotProduct(this.weights, line);

		// Catch the min value among the values greater or equal then the doc
		// product. That to say, the minimum threshold value greater than dot product!
		for (int i = 0; i < this.thresholds.length - 1; i++) {
//TODO vefify this point!!!!!!!!
			if (dotProduct < this.thresholds[i]) {

				if (this.thresholds[i] < minValue) {
					minValue = this.thresholds[i];
					indexMinValue = i;
				}
			}
		}

		// label which contains the threshold interval.
		//return this.labels[indexMinValue];
		return indexMinValue;

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

		// According to the algorithm, the last threshold value is "infinitely" positive
		this.thresholds[this.thresholds.length - 1] = Integer.MAX_VALUE;

		double dotProduct;

		// observed value in the training set (label: Yi)
		int observedLabel;

		int auxLabels[] = new int[this.thresholds.length];

		int tau[] = new int[this.thresholds.length];
		
		/* While there is misclassification and the quantity of attempts is less
		 * than the maximum value initially set (maxCount attribute) */
		while ((hasError == true) && (count < this.maxCount)) {
			hasError = false;
			count++;

			// For each example in the training data set do
			for (int i = 0; i < qtdExamples; i++) {

				//min{r / wi*xi - br < 0} (r in 1 .. k)
				predicted = this.labels[minThresholdValue(i)];
				observedLabel = this.example.labels.get(i);

				// Updtade W and thresholds
				if (predicted != observedLabel) {

					for (int j = 0; j < this.thresholds.length - 1; j++) {
						if (observedLabel <= this.labels[j])
							auxLabels[j] = -1;
						else
							auxLabels[j] = 1;
					}
					
					for (int j = 0; j < this.thresholds.length - 1; j++) {
						dotProduct = example.offerings.dotProduct(weights, i); //If there is a mistake, then fix the index where the mistake occur!
						if ((dotProduct - this.labels[j]) * auxLabels[j] <= 0)
							tau[j] = auxLabels[j];
						else
							tau[j] = 0;
					}

					//update weight vector W 
					for (int j = 0; j < this.weights.length; j++) {
						this.weights[j] = this.weights[j] + sumTau(tau) * this.example.offerings.getElement(i, j);
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