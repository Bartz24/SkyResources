package com.bartz24.skyresources.plugin.ctweaker;

import java.util.Arrays;
import java.util.Collections;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.knife")
public class MTKnifeRecipe extends MTRecipeBase
{

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input)
	{
		addRecipe(
				new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)),
						Arrays.asList(CraftTweakerPlugin.toStack(input)), 1, "knife"),
				ProcessRecipeManager.knifeRecipes);
	}
	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		removeRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)), Collections.emptyList(), 0,
				"knife"), ProcessRecipeManager.knifeRecipes);
	}

}
