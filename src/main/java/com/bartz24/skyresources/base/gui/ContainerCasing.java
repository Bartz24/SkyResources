package com.bartz24.skyresources.base.gui;

import com.bartz24.skyresources.base.tile.TileCasing;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerCasing extends ContainerBase
{

	public ContainerCasing(IInventory playerInv, TileCasing te)
	{
		super(playerInv, te, te.machineStored.isEmpty() ? 0 : te.getMachine().getInvPos(te.machineStored)[0],
				te.machineStored.isEmpty() ? 0 : te.getMachine().getInvPos(te.machineStored)[1]);

		if (!te.machineStored.isEmpty())
		{
			for (Slot s : te.getMachine().getSlots(te))
				this.addSlotToContainer(s);
		}
	}

}
