package com.bartz24.skyresources.plugin.ctweaker;

import java.util.Arrays;
import java.util.Collections;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.cauldronclean")
public class MTCauldronCleaningRecipe extends MTRecipeBase
{

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input)
	{
		addRecipe(
				new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)),
						Arrays.asList(CraftTweakerPlugin.toStack(input)), 1, "cauldronclean"),
				ProcessRecipeManager.cauldronCleanRecipes);
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, float chance)
	{
		addRecipe(
				new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)),
						Arrays.asList(CraftTweakerPlugin.toStack(input)), chance, "cauldronclean"),
				ProcessRecipeManager.cauldronCleanRecipes);
	}
	
	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		removeRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)), Collections.emptyList(), 0, "cauldronclean"),
				ProcessRecipeManager.cauldronCleanRecipes);
	}

}
