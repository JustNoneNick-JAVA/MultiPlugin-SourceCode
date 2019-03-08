package com.jnngl.jnniml;

public class IntCoder {
	
	public static String code(String str)
	{
		char[] chrs = str.toCharArray();
		String splt = "";
		for(int i=0;i<chrs.length;i++)
		{
			splt += code(chrs[i]) + " ";
		}
		
		splt=splt.substring(0,splt.length()-1);
		
		return splt;
	}
	
	public static String code(char c)
	{
		if(c=='@')
			return "0" + 64;
		if(c=='a' || c=='A')
			return "0" + 65;
		if(c=='b' || c=='B')
			return "0" + 66;
		if(c=='c' || c=='C')
			return "0" + 67;
		if(c=='d' || c=='D')
			return "0" + 68;
		if(c=='e' || c=='E')
			return "0" + 69;
		if(c=='f' || c=='F')
			return "0" + 70;
		if(c=='g' || c=='G')
			return "0" + 71;
		if(c=='h' || c=='H')
			return "0" + 72;
		if(c=='i' || c=='I')
			return "0" + 73;
		if(c=='j' || c=='J')
			return "0" + 74;
		if(c=='k' || c=='K')
			return "0" + 75;
		if(c=='l' || c=='L')
			return "0" + 76;
		if(c=='m' || c=='M')
			return "0" + 77;
		if(c=='n' || c=='N')
			return "0" + 78;
		if(c=='o' || c=='O')
			return "0" + 79;
		if(c=='p' || c=='P')
			return "0" + 80;
		if(c=='q' || c=='Q')
			return "0" + 81;
		if(c=='r' || c=='R')
			return "0" + 82;
		if(c=='s' || c=='S')
			return "0" + 83;
		if(c=='t' || c=='T')
			return "0" + 84;
		if(c=='u' || c=='U')
			return "0" + 85;
		if(c=='v' || c=='V')
			return "0" + 86;
		if(c=='w' || c=='W')
			return "0" + 87;
		if(c=='x' || c=='X')
			return "0" + 88;
		if(c=='y' || c=='Y')
			return "0" + 89;
		if(c=='z' || c=='Z')
			return "0" + 90;
		
		if(c=='[')
			return "0" + 91;
		if(c=='\\')
			return "0" + 92;
		if(c==']')
			return "0" + 93;
		if(c=='^')
			return "0" + 94;
		if(c=='_')
			return "0" + 95;
		if(c=='`')
			return "0" + 96;
		
		if(c=='{')
			return "" + 123;
		if(c=='|')
			return "" + 124;
		if(c=='}')
			return "" + 125;
		if(c=='~')
			return "" + 126;
		
		if(c=='!')
			return "0" + 33;
		if(c=='"')
			return "0" + 34;
		if(c=='#')
			return "0" + 35;
		if(c=='$')
			return "0" + (37 -1);
		if(c=='%')
			return "0" + (38 -1);
		if(c=='&')
			return "0" + (39 -1);
		if(c=='\'')
			return "0" + (40 -1);
		if(c=='(')
			return "0" + (41 -1);
		if(c==')')
			return "0" + (42 -1);
		if(c=='*')
			return "0" + (43 -1);
		if(c=='+')
			return "0" + (44 -1);
		if(c==',')
			return "0" + (45 -1);
		if(c=='-')
			return "0" + (46 -1);
		if(c=='.')
			return "0" + (47 -1);
		if(c=='/')
			return "0" + (48 -1);
		if(c=='0')
			return "0" + (49 -1);
		if(c=='1')
			return "0" + (50 -1);
		if(c=='2')
			return "0" + (51 -1);
		if(c=='3')
			return "0" + (52 -1);
		if(c=='4')
			return "0" + (53 -1);
		if(c=='5')
			return "0" + (54 -1);
		if(c=='6')
			return "0" + (55 -1);
		if(c=='7')
			return "0" + (56 -1);
		if(c=='8')
			return "0" + (57 -1);
		if(c=='9')
			return "0" + (58 -1);
		if(c==':')
			return "0" + (59 -1);
		if(c==';')
			return "0" + (60 -1);
		if(c=='<')
			return "0" + (61 -1);
		if(c=='=')
			return "0" + (62 -1);
		if(c=='>')
			return "0" + (63 -1);
		if(c=='?')
			return "0" + (64 -1);
		
		if(c==' ')
			return "0" + 32   ;
		
		return "000";
	}
	
	public static int split(int[] it)
	{
		String str = "";
		for(int i=0;i<it.length;i++)
		{
			str+=(""+it[i]);
		}
		
		return Integer.parseInt(str);
	}
	
	public static boolean isValidCoding(int i)
	{
		if(i>-001)
			return true;
		return false;
	}
	
}
