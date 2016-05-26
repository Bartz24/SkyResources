package com.bartz24.skyresources.jei.waterextractor.extract;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.base.waterextractor.WaterExtractorRecipe;
import com.bartz24.skyresources.base.waterextractor.WaterExtractorRecipes;

public class WaterExtractorExtractRecipeMaker
{
	public static List<WaterExtractorExtractRecipeJEI> getRecipes()
	{
		List<WaterExtractorRecipe> extractorRecipes = WaterExtractorRecipes
				.getRecipes();

		ArrayList<WaterExtractorExtractRecipeJEI> recipes = new ArrayList<WaterExtractorExtractRecipeJEI>();

		for (WaterExtractorRecipe recipe : extractorRecipes)
		{
			if (recipe.isExtractRecipe())
			{
				WaterExtractorExtractRecipeJEI addRecipe = new WaterExtractorExtractRecipeJEI(
						recipe.getFluidAmt(), recipe.getFuzzyInput(),
						recipe.getInputBlock(), recipe.getOutput());
				recipes.add(addRecipe);
			}
		}

		return recipes;
	}
}
