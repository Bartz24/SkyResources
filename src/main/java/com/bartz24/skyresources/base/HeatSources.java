package com.bartz24.skyresources.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.bartz24.skyresources.jei.heatsources.HeatSourcesRecipeJEI;

import net.minecraft.block.state.IBlockState;

public class HeatSources
{
	static HashMap<IBlockState, Integer> validHeatSources;

	public HeatSources()
	{
		validHeatSources = new HashMap<IBlockState, Integer>();
	}

	public static void addHeatSource(IBlockState blockState, int value)
	{
		validHeatSources.put(blockState, value);
	}

	public static boolean isValidHeatSource(IBlockState blockState)
	{
		if (!validHeatSources.containsKey(blockState))
		{
			for (IBlockState key : validHeatSources.keySet())
			{
				IBlockState block = key;
				int val = (Integer) validHeatSources.get(key);
				if (block.getBlock() == blockState.getBlock())
					return true;
			}
		}

		return validHeatSources.containsKey(blockState);
	}

	public static int getHeatSourceValue(IBlockState blockState)
	{
		if (!isValidHeatSource(blockState))
			return 0;

		if (validHeatSources.containsKey(blockState))
			return validHeatSources.get(blockState);
		else
		{
			for (IBlockState key : validHeatSources.keySet())
			{
				IBlockState block = key;
				int val = (Integer) validHeatSources.get(key);
				if (block.getBlock() == blockState.getBlock())
					return val;
			}
		}
		return 0;
	}

	public static void removeHeatSource(IBlockState blockState)
	{
		validHeatSources.remove(blockState);
	}

	public static HashMap<IBlockState, Integer> getHeatSources()
	{
		return validHeatSources;
	}
}
