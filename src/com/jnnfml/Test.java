package com.jnnfml;

public class Test {

	public static void example() {
		String[] data = new String[2];
		
		data[0] = "Hello World!";
		data[1] = "It's support MultiLines!";
		
		FileManager.writeText("test.txt", data, 2);
		
		String str = FileManager.readFile("test.txt", true);
		System.out.println(str);
	}

}
