package com.bartz24.skyresources.jei.freezer;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.technology.freezer.FreezerRecipe;
import com.bartz24.skyresources.technology.freezer.FreezerRecipes;

public class FreezerRecipeMaker
{
	public static List<FreezerRecipeJEI> getRecipes()
	{
		List<FreezerRecipe> grinderRecipes = FreezerRecipes
				.getRecipes();

		ArrayList<FreezerRecipeJEI> recipes = new ArrayList<FreezerRecipeJEI>();

		for (FreezerRecipe recipe : grinderRecipes)
		{
			FreezerRecipeJEI addRecipe = new FreezerRecipeJEI(
					recipe.getOutput(), recipe.getInput(), recipe.getTimeReq());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
