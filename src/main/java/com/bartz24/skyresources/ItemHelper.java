package com.bartz24.skyresources;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHelper
{
	private static List<ItemStack> infusionStones = new ArrayList<ItemStack>();
	public static List<ItemStack> getInfusionStones()
	{
		return infusionStones;
	}
	
	public static void addInfusionStone(Item item)
	{
		infusionStones.add(new ItemStack(item));
	}
}
