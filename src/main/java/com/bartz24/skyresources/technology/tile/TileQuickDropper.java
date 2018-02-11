package com.bartz24.skyresources.technology.tile;

import com.bartz24.skyresources.base.tile.TileItemInventory;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileQuickDropper extends TileItemInventory implements ITickable
{
	public TileQuickDropper()
	{
		super("quickDropper", 1);
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			updateRedstone();
			if (this.getRedstoneSignal() == 0 && !world.isSideSolid(pos.down(), EnumFacing.UP)
					&& !this.getInventory().getStackInSlot(0).isEmpty())
			{
				EntityItem item = new EntityItem(world, pos.down().getX() + 0.5f, pos.down().getY() + 0.5f,
						pos.down().getZ() + 0.5f, this.getInventory().getStackInSlot(0));
				item.motionY = 0;
				item.motionX = 0;
				item.motionZ = 0;
				world.spawnEntity(item);
				this.getInventory().setStackInSlot(0, ItemStack.EMPTY);
			}
			this.markDirty();
		}
	}
}
