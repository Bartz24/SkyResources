package com.bartz24.skyresources.alchemy.tile;

import java.util.Random;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.fluid.FluidCrystalBlock;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class CrystallizerTile extends TileEntity implements ITickable
{
	int timeCondense;
	int randInterval;

	@Override
	public void update()
	{
		fluidUpdate();
	}

	void fluidUpdate()
	{
		Random rand = world.rand;
		Block block = getBlockAbove();
		boolean success = false;
		if (!world.isRemote)
		{
			if (block instanceof FluidCrystalBlock)
			{
				FluidCrystalBlock crystalBlock = (FluidCrystalBlock) block;
				Fluid fluid = crystalBlock.getFluid();
				String type = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].name;

				if (crystalBlock.isSourceBlock(world, pos.up())
						&& crystalBlock.isNotFlowing(world, pos.up(), world.getBlockState(pos.up())))
					timeCondense++;
				else
					timeCondense = 0;
				if (timeCondense >= randInterval)
				{
					if (crystallize(crystalBlock))
					{
						if (rand.nextInt(10 + ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
								.indexOf(crystalBlock)].rarity / 2) >= 8)
							world.setBlockToAir(pos.up());

						ItemStack stack = new ItemStack(ModItems.metalCrystal, 1, ModFluids
								.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].crystalIndex);
						ejectResultSlot(stack);
						success = true;
					}
					timeCondense = 0;
					randInterval = rand.nextInt(1) + 1;
				}
			}
		}
		if (success)
			world.playSound((EntityPlayer) null, pos, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.BLOCKS, 1.0F,
					2.2F / (rand.nextFloat() * 0.2F + 0.9F));
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

	public boolean crystallize(FluidCrystalBlock block)
	{
		return world.rand
				.nextInt(ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(block)].rarity * 2) == 0;
	}

	public Block getBlockAbove()
	{
		return this.world.getBlockState(pos.add(0, 1, 0)).getBlock();
	}

	void ejectResultSlot(ItemStack output)
	{
		if (!world.isRemote)
		{

			BlockPos facingPos = getPos().down();

			TileEntity tile = world.getTileEntity(facingPos);
			if (tile instanceof IInventory)
			{
				output = RandomHelper.fillInventory((IInventory) tile, output);
			}

			if (output != ItemStack.EMPTY && output.getCount() > 0)
				RandomHelper.spawnItemInWorld(world, output, facingPos);
		}
	}
}
