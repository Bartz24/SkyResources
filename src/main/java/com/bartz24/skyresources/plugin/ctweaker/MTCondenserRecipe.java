package com.bartz24.skyresources.plugin.ctweaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bartz24.skyresources.recipe.ProcessRecipe;
import com.bartz24.skyresources.recipe.ProcessRecipeManager;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.skyresources.condenser")
public class MTCondenserRecipe extends MTRecipeBase
{
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack catalyst, ILiquidStack inputFluid)
	{
		FluidStack stack = CraftTweakerPlugin.toFluidStack(inputFluid);
		stack.amount = 1000;
		addRecipe(
				new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)),
						Arrays.asList(CraftTweakerPlugin.toStack(catalyst), stack), 0, "condenser"),
				ProcessRecipeManager.condenserRecipes);
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack catalyst, IItemStack inputBlock)
	{
		if (!(CraftTweakerPlugin.toStack(inputBlock).getItem() instanceof ItemBlock))
		{
			CraftTweakerAPI.logError("Input block is not block. Did not add recipe.");
			return;
		}
		List<Object> inputs = new ArrayList<>();
		inputs.addAll(Arrays.asList(CraftTweakerPlugin.toStack(catalyst), CraftTweakerPlugin.toStack(inputBlock)));
		addRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)), inputs, 0, "condenser"),
				ProcessRecipeManager.condenserRecipes);
	}

	@ZenMethod
	public static void removeRecipe(IItemStack output)
	{
		removeRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)), Collections.emptyList(), 0,
				"condenser"), ProcessRecipeManager.condenserRecipes);
	}

}
