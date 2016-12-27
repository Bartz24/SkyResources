package com.bartz24.skyresources.jei.crucible;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;

public class CrucibleRecipeMaker
{
	public static List<CrucibleRecipeJEI> getRecipes()
	{
		List<ProcessRecipe> crucibleRecipes = ProcessRecipeManager.crucibleRecipes.getRecipes();

		ArrayList<CrucibleRecipeJEI> recipes = new ArrayList<CrucibleRecipeJEI>();

		for (ProcessRecipe recipe : crucibleRecipes)
		{
			CrucibleRecipeJEI addRecipe = new CrucibleRecipeJEI((ItemStack) recipe.getInputs().get(0),
					recipe.getFluidOutputs().get(0));
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
