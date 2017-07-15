package com.bartz24.skyresources.plugin.ctweaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemBlock;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.infusion")
public class MTInfusionRecipe extends MTRecipeBase
{

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack inputStack, IItemStack inputBlock, int health)
	{
		if (!(CraftTweakerPlugin.toStack(inputBlock).getItem() instanceof ItemBlock))
		{
			CraftTweakerAPI.logError("Input block is not block. Did not add recipe.");
			return;
		}
		List<Object> inputs = new ArrayList<>();
		inputs.addAll(Arrays.asList(CraftTweakerPlugin.toStack(inputStack), CraftTweakerPlugin.toStack(inputBlock)));
		addRecipe(
				new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)), inputs, health, "infusion"),
				ProcessRecipeManager.infusionRecipes);
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		removeRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)), Collections.emptyList(), 0,
				"infusion"), ProcessRecipeManager.infusionRecipes);
	}

}
