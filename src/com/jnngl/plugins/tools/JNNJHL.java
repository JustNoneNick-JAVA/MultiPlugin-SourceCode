package com.jnngl.plugins.tools;

import org.bukkit.Bukkit;

import com.jnnfml.FileManager;

public class JNNJHL {
	
	public void start(String filename)
	{
		String code = FileManager.readFile(filename, true);
		Bukkit.broadcastMessage(new HTML().HTMLize(code));
	}
	
}
