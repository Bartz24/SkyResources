package com.bartz24.skyresources.alchemy.gui.container;

import com.bartz24.skyresources.alchemy.tile.TileAlchemyFusionTable;
import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ContainerFusionTable extends ContainerBase
{
	public ContainerFusionTable(IInventory playerInv, TileAlchemyFusionTable te)
	{
		super(playerInv, te, 0, 15);

		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 0, 116, 74));
		this.addSlotToContainer(getFilterSlot(1, 8, 34));
		this.addSlotToContainer(getFilterSlot(2, 26, 34));
		this.addSlotToContainer(getFilterSlot(3, 44, 34));
		this.addSlotToContainer(getFilterSlot(4, 62, 34));
		this.addSlotToContainer(getFilterSlot(5, 80, 34));
		this.addSlotToContainer(getFilterSlot(6, 98, 34));
		this.addSlotToContainer(getFilterSlot(7, 116, 34));
		this.addSlotToContainer(getFilterSlot(8, 134, 34));
		this.addSlotToContainer(getFilterSlot(9, 152, 34));
		this.addSlotToContainer(new SlotSpecial(tile.getInventory(), 10, 80, 74));
	}

	private SlotSpecial getFilterSlot(int index, int x, int y)
	{
		return new SlotSpecial(tile.getInventory(), index, x, y, true)
		{
			public ItemStack decrStackSize(int amount)
			{
				ItemStack newStack = super.decrStackSize(amount);
				if (tile.getInventory().getStackInSlot(index).isEmpty())
					((TileAlchemyFusionTable) tile).setFilter(index-1, ItemStack.EMPTY);
				return newStack;
			}
		};
	}
}
