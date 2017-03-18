package com.bartz24.skyresources.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ProcessRecipeJEI extends BlankRecipeWrapper
{
	ProcessRecipe rec;

	public ProcessRecipeJEI(ProcessRecipe recipe)
	{
		rec = recipe;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		ProcessRecipeManager.getManagerFromType(rec.getRecipeType()).drawJEIInfo(rec, minecraft, recipeWidth,
				recipeHeight, mouseX, mouseY);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return null;
	}

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		List<List<ItemStack>> input = new ArrayList<>();
		for (Object o : rec.getInputs())
		{
			if (o instanceof String)
				input.add(OreDictionary.getOres(o.toString()));
			else if (o instanceof ItemStack)
				input.add(Collections.singletonList((ItemStack) o));
		}
		ingredients.setInputLists(ItemStack.class, input);
		ingredients.setOutputs(ItemStack.class, rec.getOutputs());
		ingredients.setInputs(FluidStack.class, rec.getFluidInputs());
		ingredients.setOutputs(FluidStack.class, rec.getFluidOutputs());
	}
}
