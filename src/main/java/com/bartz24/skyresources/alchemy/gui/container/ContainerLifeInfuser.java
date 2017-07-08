package com.bartz24.skyresources.alchemy.gui.container;

import com.bartz24.skyresources.alchemy.tile.LifeInfuserTile;
import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;

import net.minecraft.inventory.IInventory;

public class ContainerLifeInfuser extends ContainerBase
{

	public ContainerLifeInfuser(IInventory playerInv, LifeInfuserTile te)
	{
		super(playerInv, te);

		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 100, 25));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 1, 59, 25));
	}
}
