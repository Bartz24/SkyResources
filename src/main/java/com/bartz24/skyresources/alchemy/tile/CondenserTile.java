package com.bartz24.skyresources.alchemy.tile;

import java.util.Random;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.fluid.FluidCrystalBlock;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

public class CondenserTile extends TileEntity implements ITickable
{
	int timeCondense;

	@Override
	public void update()
	{
		if (!ConfigOptions.easyMode)
		{
			crystalFluidUpdate();
			moltenCrystalFluidUpdate();
		}
	}

	void crystalFluidUpdate()
	{
		Random rand = world.rand;
		Block block = getBlockAbove();
		if (block instanceof FluidCrystalBlock
				&& this.world.getBlockState(getPos()).getBlock() == ModBlocks.alchemicalCondenser)
		{
			FluidCrystalBlock crystalBlock = (FluidCrystalBlock) block;
			Fluid fluid = crystalBlock.getFluid();
			String type = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].name;

			String oreDictCheck = "ingot" + RandomHelper.capatilizeString(type);

			if (crystalBlock.isSourceBlock(world, pos.add(0, 1, 0))
					&& crystalBlock.isNotFlowing(world, pos.add(0, 1, 0), world.getBlockState(pos.add(0, 1, 0)))
					&& OreDictionary.getOres(oreDictCheck).size() > 0
					&& HeatSources.isValidHeatSource(pos.down(), world))
			{
				this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextFloat(),
						pos.getY() + 1.5D, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				if (!world.isRemote)
					timeCondense += HeatSources.getHeatSourceValue(pos.down(), world);
			} else if (!world.isRemote)
				timeCondense = 0;

			if (timeCondense >= getTimeToCondense(crystalBlock))
			{
				world.setBlockToAir(pos.add(0, 1, 0));
				ItemStack stack = OreDictionary.getOres(oreDictCheck).get(0).copy();
				stack.setCount(1);
				Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 1.5F, pos.getZ() + 0.5F, stack);
				world.spawnEntity(entity);
				timeCondense = 0;
			}
		}
	}

	void moltenCrystalFluidUpdate()
	{
		Random rand = world.rand;
		Block block = getBlockAbove();
		if (block instanceof FluidCrystalBlock
				&& this.world.getBlockState(getPos()).getBlock() == ModBlocks.advancedCoolingCondenser)
		{
			FluidCrystalBlock crystalBlock = (FluidCrystalBlock) block;
			Fluid fluid = crystalBlock.getFluid();
			String type = ModFluids.crystalFluidInfos()[ModBlocks.moltenCrystalFluidBlocks
					.indexOf(crystalBlock)].name;

			String oreDictCheck = "ingot" + RandomHelper.capatilizeString(type);

			if (crystalBlock.isSourceBlock(world, pos.add(0, 1, 0))
					&& crystalBlock.isNotFlowing(world, pos.add(0, 1, 0), world.getBlockState(pos.add(0, 1, 0)))
					&& OreDictionary.getOres(oreDictCheck).size() > 0
					&& HeatSources.isValidHeatSource(pos.down(), world))
			{
				this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextFloat(),
						pos.getY() + 1.5D, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				if (!world.isRemote)
				{
					timeCondense += HeatSources.getHeatSourceValue(pos.down(), world);
				}
			} else if (!world.isRemote)
				timeCondense = 0;

			if (timeCondense >= getMoltenTimeToCondense(crystalBlock))
			{
				world.setBlockToAir(pos.add(0, 1, 0));
				ItemStack stack = OreDictionary.getOres(oreDictCheck).get(0).copy();
				stack.setCount(1);
				Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 1.5F, pos.getZ() + 0.5F, stack);
				world.spawnEntity(entity);
				timeCondense = 0;
			}
		}
	}

	public int getTimeToCondense(FluidCrystalBlock block)
	{
		return ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(block)].rarity
				* ConfigOptions.condenserProcessTimeBase;
	}

	public int getMoltenTimeToCondense(FluidCrystalBlock block)
	{
		return ModFluids.crystalFluidInfos()[ModBlocks.moltenCrystalFluidBlocks.indexOf(block)].rarity
				* ConfigOptions.condenserProcessTimeBase * 20;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("time", timeCondense);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		timeCondense = compound.getInteger("time");
	}

	public Block getBlockAbove()
	{
		return this.world.getBlockState(pos.add(0, 1, 0)).getBlock();
	}

}
