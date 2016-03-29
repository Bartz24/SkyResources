package com.bartz24.skymod.jei.combustion;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skymod.technology.combustion.CombustionRecipe;
import com.bartz24.skymod.technology.combustion.CombustionRecipes;

public class CombustionRecipeMaker
{
	public static List<CombustionRecipeJEI> getRecipes()
	{
		List<CombustionRecipe> combustionRecipes = CombustionRecipes.getRecipes();

		ArrayList<CombustionRecipeJEI> recipes = new ArrayList<CombustionRecipeJEI>();

		for (CombustionRecipe recipe : combustionRecipes)
		{
			CombustionRecipeJEI addRecipe = new CombustionRecipeJEI(recipe.getOutput(), recipe.getInputStacks(),
					recipe.getHeatReq());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
