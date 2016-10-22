package com.bartz24.skyresources.technology.cauldron;

import java.util.Arrays;
import java.util.List;

import com.bartz24.skyresources.technology.combustion.CombustionRecipe;

import net.minecraft.item.ItemStack;

public class CauldronCleanRecipe
{
	private ItemStack output;
	private ItemStack input;
	private float dropChance;

	public CauldronCleanRecipe(ItemStack out, float chance, ItemStack in)
	{
		output = out;
		dropChance = chance;
		input = in;
	}

	public CauldronCleanRecipe(ItemStack in)
	{
		input = in;
	}

	public boolean isInputRecipeEqualTo(CauldronCleanRecipe recipe)
	{
		return stacksAreValid(recipe);
	}

	boolean stacksAreValid(CauldronCleanRecipe recipe)
	{
		if (input == null)
			return false;

		return input.isItemEqual(recipe.input)
				&& input.stackSize >= recipe.input.stackSize;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public ItemStack getInput()
	{
		return input;
	}

	public float getDropChance()
	{
		return dropChance;
	}
}
