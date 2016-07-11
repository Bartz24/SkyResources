package com.bartz24.skyresources.jei.waterextractor.insert;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.base.waterextractor.WaterExtractorRecipe;
import com.bartz24.skyresources.base.waterextractor.WaterExtractorRecipes;

public class WaterExtractorInsertRecipeMaker
{
	public static List<WaterExtractorInsertRecipeJEI> getRecipes()
	{
		List<WaterExtractorRecipe> extractorRecipes = WaterExtractorRecipes
				.getRecipes();

		ArrayList<WaterExtractorInsertRecipeJEI> recipes = new ArrayList<WaterExtractorInsertRecipeJEI>();

		for (WaterExtractorRecipe recipe : extractorRecipes)
		{
			if (!recipe.isExtractRecipe())
			{
				WaterExtractorInsertRecipeJEI addRecipe = new WaterExtractorInsertRecipeJEI(
						recipe.getOutput(), recipe.getFuzzyInput(),
						recipe.getInputBlock(), recipe.getFluidAmt());
				recipes.add(addRecipe);
			}
		}

		return recipes;
	}
}
