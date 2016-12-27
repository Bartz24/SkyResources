package com.bartz24.skyresources.technology.gui.container;

import com.bartz24.skyresources.technology.tile.FreezerTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFreezer extends Container
{
	private FreezerTile tile;
	private int timeValue;

	public ContainerFreezer(IInventory playerInv,
			FreezerTile te)
	{
		tile = te;

		for (int y = 0; y < Math.floor((float)tile.getSizeInventory() / 5f) + 1; ++y)
		{
			for (int x = 0; x < Math.min(5, tile.getSizeInventory() - y * 5); ++x)
			{
				this.addSlotToContainer(new Slot(tile, x + y * 5,
						53 + x * 18, 22 + y * 18));
			}
		}

		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9,
						8 + x * 18, 84 + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x)
		{
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tile.isUsableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot)
	{
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack())
		{
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < tile.getSizeInventory())
			{
				if (!this.mergeItemStack(current, tile.getSizeInventory(), 36+tile.getSizeInventory(), true))
					return ItemStack.EMPTY;
			} else
			{
				if (!this.mergeItemStack(current, 0, tile.getSizeInventory(), false))
					return ItemStack.EMPTY;
			}

			if (current.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (current.getCount() == previous.getCount())
				return ItemStack.EMPTY;
			slot.onTake(playerIn, current);
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

			icrafting.sendProgressBarUpdate(this, 0, this.tile.getField(0));

		}

		this.timeValue = this.tile.getField(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tile.setField(id, data);
	}
}
