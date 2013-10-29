package bkp;

import java.util.Random;

import core.Perceptron;

/** @author Olivério */

/** Implements the simplest learning perceptron rule to obtain a
 * synaptic weight vector for a single perceptron unit, which yields 
 * a correct output (1 or -1) for each given example. **/
public class PerceptronRuleBKP {

    double[][] trainingExamples;
    double learningRate;
    
    /** The original Perceptron includes a fixed threshold value which aid the
     * perceptron activation function yields this results. This algorithm
     * actually learn the threshold value too. For this
     * reason, this method includes a default constant, equals 1,
     * intending match with the value of w0 (thresholded value), considering
     * that threshold as any weight value. So its execution give one dumb
     * column on each example valued by 1.
     *
     * @param matrix
     * @param learningRate
     */
    public PerceptronRuleBKP(double[][] matrix, double learningRate) {
        this.learningRate = learningRate;
       
        //Adds a column in the matrix with a value equal to 1.
        configureExamples(matrix);

        //Register the labels of the data set.
     //   regLabels(labels);
       
    }
   
    public void configureExamples(double[][] matrix){
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

        // The length weight vector must have a dimension size less than
        // the dimension of training example, by one - for contempling the w0 thresholded that will be learned
     //   this.weights = new double[col - 1];
    }
   
    public double[][] adjustExamples(double[][] matrix){
        int lin = matrix.length;
        int col = matrix[0].length + 1; // one more column to setting 1 at W0
        double [][] matrix2 = new double[lin][col];

        /* Adds a column in the matrix with a value equal to 1. This new column
         * corresponds the first index column. In other words, the x0 values
         * (equals 1 on each example) match with w0 weight that consists in the
         * thresholded value that will be learning by the Perceptron Rule. */
        for (int i = 0; i < lin; i++) {
            for (int j = 1; j < col; j++) {
                matrix2 [i][j] = matrix[i][j - 1];
            }
        }

        // fill value '1' for each training example.
        for (int i = 0; i < lin; i++) {
            matrix2 [i][0] = 1;
        }

        // The length weight vector must have a dimension size less than
        // the dimension of training example, by one - for contempling the w0 thresholded that will be learned
   
        return matrix2;
    }
   
    public void setTrainingExamples(double [][] examples){
   
        if (examples[0].length != this.trainingExamples[0].length)
            this.trainingExamples = adjustExamples(examples);
        else
            this.trainingExamples = examples;
   
    }

    /** Initialize the weight vector yielding pseudo random values (each weight). */
    public double[] initWeights(double[] vetor) {
        // The generator of pseudo random numbers between 0 and 1.
        Random generator = new Random();

        /* To guarantee that each value produced is greater than zero. It is
         * added a small value (1/vector.length) at the number generated based
         * of the size of the vector. */
        for (int i = 0; i < vetor.length; i++)
            vetor[i] = (generator.nextDouble() + (1 / vetor.length));

        return vetor;

    }

    /** Get the line of the matrix of training examples.
     *
     * @param index
     * @return example
     */
    public double[] getExample(int index) {
       
        int length = this.trainingExamples[index].length - 1;
       
        double[] example = new double[length];

        for (int j = 0; j < length; j++)
            example[j] = this.trainingExamples[index][j];

        return example;

    }

   
public double[] getExample(int index, double[][] training) {
       
        int length = training[index].length - 1;
       
        double[] example = new double[length];

        for (int j = 0; j < length; j++)
            example[j] = training[index][j];

        return example;

    }
   
    /** Get the class of an example.
     *
     * @param index
     * @return the class (value) belonging to the current example
     */
    public int getTarget(int index) {
        int length = this.trainingExamples[index].length - 1;

        return (int) this.trainingExamples[index][length];
    }

    public int getTarget(int index,double training[][]) {
        int length = training[index].length - 1;

        return (int) training[index][length];
    }

    public void setTarget(int index, double newValue, double training[][]) {
        int length = training[index].length - 1;

        training[index][length] = newValue;
    }
   
