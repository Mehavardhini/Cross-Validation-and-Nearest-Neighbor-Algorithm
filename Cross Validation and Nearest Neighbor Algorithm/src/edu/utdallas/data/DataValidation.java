package edu.utdallas.data;

import edu.utdallas.checker.Fold;

public class DataValidation {
	double classifierError; //---> e 
	double E[];
	double sigma;
	int kNN;
	int noOfPermutation;
	
	
			
	public DataValidation(int noOfPermutation,int kNN) {
		super();
		this.noOfPermutation = noOfPermutation;
		E= new double[noOfPermutation];
		this.kNN=kNN;
	}

	public double getClassifierError() {
		return classifierError;
	}

	public void setClassifierError(double classifierError) {
		this.classifierError = classifierError;
	}

	public double[] getE() {
		return E;
	}

	public void setE(double[] e) {
		E = e;
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	public int getkNN() {
		return kNN;
	}

	public void setkNN(int kNN) {
		this.kNN = kNN;
	}

	public int getNoOfPermutation() {
		return noOfPermutation;
	}

	public void setNoOfPermutation(int noOfPermutation) {
		this.noOfPermutation = noOfPermutation;
	}

	public void calculateE(int permIndex,Fold folds[], int noOfSamples){
		
		int totalErrors=0;
		for(Fold fold:folds){
			
			totalErrors+=fold.getNoOfErrors();
			
		}
		E[permIndex]=(double)totalErrors/noOfSamples;
		
		
	}
	
	public void calculateClassifierError(){
		
		double totalAccuracy=0.0;
		
		for(double accuracy:E){
		totalAccuracy+= accuracy;	
		}
		classifierError=totalAccuracy/noOfPermutation;
	}
	
public void calculateClassifierSigma(){
	
		
		
		double meanDiffSum=0.0;
		double variance=0.0;
		
		for(double accuracy:E){
			meanDiffSum+=  Math.pow(accuracy-classifierError, 2);	
		}
		variance= meanDiffSum/(noOfPermutation-1);
		
		sigma=Math.pow(variance, 0.5);
	}
	

}
