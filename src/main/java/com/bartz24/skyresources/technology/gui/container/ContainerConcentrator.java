package com.bartz24.skyresources.technology.gui.container;

import com.bartz24.skyresources.technology.tile.CombustionHeaterTile;
import com.bartz24.skyresources.technology.tile.ConcentratorTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerConcentrator extends Container
{
	private ConcentratorTile tile;
	private int heatValue;
	private int fuelBurnTime;
	private int heatPerTick;
	private int currentItemBurnTime;

	public ContainerConcentrator(IInventory playerInv,
			ConcentratorTile te)
	{
		tile = te;

		this.addSlotToContainer(new Slot(tile, 0, 80, 53));

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

			if (fromSlot < 1)
			{
				if (!this.mergeItemStack(current, 1, 37, true))
					return null;
			} else
			{
				if (!this.mergeItemStack(current, 0, 1, false))
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

		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting icrafting = this.crafters.get(i);

			icrafting.sendProgressBarUpdate(this, 0, this.tile.getField(0));

			icrafting.sendProgressBarUpdate(this, 1, this.tile.getField(1));

			icrafting.sendProgressBarUpdate(this, 2, this.tile.getField(2));

			icrafting.sendProgressBarUpdate(this, 3, this.tile.getField(3));
		}

		this.fuelBurnTime = this.tile.getField(0);
		this.currentItemBurnTime = this.tile.getField(1);
		this.heatValue = this.tile.getField(2);
		this.heatPerTick = this.tile.getField(3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tile.setField(id, data);
	}
}
