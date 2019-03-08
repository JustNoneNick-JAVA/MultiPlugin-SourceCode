package com.jnngl.plugins.tools;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Compiler extends ProgrammingCore implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	// Instance Name : Value
	HashMap<String, String> _str = new HashMap<>();
	HashMap<String, int[]> _int = new HashMap<>();
	
	@SuppressWarnings("resource")
	@Override
	public void onCommand(String cmd, String[] args) {
		if(cmd.equals("print"))
		{
			String pr = splitArray(args, true);
			Bukkit.broadcastMessage(pr);
			//.println(pr);
		}
		if(cmd.equals("!print!"))
		{
			String pr = splitArray(args, true);
			if(pr.length()>0)
				pr=pr.substring(0,pr.length()-1);
			Bukkit.broadcastMessage(pr);
			//.print(pr);
		}
		else if(cmd.equals("slPrnt"))
		{
			if(args.length>0)
			{
				String text = splitArray(args, true);
				for(int i=0;i<text.length()-1;i++)
				{
					this.executeCode("sp_exec/interPrint", true, "^^", text.substring(0,i+1));
				}
			}
		}
		else if(cmd.equals("slPrntStr"))
		{
			String text = "";
			if(_str.containsKey(args[0]))
			{
				text=_str.get(args[0]);
			}
			for(int i=0;i<text.length();i++)
			{
				
				this.executeCode("sp_exec/interPrint", true, "^^", text.substring(0,i+1));
			}
		}
		else if(cmd.equals("print!"))
		{
			String pr = splitArray(args, true);
			Bukkit.broadcastMessage(pr);
			//.print(pr);
		}
		else if(cmd.equals("printErr"))
		{
			String pr = splitArray(args, true);
			Bukkit.broadcastMessage(ChatColor.RED + pr);
			//.println(pr);
		}
		else if(cmd.equals("sleep"))
		{
			try {
				Thread.sleep((long)((StrToInt.convert(args[0]))*50));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else if(cmd.equals("scan"))
		{
			Scanner s = new Scanner(System.in);
			String str = s.nextLine();
			_str.put(args[0], str);
		}
		else if(cmd.equals("printString"))
		{
			if(_str.containsKey(args[0]))
			{
				Bukkit.broadcastMessage(_str.get(args[0]));
				//.println(_str.get(args[0]));
			}
			else
			{
				//.println("String not found.");
				System.exit(1);
			}
		}
		else if(cmd.equals("printErrString"))
		{
			if(_str.containsKey(args[0]))
			{
				Bukkit.broadcastMessage(_str.get(args[0]));
				//.println(_str.get(args[0]));
			}
			else
			{
				//.println("String not found.");
				System.exit(1);
			}
		}
		else if(cmd.equals("clear"))
		{
			for(int i=0;i<50;i++)
			{
				Bukkit.broadcastMessage(" ");
				//System.setOut(//.printf("\n"));
			}
		}
		else if(cmd.equals("ifs"))
		{
			boolean isString=false;
			String str = "";
			String strw = "";
			if(_str.containsKey(args[0]))
			{
				str=_str.get(args[0]);
			}
			if(_str.containsKey(args[2]))
			{
				strw=_str.get(args[2]);
				isString=true;
			}
			
			if(!isString)
			{
				strw=args[2];
			}
			
			if(args[1].equals("="))
			{
				if(!str.equals(""))
				{
					if(!strw.equals(""))
					{
						
						
						if(str.substring(str.length()-1,str.length()).equals(" "))
						{
							str=str.substring(0,str.length()-1);
						}
						if(strw.substring(strw.length()-1,strw.length()).equals(" "))
						{
							strw=strw.substring(0,strw.length()-1);
						}
						if(str.equals(strw))
						{
							String _cmd = args[3];
							String[] _args = new String[20];
							for(int i=4;i<args.length;i++)
							{
								_args[i-4]=args[i];
							}
							onCommand(_cmd,_args);
						}
					}
				}
			}
		}
		else if(cmd.equals("string"))
		{
			String name = args[0];
			String text = splitArray(args, true, 1);
			_str.put(name, text);
		}
		else if(cmd.equals("int"))
		{
			String name = args[0];
			int[] value = new int[1];
			value[0]=StrToInt.convert(args[1]);
			_int.put(name, value);
		}
		else if(cmd.equals("printString!"))
		{
			if(_str.containsKey(args[0]))
			{
				//.print(_str.get(args[0]));
				Bukkit.broadcastMessage(_str.get(args[0]));
			}
			else
			{
				//.println("String not found.");
				System.exit(1);
			}
		}
		else if(cmd.equals("call"))
		{
			this.executeCode(args[0], true);
		}
		else if(cmd.equals("printInt!"))
		{
			if(_int.containsKey(args[0]))
			{
				//.print(getInt(_int.get(args[0])));
				Bukkit.broadcastMessage("" + getInt(_int.get(args[0])));
			}
			else
			{
				//.println("Int not found.");
				System.exit(1);
			}
		}
		else if(cmd.equals("window"))
		{
			JFrame jf = new JFrame(args[2]);
			jf.setBackground(Color.BLACK);
			jf.setSize(StrToInt.convert(args[0]), StrToInt.convert(args[1]));
			jf.setDefaultCloseOperation(3);
			jf.setResizable(false);
			jf.setLocationRelativeTo(null);
			jf.addKeyListener(this);
			jf.setVisible(true);
		}
		else if(cmd.equals("printInt"))
		{
			if(_int.containsKey(args[0]))
			{
				//.println(getInt(_int.get(args[0])));
				Bukkit.broadcastMessage("" + getInt(_int.get(args[0])));
			}
			else
			{
				//.println("Int not found.");
				System.exit(1);
			}
		}
		else if(cmd.equals("encode"))
		{
			if(_str.containsKey(args[0]))
			{
				_str.remove(args[0]);
			}
			
			String key = args[0];
			String value = splitArray(args, true, 1);
			value=encode(value);
			
			_str.put(key, value);
		}
		else if(cmd.equals("decode"))
		{
			if(_str.containsKey(args[0]))
			{
				_str.remove(args[0]);
			}
			
			String key = args[0];
			String value = args[1];
			value=decode(value);
			
			_str.put(key, value);
		}
		else if(cmd.equals("str2int"))
		{
			if(_str.containsKey(args[0]))
			{
				if(_int.containsKey(args[1]))
				{
					_int.remove(args[1]);
				}
				String name = args[1];
				int[] value = new int[1];
				value[0]=StrToInt.convert(_str.get(args[0]));
				_int.put(name, value);
			}
		}
		else if(cmd.equals("int2str"))
		{
			if(_int.containsKey(args[0]))
			{
				if(_str.containsKey(args[1]))
				{
					_str.remove(args[1]);
				}
				String name = args[1];
				String text = "" + getInt(_int.get(args[0]));
				_str.put(name, text);
			}
		}
		else if(cmd.equals("math"))
		{
			int result = 0;
			int n1 = StrToInt.convert(args[2]);
			int n2 = StrToInt.convert(args[3]);
			if(args[0].equals("+"))
			{
				result=n1+n2;
			}
			else if(args[0].equals("-"))
			{
				result=n1-n2;
			}
			else if(args[0].equals("/"))
			{
				result=n1/n2;
			}
			else if(args[0].equals("*"))
			{
				result=n1*n2;
			}
			else
			{
				//.print("Invalid operation: " + args[0]);
				System.exit(-1);
			}
			
			if(_int.containsKey(args[1]))
			{
				_int.remove(args[1]);
			}
			String key = args[1];
			int[] value = new int[1];
			value[0]=result;
			_int.put(key, value);
		}
		else if(cmd.equals("exit"))
		{
			Bukkit.broadcastMessage(ChatColor.RED + "<terminated> by: code, status: " + args[0]);
			//.print("<terminated> by: code, status: " + args[0]);
			System.exit(0);
		}
	}
	
	public static void start(String file)
	{
		setupCmds();
		Compiler executor = new Compiler();
		executor.executeCode(file, true);
	}
	
	public Compiler()
	{
		addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(_str.containsKey("$key"))
		{
			_str.remove("$key");
		}
		_str.put("$key",KeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
