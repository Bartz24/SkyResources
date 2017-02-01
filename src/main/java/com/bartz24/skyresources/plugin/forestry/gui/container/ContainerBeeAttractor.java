package com.bartz24.skyresources.plugin.forestry.gui.container;

import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.plugin.forestry.tile.TileBeeAttractor;

import net.minecraft.inventory.IInventory;

public class ContainerBeeAttractor extends ContainerBase
{
	public ContainerBeeAttractor(IInventory playerInv, TileBeeAttractor te)
	{
		super(playerInv, te, 0, 24);
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 59, 39));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 1, 80, 26));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 2, 101, 39));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 3, 101, 65));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 4, 80, 78));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 5, 59, 65));
	}
}
