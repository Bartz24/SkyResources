package com.bartz24.skyresources.technology.gui.container;

import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.technology.tile.TileCrucibleInserter;

import net.minecraft.inventory.IInventory;

public class ContainerCrucibleInserter extends ContainerBase
{
	public ContainerCrucibleInserter(IInventory playerInv,
			TileCrucibleInserter te)
	{
		super(playerInv, te);

		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 80, 53));
	}
}
