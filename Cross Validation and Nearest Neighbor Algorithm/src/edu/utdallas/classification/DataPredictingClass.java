package edu.utdallas.classification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import edu.utdallas.data.SampleData;

public class DataPredictingClass {

	HashMap<Integer, SampleData> exampleMap;
	ArrayList<SampleData> unLabelledDataList;
	char dataSamples[][];
	double unLabelledDistanceMatrix[][];
	char predictedDataMatrix[][];

	HashMap<SampleData, ArrayList<NeighborSamples>> neighborsOfUnlabelledMap = new HashMap<SampleData, ArrayList<NeighborSamples>>();
	int unLabelledDataSize = 0;
	int exampleSize = 0;

	public DataPredictingClass(HashMap<Integer, SampleData> exampleMap,
			ArrayList<SampleData> unLabelledDataList, char[][] dataSamples) {
		super();
		this.exampleMap = exampleMap;
		this.unLabelledDataList = unLabelledDataList;
		this.dataSamples = dataSamples;

		this.unLabelledDataSize = unLabelledDataList.size();
		this.exampleSize = exampleMap.size();

		this.unLabelledDistanceMatrix = new double[unLabelledDataSize][exampleSize];
	}

	public char[][] getPredictedDataMatrix() {
		return predictedDataMatrix;
	}

	public void setPredictedDataMatrix(char[][] predictedDataMatrix) {
		this.predictedDataMatrix = predictedDataMatrix;
	}

	public ArrayList<NeighborSamples> computeNeighbors() {

		return null;
	}
	public void generatePredictedData(int rowCount, int colCount, int kNN) {
		predictedDataMatrix = new char[rowCount][colCount];
		if (dataSamples != null) {
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < colCount; j++) {

					if (dataSamples[i][j] == '+' || dataSamples[i][j] == '-') {

						predictedDataMatrix[i][j] = dataSamples[i][j];
					} else {
						char predictedChar = predictUnlabelledData(i, j, kNN);
						predictedDataMatrix[i][j] = predictedChar;
					}
				}

			}

		}
	}

	


	public int generateUnlabelledNeighbours() {

		generateUnLabelledDistanceMatrix();

		for (int unlabelledIndex = 0; unlabelledIndex < unLabelledDataList
				.size(); unlabelledIndex++) {

			Set<Integer> neighborSet = exampleMap.keySet();
			SampleData unLabelledData = unLabelledDataList.get(unlabelledIndex);

			ArrayList<NeighborSamples> neighborList = DistanceOperations
					.extrtactNeighborDistances(unlabelledIndex,
							unLabelledDistanceMatrix, neighborSet);

			neighborsOfUnlabelledMap.put(unLabelledData, neighborList);

		}
		return 0;

	}

	public char predictUnlabelledData(int row, int column, int kNN) {

		ArrayList<NeighborSamples> neighborList = neighborsOfUnlabelledMap
				.get(new SampleData(row, column, ' '));
		char predictedSign = DistanceOperations.signPredictor(exampleMap,
				neighborList, kNN);
		return predictedSign;
	}
	
	public int generateUnLabelledDistanceMatrix() {

		for (int unlabelledIndex = 0; unlabelledIndex < unLabelledDataList
				.size(); unlabelledIndex++) {

			SampleData unLabelledData = unLabelledDataList.get(unlabelledIndex);

			Set<Integer> egKeySet = exampleMap.keySet();

			if (egKeySet != null) {
				Iterator<Integer> exampleIterator = egKeySet.iterator();

				while (exampleIterator.hasNext()) {

					int exampleIndex = exampleIterator.next();
					SampleData exampleData = exampleMap.get(exampleIndex);

					unLabelledDistanceMatrix[unlabelledIndex][exampleIndex] = DistanceOperations
							.getDistance(unLabelledData.getRow(),
									unLabelledData.getColumn(),
									exampleData.getRow(),
									exampleData.getColumn());

				}
			}

		}

		return 0;
	}

	

}
