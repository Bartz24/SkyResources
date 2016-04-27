package com.bartz24.skyresources.technology.freezer;

import java.util.Arrays;
import java.util.List;

import com.bartz24.skyresources.technology.combustion.CombustionRecipe;

import net.minecraft.item.ItemStack;

public class FreezerRecipe
{
	private ItemStack output;
	private ItemStack input;
	private int timeReq;

	public FreezerRecipe(ItemStack out, int time, ItemStack in)
	{
		output = out;
		timeReq = time;
		input = in;
	}

	public FreezerRecipe(ItemStack in)
	{
		input = in;
	}

	public boolean isInputRecipeEqualTo(FreezerRecipe recipe)
	{
		return stacksAreValid(recipe);
	}

	boolean stacksAreValid(FreezerRecipe recipe)
	{
		if (input == null)
			return false;

		return input.isItemEqual(recipe.input)
				&& input.stackSize == recipe.input.stackSize;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public ItemStack getInput()
	{
		return input;
	}

	public int getTimeReq()
	{
		return timeReq;
	}
}
