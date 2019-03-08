package com.jnntml;

import com.jnnpml.Tokenizer;

public class TextUtils {
	
	public static int findBefore(String s, String t)
	{
		s+=" ";
		if(contains(s, t))
		{
			String tkns[] = Tokenizer.combinedTokenize(s,t.length(),true);
			for(int i=0;i<tkns.length;i+=1)
			{
				if(tkns[i].equals(t))
				{
					return i;
				}
			}
		}
		return -1;
	}
	
	public static int findAfter(String s, String t)
	{
		s+=" ";
		if(contains(s, t))
		{
			String tkns[] = Tokenizer.combinedTokenize(s,t.length(),true);
			for(int i=0;i<tkns.length;i+=1)
			{
				if(tkns[i].equals(t))
				{
					return i+t.length();
				}
			}
		}
		return -1;
	}
	
	public static boolean isValid(int i)
	{
		if(i>-1)
			return true;
		return false;
	}
	
	public static boolean contains(String s, String t)
	{
		String str = s.replace(t, "8");
		
		if(str.equals(s))
			return false;
		return true;
	}
	
}
