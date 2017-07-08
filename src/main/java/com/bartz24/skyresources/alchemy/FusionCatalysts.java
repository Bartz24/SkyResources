package com.bartz24.skyresources.alchemy;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

public class FusionCatalysts
{
	static HashMap<ItemStack, Float> catalysts = new HashMap();

	public FusionCatalysts()
	{
		catalysts = new HashMap<ItemStack, Float>();
	}

	public static void addCatalyst(ItemStack stack, float value)
	{
		catalysts.put(stack, value);
	}

	public static boolean isCatalyst(ItemStack stack)
	{
		for (ItemStack key : catalysts.keySet())
		{
			if (stack.isItemEqual(key))
				return true;
		}

		return false;
	}

	public static float getCatalystValue(ItemStack stack)
	{
		for (ItemStack key : catalysts.keySet())
		{
			if (stack.isItemEqual(key))
				return catalysts.get(key);
		}
		return 0;
	}

	public static void removeCatalyst(ItemStack catalyst)
	{
		catalysts.remove(catalyst);
	}

	public static HashMap<ItemStack, Float> getCatalysts()
	{
		return catalysts;
	}
}
