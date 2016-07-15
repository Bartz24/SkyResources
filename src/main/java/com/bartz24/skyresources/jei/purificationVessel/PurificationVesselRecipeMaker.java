package com.bartz24.skyresources.jei.purificationVessel;

import java.util.ArrayList;
import java.util.List;

import com.bartz24.skyresources.config.ConfigOptions;
import com.bartz24.skyresources.registry.ModBlocks;
import com.bartz24.skyresources.registry.ModFluids;
import com.bartz24.skyresources.registry.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PurificationVesselRecipeMaker
{
	public static List<PurificationVesselRecipeJEI> getRecipes()
	{
		ArrayList<PurificationVesselRecipeJEI> recipes = new ArrayList<PurificationVesselRecipeJEI>();

		for (int i = 0; i < ModFluids.crystalFluids.size(); i++)
		{
			PurificationVesselRecipeJEI addRecipe = new PurificationVesselRecipeJEI(
					new FluidStack(ModFluids.dirtyCrystalFluids.get(i), 1000),
					new FluidStack(ModFluids.crystalFluids.get(i), 1000));
			recipes.add(addRecipe);
		}

		return recipes;
	}
}
