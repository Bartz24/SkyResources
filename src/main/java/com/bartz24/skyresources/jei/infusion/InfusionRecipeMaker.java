package com.bartz24.skyresources.jei.infusion;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.alchemy.infusion.InfusionRecipe;
import com.bartz24.skyresources.alchemy.infusion.InfusionRecipes;

import net.minecraft.item.ItemStack;

public class InfusionRecipeMaker
{
	public static List<InfusionRecipeJEI> getRecipes()
	{
		List<InfusionRecipe> infusionRecipes = InfusionRecipes.getRecipes();

		ArrayList<InfusionRecipeJEI> recipes = new ArrayList<InfusionRecipeJEI>();

		for (InfusionRecipe recipe : infusionRecipes)
		{
			ItemStack input = recipe.getInputStack();
			ItemStack inputBlock = recipe.getInputBlock();
			ItemStack output = recipe.getOutput();

			InfusionRecipeJEI addRecipe = new InfusionRecipeJEI(input,
					inputBlock, output, recipe.getHealthReq());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
