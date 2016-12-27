package com.bartz24.skyresources.jei.waterextractor.insert;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;

public class WaterExtractorInsertRecipeMaker
{
	public static List<WaterExtractorInsertRecipeJEI> getRecipes()
	{
		List<ProcessRecipe> insertRecipes = ProcessRecipeManager.waterExtractorInsertRecipes.getRecipes();

		ArrayList<WaterExtractorInsertRecipeJEI> recipes = new ArrayList<WaterExtractorInsertRecipeJEI>();

		for (ProcessRecipe recipe : insertRecipes)
		{
			WaterExtractorInsertRecipeJEI addRecipe = new WaterExtractorInsertRecipeJEI(recipe.getOutputs().get(0),
					true, (ItemStack) recipe.getInputs().get(0), recipe.getFluidInputs().get(0).amount);
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
