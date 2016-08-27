package com.bartz24.skyresources.base.block;

import java.util.Random;

import com.bartz24.skyresources.base.HeatSources;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlazePowderBlock extends BaseBlock
{

	public BlazePowderBlock(Material material, String unlocalizedName, String registryName,
			float hardness, float resistance, SoundType stepSound)
	{
		super(material, registryName, registryName, resistance, resistance, stepSound);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(world, pos, state, rand);
		if (!world.isRemote)
		{
			
			int chance =  rand.nextInt(2500);
			System.out.println(chance);
			if (HeatSources.isValidHeatSource(pos.down(), world)
					&& chance <= HeatSources.getHeatSourceValue(pos.down(), world))
			{
				world.setBlockState(pos, Blocks.LAVA.getDefaultState());
			} else
				world.scheduleUpdate(pos, this, tickRate(world));
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		world.scheduleUpdate(pos, this, tickRate(world));
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX,
			float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta);
	}
}
