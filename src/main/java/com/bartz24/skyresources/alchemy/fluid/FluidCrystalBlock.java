package com.bartz24.skyresources.alchemy.fluid;

import java.util.Random;

import javax.annotation.Nullable;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.alchemy.block.CondenserBlock;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidCrystalBlock extends BlockFluidClassic
{

	public FluidCrystalBlock(Fluid fluid, Material material, String unlocalizedName,
			String registryName)
	{
		super(fluid, material);
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		this.setRegistryName(registryName);
		this.setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	public boolean isNotFlowing(World world, BlockPos pos, IBlockState state)
	{
		BlockPos[] checkPos = new BlockPos[]
		{ pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 0, 1), pos.add(0, 0, -1) };
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn,
			BlockPos pos)
	{
		return NULL_AABB;
	}
}
