package com.bartz24.skyresources.plugin.ctweaker;

import java.util.Arrays;
import java.util.Collections;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import minetweaker.api.item.IItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.waterextractor.insert")
public class MTWaterExtractorInsertRecipe extends MTRecipeBase
{
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, int waterIn)
	{
		addRecipe(
				new ProcessRecipe(
						Arrays.asList(CraftTweakerPlugin.toStack(output)),
						Arrays.asList(CraftTweakerPlugin.toStack(input),
								new FluidStack(FluidRegistry.WATER, waterIn)), 0, "waterextractor-insert"),
				ProcessRecipeManager.waterExtractorInsertRecipes);
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		removeRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)), Collections.emptyList(), 0,
				"waterextractor-insert"), ProcessRecipeManager.waterExtractorInsertRecipes);
	}

}
