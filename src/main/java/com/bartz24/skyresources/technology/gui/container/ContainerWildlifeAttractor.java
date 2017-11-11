package com.bartz24.skyresources.technology.gui.container;

import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.technology.tile.TileWildlifeAttractor;

import net.minecraft.inventory.IInventory;

public class ContainerWildlifeAttractor extends ContainerBase
{
	public ContainerWildlifeAttractor(IInventory playerInv,
			TileWildlifeAttractor te)
	{
		super(playerInv, te, 0, 24);

		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 80, 59));
	}
}
