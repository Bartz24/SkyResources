package com.bartz24.skymod.alchemy.tile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

import com.bartz24.skymod.RandomHelper;
import com.bartz24.skymod.alchemy.fluid.FluidCrystalBlock;
import com.bartz24.skymod.base.HeatSources;
import com.bartz24.skymod.registry.ModBlocks;
import com.bartz24.skymod.registry.ModFluids;

public class CondenserTile extends TileEntity implements ITickable
{
	int timeCondense;

	@Override
	public void update()
	{
		Block block = getBlockAbove();
		if (getBlockAbove() instanceof FluidCrystalBlock)
		{
			FluidCrystalBlock crystalBlock = (FluidCrystalBlock) block;
			Fluid fluid = crystalBlock.getFluid();
			String type = ModFluids.crystalFluidNames()[ModBlocks.crystalFluidBlocks
					.indexOf(crystalBlock)];

			String oreDictCheck = "ingot" + RandomHelper.capatilizeString(type);

			if (crystalBlock.isSourceBlock(worldObj, pos.add(0, 1, 0))
					&& crystalBlock.isNotFlowing(worldObj, pos.add(0, 1, 0),
							worldObj.getBlockState(pos.add(0, 1, 0)))
					&& OreDictionary.doesOreNameExist(oreDictCheck)
					&& HeatSources.isValidHeatSource(worldObj.getBlockState(pos
							.down())))
			{
				this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
						pos.getX() + 0.5F, pos.getY() + 1.5D,
						pos.getZ() + 0.5F, 0.0D, 0.0D, 0.0D, new int[0]);
				if (!worldObj.isRemote)
					timeCondense++;
			} else if (!worldObj.isRemote)
				timeCondense = 0;

			if (timeCondense >= getTimeToCondense(crystalBlock))
			{
				worldObj.setBlockToAir(pos.add(0, 1, 0));
				ItemStack stack = OreDictionary.getOres(oreDictCheck).get(0)
						.copy();
				Entity entity = new EntityItem(worldObj, pos.getX() + 0.5F,
						pos.getY() + 1.5F, pos.getZ() + 0.5F, stack);
				worldObj.spawnEntityInWorld(entity);
				timeCondense = 0;
			}
		}

	}

	public int getTimeToCondense(FluidCrystalBlock block)
	{
		return ModFluids.crystalFluidRarity()[ModBlocks.crystalFluidBlocks
				.indexOf(block)] * 150;
	}

	public Block getBlockAbove()
	{
		return this.worldObj.getBlockState(pos.add(0, 1, 0)).getBlock();
	}

}
