package com.bartz24.skyresources.jei.infusion;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InfusionRecipeMaker
{
	public static List<InfusionRecipeJEI> getRecipes()
	{
		List<ProcessRecipe> infusionRecipes = ProcessRecipeManager.infusionRecipes.getRecipes();

		ArrayList<InfusionRecipeJEI> recipes = new ArrayList<InfusionRecipeJEI>();

		for (ProcessRecipe recipe : infusionRecipes)
		{
			ItemStack input = (ItemStack) recipe.getInputs().get(0);

			Object input2 = recipe.getInputs().get(1);

			ItemStack inputBlock = (ItemStack) (input2 instanceof ItemStack ? input2
					: OreDictionary.getOres(input2.toString()).get(0));
			ItemStack output = recipe.getOutputs().get(0);

			InfusionRecipeJEI addRecipe = new InfusionRecipeJEI(input, inputBlock, output,
					(int) recipe.getIntParameter());
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
