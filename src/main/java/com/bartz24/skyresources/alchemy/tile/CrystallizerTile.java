package com.bartz24.skyresources.alchemy.tile;

import java.util.Random;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.fluid.FluidCrystalBlock;
import com.bartz24.skyresources.alchemy.fluid.FluidMoltenCrystalBlock;
import com.bartz24.skyresources.base.HeatSources;
import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;
import com.bartz24.skyresources.technology.block.BlockFreezer;
import com.bartz24.skyresources.technology.tile.FreezerTile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

public class CrystallizerTile extends TileEntity implements ITickable
{
	int timeCondense;
	int randInterval;

	@Override
	public void update()
	{
		moltenCrystalFluidUpdate();
	}

	void moltenCrystalFluidUpdate()
	{
		Random rand = worldObj.rand;
		Block block = getBlockAbove();
		if (!worldObj.isRemote)
		{
			if (block instanceof FluidMoltenCrystalBlock)
			{
				FluidMoltenCrystalBlock crystalBlock = (FluidMoltenCrystalBlock) block;
				Fluid fluid = crystalBlock.getFluid();
				String type = ModFluids.moltenCrystalFluidInfos()[ModBlocks.moltenCrystalFluidBlocks
						.indexOf(crystalBlock)].name;

				if (crystalBlock.isSourceBlock(worldObj, pos.up()) && crystalBlock
						.isNotFlowing(worldObj, pos.up(), worldObj.getBlockState(pos.up())))
					timeCondense++;
				else
					timeCondense = 0;
				if (timeCondense >= randInterval)
				{
					if (crystallize(crystalBlock))
					{
						if (rand.nextInt(
								10 + ModFluids.crystalFluidInfos()[ModBlocks.moltenCrystalFluidBlocks
										.indexOf(crystalBlock)].rarity / 2) >= 8)
							worldObj.setBlockToAir(pos.up());
						
						ItemStack stack = new ItemStack(ModItems.metalCrystal, 1,
								ModFluids.moltenCrystalFluidInfos()[ModBlocks.moltenCrystalFluidBlocks
										.indexOf(crystalBlock)].crystalIndex);
						ejectResultSlot(stack);
					}
					timeCondense = 0;
					randInterval = rand.nextInt(240) + 20;
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("time", timeCondense);
		compound.setInteger("rand", randInterval);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		timeCondense = compound.getInteger("time");
		randInterval = compound.getInteger("rand");
	}

	public boolean crystallize(FluidMoltenCrystalBlock block)
	{
		return worldObj.rand
				.nextInt(ModFluids.crystalFluidInfos()[ModBlocks.moltenCrystalFluidBlocks
						.indexOf(block)].rarity * 2) == 0;
	}

	public Block getBlockAbove()
	{
		return this.worldObj.getBlockState(pos.add(0, 1, 0)).getBlock();
	}

	void ejectResultSlot(ItemStack output)
	{
		if (!worldObj.isRemote)
		{

			BlockPos facingPos = getPos().down();

			TileEntity tile = worldObj.getTileEntity(facingPos);
			if (tile instanceof IInventory)
			{
				output = RandomHelper.fillInventory((IInventory) tile, output);
			}

			if (output != null && output.stackSize > 0)
				RandomHelper.spawnItemInWorld(worldObj, output, facingPos);
		}
	}
}
