package com.bartz24.skyresources.alchemy.gui.container;

import com.bartz24.skyresources.alchemy.tile.LifeInjectorTile;
import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;

import net.minecraft.inventory.IInventory;

public class ContainerLifeInjector extends ContainerBase
{
	public ContainerLifeInjector(IInventory playerInv, LifeInjectorTile te)
	{
		super(playerInv, te);

		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 80, 53));
	}
}
