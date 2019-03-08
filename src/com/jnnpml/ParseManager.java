package com.jnnpml;

public class ParseManager {
	public static String[] tokenize(String to) {return Tokenizer.tokenize(to);}
	public static String[] combinedTokenize(String to, int length, boolean repeating) {return Tokenizer.combinedTokenize(to, length, repeating);}
	public static String[] combinedTokenize(String to, int length, int space) {return Tokenizer.combinedTokenize(to, length, space);}
	
	public static boolean find(String from, String what) {return Finder.find(from, what);}
}
