package com.bartz24.skyresources.base.block;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSilverfishDisruptor extends BaseBlock
{
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.625D, 0.625D);

	public BlockSilverfishDisruptor()
	{
		super(Material.CIRCUITS, "silverfishDisruptor", "SilverfishDisruptor", 0.5F, 0.5F, SoundType.GLASS);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return AABB;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
	{
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
