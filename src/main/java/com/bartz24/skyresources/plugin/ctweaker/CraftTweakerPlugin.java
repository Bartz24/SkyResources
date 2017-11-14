package com.bartz24.skyresources.plugin.ctweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CraftTweakerPlugin implements IModPlugin
{
	public void preInit()
	{
		initRecipes();

	}

	public void init()
	{
		SkyResourcesGuide.addPage("minetweaker", "guide.skyresources.misc", new ItemStack(Blocks.CRAFTING_TABLE));
	}
	public static void initRecipes()
	{
		CraftTweakerAPI.registerClass(MTRockGrinderRecipe.class);
		CraftTweakerAPI.registerClass(MTCombustionRecipe.class);
		CraftTweakerAPI.registerClass(MTCauldronCleaningRecipe.class);
		CraftTweakerAPI.registerClass(MTInfusionRecipe.class);
		CraftTweakerAPI.registerClass(MTFreezerRecipe.class);
		CraftTweakerAPI.registerClass(MTCrucibleRecipe.class);
		CraftTweakerAPI.registerClass(MTCondenserRecipe.class);
		CraftTweakerAPI.registerClass(MTKnifeRecipe.class);
		CraftTweakerAPI.registerClass(MTWaterExtractorInsertRecipe.class);
		CraftTweakerAPI.registerClass(MTWaterExtractorExtractRecipe.class);
		CraftTweakerAPI.registerClass(MTCatalysts.class);
		CraftTweakerAPI.registerClass(MTFusionRecipe.class);
		CraftTweakerAPI.registerClass(MTHeatSources.class);
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}

	public static ItemStack toStack(IItemStack iStack)
	{
		return CraftTweakerMC.getItemStack(iStack);
	}

	public static ItemStack[] toStacks(IItemStack[] iStacks)
	{
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for (IItemStack is : iStacks)
		{
			stacks.add(toStack(is));
		}
		return stacks.toArray(new ItemStack[stacks.size()]);
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
				return CraftTweakerMC.getItemStack((IItemStack) iStack);
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
		return CraftTweakerMC.getLiquidStack(iStack);
	}	
}
