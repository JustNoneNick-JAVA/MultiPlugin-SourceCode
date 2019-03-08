package com.jnnfml;

public class FileManager {
	public static final void writeLine(String file, String data) {FileWriter.writeLine(file, data);}
	public static final void writeText(String file, String data[], int numOfLines) {FileWriter.writeText(file, data, numOfLines);}
	public static final void writeLine(String file, String data, String format) {FileWriter.writeLine(file, data, format);}
	public static final void writeText(String file, String data[], int numOfLines, String format) {FileWriter.writeText(file, data, numOfLines, format);}

	public static final String readFile(String file, boolean workspace) {return FileReader.readFile(file, workspace);}

	public static final void createFile(String name) {FileCreator.createEmptyFile(name);}
}
