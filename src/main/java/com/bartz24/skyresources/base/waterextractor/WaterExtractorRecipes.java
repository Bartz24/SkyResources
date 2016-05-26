package com.bartz24.skyresources.base.waterextractor;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipe;

import net.minecraft.block.state.IBlockState;

public class WaterExtractorRecipes
{

	public WaterExtractorRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<WaterExtractorRecipe> Recipes;

	public static WaterExtractorRecipe getExtractRecipe(IBlockState block)
	{
		WaterExtractorRecipe rec = new WaterExtractorRecipe(block, true,
				0);

		for (WaterExtractorRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				return recipe;
			}
		}
		return null;
	}
	
	public static WaterExtractorRecipe getInsertRecipe(IBlockState block, int amt)
	{
		WaterExtractorRecipe rec = new WaterExtractorRecipe(block, false,
				amt);

		for (WaterExtractorRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				return recipe;
			}
		}
		return null;
	}

	public static List<WaterExtractorRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addExtractRecipe(int output, boolean fuzzyInput,
			IBlockState block, IBlockState blockReplace)
	{

		if (block == null)
		{
			SkyResources.logger
					.error("Need input block for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output <= 0)
		{
			SkyResources.logger.error("Need a output for recipe.");
			return;
		}

		Recipes.add(new WaterExtractorRecipe(output, fuzzyInput, block,
				blockReplace));
	}

	public static void addInsertRecipe(IBlockState outBlock, boolean fuzzyInput,
			IBlockState blockIn, int amtReq)
	{

		if (blockIn == null)
		{
			SkyResources.logger
					.error("Need input block for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (outBlock == null)
		{
			SkyResources.logger.error(
					"Need output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(new WaterExtractorRecipe(outBlock, fuzzyInput, blockIn,
				amtReq));
	}

	public static void addRecipe(WaterExtractorRecipe recipe)
	{

		if (recipe.getInputBlock() == null)
		{
			SkyResources.logger
					.error("Need input stacks for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (recipe.isExtractRecipe() && recipe.getFluidAmt() <= 0)
		{
			SkyResources.logger.error("Need a output greater than 0.");
			return;
		}

		if (!recipe.isExtractRecipe() && recipe.getOutput() == null)
		{
			SkyResources.logger.error(
					"Need output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(recipe);
	}

	public static List<WaterExtractorRecipe> removeRecipe(
			WaterExtractorRecipe recipe)
	{

		if (recipe.isExtractRecipe() && recipe.getFluidAmt() <= 0)
		{
			SkyResources.logger.error("Need a output greater than 0.");
			return null;
		}

		if (!recipe.isExtractRecipe() && recipe.getOutput() == null)
		{
			SkyResources.logger.error(
					"Need output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return null;
		}

		if (recipe.getInputBlock() == null)
		{
			SkyResources.logger.error("Need an input.");
			return null;
		}

		if (recipe.isExtractRecipe())
		{
			List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
			List<WaterExtractorRecipe> recipesToRemove = new ArrayList<WaterExtractorRecipe>();
			for (int i = 0; i < Recipes.size(); i++)
			{
				if (Recipes.get(i).isInputRecipeEqualTo(recipe))
					recipesToRemoveAt.add(i);
			}
			for (int i = recipesToRemoveAt.size() - 1; i >= 0; i--)
			{
				recipesToRemove.add(Recipes.get(recipesToRemoveAt.get(i)));
				Recipes.remove((int) recipesToRemoveAt.get(i));
			}
			return recipesToRemove;
		} else
		{

			if (recipe.getInputBlock() == null)
			{
				List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
				List<WaterExtractorRecipe> recipesToRemove = new ArrayList<WaterExtractorRecipe>();
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
			List<WaterExtractorRecipe> recipesToRemove = new ArrayList<WaterExtractorRecipe>();
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
}
