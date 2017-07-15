package com.bartz24.skyresources.plugin.ctweaker;

import java.util.Arrays;
import java.util.Collections;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.crucible")
public class MTCrucibleRecipe extends MTRecipeBase
{
	@ZenMethod
	public static void addRecipe(ILiquidStack output, IItemStack input)
	{
		addRecipe(
				new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toFluidStack(output)),
						Arrays.asList(CraftTweakerPlugin.toStack(input)), 0, "crucible"),
				ProcessRecipeManager.crucibleRecipes);
	}

	@ZenMethod
	public static void removeRecipe(ILiquidStack output)
	{
		removeRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toFluidStack(output)), Collections.emptyList(),
				0, "crucible"), ProcessRecipeManager.crucibleRecipes);
	}

}
