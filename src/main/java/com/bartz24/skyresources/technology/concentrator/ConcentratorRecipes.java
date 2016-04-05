package com.bartz24.skyresources.technology.concentrator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bartz24.skyresources.SkyResources;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ConcentratorRecipes
{

	public ConcentratorRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<ConcentratorRecipe> Recipes;

	public static ConcentratorRecipe getRecipe(ItemStack input, IBlockState inputBlock,
			int heatValue)
	{
		ConcentratorRecipe rec = new ConcentratorRecipe(heatValue, input, inputBlock);

		for (ConcentratorRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				return recipe;
			}
		}

		return null;
	}

	public static List<ConcentratorRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addRecipe(IBlockState output, int heatValue,
			ItemStack input, IBlockState inputBlock)
	{

		if (input == null)
		{
			SkyResources.logger
					.error("Need input stacks for recipe. DID NOT ADD RECIPE.");
			return;
		}
		if (inputBlock == null)
		{
			SkyResources.logger
					.error("Need input block for recipe. DID NOT ADD RECIPE.");
			return;
		}
		if (output == null)
		{
			SkyResources.logger.error(
					"Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(new ConcentratorRecipe(output, heatValue, input, inputBlock));
	}
}
