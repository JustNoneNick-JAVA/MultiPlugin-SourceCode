package com.jnntml;

public class AutoTextFix {
	public static String capsAll(String text)
	{
		String a = text;
		
		a=Editor.removeSpaceAfterDot(a);
		
		a=a.replace('q', 'Q');
		a=a.replace('w', 'W');
		a=a.replace('e', 'E');
		a=a.replace('r', 'R');
		a=a.replace('t', 'T');
		a=a.replace('y', 'Y');
		a=a.replace('u', 'U');
		a=a.replace('i', 'I');
		a=a.replace('o', 'O');
		a=a.replace('p', 'P');
		
		a=a.replace('a', 'A');
		a=a.replace('s', 'S');
		a=a.replace('d', 'D');
		a=a.replace('f', 'F');
		a=a.replace('g', 'G');
		a=a.replace('h', 'H');
		a=a.replace('j', 'J');
		a=a.replace('k', 'K');
		a=a.replace('l', 'L');
		
		a=a.replace('z', 'Z');
		a=a.replace('x', 'X');
		a=a.replace('c', 'C');
		a=a.replace('v', 'V');
		a=a.replace('b', 'B');
		a=a.replace('n', 'N');
		a=a.replace('m', 'M');
		
		a=Editor.addSpaceAfterDot(a);
		
		return a;
	}
	
	public static String letterAfterDot(String text)
	{
		String a = text;
		
		a=Editor.removeSpaceAfterDot(a);
		
		a=a.replace(".q", ".Q");
		a=a.replace(".w", ".W");
		a=a.replace(".e", ".E");
		a=a.replace(".r", ".R");
		a=a.replace(".t", ".T");
		a=a.replace(".y", ".Y");
		a=a.replace(".u", ".U");
		a=a.replace(".i", ".I");
		a=a.replace(".o", ".O");
		a=a.replace(".p", ".P");
		
		a=a.replace(".a", ".A");
		a=a.replace(".s", ".S");
		a=a.replace(".d", ".D");
		a=a.replace(".f", ".F");
		a=a.replace(".g", ".G");
		a=a.replace(".h", ".H");
		a=a.replace(".j", ".J");
		a=a.replace(".k", ".K");
		a=a.replace(".l", ".L");
		
		a=a.replace(".z", ".Z");
		a=a.replace(".x", ".X");
		a=a.replace(".c", ".C");
		a=a.replace(".v", ".V");
		a=a.replace(".b", ".B");
		a=a.replace(".n", ".N");
		a=a.replace(".m", ".M");
		
		a=Editor.addSpaceAfterDot(a);
		
		return a;
	}
	
	public static String uncapsAll(String text)
	{
		String a = text;
		
		a=Editor.removeSpaceAfterDot(a);
		
		a=a.replace('Q', 'q');
		a=a.replace('W', 'w');
		a=a.replace('E', 'e');
		a=a.replace('R', 'r');
		a=a.replace('T', 't');
		a=a.replace('Y', 'y');
		a=a.replace('U', 'u');
		a=a.replace('I', 'i');
		a=a.replace('O', 'o');
		a=a.replace('P', 'p');
		
		a=a.replace('A', 'a');
		a=a.replace('S', 's');
		a=a.replace('D', 'd');
		a=a.replace('F', 'f');
		a=a.replace('G', 'g');
		a=a.replace('H', 'h');
		a=a.replace('J', 'j');
		a=a.replace('K', 'k');
		a=a.replace('L', 'l');
		
		a=a.replace('Z', 'z');
		a=a.replace('X', 'x');
		a=a.replace('C', 'c');
		a=a.replace('V', 'v');
		a=a.replace('B', 'b');
		a=a.replace('N', 'n');
		a=a.replace('M', 'm');
		
		a=Editor.addSpaceAfterDot(a);
		
		return a;
	}
	
	private static String[] tokenize(String to)
	{
		String tokenized[] = new String[to.length()];
		
		for(int i = 0; i < to.length()-1; i++)
		{
			tokenized[i] = to.substring(i, i+1);
			 
		}
		
		return tokenized;
	}
	
	public static String remove(String text, String list)
	{
		int length = list.length();
		
		String[] tknzd = tokenize(text);
		String done = tknzd[0];
		
		for(int i = 1; i < length-1; i++)
		{
			done.replace(tknzd[i], "");
		}
		
		return done;
	}
}
