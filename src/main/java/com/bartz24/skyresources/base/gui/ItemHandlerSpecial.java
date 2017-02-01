package com.bartz24.skyresources.base.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemHandlerSpecial extends ItemStackHandler
{	

    public ItemHandlerSpecial(int size)
    {
    	super(size);
		this.slotsNoExtract = new ArrayList<Integer>();
		this.slotsNoInsert = new ArrayList<Integer>();
    }

    public ItemHandlerSpecial(int size, Integer[] noInsert, Integer[] noExtract)
    {
    	super(size);
		this.slotsNoExtract = noExtract == null ? new ArrayList<Integer>() : 
			Arrays.asList(noExtract);
		this.slotsNoInsert = noInsert == null ? new ArrayList<Integer>() : 
			Arrays.asList(noInsert);
    }

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if(!isItemValid(slot, stack)) {
			return stack;
		}
		return super.insertItem(slot, stack, simulate);
	}

	public boolean isItemValid(int slot, ItemStack itemStack) {
		return !slotsNoInsert.contains(slot);
	}
    
	private List<Integer> slotsNoExtract;
	private List<Integer> slotsNoInsert;
	public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
		if(slotsNoExtract.contains(slot))
			return ItemStack.EMPTY;
		return super.extractItem(slot, amount, simulate);				
    }
	
	public ItemStack containerExtractItem(int slot, int amount, boolean simulate)
	{
		return super.extractItem(slot, amount, simulate);					
	}
}
