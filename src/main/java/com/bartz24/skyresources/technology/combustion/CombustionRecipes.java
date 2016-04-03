package com.bartz24.skyresources.technology.combustion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bartz24.skyresources.SkyResources;

import net.minecraft.item.ItemStack;

public class CombustionRecipes
{

	public CombustionRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<CombustionRecipe> Recipes;

	public static CombustionRecipe getRecipe(List<ItemStack> input,
			int heatValue)
	{
		CombustionRecipe rec = new CombustionRecipe(heatValue, input);

		for (CombustionRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				return recipe;
			}
		}

		return null;
	}

	public static List<CombustionRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addRecipe(ItemStack output, int heatValue,
			List<ItemStack> input)
	{

		if (input == null || input.size() == 0)
		{
			SkyResources.logger
					.error("Need input stacks for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output == null)
		{
			SkyResources.logger.error(
					"Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(new CombustionRecipe(output, heatValue, input));
	}

	public static void addRecipe(ItemStack output, int heatValue,
			ItemStack... input)
	{
		addRecipe(output, heatValue, Arrays.asList(input));
	}
}
