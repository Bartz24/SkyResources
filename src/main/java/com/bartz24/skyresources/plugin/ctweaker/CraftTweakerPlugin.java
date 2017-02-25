package com.bartz24.skyresources.plugin.ctweaker;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.base.guide.SkyResourcesGuide;
import com.bartz24.skyresources.plugin.IModPlugin;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IngredientStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CraftTweakerPlugin implements IModPlugin
{
	public void preInit()
	{

	}

	public void init()
	{
		initRecipes();
		SkyResourcesGuide.addPage("minetweaker", "guide.skyresources.misc", new ItemStack(Blocks.CRAFTING_TABLE));
	}
	public static void initRecipes()
	{
		MineTweakerAPI.registerClass(MTRockGrinderRecipe.class);
		MineTweakerAPI.registerClass(MTCombustionRecipe.class);
		MineTweakerAPI.registerClass(MTCauldronCleaningRecipe.class);
		MineTweakerAPI.registerClass(MTInfusionRecipe.class);
		MineTweakerAPI.registerClass(MTFreezerRecipe.class);
		MineTweakerAPI.registerClass(MTCrucibleRecipe.class);
		MineTweakerAPI.registerClass(MTWaterExtractorInsertRecipe.class);
		MineTweakerAPI.registerClass(MTWaterExtractorExtractRecipe.class);
		MineTweakerAPI.registerClass(MTHeatSources.class);
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}

	public static ItemStack toStack(IItemStack iStack)
	{
		return MineTweakerMC.getItemStack(iStack);
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
				return MineTweakerMC.getItemStack((IItemStack) iStack);
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
		return MineTweakerMC.getLiquidStack(iStack);
	}	
}
