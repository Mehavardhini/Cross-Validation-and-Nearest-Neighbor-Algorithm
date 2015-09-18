package edu.utdallas.fileprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import edu.utdallas.data.KFoldCheckConfig;

public class FileReaderForInput {

	String readNextField(Scanner configFileScanner) {
		if (configFileScanner.hasNext()) {
			return configFileScanner.next();
		} else {
			return null;
		}

	}

	int readPermutations(Scanner configFileScanner, int permutations[][],int permutationCount, int dataSampleCount) {

		for (int i = 0; i < permutationCount; i++) {
			for (int j = 0; j < dataSampleCount; j++) {
				permutations[i][j] = configFileScanner.nextInt();
				// ////System.out.println(permutations[i][j]);
			}
		}
		return 0;
	}
	
	int readDataSamples(Scanner configFileScanner, char dataSamples[][],int rowCount, int colCount) {
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				dataSamples[i][j] = configFileScanner.next().charAt(0);
				// ////System.out.println(permutations[i][j]);
			}
		}
		return 0;
	}
	

	public int readConfigFile(String fileName, KFoldCheckConfig config) {

		Scanner configFileScanner = null;

		try {
			configFileScanner = new Scanner(new BufferedReader(new FileReader(fileName)));

			// get the fold count, data sample count and permutation count
			config.setFoldCount(configFileScanner.nextInt());

			int dataSampleCount = configFileScanner.nextInt();
			config.setDataSampleCount(dataSampleCount);

			int permutationCount = configFileScanner.nextInt();
			config.setPermutationCount(permutationCount);

			// Allocate memory for permutation array based on no. of data
			// samples and permutations
			int permutations[][] = new int[permutationCount][dataSampleCount];

			// Read the permutations
			readPermutations(configFileScanner, permutations, permutationCount,
					dataSampleCount);
			config.setPermutations(permutations);

			////System.out.println(config);
			configFileScanner.close();

		} catch (Exception e) {
			e.printStackTrace();
			configFileScanner.close();
			return -1;
		}

		return 0;

	}

	public int readDataSampleFile(String fileName, KFoldCheckConfig config) {

		Scanner configFileScanner = null;

		try {
			configFileScanner = new Scanner(new BufferedReader(new FileReader(
					fileName)));

			// get the fold count, data sample count and permutation count
			int rowCount = configFileScanner.nextInt();
			config.setRowCount(rowCount);

			int colCount = configFileScanner.nextInt();
			config.setColCount(colCount);
			
			// Allocate memory for permutation array based on no. of data
			// samples and permutations
			char dataSamples[][] = new char[rowCount][colCount];

			// Read the permutations
			readDataSamples(configFileScanner, dataSamples, rowCount,	colCount);
			config.setDataSamples(dataSamples);

			////System.out.println(config);
			configFileScanner.close();

		} catch (Exception e) {
			e.printStackTrace();
			configFileScanner.close();
			return -1;
		}

		return 0;

	}

}
