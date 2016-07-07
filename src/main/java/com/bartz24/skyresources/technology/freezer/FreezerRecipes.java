package com.bartz24.skyresources.technology.freezer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.technology.combustion.CombustionRecipe;

import net.minecraft.item.ItemStack;

public class FreezerRecipes
{
	public FreezerRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<FreezerRecipe> Recipes;

	public static FreezerRecipe getRecipe(ItemStack input)
	{
		FreezerRecipe rec = new FreezerRecipe(input);

		for (FreezerRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				return recipe;
			}
		}

		return null;
	}

	public static List<FreezerRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addRecipe(ItemStack output, int timeValue,
			ItemStack input)
	{

		if (input == null)
		{
			SkyResources.logger
					.error("Need input stack for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output == null)
		{
			SkyResources.logger.error(
					"Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(new FreezerRecipe(output, timeValue, input));
	}

	public static void addRecipe(FreezerRecipe recipe)
	{

		if (recipe.getInput() == null)
		{
			SkyResources.logger
					.error("Need input stack for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (recipe.getOutput() == null)
		{
			SkyResources.logger.error(
					"Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(recipe);
	}

	public static List<FreezerRecipe> removeRecipe(FreezerRecipe recipe)
	{

		if (recipe.getOutput() == null)
		{
			SkyResources.logger.error(
					"Need a output for recipe. DID NOT REMOVE RECIPE FOR NULL.");
			return null;
		}

		if (recipe.getInput() == null)
		{

			List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
			List<FreezerRecipe> recipesToRemove = new ArrayList<FreezerRecipe>();
			for (int i = 0; i < Recipes.size(); i++)
			{
				if (Recipes.get(i).getOutput().isItemEqual(recipe.getOutput()))
					recipesToRemoveAt.add(i);
			}
			for (int i = recipesToRemoveAt.size() - 1; i >= 0; i--)
			{
				recipesToRemove.add(Recipes.get(recipesToRemoveAt.get(i)));
				Recipes.remove((int) recipesToRemoveAt.get(i));
			}
			return recipesToRemove;
		}

		List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
		List<FreezerRecipe> recipesToRemove = new ArrayList<FreezerRecipe>();
		for (int i = 0; i < Recipes.size(); i++)
		{
			if (Recipes.get(i).isInputRecipeEqualTo(recipe) && Recipes.get(i)
					.getOutput().isItemEqual(recipe.getOutput()))
				recipesToRemoveAt.add(i);
		}
		for (int i = recipesToRemoveAt.size() - 1; i >= 0; i--)
		{
			recipesToRemove.add(Recipes.get(recipesToRemoveAt.get(i)));
			Recipes.remove((int) recipesToRemoveAt.get(i));
		}
		return recipesToRemove;
	}
}
