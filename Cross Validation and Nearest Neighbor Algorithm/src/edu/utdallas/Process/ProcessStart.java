package edu.utdallas.Process;

import edu.utdallas.checker.KFoldCheck;
import edu.utdallas.classification.DataPredictingClass;
import edu.utdallas.data.KFoldCheckConfig;
import edu.utdallas.fileprocessing.FileUtilities;
import edu.utdallas.fileprocessing.FileWriterForOutput;

public class ProcessStart {

	public static void main(String args[]) {

		try {

			if (args.length < 4) {
				System.err.println("Insufficent no of arguments: "
						+ args.length);
				System.err.println("4 arguments required");
				System.err.println("1.configFile 2.dataFile 3.Destination Directory 4.kNN");
				System.err.println("eg.java -jar kNNValidator.jar \"G:\\input\\input1.txt\" "
								+ "\"G:\\input\\input2.txt\" "
								+ "\"G:\\output\" 4");
				
				
				for (String arg : args) {

					System.out.println("Argument:" + arg);
					
				}
				return;
			}

			String configFile = args[0];
			String dataFile = args[1];
			String outputDir = args[2];
			int kNNMax = Integer.parseInt(args[3]);


			if (kNNMax < 1) {
				System.err.println("K cannot be less than 1 Exiting....");
				return;
			}
			if (!FileUtilities.isFilePresent(configFile)
					|| !FileUtilities.isFilePresent(dataFile)
					|| !FileUtilities.isDirPresent(outputDir)) {
				System.err.println("File/Directory Error. Exiting......");
				return;

			}			
			

			KFoldCheckConfig validatorConfiguration = new KFoldCheckConfig(
					configFile, dataFile);
			int initSuccess=validatorConfiguration.setValues();
			if(initSuccess!=0)
			{
				System.err.println("K-Fold Validator initialization failed");
				return;
			}
			DataPredictingClass unLabelledDataPredictor = new DataPredictingClass(
					validatorConfiguration.getExampleMap(),
					validatorConfiguration.getUnLabelledDataList(),
					validatorConfiguration.getDataSamples());

			unLabelledDataPredictor.generateUnlabelledNeighbours();

			for (int k = 1; k <= kNNMax; k++) {
				KFoldCheck kFoldValidator = new KFoldCheck(
						validatorConfiguration);
				int validationStatus=kFoldValidator.checker(k);
				
				if(validationStatus!=0)
				{
					return;
				}
				unLabelledDataPredictor.generatePredictedData(
						validatorConfiguration.getRowCount(),
						validatorConfiguration.getColCount(), k);
				FileWriterForOutput outputFileWriter = new FileWriterForOutput();
				
				outputFileWriter.outputFileGeneration(outputDir,
						unLabelledDataPredictor.getPredictedDataMatrix(),
						kFoldValidator.getValidationData(), k,
						validatorConfiguration.getRowCount(),
						validatorConfiguration.getColCount());
			}
			System.out.println("Process Complete!!!");
			System.out.println("Output File :" + args[2] + "\\" + "sxa144630_output.txt");

		} catch (NumberFormatException ne) {
			System.err.println("Argument kNN expected to be an integer");
			ne.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
