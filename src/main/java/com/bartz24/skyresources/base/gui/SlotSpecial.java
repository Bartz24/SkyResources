package com.bartz24.skyresources.base.gui;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotSpecial extends SlotItemHandler
{

	public SlotSpecial(IItemHandler itemHandler, int index, int x, int y)
	{
		super(itemHandler, index, x, y);
	}
	
	@Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
		return true;
    }
	
	@Override
    @Nonnull
    public ItemStack decrStackSize(int amount)
    {
		if(this.getItemHandler() instanceof ItemHandlerSpecial)
		{
			return ((ItemHandlerSpecial)this.getItemHandler()).containerExtractItem(this.getSlotIndex(), amount, false);
		}
		
        return this.getItemHandler().extractItem(this.getSlotIndex(), amount, false);
    }
}
