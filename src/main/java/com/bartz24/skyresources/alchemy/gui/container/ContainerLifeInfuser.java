package com.bartz24.skyresources.alchemy.gui.container;

import com.bartz24.skyresources.alchemy.item.ItemHealthGem;
import com.bartz24.skyresources.alchemy.item.ItemInfusionStone;
import com.bartz24.skyresources.alchemy.tile.LifeInfuserTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerLifeInfuser extends Container
{
	private LifeInfuserTile tile;
	private int heatValue;
	private int fuelBurnTime;
	private int heatPerTick;
	private int currentItemBurnTime;

	public ContainerLifeInfuser(IInventory playerInv, LifeInfuserTile te)
	{
		tile = te;

		this.addSlotToContainer(new Slot(tile, 0, 100, 25));
		this.addSlotToContainer(new Slot(tile, 1, 59, 25));
		this.addSlotToContainer(new Slot(tile, 2, 27, 25));

		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
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

			if (fromSlot < 3)
			{
				if (!this.mergeItemStack(current, 3, 39, true))
					return null;
			} else
			{
				if (current.getItem() instanceof ItemHealthGem)
				{
					if (!this.mergeItemStack(current, 0, 1, false))
						return null;
				} else if (current.getItem() instanceof ItemInfusionStone)
				{
					if (!this.mergeItemStack(current, 1, 2, false))
						return null;
				} else if (!this.mergeItemStack(current, 2, 3, false))
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
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tile.setField(id, data);
	}
}
