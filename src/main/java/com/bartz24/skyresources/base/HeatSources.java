package com.bartz24.skyresources.base;

import java.util.HashMap;

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
		return validHeatSources.containsKey(blockState);
	}

	public static int getHeatSourceValue(IBlockState blockState)
	{
		return validHeatSources.get(blockState);
	}
}
