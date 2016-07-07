package com.bartz24.skyresources.forestry.gui.container;

import com.bartz24.skyresources.forestry.tile.TileBeeAttractor;

import forestry.core.gui.slots.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBeeAttractor extends Container
{

	private TileBeeAttractor tile;

	public ContainerBeeAttractor(IInventory playerInv, TileBeeAttractor te)
	{
		tile = te;

		this.addSlotToContainer(new SlotOutput(tile, 0, 59, 39));
		this.addSlotToContainer(new SlotOutput(tile, 1, 80, 26));
		this.addSlotToContainer(new SlotOutput(tile, 2, 101, 39));
		this.addSlotToContainer(new SlotOutput(tile, 3, 101, 65));
		this.addSlotToContainer(new SlotOutput(tile, 4, 80, 78));
		this.addSlotToContainer(new SlotOutput(tile, 5, 59, 65));

		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9,
						8 + x * 18, 108 + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x)
		{
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 166));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot)
	{
		ItemStack previous = null;
		Slot slot = this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack())
		{
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < 6)
			{
				if (!this.mergeItemStack(current, 6, 42, true))
					return null;
			}

			if (current.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (current.stackSize == previous.stackSize)
				return null;
			slot.onPickupFromSlot(playerIn, current);
		}
		return previous;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener icrafting = this.listeners.get(i);

			// icrafting.sendProgressBarUpdate(this, 0, this.tile.getField(0));
		}

		// this.fuelBurnTime = this.tile.getField(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tile.setField(id, data);
	}
}
