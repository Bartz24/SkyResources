package com.bartz24.skyresources.jei.waterextractor.extract;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;

public class WaterExtractorExtractRecipeMaker
{
	public static List<WaterExtractorExtractRecipeJEI> getRecipes()
	{
		List<ProcessRecipe> extractorRecipes = ProcessRecipeManager.waterExtractorExtractRecipes.getRecipes();

		ArrayList<WaterExtractorExtractRecipeJEI> recipes = new ArrayList<WaterExtractorExtractRecipeJEI>();

		for (ProcessRecipe recipe : extractorRecipes)
		{
			WaterExtractorExtractRecipeJEI addRecipe = new WaterExtractorExtractRecipeJEI(
					recipe.getFluidOutputs().get(0).amount, true, (ItemStack) recipe.getInputs().get(0),
					recipe.getOutputs().get(0));
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
