package core;

/** Implements the simplest learning method for a neuron.
 * The perceptron algorithm obtains a synaptic weight vector 
 * of single perceptron unit. 
 * 
 * @author Olivério 
 * 
 **/
public class BinaryPerceptron extends PerceptronRule {
	
    public BinaryPerceptron(double[][] matrix, int maxCount) {
       
    	    super(matrix,maxCount);
    } 	    

    /** Execute the training step: producing/adjusting the 'synaptic' 
	* weights of a binary perceptron
    *
	* @return the synaptic weight vector 'learned' */
    public double[] trainingBinaryPerceptron() {
       //vector of weights (same dimension of the examples into the training)
        double [] w = new double[trainingExamples[0].length-1];

        double activation; // predicted value
        int label; // observed value
        int qtdExamples = trainingExamples.length; // qtd of examples
        int count = 0;

        boolean hasError;
        do {
            hasError = false;
            count++;
            // For each example in the training do
            for (int i = 0; i < qtdExamples; i++) {
                
            	// Calculates the perceptron prediction (activation function)
                activation = Perceptron.activation(w, this.getExample(i));
                
                //observed value
                label = getTarget(i);

                // if the predicted value differs from observed value 
                if (label*activation <= 0 ) { 
                    hasError = true;
                    // adjusting the synaptic weight vector
                    for (int j = 0; j < w.length; j++) 
                        w[j] = w[j] + label*trainingExamples[i][j];
                }
            } /* while there is misclassifications and the quantity 
             of attempts is less than the maximum permitted (maxCount attribute)*/
        } while ( (hasError == true) && (count < this.maxCount) ); 

        System.out.println("Number of trials = " + count);
        return w;
    }
}