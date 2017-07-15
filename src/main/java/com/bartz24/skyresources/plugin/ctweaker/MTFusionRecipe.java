package com.bartz24.skyresources.plugin.ctweaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.fusion")
public class MTFusionRecipe extends MTRecipeBase
{

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack[] input, float catalystUsedPerCraft)
	{
		List<Object> inputs = new ArrayList<>();
		inputs.addAll(Arrays.asList(CraftTweakerPlugin.toStacks(input)));
		addRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)), inputs,
				catalystUsedPerCraft * 0.01f, "fusion"), ProcessRecipeManager.fusionRecipes);
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		removeRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)), Collections.emptyList(), 0,
				"fusion"), ProcessRecipeManager.fusionRecipes);
	}

}
