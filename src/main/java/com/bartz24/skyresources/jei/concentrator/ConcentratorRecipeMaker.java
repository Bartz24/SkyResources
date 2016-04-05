package com.bartz24.skyresources.jei.concentrator;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.jei.rockgrinder.RockGrinderRecipeJEI;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipe;
import com.bartz24.skyresources.technology.concentrator.ConcentratorRecipes;
import com.bartz24.skyresources.technology.rockgrinder.RockGrinderRecipe;

public class ConcentratorRecipeMaker
{
	public static List<ConcentratorRecipeJEI> getRecipes()
	{
		List<ConcentratorRecipe> concentratorRecipes = ConcentratorRecipes
				.getRecipes();

		ArrayList<ConcentratorRecipeJEI> recipes = new ArrayList<ConcentratorRecipeJEI>();

		for (ConcentratorRecipe recipe : concentratorRecipes)
		{
			ConcentratorRecipeJEI addRecipe = new ConcentratorRecipeJEI(
					recipe.getOutput(), recipe.getInputBlock(),
					recipe.getInputStacks(), recipe.getHeatReq());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
