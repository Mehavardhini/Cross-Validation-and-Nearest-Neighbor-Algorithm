package edu.utdallas.fileprocessing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

import edu.utdallas.data.DataValidation;

public class FileWriterForOutput {

	String baseFileName="sxa144630_output";

	public int outputFileGeneration(String outputDirectory,char predictedDataMatrix[][], DataValidation validationData,int kNN,int rowCount,int colCount)
	{
		if(!FileUtilities.isDirPresent(outputDirectory))
		{
			return -1;			
		}
		
		File dir= new File(outputDirectory);


		String filePath=dir.getAbsolutePath()+File.separator+baseFileName+".txt";
		
		File file = new File (filePath);
		
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			printWriter.println();
			printWriter.println("k="+ kNN+ " e="+validationData.getClassifierError()+ " sigma="+validationData.getSigma());
			
			
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < colCount; j++) {
					printWriter.print(predictedDataMatrix[i][j] + "\t");
				}
				printWriter.println();

			}

			printWriter.close ();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return 0;


	}

}
