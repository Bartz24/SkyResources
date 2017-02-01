package com.bartz24.skyresources.base.tile;

import com.bartz24.skyresources.base.gui.ItemHandlerSpecial;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileItemInventory extends TileBase
{
	private ItemHandlerSpecial inventory;
	
	public TileItemInventory(String name, int slots)
	{
		super(name);
		inventory = new ItemHandlerSpecial(slots)
		{
			protected void onContentsChanged(int slot){
	            super.onContentsChanged(slot);
	            TileItemInventory.this.markDirty();
	        }			
		};
	}

	public TileItemInventory(String name, int slots, Integer[] noInsert, Integer[] noExtract)
	{
		super(name);
		inventory = new ItemHandlerSpecial(slots, noInsert, noExtract)
		{
			protected void onContentsChanged(int slot){
	            super.onContentsChanged(slot);
	            TileItemInventory.this.markDirty();
	        }			
		};
	}

	public ItemHandlerSpecial getInventory()
	{
		return inventory;
	}

	public void setInventory(ItemHandlerSpecial handler)
	{
		inventory = handler;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setTag("inv", inventory.serializeNBT());
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		inventory.deserializeNBT(compound.getCompoundTag("inv"));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T) this.inventory;
		}
		return super.getCapability(capability, facing);
	}

	public void dropInventory()
	{
		for (int i = 0; i < inventory.getSlots(); ++i)
		{
			ItemStack itemstack = inventory.getStackInSlot(i);

			if (!itemstack.isEmpty())
			{
				InventoryHelper.spawnItemStack(getWorld(), pos.getX(), pos.getY(), pos.getZ(), itemstack);
			}
		}
	}
}
