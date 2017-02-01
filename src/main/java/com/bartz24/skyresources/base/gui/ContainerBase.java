package com.bartz24.skyresources.base.gui;

import com.bartz24.skyresources.base.tile.TileItemInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBase extends Container
{
	protected TileItemInventory tile;

	protected int slotCount;

	public ContainerBase(IInventory playerInv, TileItemInventory te, int playerInvOffX, int playerInvOffY)
	{
		tile = te;

		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlotToContainer(
						new Slot(playerInv, x + y * 9 + 9, 8 + playerInvOffX + x * 18, 84 + playerInvOffY + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x)
		{
			this.addSlotToContainer(new Slot(playerInv, x, 8+ playerInvOffX + x * 18, 142+ playerInvOffY));
		}
	}
	
	public ContainerBase(IInventory playerInv, TileItemInventory te)
	{
		this(playerInv, te, 0, 0);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return player.getDistanceSq(tile.getPos().add(0.5, 0.5, 0.5)) <= 64;
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

			if (fromSlot < 36)
			{
				if (!this.mergeItemStack(current, 36, slotCount, false))
					return ItemStack.EMPTY;
			} else
			{
				if (!this.mergeItemStack(current, 0, 36, false))
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

	protected Slot addSlotToContainer(Slot slotIn)
	{
		slotCount++;
		return super.addSlotToContainer(slotIn);
	}
}