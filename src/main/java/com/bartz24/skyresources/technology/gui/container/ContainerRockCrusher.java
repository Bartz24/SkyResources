package com.bartz24.skyresources.technology.gui.container;

import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.technology.tile.TileRockCrusher;

import net.minecraft.inventory.IInventory;

public class ContainerRockCrusher extends ContainerBase
{
	private int curProgress;

	public ContainerRockCrusher(IInventory playerInv, TileRockCrusher te)
	{
		super(playerInv, te, 0, 24);
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 44, 84));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 1, 55, 49));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 2, 109, 31));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 3, 109, 49));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 4, 109, 67));
	}
}
