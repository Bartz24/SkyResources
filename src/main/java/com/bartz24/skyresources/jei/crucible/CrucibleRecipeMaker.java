package com.bartz24.skyresources.jei.crucible;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.alchemy.crucible.CrucibleRecipe;
import com.bartz24.skyresources.alchemy.crucible.CrucibleRecipes;
import com.bartz24.skyresources.jei.freezer.FreezerRecipeJEI;

public class CrucibleRecipeMaker
{
	public static List<CrucibleRecipeJEI> getRecipes()
	{
		List<CrucibleRecipe> grinderRecipes = CrucibleRecipes
				.getRecipes();

		ArrayList<CrucibleRecipeJEI> recipes = new ArrayList<CrucibleRecipeJEI>();

		for (CrucibleRecipe recipe : grinderRecipes)
		{
			CrucibleRecipeJEI addRecipe = new CrucibleRecipeJEI(
					recipe.getInput(), recipe.getOutput());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
