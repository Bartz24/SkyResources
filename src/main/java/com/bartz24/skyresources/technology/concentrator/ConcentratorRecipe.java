package com.bartz24.skyresources.technology.concentrator;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ConcentratorRecipe
{
	private IBlockState output;
	private IBlockState inputBlock;
	private ItemStack inputStack;
	private int heatRequired;

	public ConcentratorRecipe(IBlockState out, int heatNeeded, ItemStack input,
			IBlockState inBlock)
	{
		this.inputStack = input;
		this.inputBlock = inBlock;
		this.output = out;
		this.heatRequired = heatNeeded;
	}

	public ConcentratorRecipe(int currentHeat, ItemStack input,
			IBlockState inBlock)
	{
		this.inputStack = input;
		this.inputBlock = inBlock;
		this.heatRequired = currentHeat;
	}

	public boolean isInputRecipeEqualTo(ConcentratorRecipe recipe)
	{
		return stacksAreValid(recipe) && blockIsValid(recipe) && heatHighEnough(recipe);
	}

	boolean blockIsValid(ConcentratorRecipe recipe)
	{
		if (inputBlock == null || inputBlock == Blocks.air.getDefaultState())
			return false;

		return recipe.inputBlock == inputBlock;
	}

	boolean stacksAreValid(ConcentratorRecipe recipe)
	{
		if (inputStack == null)
			return false;
		boolean valid = false;
		if (inputStack.isItemEqual(recipe.inputStack)
				&& inputStack.stackSize < recipe.inputStack.stackSize)
			return false;
		else if (inputStack.isItemEqual(recipe.inputStack)
				&& inputStack.stackSize >= recipe.inputStack.stackSize)
			valid = true;
		if (!valid)
			return false;

		return true;
	}

	boolean heatHighEnough(ConcentratorRecipe recipe)
	{
		return heatRequired >= recipe.heatRequired;
	}

	public IBlockState getOutput()
	{
		return output;
	}

	public ItemStack getInputStacks()
	{
		return inputStack;
	}

	public IBlockState getInputBlock()
	{
		return inputBlock;
	}

	public int getHeatReq()
	{
		return heatRequired;
	}
}
