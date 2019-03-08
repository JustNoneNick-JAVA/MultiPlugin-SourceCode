package com.jnnfml;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileWriter {
	public static void writeLine(String file, String data)
	{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.println(data);
		writer.close();
	}
	public static void writeText(String file, String data[], int numOfLines)
	{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(int i = 0; i <= numOfLines-1; i++) 
			writer.println(data[i]);
		writer.close();
	}
	
	public static void writeLine(String file, String data, String format)
	{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file, format);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.println(data);
		writer.close();
	}
	public static void writeText(String file, String data[], int numOfLines, String format)
	{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file, format);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(int i = 0; i <= numOfLines-1; i++) 
			writer.println(data[i]);
		writer.close();
	}
}
