package com.bartz24.skyresources.technology.rockgrinder;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class RockGrinderRecipes
{

	public RockGrinderRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<RockGrinderRecipe> Recipes;

	public static List<RockGrinderRecipe> getRecipes(IBlockState block)
	{
		RockGrinderRecipe rec = new RockGrinderRecipe(block);

		List<RockGrinderRecipe> recs = new ArrayList<RockGrinderRecipe>();

		for (RockGrinderRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				recs.add(recipe);
			}
		}

		if (recs.size() == 0)
			return null;
		else
			return recs;
	}

	public static List<RockGrinderRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addRecipe(ItemStack output, boolean fuzzyInput,
			IBlockState block)
	{

		if (block == null)
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

		Recipes.add(new RockGrinderRecipe(output, fuzzyInput, block));
	}

	public static void addRecipe(ItemStack output, boolean fuzzyInput,
			IBlockState block, float chance)
	{

		if (block == null)
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

		Recipes.add(new RockGrinderRecipe(output, chance, fuzzyInput, block));
	}

	public static void addRecipe(RockGrinderRecipe recipe)
	{

		if (recipe.getInputBlock() == null)
		{
			SkyResources.logger
					.error("Need input stacks for recipe. DID NOT ADD RECIPE.");
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

	public static List<RockGrinderRecipe> removeRecipe(RockGrinderRecipe recipe)
	{

		if (recipe.getOutput() == null)
		{
			SkyResources.logger.error(
					"Need a output for recipe. DID NOT REMOVE RECIPE FOR NULL.");
			return null;
		}

		if (recipe.getInputBlock() == null)
		{
			List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
			List<RockGrinderRecipe> recipesToRemove = new ArrayList<RockGrinderRecipe>();
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
		List<RockGrinderRecipe> recipesToRemove = new ArrayList<RockGrinderRecipe>();
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
