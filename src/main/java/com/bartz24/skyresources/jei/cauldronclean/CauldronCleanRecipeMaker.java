package com.bartz24.skyresources.jei.cauldronclean;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;

public class CauldronCleanRecipeMaker
{
	public static List<CauldronCleanRecipeJEI> getRecipes()
	{
		List<ProcessRecipe> cauldronRecipes = ProcessRecipeManager.cauldronCleanRecipes.getRecipes();
		ArrayList<CauldronCleanRecipeJEI> recipes = new ArrayList<CauldronCleanRecipeJEI>();

		for (ProcessRecipe recipe : cauldronRecipes)
		{
			CauldronCleanRecipeJEI addRecipe = new CauldronCleanRecipeJEI(recipe.getOutputs().get(0),
					recipe.getIntParameter(), (ItemStack) recipe.getInputs().get(0));
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
