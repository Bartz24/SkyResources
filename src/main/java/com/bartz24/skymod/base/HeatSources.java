package com.bartz24.skymod.base;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;

public class HeatSources
{
	static List<IBlockState> validHeatSources;
	
	public HeatSources()
	{
		validHeatSources = new ArrayList<IBlockState>();
	}
	
	public static void addHeatSource(IBlockState blockState)
	{
		validHeatSources.add(blockState);
	}
	
	public static boolean isValidHeatSource(IBlockState blockState)
	{
		return validHeatSources.contains(blockState);
	}
}
