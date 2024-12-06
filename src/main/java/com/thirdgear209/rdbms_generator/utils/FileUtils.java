package com.thirdgear209.rdbms_generator.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	public static boolean deleteDirectory(String dirName) {
		return deleteDirectory(new File(dirName));
    }
	
	public static boolean deleteDirectory(File directory) {
		if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file); // Recursively delete subdirectories
                    } else {
                        file.delete(); // Delete files
                    }
                }
            }
        }
        return directory.delete(); // Finally, delete the directory itself
    }

	public static void writeFile(String fileName, String content) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    writer.write(content);
	    
	    writer.close();
	}
}
