package com.bartz24.skyresources.technology.rockgrinder;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class RockGrinderRecipe
{
	IBlockState inputBlock;

	boolean fuzzyInput;

	ItemStack output;

	public RockGrinderRecipe(ItemStack outputStack, boolean fuzzy,
			IBlockState inputState)
	{
		inputBlock = inputState;
		fuzzyInput = fuzzy;
		output = outputStack;
	}

	public RockGrinderRecipe(IBlockState inputState)
	{
		inputBlock = inputState;
	}

	public boolean isInputRecipeEqualTo(RockGrinderRecipe recipe)
	{
		return stacksAreValid(recipe);
	}

	boolean stacksAreValid(RockGrinderRecipe recipe)
	{
		if (inputBlock == null)
			return false;

		if (fuzzyInput)
			return recipe.inputBlock.getBlock() == this.inputBlock.getBlock();
		else
			return recipe.inputBlock == inputBlock;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public boolean getFuzzyInput()
	{
		return fuzzyInput;
	}

	public IBlockState getInputBlock()
	{
		return inputBlock;
	}
}
