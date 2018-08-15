package com.bartz24.skyresources.alchemy.fluid;

import java.util.Random;

import javax.annotation.Nullable;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.registry.ModFluids;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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
	}
}
