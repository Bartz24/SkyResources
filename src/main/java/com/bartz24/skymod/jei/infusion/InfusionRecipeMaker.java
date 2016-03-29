package com.bartz24.skymod.jei.infusion;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.bartz24.skymod.alchemy.infusion.InfusionRecipe;
import com.bartz24.skymod.alchemy.infusion.InfusionRecipes;

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
