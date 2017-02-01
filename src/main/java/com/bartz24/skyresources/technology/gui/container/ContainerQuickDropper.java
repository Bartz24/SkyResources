package com.bartz24.skyresources.technology.gui.container;

import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.technology.tile.TileQuickDropper;

import net.minecraft.inventory.IInventory;

public class ContainerQuickDropper extends ContainerBase
{
	public ContainerQuickDropper(IInventory playerInv,
			TileQuickDropper te)
	{
		super(playerInv, te);

		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 80, 53));
	}
}
