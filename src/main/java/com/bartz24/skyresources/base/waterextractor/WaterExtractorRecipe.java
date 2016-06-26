package com.bartz24.skyresources.base.waterextractor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class WaterExtractorRecipe
{
	IBlockState inputBlock;
	IBlockState outputBlock;

	boolean extract;
	boolean fuzzyInput;

	int fluid;

	/**
	 * 
	 * Extracting Recipe
	 * 
	 * @param outAmt
	 * @param fuzzy
	 * @param inputState
	 * @param outputState
	 */
	public WaterExtractorRecipe(int outAmt, boolean fuzzy,
			IBlockState inputState, IBlockState outputState)
	{
		inputBlock = inputState;
		outputBlock = outputState;
		fuzzyInput = fuzzy;
		fluid = outAmt;
		extract = true;
	}

	/**
	 * 
	 * Insert Recipe
	 * 
	 * @param outputState
	 * @param fuzzy
	 * @param inputState
	 * @param amtReq
	 */
	public WaterExtractorRecipe(IBlockState outputState, boolean fuzzy,
			IBlockState inputState, int amtReq)
	{
		inputBlock = inputState;
		outputBlock = outputState;
		fuzzyInput = fuzzy;
		fluid = amtReq;
		extract = false;
	}

	public WaterExtractorRecipe(IBlockState inputState, boolean extracting,
			int fluidAmt)
	{
		inputBlock = inputState;
		extract = extracting;
		fluid = fluidAmt;
	}

	public boolean isInputRecipeEqualTo(WaterExtractorRecipe recipe)
	{
		return validRecipeType(recipe) && stacksAreValid(recipe)
				&& validFluidAmt(recipe);
	}

	boolean validRecipeType(WaterExtractorRecipe recipe)
	{
		return extract == recipe.extract;
	}

	boolean stacksAreValid(WaterExtractorRecipe recipe)
	{
		if (inputBlock == null)
			return false;

		if (recipe.fuzzyInput)
		{
			return recipe.inputBlock.getBlock() == this.inputBlock.getBlock();
			
		} else
			return recipe.inputBlock == inputBlock;
	}

	boolean validFluidAmt(WaterExtractorRecipe recipe)
	{
		return extract ? true : getFluidAmt() >= recipe.getFluidAmt();
	}

	public IBlockState getOutput()
	{
		return outputBlock;
	}

	public int getFluidAmt()
	{
		return fluid;
	}

	public boolean getFuzzyInput()
	{
		return fuzzyInput;
	}

	public IBlockState getInputBlock()
	{
		return inputBlock;
	}

	public boolean isExtractRecipe()
	{
		return extract;
	}
}
