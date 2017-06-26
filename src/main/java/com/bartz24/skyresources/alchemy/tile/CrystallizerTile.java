package com.bartz24.skyresources.alchemy.tile;

import java.util.Random;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.alchemy.fluid.FluidCrystalBlock;
import com.bartz24.skyresources.alchemy.fluid.FluidRegisterInfo.CrystalFluidType;
import com.bartz24.skyresources.base.tile.TileBase;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.CapabilityItemHandler;

public class CrystallizerTile extends TileBase implements ITickable
{
	public CrystallizerTile()
	{
		super("crystallizer");
	}

	int timeCondense;
	int randInterval;

	@Override
	public void update()
	{
		updateRedstone();
		fluidUpdate();
	}

	void fluidUpdate()
	{
		Random rand = world.rand;
		Block block = getBlockAbove();
		boolean success = false;
		if (!world.isRemote && getRedstoneSignal() == 0)
		{
			if (block instanceof FluidCrystalBlock)
			{
				FluidCrystalBlock crystalBlock = (FluidCrystalBlock) block;
				Fluid fluid = crystalBlock.getFluid();
				String type = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].name;

				if (this.tierCanCrystallize(
						ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].type))
				{
					if (crystalBlock.isSourceBlock(world, pos.up())
							&& crystalBlock.isNotFlowing(world, pos.up(), world.getBlockState(pos.up())))
						timeCondense++;
					else
						timeCondense = 0;
					if (timeCondense >= randInterval)
					{
						if (rand.nextInt(50 + ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
								.indexOf(crystalBlock)].rarity * 5) >= 40 + 15 * getCrystallizeEfficiencyFromTier())
							world.setBlockToAir(pos.up());

						ItemStack stack = new ItemStack(ModItems.metalCrystal, 1, ModFluids
								.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].crystalIndex);
						ejectResultSlot(stack);
						success = true;
						timeCondense = 0;
						randInterval = (int) ((float) (20 * ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
								.indexOf(crystalBlock)].rarity + 20) / getCrystallizeSpeedFromTier());
					}
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

			if (tile != null)
			{
				if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP))
				{
					output = RandomHelper.fillInventory(
							tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP), output);
				} else if (tile instanceof IInventory)
				{
					output = RandomHelper.fillInventory((IInventory) tile, output);
				}
			}

			if (output != ItemStack.EMPTY && output.getCount() > 0)
			{
				EntityItem item = new EntityItem(world, pos.down().getX() + 0.5f, pos.down().getY() + 0.5f,
						pos.down().getZ() + 0.5f, output.copy());
				item.motionY = 0;
				item.motionX = 0;
				item.motionZ = 0;
				world.spawnEntity(item);
			}
		}
	}

	public boolean tierCanCrystallize(CrystalFluidType type)
	{
		switch (world.getTileEntity(pos).getBlockMetadata())
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

	public float getCrystallizeSpeedFromTier()
	{
		switch (world.getTileEntity(pos).getBlockMetadata())
		{
		case 0:
			return 0.5f;
		case 1:
			return 1;
		case 2:
			return 2.5f;
		case 3:
			return 4;
		}
		return 1;
	}

	public float getCrystallizeEfficiencyFromTier()
	{
		switch (world.getTileEntity(pos).getBlockMetadata())
		{
		case 0:
			return 1;
		case 1:
			return 1.5f;
		case 2:
			return 2;
		case 3:
			return 3;
		}
		return 1;
	}
}
