package com.jnngl.plugins.tools;

public class StrToInt {
	public static int convert(String str)
	{
		int i = 0;
		
		while(!str.equals("" + i+1)) {i++; if(str.equals("" + i)) {return i;}}
		return i;
	}
}
