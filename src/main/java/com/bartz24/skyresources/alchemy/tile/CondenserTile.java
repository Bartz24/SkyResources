package com.bartz24.skyresources.alchemy.tile;

import java.util.Random;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.fluid.FluidCrystalBlock;
import com.bartz24.skyresources.alchemy.fluid.FluidMoltenCrystalBlock;
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
		crystalFluidUpdate();
		moltenCrystalFluidUpdate();
	}

	void crystalFluidUpdate()
	{
		Random rand = worldObj.rand;
		Block block = getBlockAbove();
		if (block instanceof FluidCrystalBlock && this.worldObj.getBlockState(getPos())
				.getBlock() == ModBlocks.alchemicalCondenser)
		{
			FluidCrystalBlock crystalBlock = (FluidCrystalBlock) block;
			Fluid fluid = crystalBlock.getFluid();
			String type = "";
			boolean clean = false;
			if (ModBlocks.crystalFluidBlocks.contains(block))
			{
				type = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
						.indexOf(crystalBlock)].name;
				clean = true;
			} else if (ModBlocks.dirtyCrystalFluidBlocks.contains(block))
				type = ModFluids.crystalFluidInfos()[ModBlocks.dirtyCrystalFluidBlocks
						.indexOf(crystalBlock)].name;

			String oreDictCheck = "ingot" + RandomHelper.capatilizeString(type);

			if (crystalBlock.isSourceBlock(worldObj, pos.add(0, 1, 0))
					&& crystalBlock.isNotFlowing(worldObj, pos.add(0, 1, 0),
							worldObj.getBlockState(pos.add(0, 1, 0)))
					&& OreDictionary.doesOreNameExist(oreDictCheck)
					&& HeatSources.isValidHeatSource(pos.down(), worldObj))
			{
				this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
						pos.getX() + rand.nextFloat(), pos.getY() + 1.5D,
						pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				if (!worldObj.isRemote)
					timeCondense += HeatSources.getHeatSourceValue(pos.down(), worldObj);
			} else if (!worldObj.isRemote)
				timeCondense = 0;

			if (timeCondense >= getTimeToCondense(crystalBlock, clean))
			{
				worldObj.setBlockToAir(pos.add(0, 1, 0));
				ItemStack stack = OreDictionary.getOres(oreDictCheck).get(0).copy();
				stack.stackSize = 1;
				Entity entity = new EntityItem(worldObj, pos.getX() + 0.5F, pos.getY() + 1.5F,
						pos.getZ() + 0.5F, stack);
				worldObj.spawnEntityInWorld(entity);
				timeCondense = 0;
			}
		}
	}

	void moltenCrystalFluidUpdate()
	{
		Random rand = worldObj.rand;
		Block block = getBlockAbove();
		if (block instanceof FluidMoltenCrystalBlock && this.worldObj.getBlockState(getPos())
				.getBlock() == ModBlocks.advancedCoolingCondenser)
		{
			FluidMoltenCrystalBlock crystalBlock = (FluidMoltenCrystalBlock) block;
			Fluid fluid = crystalBlock.getFluid();
			String type = ModFluids.moltenCrystalFluidInfos()[ModBlocks.moltenCrystalFluidBlocks
					.indexOf(crystalBlock)].name;

			String oreDictCheck = "ingot" + RandomHelper.capatilizeString(type);

			if (crystalBlock.isSourceBlock(worldObj, pos.add(0, 1, 0))
					&& crystalBlock.isNotFlowing(worldObj, pos.add(0, 1, 0),
							worldObj.getBlockState(pos.add(0, 1, 0)))
					&& OreDictionary.doesOreNameExist(oreDictCheck)
					&& HeatSources.isValidHeatSource(pos.down(), worldObj))
			{
				this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
						pos.getX() + rand.nextFloat(), pos.getY() + 1.5D,
						pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				if (!worldObj.isRemote)
				{
					timeCondense += HeatSources.getHeatSourceValue(pos.down(), worldObj);
				}
			} else if (!worldObj.isRemote)
				timeCondense = 0;

			if (timeCondense >= getMoltenTimeToCondense(crystalBlock))
			{
				worldObj.setBlockToAir(pos.add(0, 1, 0));
				ItemStack stack = OreDictionary.getOres(oreDictCheck).get(0).copy();
				stack.stackSize = 1;
				Entity entity = new EntityItem(worldObj, pos.getX() + 0.5F, pos.getY() + 1.5F,
						pos.getZ() + 0.5F, stack);
				worldObj.spawnEntityInWorld(entity);
				timeCondense = 0;
			}
		}
	}

	public int getTimeToCondense(FluidCrystalBlock block, boolean clean)
	{
		if (!clean)
			return ModFluids.crystalFluidInfos()[ModBlocks.dirtyCrystalFluidBlocks
					.indexOf(block)].rarity * ConfigOptions.condenserProcessTimeBase * 10;

		return ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(block)].rarity
				* ConfigOptions.condenserProcessTimeBase;
	}

	public int getMoltenTimeToCondense(FluidMoltenCrystalBlock block)
	{
		return ModFluids.moltenCrystalFluidInfos()[ModBlocks.moltenCrystalFluidBlocks
				.indexOf(block)].rarity * ConfigOptions.condenserProcessTimeBase * 20;
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
		return this.worldObj.getBlockState(pos.add(0, 1, 0)).getBlock();
	}

}