    /** Execute the training step, adjusting the 'synaptic' weights
     *
     * @return the synaptic weight vector learned
     */
    public double[] training() {

    	//weights
    	double [] w = new double[this.trainingExamples[0].length-1];
        double deltaWeights[] = new double[w.length];

        double output;
        int qtdExamples = this.trainingExamples.length; // qtd of examples
        int count = 0;

        boolean hasError;
        do {
            hasError = false;
            count++;

            // For each value in training examples, Do
            for (int i = 0; i < qtdExamples; i++) {
                // Calculates the perceptron prediction
                output = Perceptron.activation(w, this.getExample(i));
               
                if (output*getTarget(i)>=0) { /* if the predicted value differs from the value
                                                 observed: adjusts the synaptic weight vector */
                    hasError = true;

                    // adjusting the synaptic weight vector
                    for (int j = 0; j < w.length; j++) {
                        deltaWeights[j] = this.learningRate * (getTarget(i) - output) * this.trainingExamples[i][j];
                        w[j] = w[j] + deltaWeights[j];

                        // System.out.println("peso [" + j + "]" + weights[j]);
                    }
                }
            }
        } while (hasError == true); // until there is no misclassifications
        return w;
    }
   
    /** Execute the training step, adjusting the 'synaptic' weights
    *
    * @return the synaptic weight vector learned
    */
    public double[] training(double[][] examples, int maxCount) {
       //weights
        double [] w = new double[examples[0].length-1];
        double deltaW[] = new double[w.length];

        double activation;
        int qtdExamples = examples.length; // qtd of examples
        int count = 0;

        boolean hasError;
        do {
            hasError = false;
            count++;
            // For each value in training examples, Do
            for (int i = 0; i < qtdExamples; i++) {
                // Calculates the perceptron prediction
                activation = Perceptron.activation(w, this.getExample(i,examples));
               
                if (activation != getTarget(i,examples)) { /* if the predicted value differs from the value
                                                 observed: adjusts the synaptic weight vector */
                    hasError = true;

                    // adjusting the synaptic weight vector
                    for (int j = 0; j < w.length; j++) {
                        deltaW[j] = this.learningRate * (getTarget(i,examples) - activation) * examples[i][j];
                        w[j] = w[j] + deltaW[j];
                    }
                }
            }
        } while ( (hasError == true) || (count>maxCount) ); // until there is no misclassifications or count > max of trials 

    //    for (int j = 0; j < this.weights.length; j++) {
    //        System.out.println("peso [" + j + "]" + weights[j]);
    //    }
        return w;
    }


    
    
    
    
    
    
    
        
    
    
    
    
    
    
   
       
    public static void main(String[] args) {
       
        /* Set points linearly separable - values for OR and AND boolean
           functions with 2 and 3 variables. */
       
        double orWith2Variables[][] = { { -1.0, -1, -1 }, { 1, -1, 1 },
                { -1, 1, 1 }, { 1, 1, 1 } };

        double andWith2Variables[][] = { { -1.0, -1, -1 }, { 1, -1, -1 },
                { -1, 1, -1 }, { 1, 1, 1 } };

        double orWith3Variables[][] = { { -1, -1, -1, -1 }, { 1, -1, -1, 1 },
                { -1, 1, -1, 1 }, { -1, -1, 1, 1 }, { 1, 1, -1, 1 },
                { 1, -1, 1, 1 }, { -1, 1, 1, 1 }, { 1, 1, 1, 1 } };

        double andWith3Variables[][] = { { -1, -1, -1, -1 }, { 1, -1, -1, -1 },
                { -1, 1, -1, -1 }, { -1, -1, 1, -1 }, { 1, 1, -1, -1 },
                { 1, -1, 1, -1 }, { -1, 1, 1, -1 }, { 1, 1, 1, 1 } };

        double learningRate = 0.5;
        
        

        PerceptronRuleBKP pRule = new PerceptronRuleBKP(andWith2Variables, learningRate);

        for (int i = 0; i < andWith2Variables.length; i++) {
            System.out.println(pRule.getTarget(i));
        }
       
       
    //    System.out.println("qtd examples = " + pRule.labels.size());
       
        double [] w = pRule.training();
       
        for (int i = 0; i < w.length; i++) {
            System.out.println("peso [" + i + "] = " + w[i]);
        }
    }
}
