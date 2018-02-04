package com.bartz24.skyresources.base.block;

import java.util.Random;

import com.bartz24.skyresources.registry.ModBlocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagmafiedStoneBlock extends BaseBlock
{

	public MagmafiedStoneBlock(Material material, String unlocalizedName, String registryName, float hardness,
			float resistance, SoundType stepSound)
	{
		super(material, registryName, registryName, resistance, resistance, stepSound);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(world, pos, state, rand);
		boolean success = false;
		if (!world.isRemote)
		{
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				BlockPos waterPos = pos.add(dir.getDirectionVec());
				IBlockState stateDir = world.getBlockState(waterPos);
				if (stateDir.getBlock() == ModBlocks.crystalFluidBlock)
				{
					int chance = rand.nextInt(800);
					if (chance < 30f)
					{
						success = true;
						EntityItem entity = new EntityItem(world, pos.add(dir.getDirectionVec()).getX() + 0.5F,
								pos.add(dir.getDirectionVec()).getY() + 0.5F, pos.add(dir.getDirectionVec()).getZ() + 0.5F,
								new ItemStack(Blocks.COBBLESTONE));
						entity.lifespan = 600;

						world.spawnEntity(entity);
						for (int i = 0; i < 8; ++i)
						{
							world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + Math.random(),
									pos.getY() + 1.2D, pos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
						}
					}
				}
			}
			world.scheduleUpdate(pos, this, tickRate(world));
		}
		if (success)
		{
			world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5F,
					2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		world.scheduleUpdate(pos, this, tickRate(world));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta);
	}
}
