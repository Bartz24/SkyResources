package com.bartz24.skyresources.plugin.ctweaker;

import java.util.Arrays;
import java.util.Collections;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.waterextractor.insert")
public class MTWaterExtractorInsertRecipe extends MTRecipeBase
{
	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input, int waterIn)
	{
		addRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)),
				Arrays.asList(CraftTweakerPlugin.toObject(input), new FluidStack(FluidRegistry.WATER, waterIn)), 0,
				"waterextractor-insert"), ProcessRecipeManager.waterExtractorInsertRecipes);
	}

	@ZenMethod
	public static void removeRecipe(IIngredient output)
	{
		removeRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toObject(output)), Collections.emptyList(), 0,
				"waterextractor-insert"), ProcessRecipeManager.waterExtractorInsertRecipes);
	}

}
