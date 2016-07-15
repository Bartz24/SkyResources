package com.bartz24.skyresources.alchemy.crucible;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CrucibleRecipe
{
	private FluidStack output;
	private ItemStack input;

	public CrucibleRecipe(FluidStack out, ItemStack in)
	{
		output = out;
		input = in;
	}

	public CrucibleRecipe(ItemStack in)
	{
		input = in;
	}

	public boolean isInputRecipeEqualTo(CrucibleRecipe recipe)
	{
		return stacksAreValid(recipe);
	}

	boolean stacksAreValid(CrucibleRecipe recipe)
	{
		if (input == null)
			return false;

		return input.isItemEqual(recipe.input)
				&& input.stackSize >= recipe.input.stackSize;
	}

	public FluidStack getOutput()
	{
		return output;
	}

	public ItemStack getInput()
	{
		return input;
	}
}
