package com.bartz24.skyresources.base.item;

import java.util.Arrays;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.base.MachineVariants;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemComponent extends Item
{

	public ItemComponent(String baseName, CreativeTabs tab)
	{
		setUnlocalizedName(References.ModID + "." + baseName + ".");
		setRegistryName(baseName);
		setHasSubtypes(true);
		this.setCreativeTab(tab);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + MachineVariants.values()[stack.getMetadata()].getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> list)
	{
		if (isInCreativeTab(creativeTab))
			for (int i = 0; i < MachineVariants.values().length; i++)
				list.add(new ItemStack(this, 1, i));
	}

	public ItemStack getStack(MachineVariants variant)
	{
		return new ItemStack(this, 1, Arrays.asList(MachineVariants.values()).indexOf(variant));
	}
}
