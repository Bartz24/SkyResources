package com.bartz24.skyresources.technology.gui.container;

import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.technology.tile.TileRockCleaner;

import net.minecraft.inventory.IInventory;

public class ContainerRockCleaner extends ContainerBase
{
	public ContainerRockCleaner(IInventory playerInv, TileRockCleaner te)
	{
		super(playerInv, te, 0, 24);
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 55, 49));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 1, 109, 31));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 2, 109, 49));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 3, 109, 67));
	}
}
