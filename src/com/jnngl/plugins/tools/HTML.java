package com.jnngl.plugins.tools;

import org.bukkit.ChatColor;

import com.jnntml.TextUtils;

public class HTML {
	
	public static final String ERROR = "Ar4uufhdbcsdfgscdfcsbddvdvsacrerfe45gd";
	
	public String textTag(String txt, String tg, ChatColor wh)
	{
		String tx = txt;
		
		String tgO = "<"  + tg + ">";
		String tgE = "</" + tg + ">";
		
		if(TextUtils.contains(tx, tgO))
		{
			if(TextUtils.contains(tx, tgE))
			{
				int ft4 = TextUtils.findAfter(tx, tgO);
				int tt4 = TextUtils.findBefore(tx, tgE);
				
				int tf4 = TextUtils.findAfter(tx, tgE);
				int ff4 = TextUtils.findBefore(tx, tgO);
				
				String bef = tx.substring(0, ff4);
				String text = tx.substring(ft4, tt4);
				String aft = tx.substring(tf4);
				
				tx = (bef + wh + text + ChatColor.RESET + aft);
			}
			else
				return ERROR;
		}
		
		return tx;
	}
	
	public String HTMLize(String txt)
	{
		String str = "!NotHTML!";
		if(txt.length()>15)
		{
			if(txt.substring(0,15).equals("<!DOCTYPE HTML>"))
			{
				str=ERROR;
				int f = TextUtils.findAfter(txt, "<html>");
				int t = TextUtils.findBefore(txt, "</html>");
				
				if(TextUtils.isValid(f) && TextUtils.isValid(t))
				{
					String html = txt.substring(f, t);
					
					str="";
					
					int f2 = TextUtils.findAfter(html, "<body>");
					int t2 = TextUtils.findBefore(html, "</body>");
					
					if(TextUtils.isValid(f2) && TextUtils.isValid(t2))
					{
						String body = html.substring(f2, t2);
						
						str="";
						
						if(TextUtils.contains(body, "<p>"))
						{
							if(TextUtils.contains(body, "</p>"))
							{
								int f3 = TextUtils.findAfter(body, "<p>");
								int t3 = TextUtils.findBefore(body, "</p>");
								
								String tx = body.substring(f3, t3);
								
								String b = textTag( tx, "b", ChatColor.BOLD          );
								String u = textTag( b,  "u", ChatColor.UNDERLINE     );
								String m = textTag( u,  "m", ChatColor.MAGIC         ); 
								String i = textTag( m,  "i", ChatColor.ITALIC        ); 
								String s = textTag( i,  "s", ChatColor.STRIKETHROUGH );
								
								tx=s;
								
								// COLORIZE
								String tgO = "<color=Color.&&>";
								String tgE = "</color>";
								ChatColor wh = ChatColor.WHITE;
								
								if(     TextUtils.contains(tx, tgO.replace("&&", "01"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "02"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "03"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "04"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "05"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "06"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "07"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "08"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "09"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "10"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "11"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "12"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "13"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "14"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "15"))   || 
										TextUtils.contains(tx, tgO.replace("&&", "16")    ))
								{
									if(TextUtils.contains(tx, tgE))
									{
										System.out.println(tgO);
										int ft4 = TextUtils.findAfter(tx, tgO);
										int tt4 = TextUtils.findBefore(tx, tgE);
										
										for(int j=1;j<=16;j++)
										{
											if(!TextUtils.isValid(ft4))
											{
												String k="";
												if(j<10)
													k="0"+j;
												else
													k="" +j;
												
												ft4=TextUtils.findAfter(tx, tgO.replace("&&", k));
											}
										}
										
										int tf4 = TextUtils.findAfter(tx, tgE);
										int ff4 = TextUtils.findBefore(tx, tgO);
										
										for(int j=1;j<=16;j++)
										{
											if(!TextUtils.isValid(ff4))
											{
												String k="";
												if(j<10)
													k="0"+j;
												else
													k="" +j;
												
												ff4=TextUtils.findBefore(tx, tgO.replace("&&", k));
											}
										}
										
										//<color=Color.&&>g</color>
										
										String cut = tx.substring(ff4, tf4);
										String color = cut.substring(13, 15);
										
										if(color.equals("01"))
											wh=ChatColor.AQUA;
										if(color.equals("02"))
											wh=ChatColor.BLACK;
										if(color.equals("03"))
											wh=ChatColor.BLUE;
										if(color.equals("04"))
											wh=ChatColor.DARK_AQUA;
										if(color.equals("05"))
											wh=ChatColor.DARK_BLUE;
										if(color.equals("06"))
											wh=ChatColor.DARK_GRAY;
										if(color.equals("07"))
											wh=ChatColor.DARK_GREEN;
										if(color.equals("08"))
											wh=ChatColor.DARK_PURPLE;
										if(color.equals("09"))
											wh=ChatColor.DARK_RED;
										if(color.equals("10"))
											wh=ChatColor.GOLD;
										if(color.equals("11"))
											wh=ChatColor.GRAY;
										if(color.equals("12"))
											wh=ChatColor.GREEN;
										if(color.equals("13"))
											wh=ChatColor.LIGHT_PURPLE;
										if(color.equals("14"))
											wh=ChatColor.RED;
										if(color.equals("15"))
											wh=ChatColor.WHITE;
										if(color.equals("16"))
											wh=ChatColor.YELLOW;
										
										String bef = tx.substring(0, ff4);
										String text = tx.substring(ft4, tt4);
										String aft = tx.substring(tf4);
										
										tx = (bef + wh + text + ChatColor.RESET + aft);
									}
									else
										return ERROR;
								}
								
								return tx;
								
							}
							else
								return ERROR;
						}
					}
					else
						return ERROR;
				}
			}
		}
		return str;
	}
	
}
