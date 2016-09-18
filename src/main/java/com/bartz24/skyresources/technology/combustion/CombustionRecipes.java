package com.bartz24.skyresources.technology.combustion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bartz24.skyresources.RandomHelper;
import com.bartz24.skyresources.SkyResources;

import net.minecraft.item.ItemStack;

public class CombustionRecipes
{

	public CombustionRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<CombustionRecipe> Recipes;

	public static CombustionRecipe getRecipe(List<ItemStack> input, int heatValue)
	{
		int checks = 0;
		boolean merged = true;
		while (merged && checks < 50)
		{
			merged = false;
			for (int i = 0; i < input.size(); i++)
			{
				ItemStack stack1 = input.get(i);
				for (int i2 = i + 1; i2 < input.size(); i2++)
				{
					ItemStack stack2 = input.get(i2);

					int moveAmt = RandomHelper.mergeStacks(stack2, stack1, false);
					if (moveAmt > 0)
					{
						stack1.stackSize += moveAmt;
						stack2.stackSize -= moveAmt;
						if (stack2.stackSize <= 0)
							stack2 = null;
						merged = true;
						break;
					}
				}
				if (merged)
					break;
			}

			for (int i = input.size() - 1; i >= 0; i--)
			{
				ItemStack stack = input.get(i);
				if (stack == null)
					input.remove(i);
			}

			checks++;
		}

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
	public static CombustionRecipe getMultiRecipe(List<ItemStack> input, int heatValue)
	{
		int checks = 0;
		boolean merged = true;
		while (merged && checks < 50)
		{
			merged = false;
			for (int i = 0; i < input.size(); i++)
			{
				ItemStack stack1 = input.get(i);
				for (int i2 = i + 1; i2 < input.size(); i2++)
				{
					ItemStack stack2 = input.get(i2);

					int moveAmt = RandomHelper.mergeStacks(stack2, stack1, false);
					if (moveAmt > 0)
					{
						stack1.stackSize += moveAmt;
						stack2.stackSize -= moveAmt;
						if (stack2.stackSize <= 0)
							stack2 = null;
						merged = true;
						break;
					}
				}
				if (merged)
					break;
			}

			for (int i = input.size() - 1; i >= 0; i--)
			{
				ItemStack stack = input.get(i);
				if (stack == null)
					input.remove(i);
			}

			checks++;
		}

		CombustionRecipe rec = new CombustionRecipe(heatValue, input);

		for (CombustionRecipe recipe : Recipes)
		{
			if (rec.isInputMultiRecipeEqualTo(recipe))
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

	public static void addRecipe(ItemStack output, int heatValue, List<ItemStack> input)
	{

		if (input == null || input.size() == 0)
		{
			SkyResources.logger.error("Need input stacks for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (output == null)
		{
			SkyResources.logger.error("Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(new CombustionRecipe(output, heatValue, input));
	}

	public static void addRecipe(CombustionRecipe recipe)
	{

		if (recipe.getInputStacks() == null || recipe.getInputStacks().size() == 0)
		{
			SkyResources.logger.error("Need input stacks for recipe. DID NOT ADD RECIPE.");
			return;
		}

		if (recipe.getOutput() == null)
		{
			SkyResources.logger.error("Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(recipe);
	}

	public static List<CombustionRecipe> removeRecipe(CombustionRecipe recipe)
	{

		if (recipe.getOutput() == null)
		{
			SkyResources.logger.error("Need a output for recipe. DID NOT REMOVE RECIPE FOR NULL.");
			return null;
		}

		if (recipe.getInputStacks() == null || recipe.getInputStacks().size() == 0)
		{

			List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
			List<CombustionRecipe> recipesToRemove = new ArrayList<CombustionRecipe>();
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
		List<CombustionRecipe> recipesToRemove = new ArrayList<CombustionRecipe>();
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

	public static void addRecipe(ItemStack output, int heatValue, ItemStack... input)
	{
		addRecipe(output, heatValue, Arrays.asList(input));
	}
}
