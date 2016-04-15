package com.bartz24.skyresources.technology.concentrator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.technology.combustion.CombustionRecipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ConcentratorRecipes
{

	public ConcentratorRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<ConcentratorRecipe> Recipes;

	public static ConcentratorRecipe getRecipe(ItemStack input,
			IBlockState inputBlock, int heatValue)
	{
		ConcentratorRecipe rec = new ConcentratorRecipe(heatValue, input,
				inputBlock);

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

		Recipes.add(
				new ConcentratorRecipe(output, heatValue, input, inputBlock));
	}

	public static void addRecipe(ConcentratorRecipe recipe)
	{

		if (recipe.getInputStacks() == null || recipe.getInputBlock() == null)
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

	public static List<ConcentratorRecipe> removeRecipe(
			ConcentratorRecipe recipe)
	{

		if (recipe.getOutput() == null)
		{
			SkyResources.logger.error(
					"Need a output for recipe. DID NOT REMOVE RECIPE FOR NULL.");
			return null;
		}

		if (recipe.getInputStacks() == null || recipe.getInputBlock() == null)
		{

			List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
			List<ConcentratorRecipe> recipesToRemove = new ArrayList<ConcentratorRecipe>();
			for (int i = 0; i < Recipes.size(); i++)
			{
				if (Recipes.get(i).getOutput() == recipe.getOutput())
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
		List<ConcentratorRecipe> recipesToRemove = new ArrayList<ConcentratorRecipe>();
		for (int i = 0; i < Recipes.size(); i++)
		{
			if (Recipes.get(i).isInputRecipeEqualTo(recipe)
					&& Recipes.get(i).getOutput() == recipe.getOutput())
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
