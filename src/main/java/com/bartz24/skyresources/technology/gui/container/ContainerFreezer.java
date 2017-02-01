package com.bartz24.skyresources.technology.gui.container;

import javax.annotation.Nonnull;

import com.bartz24.skyresources.base.gui.ContainerBase;
import com.bartz24.skyresources.base.gui.SlotSpecial;
import com.bartz24.skyresources.technology.tile.MiniFreezerTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFreezer extends ContainerBase
{
	private MiniFreezerTile tile;
	private int timeValue;

	public ContainerFreezer(IInventory playerInv, MiniFreezerTile te)
	{
		super(playerInv, te);
		tile = te;

		for (int y = 0; y < Math.floor((float) tile.getInventory().getSlots() / 2f / 5f) + 1; ++y)
		{
			for (int x = 0; x < Math.min(5, tile.getInventory().getSlots() / 2f - y * 5); ++x)
			{
				this.addSlotToContainer(
						new SlotSpecial(tile.getInventory(), x + y * 5, 53 + x * 18, 22 + y * 18));
				this.addSlotToContainer(new SlotSpecial(tile.getInventory(),
						x + y * 5 + tile.getInventory().getSlots() / 2, 53 + x * 18, 40 + y * 18));

			}
		}
	}
}
