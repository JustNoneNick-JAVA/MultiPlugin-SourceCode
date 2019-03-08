package com.jnngl.plugins.tools;

public class Timer {
	
	private int timer=0, seconds=0;
	
	public Timer(int seconds)
	{
		this.seconds=seconds;
	}
	
	public boolean updateTimer()
	{
		timer++;
		if(timer>seconds)
		{
			timer=0;
			return true;
		}
		return false;
	}
	
}
