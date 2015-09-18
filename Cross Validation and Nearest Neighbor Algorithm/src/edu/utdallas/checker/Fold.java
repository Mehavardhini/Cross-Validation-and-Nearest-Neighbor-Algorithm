package edu.utdallas.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import edu.utdallas.classification.DistanceOperations;
import edu.utdallas.classification.NeighborSamples;
import edu.utdallas.data.SampleData;

public class Fold {
	
	Set<Integer> validation;
	Set<Integer> training;	
	int errorCount=0;
	HashMap<Integer, Character> predictedSignMap=new HashMap<Integer, Character>();
	
	
	public Set<Integer> getValidationSet() {
		return validation;
	}


	public void setValidationSet(Set<Integer> validationSet) {
		this.validation = validationSet;
	}


	public Set<Integer> getTrainingSet() {
		return training;
	}


	public void setTrainingSet(Set<Integer> trainingSet) {
		this.training = trainingSet;
	}


	public int getNoOfErrors() {
		return errorCount;
	}


	public void setNoOfErrors(int noOfErrors) {
		this.errorCount = noOfErrors;
	}


	public HashMap<Integer, Character> getPredictedSignMap() {
		return predictedSignMap;
	}


	public void setPredictedSignMap(HashMap<Integer, Character> predictedSignMap) {
		this.predictedSignMap = predictedSignMap;
	}


	int validate(int kNN,double exampleDistanceMatrix[][],HashMap<Integer, SampleData> exampleMap)
	{
		for(Integer dataSample:validation){
			
			char originalSign=exampleMap.get(dataSample).getSign();
			
			ArrayList<NeighborSamples> neighborList = DistanceOperations.extrtactNeighborDistances(dataSample, exampleDistanceMatrix, training);
			
			char predictedSign = DistanceOperations.signPredictor(exampleMap, neighborList, kNN);
			predictedSignMap.put(dataSample, predictedSign);
						
			if(predictedSign!=originalSign){
				errorCount++;
			}
			
		}
		return 0;
	}
	
	
	
	

}
