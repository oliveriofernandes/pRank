package bkp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.Perceptron;

/** @author Olivério */

public class MulticlassPerceptronBKP {

	double[][] trainingExamples;
	double[] weights;
	double learningRate;
	Map<Integer,Integer> labels = new HashMap<Integer,Integer>();
	public MulticlassPerceptronBKP (double[][] matrix, double ni) {
		int lin = matrix.length;
		int col = matrix[0].length + 1;
		this.trainingExamples = new double[lin][col];

		/* Add a column in the matrix with a value equal to 1.
		 * This new column corresponds the first index column.
		 * In other words, the x0 values (equals 1 on each example)
		 * match with w0 weight that consists in the thresholded
		 * value that will be learning by the Perceptron Rule. */ 
		for (int i = 0; i < lin; i++) {
			for (int j = 1; j < col; j++) {
				trainingExamples[i][j] = matrix[i][j-1];
			}
		}

		//fill value '1' for each training example.
		for (int i = 0; i < lin; i++) {
			trainingExamples[i][0] = 1;
		}

		//length of array of weights must has its size dimension less than training example (by one) 
		this.weights = new double[col - 1];
		this.learningRate = ni;
	}
	
	
	/** Fill this attribute with the values of the labels
     * presented in a dataset */
   
    void regLabels(Map<Integer, Integer> labels){
        int lim = this.trainingExamples.length;
        int index = 0;
        Set<Integer> l = new HashSet<Integer>();

        for (int i = 0; i < lim; i++)
            l.add((this.getTarget(i)));
        List<Integer> list = new ArrayList<Integer>(10);
       
        for (int i : l)
            list.add(i);

        Collections.sort(list);
       
        for (Integer i : list)
        	this.labels.put(i, index++);
    }
	
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
    
	public double[][] trainingMulticlassBKP1(){
	       
        int lines = this.labels.size();
        int columns = this.getExample(0).length;
       
        boolean hasError;
       
        double [][] classWeights = new double [lines][columns];
       
        double activation = 0;
        double maxAct = 0;
        int indexAux;
        int index;
        double predicted;
        int indexW; //index of correct weight
        do {
            hasError = false;
            for(int i = 0; i < trainingExamples.length; i++){
                maxAct = 0;
                index = 0;
                for (int j = 0; j < classWeights.length; j++) {
                    activation = 0;
                    indexAux = j;
                    for (int k = 0; k < columns; k++) { //Calculates activation
                        activation = activation + classWeights[j][k]*getExample(i)[k];
                    }
                    if(activation > maxAct){
                        maxAct = activation;
                        index = indexAux;
                    }
                }
               
                //Predict the output ^Y(xi)
                predicted = Perceptron.activation(classWeights[index], getExample(i));
               
                int observed = this.getTarget(i); //observed value 

                //index of correct weight
                indexW = labels.get(observed); // index of W which corresponds the wth vector
               
                //If prediction isn't correct
                if(predicted!=observed){
                    hasError = true;
                   
//                    for (int j = 0; j < classWeights.length; j++) {
//                        for (int k = 0; k < classWeights[0].length; k++) {
//                           
//                            int one;
//                           
//                            double u = getExample(i)[k] * (classWeights[indexW][j] - classWeights[index][j]);
//                            classWeights[j][k] = classWeights[j][k] + u; 
//                        }
//                       
//                    }
                   
                    //Update weights
                    for (int j = 0; j < classWeights[0].length; j++) {
                        classWeights[index][j] = classWeights[index][j] - getExample(i)[j];
                        classWeights[indexW][j] = classWeights[indexW][j] +  getExample(i)[j];
                    }
                }
            }
        }while (hasError == true);
       
        return classWeights;
       
    }
   
    public double[][] trainingMulticlassBKP2(){
       
        int lines = this.labels.size();
        int columns = this.getExample(0).length;
       
        boolean hasError;
       
        double [][] classWeights = new double [lines][columns];
       
        double activation = 0;
        double maxAct = 0;
        int indexAux;
        int index;
        double predicted;
        int indexW; //index of correct weight
        do {
            hasError = false;
            for(int i = 0; i < trainingExamples.length; i++){
                maxAct = 0;
                index = 0;
                for (int j = 0; j < classWeights.length; j++) {
                    activation = 0;
                    indexAux = j;
                    for (int k = 0; k < columns; k++) { //Calculates activation
                        activation = activation + classWeights[j][k]*getExample(i)[k];
                    }
                    if(activation > maxAct){
                        maxAct = activation;
                        index = indexAux;
                    }
                }
               
                //Predict the output ^Y(xi)
                predicted = Perceptron.activation(classWeights[index], getExample(i));
                int observed = this.getTarget(i); //observed value 
                //index of correct weight
                indexW = labels.get(observed); // index of W which corresponds the wth vector
                //If prediction isn't correct
                if (predicted != observed){
                    hasError = true;
       
                //update weights
                   
                    if(indexW == index ){
                        for (int j = 0; j < classWeights[0].length; j++)
                            classWeights[indexW][j] = classWeights[indexW][j] +  getExample(i)[j];
                       
                    }else{
                        for (int j = 0; j < classWeights[0].length; j++) {
                            classWeights[index][j] = classWeights[index][j] - getExample(i)[j];
                            classWeights[indexW][j] = classWeights[indexW][j] +  getExample(i)[j];
                            //double u = getExample(index)[k] * ( observed - predicted );
                            //classWeights[index][k] = classWeights[index][k] + u; 
                       
                        }   
                    }
                   
                   
               
                }
                    //Update weights
//                    for (int j = 0; j < classWeights[0].length; j++) {
//                        classWeights[index][j] = classWeights[index][j] - getExample(i)[j];
//                        classWeights[indexW][j] = classWeights[indexW][j] +  getExample(i)[j];
//                    }
//                }
            }
           
            for (int i = 0; i < classWeights.length; i++) {
                for (int j = 0; j < classWeights[0].length; j++) {
                    System.out.println("peso [" + i + "][" + j +"] = " + classWeights[i][j]);
                }
            }
            System.out.println();
        }while (hasError == true);
       
        return classWeights;

    }

    //Adapts Perceptron Algorithm for the multiclass - Kesler�s construction
    public double[] trainingMulticlass(){
       
    	int maxCount = 1000;
        double [][] training = this.trainingExamples;
       
        ArrayList<double[]> weights = new ArrayList<double[]>();
       
        //For each present label in data set
        for (Map.Entry<Integer, Integer> entry : labels.entrySet()){
           
            double label = entry.getKey();
           
            for (int i = 0; i < training.length; i++) {
                if( label == getTarget(i) )
                    training[i][trainingExamples[0].length-1] = 1;
                else
                    training[i][trainingExamples[0].length-1] = -1;
            }
           
            weights.add(this.trainingMulticlass());   
        }
//        for (double[] ds : weights) {
//            for (int i = 0; i < ds.length; i++) {
//                System.out.println(ds[i]);
//            }
//        }
       
        return null;
    }


	
}
