package com.bartz24.skyresources.alchemy.crucible;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.SkyResources;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CrucibleRecipes
{
	public CrucibleRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<CrucibleRecipe> Recipes;

	public static CrucibleRecipe getRecipe(ItemStack input)
	{
		CrucibleRecipe rec = new CrucibleRecipe(input);

		for (CrucibleRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				return recipe;
			}
		}

		return null;
	}

	public static List<CrucibleRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addRecipe(FluidStack output, ItemStack input)
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

		Recipes.add(new CrucibleRecipe(output, input));
	}

	public static void addRecipe(CrucibleRecipe recipe)
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

	public static List<CrucibleRecipe> removeRecipe(CrucibleRecipe recipe)
	{

		if (recipe.getOutput() == null)
		{
			SkyResources.logger.error("Need a output for recipe. DID NOT REMOVE RECIPE FOR NULL.");
			return null;
		}

		if (recipe.getInput() == null)
		{

			List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
			List<CrucibleRecipe> recipesToRemove = new ArrayList<CrucibleRecipe>();
			for (int i = 0; i < Recipes.size(); i++)
			{
				if (Recipes.get(i).getOutput().isFluidEqual(recipe.getOutput()))
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
		List<CrucibleRecipe> recipesToRemove = new ArrayList<CrucibleRecipe>();
		for (int i = 0; i < Recipes.size(); i++)
		{
			if (Recipes.get(i).isInputRecipeEqualTo(recipe)
					&& Recipes.get(i).getOutput().isFluidEqual(recipe.getOutput()))
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
