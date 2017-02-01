package com.bartz24.skyresources.technology.gui.container;

import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.technology.tile.TileAqueousConcentrator;

import net.minecraft.inventory.IInventory;

public class ContainerAqueousConcentrator extends ContainerBase
{
	private int curProgress;

	public ContainerAqueousConcentrator(IInventory playerInv, TileAqueousConcentrator te)
	{
		super(playerInv, te, 0, 24);
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 55, 49));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 1, 109, 49));
	}
}
