package com.jnngl.plugins.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

import javax.swing.JPanel;

public abstract class ProgrammingCore extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// Command, args //
	static HashMap<String, String[]> cmds = new HashMap<>();
	static HashMap<String, String>   al   = new HashMap<>();
	
	/*public static void main(String[] args)
	{
		setupCmds();		
	}*/
	
	int argl = 0;
	
	public static int getArgsLength(String[] args)
	{
		int length=0;
		for(int i=0;i<20;i++)
		{
			if(args[i]!=null)
			{
				length++;
			}
		}
		return length;
	}
	
	public static boolean compareLength(String[] args, int i)
	{
		boolean result = false;
		int argl = getArgsLength(args);
		if(argl==i)
			result = true;
		return result;
	}
	
	public static int getInt(int[] _int)
	{
		return _int[0];
	}
	
	public static String splitArray(String[] text, boolean wSpace, int start)
	{
		String _between = " ";
		String result="";
		
		if(!wSpace)
			_between="";
		
		int ln = 0;
		
		if(text.length==20)
			ln=getArgsLength(text)-start;
		else
			ln=text.length;
		
		for(int i=0;i<ln;i++)
		{
			result+=(text[start+i]+_between);
		}
		
		return result;
	}
	
	public static String splitArray(String[] text, boolean wSpace)
	{
		String _between = " ";
		String result="";
		
		if(!wSpace)
			_between="";
		
		int ln = 0;
		
		if(text.length==20)
			ln=getArgsLength(text);
		else
			ln=text.length;
		
		for(int i=0;i<ln;i++)
		{
			result+=(text[i]+_between);
		}
		
		return result;
	}
	
	public static String addTab(String c)
	{
		return "	"+c;
	}
	
	public static String removeTab(String c)
	{
		if(c.length()>="	".length())
		{
			if(c.substring(0,"	".length())=="	")
			{
				return c.substring("	".length());
			}
		}
		System.err.println("Error: 'Tab' not found.");
		return null;
	}
	
	public static boolean compareLength(String[] args, String[] _args)
	{
		boolean result = false;
		int argl = getArgsLength(args);
		int _argl = getArgsLength(_args);
		if(argl==_argl)
			result = true;
		return result;
	}
	
	public static String encode(String str)
	{
		return Base64.getEncoder().encodeToString(str.getBytes());
	}
	
	public static String decode(String str)
	{
		byte[] bytes = Base64.getDecoder().decode(rms(str));
		return new String(bytes);
	}
	
	public static String rms(String str)
	{
		return str.replace(" " , "");
	}
	
	public void executeCode(String path, boolean src)
	{
		String _path = "./";
		
		if(!src)
			path="";
		
		Path file = Paths.get(_path + path);
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader =
		      new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	line += " ";
		    	String cmd = "";
		    	String[] args = new String[20];
		    	int count = 0;
		    	int from=0;
		    	for(int i=0;i<line.length();i++)
		    	{
		    		String _char = line.substring(i,i+1);
		    		if(_char.equals(" "))
		    		{
		    			if(count==0)
		    			{
		    				cmd = line.substring(0, i);
		    				Path file2 = Paths.get("./meta/" + cmd);
				    		try (InputStream in2 = Files.newInputStream(file2);
			    				    BufferedReader reader2 =
			    				      new BufferedReader(new InputStreamReader(in2))) {
			    				    String line2 = null;
			    				    while ((line2 = reader2.readLine()) != null) {
			    				        if(line2.substring(0,4).equalsIgnoreCase("repl"))
			    				        {
			    				        	String _repl = line2.substring(4);
			    				        	for(int j = 0;j<_repl.length()-1;j++)
			    				        	{
			    				        		if(_repl.substring(j,j+1).equals(":"))
			    				        		{
			    				        			line=line.replace(_repl.substring(0,j), _repl.substring(j+1));
			    				        			if(_repl.substring(0,j).equals(" "))
			    				        			{
			    				        				line=line.substring(0,line.length()-_repl.substring(j+1).length());
			    				        				line+="  ";
			    				        			}
			    				        			line=line.substring(cmd.length()+1);
			    				        			line=cmd+" "+line;
			    				        		}
			    				        	}
			    				        }
			    				        else if(line2.substring(0,12).equalsIgnoreCase("!ALException"))
			    				        {
			    				        	al.put(cmd, "1");
			    				        }
			    				    }
			    				} catch (IOException x) {
			    				    System.err.println(x);
			    				}
		    				from = i+1;
		    				count++;
		    			}
		    			else if(count>0)
		    			{
		    				args[count-1] = line.substring(from, i);
		    				argl++;
		    				from = i+1;
		    				count++;
		    			}
		    		}
		    	}
		    	
		    	if(cmds.containsKey(cmd))
		    	{
		    		
		    		int cargl = 0;
		    		for(int i=0;i<20;i++)
		    		{
		    			if(cmds.get(cmd)[i]!=null)
		    			{
		    				cargl++;
		    			}
		    		}
		    		if(argl==cargl)
		    		{
		    			onCommand(cmd,args);
		    		}
		    		else if(al.containsKey(cmd))
		    		{
		    			onCommand(cmd,args);
		    		}
		    		else
		    		{
		    			System.err.println("Error! :Code :Invalid usage of arguments! Can't compile code!");
		    			System.exit(1);
		    		}
		    	}
		    	else
	    		{
	    			System.err.println("Error! :Code :Invalid code! Can't compile code!");
	    			System.exit(1);
	    		}
		    }
		} catch (IOException x) {
		    System.err.println(x);
		}
	}
	
	
	public void executeCode(String path, boolean src, String wh, String to)
	{
		String _path = "./";
		
		if(!src)
			path="";
		
		Path file = Paths.get(_path + path);
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader =
		      new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	line += " ";
		    	String cmd = "";
		    	String[] args = new String[20];
		    	int count = 0;
		    	int from=0;
		    	for(int i=0;i<line.length();i++)
		    	{
		    		String _char = line.substring(i,i+1);
		    		if(_char.equals(" "))
		    		{
		    			if(count==0)
		    			{
		    				cmd = line.substring(0, i);
		    				Path file2 = Paths.get("./meta/" + cmd);
				    		try (InputStream in2 = Files.newInputStream(file2);
			    				    BufferedReader reader2 =
			    				      new BufferedReader(new InputStreamReader(in2))) {
			    				    String line2 = null;
			    				    while ((line2 = reader2.readLine()) != null) {
			    				        if(line2.substring(0,4).equalsIgnoreCase("repl"))
			    				        {
			    				        	String _repl = line2.substring(4);
			    				        	for(int j = 0;j<_repl.length()-1;j++)
			    				        	{
			    				        		if(_repl.substring(j,j+1).equals(":"))
			    				        		{
			    				        			line=line.replace(_repl.substring(0,j), _repl.substring(j+1));
			    				        			if(_repl.substring(0,j).equals(" "))
			    				        			{
			    				        				line=line.substring(0,line.length()-_repl.substring(j+1).length());
			    				        				line+="  ";
			    				        			}
			    				        			line=line.substring(cmd.length()+1);
			    				        			line=cmd+" "+line;
			    				        		}
			    				        	}
			    				        }
			    				        else if(line2.substring(0,12).equalsIgnoreCase("!ALException"))
			    				        {
			    				        	al.put(cmd, "1");
			    				        }
			    				    }
			    				} catch (IOException x) {
			    				    System.err.println(x);
			    				}
		    				from = i+1;
		    				count++;
		    			}
		    			else if(count>0)
		    			{
		    				args[count-1] = line.substring(from, i).replace(wh, to);
		    				argl++;
		    				from = i+1;
		    				count++;
		    			}
		    		}
		    	}
		    	
		    	if(cmds.containsKey(cmd))
		    	{
		    		
		    		int cargl = 0;
		    		for(int i=0;i<20;i++)
		    		{
		    			if(cmds.get(cmd)[i]!=null)
		    			{
		    				cargl++;
		    			}
		    		}
		    		if(argl==cargl)
		    		{
		    			onCommand(cmd,args);
		    		}
		    		else if(al.containsKey(cmd))
		    		{
		    			onCommand(cmd,args);
		    		}
		    		else
		    		{
		    			System.err.println("Error! :Code :Invalid usage of arguments! Can't compile code!");
		    			System.exit(1);
		    		}
		    	}
		    	else
	    		{
	    			System.err.println("Error! :Code :Invalid code! Can't compile code!");
	    			System.exit(1);
	    		}
		    }
		} catch (IOException x) {
		    System.err.println(x);
		}
	}
	
	
	
	
	@SuppressWarnings("unused")
	public void executeCode(String path, boolean src, String wh, String to, String w, String t)
	{
		String _path = "./";
		
		if(!src)
			path="";
		
		Path file = Paths.get(_path + path);
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader =
		      new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	line += " ";
		    	String cmd = "";
		    	String[] args = new String[20];
		    	int count = 0;
		    	int from=0;
		    	
		    	line=line.replace(wh, to);
				line=line.replace(w , t );
		    	
		    	for(int i=0;i<line.length();i++)
		    	{
		    		String _char = line.substring(i,i+1);
		    		if(_char.equals(" "))
		    		{
		    			if(count==0)
		    			{
		    				cmd = line.substring(0, i);
		    				Path file2 = Paths.get("./meta/" + cmd);
				    		try (InputStream in2 = Files.newInputStream(file2);
			    				    BufferedReader reader2 =
			    				      new BufferedReader(new InputStreamReader(in2))) {
			    				    String line2 = null;
			    				    while ((line2 = reader2.readLine()) != null) {
			    				        if(line2.substring(0,4).equalsIgnoreCase("repl"))
			    				        {
			    				        	String _repl = line2.substring(4);
			    				        	for(int j = 0;j<_repl.length()-1;j++)
			    				        	{
			    				        		if(_repl.substring(j,j+1).equals(":"))
			    				        		{
			    				        			line=line.replace(_repl.substring(0,j), _repl.substring(j+1));
			    				        			if(_repl.substring(0,j).equals(" "))
			    				        			{
			    				        				line=line.substring(0,line.length()-_repl.substring(j+1).length());
			    				        				line+="  ";
			    				        			}
			    				        			line=line.substring(cmd.length()+1);
			    				        			line=cmd+" "+line;
			    				        		}
			    				        	}
			    				        }
			    				        else if(line2.substring(0,12).equalsIgnoreCase("!ALException"))
			    				        {
			    				        	al.put(cmd, "1");
			    				        }
			    				    }
			    				} catch (IOException x) {
			    				    System.err.println(x);
			    				}
		    				from = i+1;
		    				count++;
		    			}
		    			else if(count>0)
		    			{
		    				argl++;
		    				from = i+1;
		    				count++;
		    			}
		    		}
		    	}
		    	
		    	if(cmds.containsKey(cmd))
		    	{
		    		
		    		int cargl = 0;
		    		for(int i=0;i<20;i++)
		    		{
		    			if(cmds.get(cmd)[i]!=null)
		    			{
		    				cargl++;
		    			}
		    		}
		    		if(argl==cargl)
		    		{
		    			onCommand(cmd,args);
		    		}
		    		else if(al.containsKey(cmd))
		    		{
		    			onCommand(cmd,args);
		    		}
		    		else
		    		{
		    			System.err.println("Error! :Code :Invalid usage of arguments! Can't compile code!");
		    			System.exit(1);
		    		}
		    	}
		    	else
	    		{
	    			System.err.println("Error! :Code :Invalid code! Can't compile code!");
	    			System.exit(1);
	    		}
		    }
		} catch (IOException x) {
		    System.err.println(x);
		}
	}
	
	
	
	
	
	
	
	
	
	public abstract void onCommand(String cmd, String[] args);
	
	public static void setupCmds()
	{
		Path file = Paths.get("./raw_cmds.cmd-list");
		try (InputStream in = Files.newInputStream(file);
		    BufferedReader reader =
		      new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        // FILTER
		    	line += " ";
		    	String cmd = "";
		    	String[] args = new String[20];
		    	int count = 0;
		    	int from=0;
		    	for(int i=0;i<line.length();i++)
		    	{
		    		String _char = line.substring(i,i+1);
		    		if(_char.equals(" "))
		    		{
		    			if(count==0)
		    			{
		    				cmd = line.substring(0, i);
		    				from = i+1;
		    				count++;
		    			}
		    			else if(count>0)
		    			{
		    				args[count-1] = line.substring(from, i);
		    				if(!args[count-1].substring(0,1).equals(":"))
		    				{
		    					System.err.println("Error while loading 'raw_cmds.cmd-list' please insert ':' before argument. ");
		    					System.exit(1);
		    				}
		    				from = i+1;
		    				count++;
		    			}
		    		}
		    	}
		    	cmds.put(cmd, args);
		    }
		} catch (IOException x) {
		    System.err.println(x);
		}
	}
	
}
