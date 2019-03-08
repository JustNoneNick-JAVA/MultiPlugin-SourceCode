package com.jnngl.jnniml;

public class Boolean {
	
	public static final Boolean TRUE  = new Boolean(true ),
			                    FALSE = new Boolean(false);
	
	private boolean b = false;
	
	public Boolean(boolean b)
	{
		this.b=b;
	}
	
	
	
	public boolean getBoolean()
	{
		return b;
	}
	
	public boolean[] split(boolean b, boolean b2)
	{
		boolean[] bc = new boolean[2];
		
		bc[0] = b ;
		bc[1] = b2;
		
		return bc;
	}
	
	public char decodeChar(boolean bs)
	{
		char c = ' ';
		
		
		return c;
	}
	
	public boolean[] booleanizeInt(int n)
	{
		return null;
	}
	
}
