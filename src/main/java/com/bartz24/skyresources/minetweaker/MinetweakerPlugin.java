package com.bartz24.skyresources.minetweaker;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getLiquidStack;

import java.util.ArrayList;
import java.util.List;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IngredientStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class MinetweakerPlugin
{
	public static void postInit(FMLPostInitializationEvent event)
	{
		MineTweakerAPI.registerClass(MTCombustionRecipe.class);
		MineTweakerAPI.registerClass(MTConcentratorRecipe.class);
		MineTweakerAPI.registerClass(MTRockGrinderRecipe.class);
		MineTweakerAPI.registerClass(MTInfusionRecipe.class);
		MineTweakerAPI.registerClass(MTFreezerRecipe.class);
		MineTweakerAPI.registerClass(MTHeatSources.class);
		MineTweakerAPI.registerClass(MTCrucibleRecipe.class);
		MineTweakerAPI.registerClass(MTWaterExtractorRecipeInsert.class);
		MineTweakerAPI.registerClass(MTWaterExtractorRecipeExtract.class);
	}

	public static ItemStack toStack(IItemStack iStack)
	{
		return getItemStack(iStack);
	}

	public static ItemStack[] toStacks(IItemStack[] iStacks)
	{
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for (IItemStack is : iStacks)
		{
			stacks.add(toStack(is));
		}
		return (ItemStack[]) stacks.toArray();
	}

	public static Object toObject(IIngredient iStack)
	{
		if (iStack == null)
			return null;
		else
		{
			if (iStack instanceof IOreDictEntry)
				return ((IOreDictEntry) iStack).getName();
			else if (iStack instanceof IItemStack)
				return getItemStack((IItemStack) iStack);
			else if (iStack instanceof IngredientStack)
			{
				IIngredient ingr = ReflectionHelper.getPrivateValue(
						IngredientStack.class, (IngredientStack) iStack,
						"ingredient");
				return toObject(ingr);
			} else
				return null;
		}
	}

	public static Object[] toObjects(IIngredient[] iStacks)
	{
		List<Object> stacks = new ArrayList<Object>();
		for (IIngredient is : iStacks)
		{
			stacks.add(toObject(is));
		}
		return stacks.toArray();
	}

	public static FluidStack toFluidStack(ILiquidStack iStack)
	{
		return getLiquidStack(iStack);
	}
}
