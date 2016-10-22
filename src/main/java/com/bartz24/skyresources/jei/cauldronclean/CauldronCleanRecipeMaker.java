package com.bartz24.skyresources.jei.cauldronclean;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.technology.cauldron.CauldronCleanRecipe;
import com.bartz24.skyresources.technology.cauldron.CauldronCleanRecipes;

public class CauldronCleanRecipeMaker
{
	public static List<CauldronCleanRecipeJEI> getRecipes()
	{
		List<CauldronCleanRecipe> cauldronRecipes = CauldronCleanRecipes
				.getRecipes();
		ArrayList<CauldronCleanRecipeJEI> recipes = new ArrayList<CauldronCleanRecipeJEI>();

		for (CauldronCleanRecipe recipe : cauldronRecipes)
		{
			CauldronCleanRecipeJEI addRecipe = new CauldronCleanRecipeJEI(
					recipe.getOutput(), recipe.getDropChance(), recipe.getInput());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
