package com.bartz24.skyresources.base.gui;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

public class SlotSpecial extends SlotItemHandler
{
	private boolean internalIn;
	private int index;

	public SlotSpecial(IItemHandler itemHandler, int index, int x, int y, boolean internalInsert)
	{
		super(itemHandler, index, x, y);
		this.index = index;
		internalIn = internalInsert;
	}

	public SlotSpecial(IItemHandler itemHandler, int index, int x, int y)
	{
		this(itemHandler, index, x, y, false);
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
		if (this.getItemHandler() instanceof ItemHandlerSpecial)
		{
			return ((ItemHandlerSpecial) this.getItemHandler()).containerExtractItem(this.getSlotIndex(), amount,
					false);
		}

		return this.getItemHandler().extractItem(this.getSlotIndex(), amount, false);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack)
	{
		if (internalIn)
		{
			if (stack.isEmpty())
				return false;

			IItemHandler handler = this.getItemHandler();
			ItemStack remainder;
			if (handler instanceof ItemHandlerSpecial)
			{
				ItemHandlerSpecial handlerSpecial = (ItemHandlerSpecial) handler;
				ItemStack currentStack = handlerSpecial.getStackInSlot(index);

				handlerSpecial.setStackInSlot(index, ItemStack.EMPTY);

				remainder = handlerSpecial.insertInternalItem(index, stack, true);

				handlerSpecial.setStackInSlot(index, currentStack);
			} else
			{
				remainder = handler.insertItem(index, stack, true);
			}
			return remainder.isEmpty() || remainder.getCount() < stack.getCount();
		} else
			return super.isItemValid(stack);
	}

	@Override
	public int getItemStackLimit(@Nonnull ItemStack stack)
	{
		if (internalIn)
		{
			ItemStack maxAdd = stack.copy();
			int maxInput = stack.getMaxStackSize();
			maxAdd.setCount(maxInput);

			IItemHandler handler = this.getItemHandler();
			ItemStack currentStack = handler.getStackInSlot(index);
			if (handler instanceof ItemHandlerSpecial)
			{
				ItemHandlerSpecial handlerSpecial = (ItemHandlerSpecial) handler;

				handlerSpecial.setStackInSlot(index, ItemStack.EMPTY);

				ItemStack remainder = handlerSpecial.insertInternalItem(index, maxAdd, true);

				handlerSpecial.setStackInSlot(index, currentStack);

				return maxInput - remainder.getCount();
			} else
			{
				ItemStack remainder = handler.insertItem(index, maxAdd, true);

				int current = currentStack.getCount();
				int added = maxInput - remainder.getCount();
				return current + added;
			}
		} else
			return super.getItemStackLimit(stack);
	}
}
