package com.bartz24.skyresources.alchemy.fluid;

import java.util.Random;

import javax.annotation.Nullable;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.registry.ModFluids;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidCrystalBlock extends BlockFluidClassic
{

	public FluidCrystalBlock()
	{
		super(ModFluids.crystalFluid, Material.WATER);
		this.setUnlocalizedName(ModFluids.crystalFluid.getUnlocalizedName());
		this.setRegistryName(RandomHelper.capatilizeString(ModFluids.crystalFluid.getUnlocalizedName()));
		this.displacements.put(this, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	public boolean isNotFlowing(World world, BlockPos pos, IBlockState state)
	{
		BlockPos[] checkPos = new BlockPos[] { pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 0, 1),
				pos.add(0, 0, -1) };
		for (BlockPos pos2 : checkPos)
		{
			if (world.getBlockState(pos2).getBlock() == this)
			{
				if (!isSourceBlock(world, pos2))
					return false;
			}
		}
		if (world.getBlockState(pos.add(0, 1, 0)).getBlock() == this
				|| world.getBlockState(pos.add(0, -1, 0)).getBlock() == this)
			return false;

		return true;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos)
	{
		return !world.getBlockState(pos).getMaterial().isLiquid() && super.canDisplace(world, pos);
	}

	@Override
	public boolean displaceIfPossible(World world, BlockPos pos)
	{
		return !world.getBlockState(pos).getMaterial().isLiquid() && super.displaceIfPossible(world, pos);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	public Vec3d getFlowVector(IBlockAccess world, BlockPos pos)
	{
		double d0 = 0.0D;
		double d1 = 0.0D;
		double d2 = 0.0D;
		IBlockState state = world.getBlockState(pos);
		int i = ((Integer) state.getValue(LEVEL)).intValue();
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
		{
			blockpos$pooledmutableblockpos.setPos(pos).move(enumfacing);
			int j = ((Integer) world.getBlockState(blockpos$pooledmutableblockpos).getValue(LEVEL)).intValue();

			if (j < 0)
			{
				if (!world.getBlockState(blockpos$pooledmutableblockpos).getMaterial().blocksMovement())
				{
					j = ((Integer) world.getBlockState(blockpos$pooledmutableblockpos.down()).getValue(LEVEL))
							.intValue();

					if (j >= 0)
					{
						int k = j - (i - 8);
						d0 += (double) (enumfacing.getFrontOffsetX() * k);
						d1 += (double) (enumfacing.getFrontOffsetY() * k);
						d2 += (double) (enumfacing.getFrontOffsetZ() * k);
					}
				}
			} else if (j >= 0)
			{
				int l = j - i;
				d0 += (double) (enumfacing.getFrontOffsetX() * l);
				d1 += (double) (enumfacing.getFrontOffsetY() * l);
				d2 += (double) (enumfacing.getFrontOffsetZ() * l);
			}
		}

		Vec3d vec3d = new Vec3d(d0, d1, d2);

		if (((Integer) state.getValue(LEVEL)).intValue() >= 8)
		{
			for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
			{
				blockpos$pooledmutableblockpos.setPos(pos).move(enumfacing1);

				if (this.causesDownwardCurrent(world, blockpos$pooledmutableblockpos, enumfacing1)
						|| this.causesDownwardCurrent(world, blockpos$pooledmutableblockpos.up(), enumfacing1))
				{
					vec3d = vec3d.normalize().addVector(0.0D, -6.0D, 0.0D);
					break;
				}
			}
		}

		blockpos$pooledmutableblockpos.release();
		return vec3d.normalize();
	}

	private boolean causesDownwardCurrent(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		Material material = iblockstate.getMaterial();

		if (material == this.blockMaterial)
		{
			return false;
		} else if (side == EnumFacing.UP)
		{
			return true;
		} else if (material == Material.ICE)
		{
			return false;
		} else
		{
			boolean flag = isExceptBlockForAttachWithPiston(block) || block instanceof BlockStairs;
			return !flag && iblockstate.getBlockFaceShape(worldIn, pos, side) == BlockFaceShape.SOLID;
		}
	}
}
