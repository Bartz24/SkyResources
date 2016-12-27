package com.bartz24.skyresources.jei.freezer;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;

public class FreezerRecipeMaker
{
	public static List<FreezerRecipeJEI> getRecipes()
	{
		List<ProcessRecipe> freezerRecipes = ProcessRecipeManager.freezerRecipes
				.getRecipes();

		ArrayList<FreezerRecipeJEI> recipes = new ArrayList<FreezerRecipeJEI>();

		for (ProcessRecipe recipe : freezerRecipes)
		{
			FreezerRecipeJEI addRecipe = new FreezerRecipeJEI(
					recipe.getOutputs().get(0),(ItemStack) recipe.getInputs().get(0), (int) recipe.getIntParameter());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
