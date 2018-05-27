package com.bartz24.skyresources.base.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

public class ItemHandlerSpecial extends ItemStackHandler
{	
	private int stackLimit = 64;

    protected int[] slotsNoInsert;
    protected int[] slotsNoExtract;

    public ItemHandlerSpecial(int size)
    {
    	super(size);
		this.slotsNoExtract = new int[0];
		this.slotsNoInsert = new int[0];
    }
	
	public void setSlotLimit(int amount)
	{
		stackLimit = Math.min(64, amount);
	}
	
	@Override
    public int getSlotLimit(int slot)
    {
        return stackLimit;
    }

    public ItemHandlerSpecial(int size, int[] noInsert, int[] noExtract)
    {
    	super(size);
        this.slotsNoExtract = noExtract == null ? new int[0] : noExtract;
        this.slotsNoInsert = noInsert == null ? new int[0] : noInsert;
    }

    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (ArrayUtils.contains(slotsNoInsert, slot)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }

    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (ArrayUtils.contains(slotsNoExtract, slot)) {
            return ItemStack.EMPTY;
        }
        return super.extractItem(slot, amount, simulate);
    }
	
	public ItemStack insertInternalItem(int slot, ItemStack stack, boolean simulate) {
		return super.insertItem(slot, stack, simulate);
	}
	
	public ItemStack containerExtractItem(int slot, int amount, boolean simulate)
	{
		return super.extractItem(slot, amount, simulate);					
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = super.serializeNBT();
		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < slotsNoInsert.length; i++) {
			nbtTagList.appendTag(new NBTTagInt(slotsNoInsert[i]));
		}
		nbt.setInteger("noInsertSize", slotsNoInsert.length);
		nbt.setTag("noInsert", nbtTagList);
		nbtTagList = new NBTTagList();
		for (int i = 0; i < slotsNoExtract.length; i++) {
			nbtTagList.appendTag(new NBTTagInt(slotsNoExtract[i]));
		}
		nbt.setInteger("noExtractSize", slotsNoExtract.length);
		nbt.setTag("noExtract", nbtTagList);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		super.deserializeNBT(nbt);
		slotsNoInsert = new int[nbt.getInteger("noInsertSize")];
		NBTTagList tagList = nbt.getTagList("noInsert", Constants.NBT.TAG_INT);
		for (int i = 0; i < tagList.tagCount(); i++) {
			slotsNoInsert[i] = tagList.getIntAt(i);
		}
		slotsNoExtract = new int[nbt.getInteger("noExtractSize")];
		tagList = nbt.getTagList("noExtract", Constants.NBT.TAG_INT);
		for (int i = 0; i < tagList.tagCount(); i++) {
			slotsNoExtract[i] = tagList.getIntAt(i);
		}
	}
}
