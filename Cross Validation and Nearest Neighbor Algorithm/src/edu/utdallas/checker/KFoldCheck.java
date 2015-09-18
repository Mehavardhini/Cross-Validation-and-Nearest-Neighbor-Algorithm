package edu.utdallas.checker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.utdallas.data.KFoldCheckConfig;
import edu.utdallas.data.DataValidation;

public class KFoldCheck {

	KFoldCheckConfig kFoldValidatorConfig;
	Fold folds[][];
	DataValidation validationData;

	public KFoldCheck(KFoldCheckConfig kFoldValidatorConfig) {
		super();
		this.kFoldValidatorConfig = kFoldValidatorConfig;
		int permCount = kFoldValidatorConfig.getPermutationCount();
		int foldCount = kFoldValidatorConfig.getFoldCount();
		folds = new Fold[permCount][foldCount];
	}

	public KFoldCheckConfig getkFoldValidatorConfig() {
		return kFoldValidatorConfig;
	}

	public DataValidation getValidationData() {
		return validationData;
	}

	public void setValidationData(DataValidation validationData) {
		this.validationData = validationData;
	}

	public void setkFoldValidatorConfig(
			KFoldCheckConfig kFoldValidatorConfig) {
		this.kFoldValidatorConfig = kFoldValidatorConfig;
	}

	ArrayList<Set<Integer>> splitIntoKFolds(int foldCount, int permutation[]) {

		int exampleCount = kFoldValidatorConfig.getExampleMap().size();
		int minCount = (int) Math.floor((double) exampleCount / foldCount);
		int maxCount = (int) Math.ceil((double) exampleCount / foldCount);
		ArrayList<Set<Integer>> dataSets = new ArrayList<Set<Integer>>();

		for (int i = 0; i < foldCount; i++) {
			dataSets.add(new HashSet<Integer>());
		}

		int setCounter = 0;
		for (int i = 0; i < permutation.length; i++) {
			dataSets.get(setCounter).add(permutation[i]);
			if ((i + 1) % minCount == 0 && setCounter < (foldCount - 1)) {
				setCounter++;
			}
		}
		return dataSets;
	}

	int leaveOneOut(ArrayList<Set<Integer>> dataSets, int kNN, int permIndex) {
		Set<Integer> validationSet = null;
		Set<Integer> trainingSet = null;
		Set<Integer> mergedSet = new HashSet<Integer>();
		for (Set<Integer> dataSet : dataSets) {
			mergedSet.addAll(dataSet);
		}
		int foldCounter = 0;
		for (Set<Integer> dataSet : dataSets) {
			validationSet = dataSet;
			trainingSet = new HashSet<Integer>(mergedSet);
			trainingSet.removeAll(validationSet);
			Fold fold = new Fold();
			fold.setTrainingSet(trainingSet);
			fold.setValidationSet(validationSet);

			fold.validate(kNN, kFoldValidatorConfig.getExampleDistanceMatrix(),
					kFoldValidatorConfig.getExampleMap());

			folds[permIndex][foldCounter++] = fold;
		}

		return 0;
	}

	public int checker(int kNN) {
		
		int foldNumber = kFoldValidatorConfig.getFoldCount();
		int permNumber=kFoldValidatorConfig.getPermutations().length;
		int exampleNumber = kFoldValidatorConfig.getExampleMap().size();
		int minNumber = (int) Math.floor((double) exampleNumber / foldNumber);
		if(kNN > minNumber)
		{
			return -1;
		}
		validationData = new DataValidation(permNumber,kNN);
		for(int i=0;i<permNumber;i++)
		{
			int permutation[] = kFoldValidatorConfig.getPermutations()[i];
			ArrayList<Set<Integer>> dataSets = splitIntoKFolds(foldNumber,
					permutation);
			leaveOneOut(dataSets, kNN, i);
			validationData.calculateE(i, folds[i], kFoldValidatorConfig.getDataSampleCount());
		}
		validationData.calculateClassifierError();
		validationData.calculateClassifierSigma();
		return 0;
	}
}
