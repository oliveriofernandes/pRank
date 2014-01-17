package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;



/** This class executes a Multiclass Perceptron Learning Algorithm implementation. It receives
 * a data training containing multiples labels and returns a set of weight vectors
 * for each label, learned by the trainingMulticlassPerceptron() method. Actually
 * it returns a Map (weightVectorsMap attribute) which keys are labels and values
 * are its synaptic weights vector for a Perceptron. 
 * 
 * @author Olivério
 * 
 **/

public class MulticlassPerceptron  extends PerceptronRule{

	public Map<Integer,double[]> weightVectorsMap = new HashMap<Integer,double[]>();
	
	public MulticlassPerceptron (double[][] matrix, int maxCount) {
		super(matrix,maxCount);
		initWeightVectorsMap();
	}
	
	/** Assign the attribute Map (Map<Label,Vector>) of labels presents 
	 *  on the training set in a ordered form. */
	void initWeightVectorsMap(){
        
		int qtdExamples = this.trainingExamples.length;
		int dimension = this.trainingExamples[0].length-1;
        Set<Integer> labelSet = new HashSet<Integer>(); //Set labels of training

        for (int i = 0; i < qtdExamples; i++)
            labelSet.add((this.getTarget(i)));
        
        //List of the labels for ordering in a step after.
        List<Integer> listLabels = new ArrayList<Integer>(10);
       
        for (int i : labelSet)
            listLabels.add(i);

        Collections.sort(listLabels);
       
        /*Put the labels in a ordered form in the weightVectorsMap attribute and
          assigns a initial 'zero' synaptic weight vector for each label present*/ 
        for (Integer i : listLabels)
        	this.weightVectorsMap.put(i, new double[dimension]);
    }
	
	/**Computes the weight vector which maximize the activation function, 
	 * in a example of the training, returning its corresponded predicted label.
	 * 
	 * @param inputs
	 * @param target
	 * @return The label which its associated weight vector, maximizes 
	 * the activation function **/
	public int predictLabel(double[]inputs, int target){
		
		int predicted;
		double activation;
		double maxActivation;
		
		Set<Integer> labelSet = weightVectorsMap.keySet(); //set of all labels presented in the data set
		List<Integer> labelList = new ArrayList<Integer>(); //list of all labels presented in the data set
		
		/*Add in the labelList all labels of the data set except the present
		label in the example */
		for (Integer integer : labelSet) {
			if(!integer.equals(target))
			labelList.add(integer);
		}

		
		//Choose a label, assuming it's the predicted label (at this moment)  
		predicted = labelList.get(0);
		
		//The maximum activation is initially achieved from the weight vector previously chosen, corresponding the
		//predicted label 
		double [] wi =  weightVectorsMap.get(predicted);
		maxActivation = Perceptron.activation(wi, inputs);
		
		//Iterates over the weight vectors in order to get the maximum activation
		for (Map.Entry<Integer, double[]> entry : weightVectorsMap.entrySet()){
			double [] weights = entry.getValue();
			activation = dotProduct(weights, inputs);
            
            /*if there is a greater activation than the previously maxActivation 
             * this one will be considered the largest and its label will be the predict label */ 
            if(activation > maxActivation){
            	maxActivation = activation;
            	predicted = entry.getKey();
            }
		}
		return predicted;
	}
	
	public Map<Integer,double[]> trainingMulticlassPerceptron() {
		
		//Count the number of trials of the learning 
		int count = 0;
        int colVectors = this.getExample(0).length; //dimension of the labels
        boolean hasError;
        int predictedLabel;
        int target;
        
        do{
        	hasError = false;
        	count++;
        	
        	for (int i = 0; i < trainingExamples.length; i++) {
        		//Assign the present label in the target variable        	 
        		target = this.getTarget(i);  
			
        		//Assign the predicted label ( arg max f(Wi,X) ) in the predictedLabel variable 
        		predictedLabel = predictLabel(this.getExample(i),target);
			
        		//If there was a misclassification
        		if(predictedLabel!=target){
        			hasError = true;
        			//Adjusts the weights of the vectors
        			for (int j = 0; j < colVectors; j++) {
        				this.weightVectorsMap.get(target)[j]  += trainingExamples[i][j];
        				this.weightVectorsMap.get(predictedLabel)[j] -= trainingExamples[i][j];
					}
        		}
        	} /* while there is misclassifications and the quantity 
              of attempts is less than the maximum permitted (maxCount attribute)*/
        }while ( (hasError == true) && (count < this.maxCount) );
        
        System.out.println("Tentativas = " + count);
        return this.weightVectorsMap;
	}
	public static void main(String[] args) {
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
		
		MulticlassPerceptron p = new MulticlassPerceptron(orWith2Variables, 100);
		Map<Integer,double[]> vetores = p.trainingMulticlassPerceptron();
		for (Entry<Integer, double[]> entry: vetores.entrySet()) {
		System.out.println("vetor:");
			for (double ds : entry.getValue()) {
				System.out.println(ds);
			} 
		}
	}
}