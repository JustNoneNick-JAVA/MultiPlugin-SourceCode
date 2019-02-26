package com.jnngl.plugins.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_12_R1.Material;

public class RegenRun extends BukkitRunnable {

	ArrayList<BlockState> updatedBlockList = new ArrayList<>();
	
	public RegenRun(List<Block> blocks)
	{
		for (Block b : blocks)
		{
			updatedBlockList.add(b.getState());
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void run() {
		int max = updatedBlockList.size()-1;
		if(max > -1)
		{
			if(!updatedBlockList.get(max).getType().equals(Material.TNT))
			{
				updatedBlockList.get(max).update(true,false);
			}
			updatedBlockList.remove(max);
		}
	}

}
