package edu.utdallas.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import edu.utdallas.classification.DistanceOperations;
import edu.utdallas.fileprocessing.FileReaderForInput;

public class KFoldCheckConfig {

	int foldNumber;
	int sampleNumber;
	int permutationNumber;
	int rowNumber;
	int colNumber;
	char sampleDataArray[][];
	int permutationArray[][];
	String configFileName;
	String dataFileName;
	
	HashMap<Integer, SampleData> examples = new HashMap<Integer, SampleData>();
	ArrayList<SampleData> unLabelledDataList= new ArrayList<SampleData>();
	double exampleDistanceMatrix[][];
	
	public KFoldCheckConfig(String configFile, String dataFile) {
		super();
		this.configFileName = configFile;
		this.dataFileName = dataFile;
		
	}

	public int setValues() {
		
		FileReaderForInput fileReader = new FileReaderForInput();

		if (fileReader.readConfigFile(configFileName, this)!=0)
		{
			return -1;
			
		}

		if(fileReader.readDataSampleFile(dataFileName,this)!=0)
		{
			return -1;
		}
		

		exampleMapPopulation();
		printExampleMap();

		computeExampleDistanceMatrix();
		printExampleDistanceMatrix();	
		
		return 0;
	}

	@Override
	public String toString() {

		String str = "============================================================\n";
		str += "                  Classifier Config                         \n";
		str += " foldCount :" + foldNumber;
		str += " dataSampleCount :" + sampleNumber;
		str += " permutationCount :" + permutationNumber;
		str += " rowCount :" + rowNumber;
		str += " colCount :" + colNumber;
		str += "\n============================================================\n";

		return str;
	}

	public int getFoldCount() {
		return foldNumber;
	}

	public void setFoldCount(int foldCount) {
		this.foldNumber = foldCount;
	}

	public int getDataSampleCount() {
		return sampleNumber;
	}

	public void setDataSampleCount(int dataSampleCount) {
		this.sampleNumber = dataSampleCount;
	}

	public int getPermutationCount() {
		return permutationNumber;
	}

	public void setPermutationCount(int permutationCount) {
		this.permutationNumber = permutationCount;
	}

	public int getRowCount() {
		return rowNumber;
	}

	public void setRowCount(int rowCount) {
		this.rowNumber = rowCount;
	}

	public int getColCount() {
		return colNumber;
	}

	public void setColCount(int colCount) {
		this.colNumber = colCount;
	}

	public char[][] getDataSamples() {
		return sampleDataArray;
	}

	public void setDataSamples(char[][] dataSamples) {
		this.sampleDataArray = dataSamples;
	}

	public int[][] getPermutations() {
		return permutationArray;
	}

	public void setPermutations(int[][] permutations) {
		this.permutationArray = permutations;
	}

	public String getConfigFile() {
		return configFileName;
	}

	public void setConfigFile(String configFile) {
		this.configFileName = configFile;
	}

	public String getDataFile() {
		return dataFileName;
	}

	public void setDataFile(String dataFile) {
		this.dataFileName = dataFile;
	}

	public HashMap<Integer, SampleData> getExampleMap() {
		return examples;
	}

	public void setExampleMap(HashMap<Integer, SampleData> exampleMap) {
		this.examples = exampleMap;
	}

	public ArrayList<SampleData> getUnLabelledDataList() {
		return unLabelledDataList;
	}

	public void setUnLabelledDataList(ArrayList<SampleData> unLabelledDataList) {
		this.unLabelledDataList = unLabelledDataList;
	}

	public double[][] getExampleDistanceMatrix() {
		return exampleDistanceMatrix;
	}

	public void setExampleDistanceMatrix(double[][] exampleDistanceMatrix) {
		this.exampleDistanceMatrix = exampleDistanceMatrix;
	}


	public void exampleMapPopulation() {
		int exampleCount = 0;

		if (sampleDataArray != null) {

			for (int i = 0; i < rowNumber; i++) {
				for (int j = 0; j < colNumber; j++) {
					if (sampleDataArray[i][j] == '+' || sampleDataArray[i][j] == '-') {

						examples.put(exampleCount++, new SampleData(i, j,
								sampleDataArray[i][j]));
					}else{
						
						unLabelledDataList.add(new SampleData(i, j, sampleDataArray[i][j]));
					}
				}
			}

		}

	}

	public void printExampleMap() {

		//System.out.println("======================================");
		//System.out.println("        Example Map                   ");

		if (examples != null) {
			Iterator<Integer> exampleIterator = examples.keySet().iterator();

			while (exampleIterator.hasNext()) {
				int key = exampleIterator.next();
				//System.out.println("Example(" + key + ")" + examples.get(key));
			}

		}
		//System.out.println("======================================");

	}

	int computeExampleDistanceMatrix() {
		SampleData dataSample1 = null;
		SampleData dataSample2 = null;
		int exampleSize = examples.size();
		exampleDistanceMatrix = new double[exampleSize][exampleSize];

		if (sampleDataArray != null) {

			for (int i = 0; i < exampleSize; i++) {
				for (int j = 0; j < exampleSize; j++) {
					dataSample1 = examples.get(i);
					dataSample2 = examples.get(j);
					exampleDistanceMatrix[i][j] = DistanceOperations.getDistance(dataSample1.getRow(),
							dataSample1.getColumn(), dataSample2.getRow(),
							dataSample2.getColumn());
				}
			}

		}

		return 0;
	}

	void printExampleDistanceMatrix() {

		//System.out.println("======================================");
		//System.out.println("   Example Distance Matrix                   ");

		for (int i = 0; i < examples.size(); i++) {
			for (int j = 0; j < examples.size(); j++) {
				//System.out.print((int) exampleDistanceMatrix[i][j] + "\t");
			}

			//System.out.println();
		}

		//System.out.println("======================================");

	}

}
