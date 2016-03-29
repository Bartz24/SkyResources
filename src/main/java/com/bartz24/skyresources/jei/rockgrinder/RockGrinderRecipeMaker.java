package com.bartz24.skyresources.jei.rockgrinder;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.technology.combustion.CombustionRecipe;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipe;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipes;

public class RockGrinderRecipeMaker
{
	public static List<RockGrinderRecipeJEI> getRecipes()
	{
		List<RockGrinderRecipe> grinderRecipes = RockGrinderRecipes.getRecipes();

		ArrayList<RockGrinderRecipeJEI> recipes = new ArrayList<RockGrinderRecipeJEI>();

		for (RockGrinderRecipe recipe : grinderRecipes)
		{
			RockGrinderRecipeJEI addRecipe = new RockGrinderRecipeJEI(recipe.getOutput(), recipe.getInputBlock(), recipe.getFuzzyInput());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
