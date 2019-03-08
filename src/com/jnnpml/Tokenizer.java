package com.jnnpml;

public class Tokenizer {
	public static String[] tokenize(String to)
	{
		String tokenized[] = new String[to.length()];
		
		for(int i = 0; i < to.length()-1; i++)
		{
			tokenized[i] = to.substring(i, i+1);
			 
		}
		
		return tokenized;
	}
	
	public static String[] combinedTokenize(String to, int length, boolean repeating)
	{
		String tokenized[] = new String[to.length() - length];
		if(repeating)
			for(int i = 0; i < to.length()-length; i++)
			{
				tokenized[i] = to.substring(i, i+length);
				 
			}
		else
			for(int i = 0; i < to.length()-length; i+=length)
			{
				tokenized[i] = to.substring(i, i+length);
				 
			}
		
		return tokenized;
	}
	public static String[] combinedTokenize(String to, int length, int space)
	{
		String tokenized[] = new String[to.length() - length];
		
		for(int i = 0; i < to.length()-length; i+=space)
		{
			tokenized[i] = to.substring(i, i+length);
			 
		}
		
		return tokenized;
	}
}
