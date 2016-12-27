package com.bartz24.skyresources.jei.rockgrinder;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;

public class RockGrinderRecipeMaker
{
	public static List<RockGrinderRecipeJEI> getRecipes()
	{
		List<ProcessRecipe> grinderRecipes = ProcessRecipeManager.rockGrinderRecipes.getRecipes();

		ArrayList<RockGrinderRecipeJEI> recipes = new ArrayList<RockGrinderRecipeJEI>();

		for (ProcessRecipe recipe : grinderRecipes)
		{
			RockGrinderRecipeJEI addRecipe = new RockGrinderRecipeJEI(recipe.getOutputs().get(0),
					(ItemStack) recipe.getInputs().get(0), true, recipe.getIntParameter());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
