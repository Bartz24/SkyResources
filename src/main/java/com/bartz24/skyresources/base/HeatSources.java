package com.bartz24.skyresources.base;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

	public static boolean isValidHeatSource(BlockPos pos, World world)
	{
		if (!validHeatSources.containsKey(world.getBlockState(pos)))
		{
			for (IBlockState key : validHeatSources.keySet())
			{
				IBlockState block = key;
				int val = (Integer) validHeatSources.get(key);
				if (block.getBlock() == world.getBlockState(pos).getBlock())
					return true;
			}

			TileEntity tile = world.getTileEntity(pos);
			if (tile != null && tile instanceof IHeatSource)
				return true;

		} else
			return true;

		return false;
	}
	
	public static boolean isValidHeatSource(IBlockState state)
	{
		if (!validHeatSources.containsKey(state))
		{
			for (IBlockState key : validHeatSources.keySet())
			{
				IBlockState block = key;
				int val = (Integer) validHeatSources.get(key);
				if (block.getBlock() == state.getBlock())
					return true;
			}

		} else
			return true;

		return false;
	}

	public static int getHeatSourceValue(BlockPos pos, World world)
	{
		IBlockState state = world.getBlockState(pos);
		if (!isValidHeatSource(pos, world))
			return 0;

		if (validHeatSources.containsKey(state))
			return validHeatSources.get(state);
		else
		{
			for (IBlockState key : validHeatSources.keySet())
			{
				IBlockState block = key;
				int val = (Integer) validHeatSources.get(key);
				if (block.getBlock() == state.getBlock())
					return val;
			}
			
			TileEntity tile = world.getTileEntity(pos);
			if (tile != null && tile instanceof IHeatSource)
			{
				IHeatSource source = (IHeatSource) tile;
				return source.getHeatValue();
			}
		}
		return 0;
	}

	public static int getHeatSourceValue(IBlockState state)
	{
		if (!isValidHeatSource(state))
			return 0;

		if (validHeatSources.containsKey(state))
			return validHeatSources.get(state);
		else
		{
			for (IBlockState key : validHeatSources.keySet())
			{
				IBlockState block = key;
				int val = (Integer) validHeatSources.get(key);
				if (block.getBlock() == state.getBlock())
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
