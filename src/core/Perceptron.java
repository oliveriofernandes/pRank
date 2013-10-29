package core;

public class Perceptron {

	/** Calculates the activation function of this (unthresholded) Perceptron
	 * @param weights
	 * @param inputs
	 * @return 1 if this perceptron was activate or -1 otherwise 
	 */
	public static double activation (double[] weights, double[] inputs){
		double sum = PerceptronRule.dotProduct(weights, inputs);
		return sum;
	}
}