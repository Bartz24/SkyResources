package com.bartz24.skyresources.jei.combustion;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;

public class CombustionRecipeMaker
{
	public static List<CombustionRecipeJEI> getRecipes()
	{
		List<ProcessRecipe> combustionRecipes = ProcessRecipeManager.combustionRecipes.getRecipes();

		ArrayList<CombustionRecipeJEI> recipes = new ArrayList<CombustionRecipeJEI>();

		for (ProcessRecipe recipe : combustionRecipes)
		{
			List<ItemStack> inputs = new ArrayList();
			for (Object o : recipe.getInputs())
			{
				if (o instanceof ItemStack)
					inputs.add((ItemStack) o);
			}
			CombustionRecipeJEI addRecipe = new CombustionRecipeJEI(recipe.getOutputs().get(0), inputs,
					(int) recipe.getIntParameter());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
