package com.jnnpml;

public class Finder {
	public static boolean find(String from, String what)
	{
		
		boolean is = false;
		String parsed = from.replace(what, "㈣");
		
		for(int i = 0; i < from.length()-3; i++)
		{
			if(parsed != from) {is = true; return is;}
		}
		
		return is;
	}
}
