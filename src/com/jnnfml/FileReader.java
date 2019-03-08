package com.jnnfml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {
	public static String readFile(String path, boolean workspace)
	{
		String result = "";
		
		if(!workspace)
		{
			//path=path.replace("/", "\\\\");
			
			File file = new File(path);
			BufferedReader br = null;
			try {
				br = new BufferedReader(new java.io.FileReader(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
			  
			  String st; 
			  try {
				while ((st = br.readLine()) != null) 
				    result += st + "\n";
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		if(workspace)
		{
			BufferedReader br = null;
			try {
				br = new BufferedReader(new java.io.FileReader(path));
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = null;
				try {
					line = br.readLine();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        try {
						line = br.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
			    }
			    result = sb.toString();
			} finally {
			    try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}
