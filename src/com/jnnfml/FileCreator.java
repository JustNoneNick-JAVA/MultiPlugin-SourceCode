package com.jnnfml;

public class FileCreator {
	public static void createEmptyFile(String name)
	{
		FileWriter.writeLine(name, "");
	}
}
