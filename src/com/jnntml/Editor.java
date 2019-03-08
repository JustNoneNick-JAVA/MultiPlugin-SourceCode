package com.jnntml;

public class Editor {
	public static String newLineAfterDot(String text)
	{
		String completed = "", pre = "";
		
		pre = removeSpaceAfterDot(text);
		completed = pre.replace(".", ".\n");
		
		return completed;
	}
	public static String removeSpaceAfterDot(String text)
	{
		String comp = text.replace(". ", ".");
		//comp = text.replace(". \n", ".\n");
		return comp;
	}
	public static String addSpaceAfterDot(String text)
	{
		return text.replace(".", ". ");
	}
}
