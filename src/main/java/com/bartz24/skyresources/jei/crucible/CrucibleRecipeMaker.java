package com.bartz24.skyresources.jei.crucible;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CrucibleRecipeMaker
{
	public static List<CrucibleRecipeJEI> getRecipes()
	{
		ArrayList<CrucibleRecipeJEI> recipes = new ArrayList<CrucibleRecipeJEI>();

		for (int i = 0; i < ModFluids.crystalFluidColors().length; i++)
		{
			CrucibleRecipeJEI addRecipe = new CrucibleRecipeJEI(
					new ItemStack(ModItems.metalCrystal, 1, i),
					new FluidStack(ModFluids.crystalFluids.get(i), ConfigOptions.crucibleCrystalAmount));
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
