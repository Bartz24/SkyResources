package com.bartz24.skyresources;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.alchemy.item.ItemInfusionStone;
import com.bartz24.skyresources.technology.item.ItemRockGrinder;

import net.minecraft.item.ItemStack;

public class ItemHelper
{
	private static List<ItemStack> infusionStones = new ArrayList<ItemStack>();

	public static List<ItemStack> getInfusionStones()
	{
		return infusionStones;
	}

	public static void addInfusionStone(ItemInfusionStone item)
	{
		infusionStones.add(new ItemStack(item));
	}

	private static List<ItemStack> rockGrinders = new ArrayList<ItemStack>();

	public static List<ItemStack> getRockGrinders()
	{
		return rockGrinders;
	}

	public static void addRockGrinder(ItemRockGrinder item)
	{
		rockGrinders.add(new ItemStack(item));
	}
}
