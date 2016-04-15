package com.bartz24.skyresources.alchemy.infusion;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.SkyResources;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipe;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InfusionRecipes
{
	public InfusionRecipes()
	{
		Recipes = new ArrayList();
	}

	private static List<InfusionRecipe> Recipes;

	public static InfusionRecipe getRecipe(ItemStack inputStack,
			Block inputBlock, int inputBlockMeta)
	{
		if (inputBlock == null)
		{
			SkyResources.logger.error("Need a block input for recipe.");
			return null;
		}

		InfusionRecipe rec = new InfusionRecipe(inputStack,
				inputStack == null ? 0 : inputStack.stackSize, inputBlock,
				inputBlockMeta);

		for (InfusionRecipe recipe : Recipes)
		{
			if (rec.isInputRecipeEqualTo(recipe))
			{
				return recipe;
			}
		}

		return null;
	}

	public static List<InfusionRecipe> getRecipes()
	{
		return Recipes;
	}

	public static void addRecipe(ItemStack output, ItemStack inputStack,
			Object inputBlock, int inputBlockMeta, int healthReq)
	{
		if (inputBlock == null)
		{
			SkyResources.logger.error(
					"Need a block input for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		if (!(inputBlock instanceof Block) && !(inputBlock instanceof String))
		{
			SkyResources.logger
					.error("Need a input block as a Block or String for recipe. DID NOT ADD RECIPE FOR "
							+ inputBlock.getClass().getName() + ".");
			return;
		}

		if (output == null)
		{
			SkyResources.logger.error(
					"Need a output for recipe. DID NOT ADD RECIPE FOR NULL.");
			return;
		}

		Recipes.add(new InfusionRecipe(output, inputStack, inputBlock,
				inputBlockMeta, healthReq));
	}

	public static void addRecipe(InfusionRecipe recipe)
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

	public static List<InfusionRecipe> removeRecipe(InfusionRecipe recipe)
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
			List<InfusionRecipe> recipesToRemove = new ArrayList<InfusionRecipe>();
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
		List<InfusionRecipe> recipesToRemove = new ArrayList<InfusionRecipe>();
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
