package com.bartz24.skyresources.alchemy.tile;

import java.util.Random;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.fluid.FluidCrystalBlock;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo.CrystalFluidType;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.base.tile.TileBase;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

public class CondenserTile extends TileBase implements ITickable
{
	public CondenserTile()
	{
		super("condenser");
	}

	private int timeCondense;

	@Override
	public void update()
	{
		updateRedstone();
		crystalFluidUpdate();
	}

	void crystalFluidUpdate()
	{
		Random rand = world.rand;
		Block block = getBlockAbove();
		if (block instanceof FluidCrystalBlock && getRedstoneSignal() == 0)
		{
			FluidCrystalBlock crystalBlock = (FluidCrystalBlock) block;
			Fluid fluid = crystalBlock.getFluid();
			String type = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].name;
			CrystalFluidType fluidType = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
					.indexOf(crystalBlock)].type;

			String oreDictCheck = "ingot" + RandomHelper.capatilizeString(type);

			if (tierCanCondense(fluidType) && crystalBlock.isSourceBlock(world, pos.add(0, 1, 0))
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

	public int getTimeToCondense(FluidCrystalBlock block)
	{
		return (int)(ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(block)].rarity
				* ConfigOptions.condenserProcessTimeBase
				* (ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
						.indexOf(block)].type == CrystalFluidType.NORMAL ? 1 : 20)
				* getCondenserSpeedFromTier());
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
		return getWorld().getBlockState(pos.add(0, 1, 0)).getBlock();
	}

	public boolean tierCanCondense(CrystalFluidType type)
	{
		switch (getBlockMetadata())
		{
		case 0:
			return type == CrystalFluidType.NORMAL;
		case 1:
			return true;
		case 2:
			return true;
		case 3:
			return true;
		}
		return false;
	}

	public float getCondenserSpeedFromTier()
	{
		switch (getBlockMetadata())
		{
		case 0:
			return 1;
		case 1:
			return 0.5f;
		case 2:
			return 1f/3f;
		case 3:
			return 0.25f;
		}
		return 1;
	}
}
