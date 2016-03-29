package com.bartz24.skyresources.base.block;

import java.util.Random;

import com.bartz24.skyresources.base.HeatSources;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlazePowderBlock extends BaseBlock
{

	public BlazePowderBlock(Material material, String unlocalizedName,
			String registryName, float hardness, float resistance,
			SoundType stepSound)
	{
		super(material, registryName, registryName, resistance, resistance,
				stepSound);
	}

	public void updateTick(World world, BlockPos pos, IBlockState state,
			Random rand)
	{
		super.updateTick(world, pos, state, rand);
		if (!world.isRemote)
		{
			System.out.println("Here");
			if (HeatSources.isValidHeatSource(world.getBlockState(pos.down()))
					&& rand.nextInt(50) <= HeatSources.getHeatSourceValue(
							world.getBlockState(pos.down())))
			{
				world.setBlockState(pos, Blocks.lava.getDefaultState());
			}
			else
			world.scheduleUpdate(pos, this, tickRate(world));
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		world.scheduleUpdate(pos, this, tickRate(world));
	}
}
