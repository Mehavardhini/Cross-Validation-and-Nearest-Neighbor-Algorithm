package edu.utdallas.fileprocessing;

import java.io.File;

public class FileUtilities {

	public static boolean isFilePresent(String fileName) {

		File file = new File(fileName);
		if (file.exists()) {
			//System.out.println("File: "+fileName +" Exists");
			return true;
		} else {
			//System.err.println("File: "+fileName +" doesn't Exists");
			return false;
		}
	}
	
	public static boolean isDirPresent(String dirName){
		File file = new File(dirName);
		if (file.exists() && file.isDirectory() ) {
			//System.out.println("Directory: "+dirName +" Exists");
			return true;
		} else {
			//System.err.println("Directory: "+dirName +" doesn't Exists");
			return false;
		}
	}

}
