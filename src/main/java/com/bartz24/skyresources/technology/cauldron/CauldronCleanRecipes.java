package com.bartz24.skyresources.technology.cauldron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.technology.combustion.CombustionRecipe;

import net.minecraft.item.ItemStack;

public class CauldronCleanRecipes
{
	public CauldronCleanRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<CauldronCleanRecipe> Recipes;

	public static List<CauldronCleanRecipe> getRecipes(ItemStack input)
	{
		List<CauldronCleanRecipe> recs = new ArrayList<CauldronCleanRecipe>();
		CauldronCleanRecipe rec = new CauldronCleanRecipe(input);

		for (CauldronCleanRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				recs.add(recipe);
				
			}
		}

		return recs;
	}

	public static List<CauldronCleanRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addRecipe(ItemStack output, float chance, ItemStack input)
	{

		if (input == null)
		{
			SkyResources.logger.error("Need input stack for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output == null)
		{
			SkyResources.logger.error("Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(new CauldronCleanRecipe(output, chance, input));
	}

	public static void addRecipe(CauldronCleanRecipe recipe)
	{

		if (recipe.getInput() == null)
		{
			SkyResources.logger.error("Need input stack for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (recipe.getOutput() == null)
		{
			SkyResources.logger.error("Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(recipe);
	}

	public static List<CauldronCleanRecipe> removeRecipe(CauldronCleanRecipe recipe)
	{

		if (recipe.getOutput() == null)
		{
			SkyResources.logger.error("Need a output for recipe. DID NOT REMOVE RECIPE FOR NULL.");
			return null;
		}

		if (recipe.getInput() == null)
		{

			List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
			List<CauldronCleanRecipe> recipesToRemove = new ArrayList<CauldronCleanRecipe>();
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
		List<CauldronCleanRecipe> recipesToRemove = new ArrayList<CauldronCleanRecipe>();
		for (int i = 0; i < Recipes.size(); i++)
		{
			if (Recipes.get(i).isInputRecipeEqualTo(recipe)
					&& Recipes.get(i).getOutput().isItemEqual(recipe.getOutput()))
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
