package core;

/** @author Olivério */

public abstract class PerceptronRule {

	double[][] trainingExamples;
	public int maxCount;
	
	/** The original Perceptron includes a fixed threshold value which aid the
     * perceptron activation function yields this results. This algorithm
     * actually learn the threshold value too. For this
     * reason, this method includes a default constant, equals 1,
     * intending match with the value of w0 (thresholded value), considering
     * that threshold as any weight value. So its execution give one dumb
     * column on each example valued by 1.
     *
     * @param matrix
     */
    public PerceptronRule(double[][] matrix, int maxCount) {
        this.maxCount = maxCount;
        
    	//Adds a column in the matrix with a value equal to 1.
    	int lin = matrix.length;
        int col = matrix[0].length + 1; // one more column to setting 1 at W0
        this.trainingExamples = new double[lin][col];

        /* Adds a column in the matrix with a value equal to 1. This new column
         * corresponds the first index column. In other words, the x0 values
         * (equals 1 on each example) match with w0 weight that consists in the
         * thresholded value that will be learning by the Perceptron Rule. */
        for (int i = 0; i < lin; i++) {
            for (int j = 1; j < col; j++) {
                trainingExamples[i][j] = matrix[i][j - 1];
            }
        }

        // fill value '1' for each training example.
        for (int i = 0; i < lin; i++) {
            trainingExamples[i][0] = 1;
        }
    }
    
    public int getTarget(int index) {
        int length = this.trainingExamples[index].length - 1;

        return (int) this.trainingExamples[index][length];
    }
    
    public double[] getExample(int index) {
        
        int length = this.trainingExamples[index].length - 1;
       
        double[] example = new double[length];

        for (int j = 0; j < length; j++)
            example[j] = this.trainingExamples[index][j];

        return example;
    }
    
    /** Dot product between weights inputs parameters 
	 * 
	 * @param weights
	 * @param inputs
	 * @return dot product resulted by weights and inputs vectors. */
    
    static public double dotProduct(double[] weights, double[] inputs) {
		int length = 0;
		if(weights.length == inputs.length)
			length = inputs.length;
		else {
			System.out.println("Weights and inputs vectors don't match!");
			return 0;
		}
		
		double sum = 0;
		
		for (int i = 0; i < length; i++) {
			sum = sum +(weights[i]*inputs[i]);
		}
		return sum;
	}
}