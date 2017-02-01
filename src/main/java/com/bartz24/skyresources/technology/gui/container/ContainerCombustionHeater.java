package com.bartz24.skyresources.technology.gui.container;

import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.technology.tile.TileCombustionHeater;

import net.minecraft.inventory.IInventory;

public class ContainerCombustionHeater extends ContainerBase
{
	private int heatValue;
	private int fuelBurnTime;
	private int heatPerTick;
	private int currentItemBurnTime;

	public ContainerCombustionHeater(IInventory playerInv, TileCombustionHeater te)
	{
		super(playerInv, te);
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 80, 53));
	}
}
